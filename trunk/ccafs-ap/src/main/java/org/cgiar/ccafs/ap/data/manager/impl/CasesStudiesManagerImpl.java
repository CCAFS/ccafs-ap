package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.CasesStudiesCountriesDAO;
import org.cgiar.ccafs.ap.data.dao.CasesStudiesDAO;
import org.cgiar.ccafs.ap.data.manager.CasesStudiesManager;
import org.cgiar.ccafs.ap.data.model.CasesStudy;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class CasesStudiesManagerImpl implements CasesStudiesManager {

  private CasesStudiesDAO casesStudiesDAO;
  private CasesStudiesCountriesDAO casesStudiesCountriesDAO;

  @Inject
  public CasesStudiesManagerImpl(CasesStudiesDAO casesStudiesDAO, CasesStudiesCountriesDAO casesStudiesCountriesDAO) {
    this.casesStudiesDAO = casesStudiesDAO;
    this.casesStudiesCountriesDAO = casesStudiesCountriesDAO;
  }

  @Override
  public List<CasesStudy> getCasesStudiesList(Leader leader, Logframe logframe) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    List<Map<String, String>> casesStudiesDataList =
      casesStudiesDAO.getCasesStudiesList(leader.getId(), logframe.getId());
    Map<String, String> casesStudiesData;
    List<CasesStudy> casesStudies = new ArrayList<>();

    if (casesStudiesDataList == null) {
      return null;
    }

    for (int c = 0; c < casesStudiesDataList.size(); c++) {
      casesStudiesData = casesStudiesDataList.get(c);

      CasesStudy temporalCaseStudy = new CasesStudy(Integer.parseInt(casesStudiesData.get("id")));
      temporalCaseStudy.setTitle(casesStudiesData.get("title"));
      temporalCaseStudy.setAuthor(casesStudiesData.get("author"));
      try {
        // Parse from string to Date object
        temporalCaseStudy.setDate(dateFormat.parse(casesStudiesData.get("date")));
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      temporalCaseStudy.setPhotoFileName(casesStudiesData.get("photo"));
      temporalCaseStudy.setObjectives(casesStudiesData.get("objectives"));
      temporalCaseStudy.setDescription(casesStudiesData.get("description"));
      temporalCaseStudy.setResults(casesStudiesData.get("results"));
      temporalCaseStudy.setPartners(casesStudiesData.get("partners"));
      temporalCaseStudy.setLinks(casesStudiesData.get("links"));
      temporalCaseStudy.setKeywords(casesStudiesData.get("keywords"));

      // Add the object to the list
      casesStudies.add(temporalCaseStudy);
    }

    if (casesStudiesDataList.isEmpty()) {
      return null;
    }

    return casesStudies;
  }

  @Override
  public boolean removeAllStudyCases(int activityLeaderId, int logframeId) {
    boolean deleted = casesStudiesDAO.removeAllStudyCases(activityLeaderId, logframeId);
    return deleted;
  }

  @Override
  public boolean storeCaseStudies(CasesStudy caseStudies, int activityLeaderId, int logframeId) {
    boolean problem = false;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Map<String, Object> csData;
    csData = new HashMap<>();
    csData.put("id", caseStudies.getId());
    csData.put("title", caseStudies.getTitle());
    csData.put("author", caseStudies.getAuthor());
    // Convert the date to string
    csData.put("date", sdf.format(caseStudies.getDate()));
    csData.put("photo", caseStudies.getPhotoFileName());
    csData.put("objectives", caseStudies.getObjectives());
    csData.put("description", caseStudies.getDescription());
    csData.put("results", caseStudies.getResults());
    csData.put("partners", caseStudies.getPartners());
    csData.put("links", caseStudies.getLinks());
    csData.put("keywords", caseStudies.getKeywords());
    csData.put("activity_leader_id", activityLeaderId);
    csData.put("logframe_id", logframeId);

    int caseStudiesId = casesStudiesDAO.saveCaseStudies(csData);
    // if was successfully saved the case studies, save the countries related
    if (caseStudiesId >= 0) {
      ArrayList<String> countriesIds = (ArrayList<String>) caseStudies.getCountriesIds();
      boolean caseStudiesCountryAdded = casesStudiesCountriesDAO.saveCasesStudiesCountries(caseStudiesId, countriesIds);
      if (!caseStudiesCountryAdded) {
        return false;
      }
    }

    return true;
  }
}
