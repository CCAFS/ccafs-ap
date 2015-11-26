/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/
package org.cgiar.ccafs.ap.data.manager.impl;


import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.dao.HighLightDAO;
import org.cgiar.ccafs.ap.data.manager.HighLightManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectHighlights;
import org.cgiar.ccafs.ap.data.model.ProjectHighlightsType;
import org.cgiar.ccafs.ap.data.model.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Christian Garcia
 */
public class HighLightManagerImpl implements HighLightManager {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(HighLightManagerImpl.class);

  // DAO's
  private HighLightDAO highLightDAO;
  // Managers


  @Inject
  public HighLightManagerImpl(HighLightDAO highLightDAO) {
    this.highLightDAO = highLightDAO;


  }

  @Override
  public boolean deleteHighLight(int highLightID, User user, String justification) {
    boolean problem = false;
    // Deleting highLight.
    boolean deleted = highLightDAO.deleteHighLight(highLightID, user.getId(), justification);
    if (!deleted) {
      problem = true;
    }


    return !problem;
  }


  @Override
  public boolean deleteHighLightsByProject(int projectID) {
    return highLightDAO.deleteHighLightsByProject(projectID);
  }


  @Override
  public boolean existHighLight(int highLightID) {
    return highLightDAO.existHighLight(highLightID);
  }

  @Override
  public ProjectHighlights getHighLightById(int highLightID) {
    DateFormat dateformatter = new SimpleDateFormat(APConstants.DATE_FORMAT);
    Map<String, String> highLightData = highLightDAO.getHighLightById(highLightID);
    if (!highLightData.isEmpty()) {
      ProjectHighlights highLight = new ProjectHighlights();
      // Basic information
      highLight.setId(highLightID);
      highLight.setTitle(highLightData.get("title"));
      highLight.setAuthor(highLightData.get("author"));
      highLight.setContributor(highLightData.get("contributor"));
      highLight.setCountry(highLightData.get("country"));
      highLight.setCoverage(highLightData.get("coverage"));
      highLight.setDescription(highLightData.get("description"));

      if (highLightData.get("end_date") != null) {
        try {
          Date endDate = dateformatter.parse(highLightData.get("end_date"));
          highLight.setEndDate(endDate);
        } catch (ParseException e) {
          LOG.error("There was an error formatting the end date", e);
        }
      }

      if (highLightData.get("start_date") != null) {
        try {
          Date endDate = dateformatter.parse(highLightData.get("start_date"));
          highLight.setEndDate(endDate);
        } catch (ParseException e) {
          LOG.error("There was an error formatting the end date", e);
        }
      }

      highLight.setGlobal(Boolean.valueOf(highLightData.get("is_global")));
      highLight.setImage(highLightData.get("photo"));
      highLight.setKeywords(highLightData.get("keywords"));
      highLight.setLeader(highLightData.get("leader"));
      highLight.setLinks(highLightData.get("links"));
      highLight.setObjectives(highLightData.get("objectives"));
      highLight.setPartners(highLightData.get("partners"));
      highLight.setProject_id(Integer.parseInt(highLightData.get("project_id")));
      highLight.setPublisher(highLightData.get("publisher"));
      highLight.setRights(highLightData.get("rights"));
      highLight.setRelation(highLightData.get("relation"));
      highLight.setResults(highLightData.get("results"));
      highLight.setSubject(highLightData.get("subject"));


      highLight.setType(ProjectHighlightsType.valueOf((Integer.parseInt(highLightData.get("type_id"))) + ""));

      return highLight;
    }
    return null;

  }


