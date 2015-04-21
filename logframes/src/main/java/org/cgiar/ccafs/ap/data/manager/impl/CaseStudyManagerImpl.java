package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.CaseStudyCountriesDAO;
import org.cgiar.ccafs.ap.data.dao.CaseStudyDAO;
import org.cgiar.ccafs.ap.data.dao.CaseStudyTypeDAO;
import org.cgiar.ccafs.ap.data.manager.CaseStudyManager;
import org.cgiar.ccafs.ap.data.model.CaseStudy;
import org.cgiar.ccafs.ap.data.model.CaseStudyType;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CaseStudyManagerImpl implements CaseStudyManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(CaseStudyManagerImpl.class);
  private CaseStudyDAO caseStudyDAO;
  private CaseStudyCountriesDAO caseStudyCountriesDAO;
  private CaseStudyTypeDAO caseStudyTypeDAO;

  @Inject
  public CaseStudyManagerImpl(CaseStudyDAO caseStudyDAO, CaseStudyCountriesDAO caseStudyCountriesDAO,
    CaseStudyTypeDAO caseStudyTypeDAO) {
    this.caseStudyDAO = caseStudyDAO;
    this.caseStudyCountriesDAO = caseStudyCountriesDAO;
    this.caseStudyTypeDAO = caseStudyTypeDAO;
  }

  @Override
  public List<CaseStudy> getCaseStudyList(Leader leader, Logframe logframe) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    List<Map<String, String>> caseStudyDataList = caseStudyDAO.getCaseStudyList(leader.getId(), logframe.getId());
    Map<String, String> caseStudyData;
    List<CaseStudy> caseStudies = new ArrayList<>();

    for (int c = 0; c < caseStudyDataList.size(); c++) {
      caseStudyData = caseStudyDataList.get(c);

      CaseStudy temporalCaseStudy = new CaseStudy(Integer.parseInt(caseStudyData.get("id")));
      temporalCaseStudy.setTitle(caseStudyData.get("title"));
      temporalCaseStudy.setAuthor(caseStudyData.get("author"));
      try {
        // Parse from string to Date object
        if (caseStudyData.get("start_date") != null) {
          temporalCaseStudy.setStartDate(dateFormat.parse(caseStudyData.get("start_date")));
        }
      } catch (ParseException e) {
        String msg =
          "There was an error parsing start date '" + caseStudyData.get("start_date") + "' for the case study "
            + temporalCaseStudy.getId() + ".";
        LOG.error(msg, e);
      }
      try {
        // Parse from string to Date object
        if (caseStudyData.get("end_date") != null) {
          temporalCaseStudy.setEndDate(dateFormat.parse(caseStudyData.get("end_date")));
        }
      } catch (ParseException e) {
        String msg =
          "There was an error parsing end date '" + caseStudyData.get("start_date") + "' for the case study "
            + temporalCaseStudy.getId() + ".";
        LOG.error(msg, e);
      }
      temporalCaseStudy.setImageFileName(caseStudyData.get("photo"));
      temporalCaseStudy.setObjectives(caseStudyData.get("objectives"));
      temporalCaseStudy.setDescription(caseStudyData.get("description"));
      temporalCaseStudy.setResults(caseStudyData.get("results"));
      temporalCaseStudy.setPartners(caseStudyData.get("partners"));
      temporalCaseStudy.setLinks(caseStudyData.get("links"));
      temporalCaseStudy.setKeywords(caseStudyData.get("keywords"));
      temporalCaseStudy.setSubject(caseStudyData.get("subject"));
      temporalCaseStudy.setContributor(caseStudyData.get("contributor"));
      temporalCaseStudy.setPublisher(caseStudyData.get("publisher"));
      temporalCaseStudy.setRelation(caseStudyData.get("relation"));
      temporalCaseStudy.setCoverage(caseStudyData.get("coverage"));
      temporalCaseStudy.setRights(caseStudyData.get("rights"));
      temporalCaseStudy.setGlobal(Integer.parseInt(caseStudyData.get("is_global")) == 1);
      temporalCaseStudy.setLeader(leader);

      // Add the object to the list
      caseStudies.add(temporalCaseStudy);
    }

    return caseStudies;
  }

  @Override
  public List<CaseStudy>
    getCaseStudyListForSummary(int activityLeaderId, int year, String countriesIds, String typesIds) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    List<Map<String, String>> caseStudyDataList =
      caseStudyDAO.getCaseStudyListForSummary(activityLeaderId, year, countriesIds, typesIds);
    Map<String, String> caseStudyData;
    List<CaseStudy> caseStudies = new ArrayList<>();

    for (int c = 0; c < caseStudyDataList.size(); c++) {
      caseStudyData = caseStudyDataList.get(c);

      CaseStudy temporalCaseStudy = new CaseStudy(Integer.parseInt(caseStudyData.get("id")));
      temporalCaseStudy.setTitle(caseStudyData.get("title"));

      // Temporal activity leader
      Leader caseStudyLeader = new Leader(-1);
      caseStudyLeader.setAcronym(caseStudyData.get("activity_leader_acronym"));
      temporalCaseStudy.setLeader(caseStudyLeader);

      // Temporal case study type
      CaseStudyType cst = new CaseStudyType(-1);
      cst.setName(caseStudyData.get("type"));
      List<CaseStudyType> types = new ArrayList<>();
      types.add(cst);
      temporalCaseStudy.setTypes(types);

      // Countries
      List<Country> countries = new ArrayList<>();
      Country country = new Country("-1", caseStudyData.get("Countries"));
      countries.add(country);
      temporalCaseStudy.setCountries(countries);

      // Add the object to the list
      caseStudies.add(temporalCaseStudy);
    }

    return caseStudies;
  }

  @Override
  public boolean removeAllCaseStudies(int activityLeaderId, int logframeId) {
    boolean deleted = caseStudyDAO.removeAllCaseStudies(activityLeaderId, logframeId);
    return deleted;
  }

  @Override
  public boolean saveCaseStudy(CaseStudy caseStudy, int activityLeaderId, int logframeId) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Map<String, Object> csData;
    csData = new HashMap<>();
    if (caseStudy.getId() != -1) {
      csData.put("id", caseStudy.getId());
    } else {
      csData.put("id", null);
    }
    csData.put("title", caseStudy.getTitle());
    csData.put("author", caseStudy.getAuthor());
    // Convert the date to string
    if (caseStudy.getStartDate() != null) {
      csData.put("start_date", sdf.format(caseStudy.getStartDate()));
    } else {
      csData.put("start_date", null);
    }

    if (caseStudy.getEndDate() != null) {
      csData.put("end_date", sdf.format(caseStudy.getEndDate()));
    } else {
      csData.put("end_date", null);
    }
    csData.put("photo", caseStudy.getImageFileName());
    csData.put("objectives", caseStudy.getObjectives());
    csData.put("description", caseStudy.getDescription());
    csData.put("results", caseStudy.getResults());
    csData.put("partners", caseStudy.getPartners());
    if (caseStudy.getLinks().isEmpty()) {
      csData.put("links", null);
    } else {
      csData.put("links", caseStudy.getLinks());
    }
    csData.put("keywords", caseStudy.getKeywords());
    csData.put("subject", caseStudy.getSubject());
    csData.put("contributor", caseStudy.getContributor());
    csData.put("publisher", caseStudy.getPublisher());
    csData.put("relation", caseStudy.getRelation());
    csData.put("coverage", caseStudy.getCoverage());
    csData.put("rights", caseStudy.getRights());
    csData.put("activity_leader_id", activityLeaderId);
    csData.put("logframe_id", logframeId);
    csData.put("is_global", caseStudy.isGlobal());

    int caseStudyId = caseStudyDAO.saveCaseStudy(csData);

    // If the case study id is not equal to -1, the database return zero as identifier
    // after the record insertion, so, set caseStudyId with its original value
    caseStudyId = (caseStudyId < 1) ? caseStudy.getId() : caseStudyId;

    // if the case study was successfully saved, save the countries related and the case study types.
    if (caseStudyId >= 0) {
      // Save the countries
      if (!caseStudy.isGlobal()) {
        if (caseStudy.getCountries() != null) {
          if (caseStudy.getCountriesIds().size() > 0) {
            ArrayList<String> countriesIds = (ArrayList<String>) caseStudy.getCountriesIds();
            boolean caseStudyCountriesAdded = caseStudyCountriesDAO.saveCaseStudyCountries(caseStudyId, countriesIds);
            if (!caseStudyCountriesAdded) {
              return false;
            }
          }
        }
      }

      // Save the types
      boolean problemSavingTypes = false;

      if (caseStudy.getTypesIds().size() > 0) {
        ArrayList<String> typesIds = (ArrayList<String>) caseStudy.getTypesIds();
        int[] typesIdsArray = new int[typesIds.size()];
        for (int c = 0; c < typesIds.size(); c++) {
          typesIdsArray[c] = Integer.parseInt(typesIds.get(c));
        }
        problemSavingTypes = caseStudyTypeDAO.saveCaseStudyTypes(caseStudyId, typesIdsArray);
      }

      if (problemSavingTypes) {
        LOG.warn("There was a problem saving a new case study");
        return false;
      }
    }

    LOG.info("New case study was saved successfully");
    return true;
  }
}