  @Override
  public List<ProjectHighlights> getHighLightsByProject(int projectID) {
    List<ProjectHighlights> highLightList = new ArrayList<>();
    List<Map<String, String>> highLightDataList = highLightDAO.getHighLightsByProject(projectID);
    DateFormat dateformatter = new SimpleDateFormat(APConstants.DATE_FORMAT);

    for (Map<String, String> highLightData : highLightDataList) {
      ProjectHighlights highLight = new ProjectHighlights();
      // HighLight basic information
      highLight.setId(Integer.parseInt(highLightData.get("id")));
      highLight.setTitle(highLightData.get("title"));
      highLight.setAuthor(highLightData.get("author"));
      highLight.setContributor(highLightData.get("contributor"));
      highLight.setCountry(highLightData.get("country"));
      highLight.setCoverage(highLightData.get("coverage"));
      highLight.setDescription(highLightData.get("description"));

      if (highLightData.get("end_date") != null) {
        try {
          Date endDate = dateformatter.parse(highLightData.get("end_date"));
          highLight.setEndDate(endDate);
        } catch (ParseException e) {
          LOG.error("There was an error formatting the end date", e);
        }
      }

      if (highLightData.get("start_date") != null) {
        try {
          Date endDate = dateformatter.parse(highLightData.get("start_date"));
          highLight.setEndDate(endDate);
        } catch (ParseException e) {
          LOG.error("There was an error formatting the end date", e);
        }
      }

      highLight.setGlobal(Boolean.valueOf(highLightData.get("is_global")));
      highLight.setImage(highLightData.get("photo"));
      highLight.setKeywords(highLightData.get("keywords"));
      highLight.setLeader(highLightData.get("leader"));
      highLight.setLinks(highLightData.get("links"));
      highLight.setObjectives(highLightData.get("objectives"));
      highLight.setPartners(highLightData.get("partners"));
      highLight.setProject_id(Integer.parseInt(highLightData.get("project_id")));
      highLight.setPublisher(highLightData.get("publisher"));
      highLight.setRights(highLightData.get("rights"));
      highLight.setRelation(highLightData.get("relation"));
      highLight.setResults(highLightData.get("results"));
      highLight.setSubject(highLightData.get("subject"));


      highLight.setType(ProjectHighlightsType.valueOf((Integer.parseInt(highLightData.get("type_id"))) + ""));

      highLightList.add(highLight);
    }
    return highLightList;
  }


  @Override
  public String getStandardIdentifier(Project project, ProjectHighlights highLight, boolean useComposedCodification) {
    StringBuilder result = new StringBuilder();
    if (useComposedCodification) {
      result.append(APConstants.CCAFS_ORGANIZATION_IDENTIFIER);
      result.append("-P");
      result.append(project.getId());
      result.append("-D");
      result.append(highLight.getId());
    } else {
      result.append("P");
      result.append(project.getId());
      result.append("-D");
      result.append(highLight.getId());
    }
    return result.toString();
  }

  @Override
  public int saveHighLight(int projectID, ProjectHighlights highLight, User user, String justification) {
    Map<String, Object> highLightData = new HashMap<>();
    if (highLight.getId() != -1) {
      highLightData.put("id", highLight.getId());
    } else {
      highLightData.put("id", null);
      highLightData.put("created_by", user.getId());
    }
    highLightData.put("project_id", projectID);
    highLightData.put("title", highLight.getTitle());
    highLightData.put("type", highLight.getType() != null ? highLight.getType().getId() : null);


    highLightData.put("author", highLight.getAuthor());
    SimpleDateFormat format = new SimpleDateFormat(APConstants.DATE_FORMAT);
    highLightData.put("start_date", format.format(highLight.getStartDate()));
    highLightData.put("end_date", format.format(highLight.getEndDate()));
    highLightData.put("photo", highLight.getImage());
    highLightData.put("objectives", highLight.getObjectives());
    highLightData.put("description", highLight.getDescription());
    highLightData.put("results", highLight.getResults());
    highLightData.put("partners", highLight.getPartners());
    highLightData.put("links", highLight.getLinks());
    highLightData.put("keywords", highLight.getKeywords());
    highLightData.put("subject", highLight.getSubject());
    highLightData.put("contributor", highLight.getContributor());
    highLightData.put("publisher", highLight.getPublisher());
    highLightData.put("relation", highLight.getRelation());
    highLightData.put("coverage", highLight.getCoverage());
    highLightData.put("rights", highLight.getRights());
    highLightData.put("is_global", highLight.isGlobal());
    highLightData.put("leader", highLight.getLeader());


    highLightData.put("modified_by", user.getId());
    highLightData.put("modification_justification", justification);

    int result = highLightDAO.saveHighLight(highLightData);

    if (result > 0) {
      LOG.debug("saveHighLight > New HighLight added with id {}", result);
    } else if (result == 0) {
      LOG.debug("saveHighLight > HighLight with id={} was updated", highLight.getId());
    } else {
      LOG.error("saveHighLight > There was an error trying to save/update a HighLight from projectId={}", projectID);
    }
    return result;
  }


}
