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
import org.cgiar.ccafs.ap.data.dao.ProjectHighlightDAO;
import org.cgiar.ccafs.ap.data.dao.mysqlhiberate.ProjectHightLihgtMySQLDAO;
import org.cgiar.ccafs.ap.data.manager.HighLightManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectHighlights;
import org.cgiar.ccafs.ap.data.model.ProjectHighligths;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

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
  private ProjectHighlightDAO highLightDAO;
  // Managers


  @Inject
  public HighLightManagerImpl() {
    highLightDAO = new ProjectHightLihgtMySQLDAO();


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
  public ProjectHighligths getHighLightById(int highLightID) {

    ProjectHighligths highLight = highLightDAO.find(highLightID);


    return highLight;


  }


  @Override
  public List<ProjectHighligths> getHighLightsByProject(int projectID) {
    List<ProjectHighligths> highLightList = highLightDAO.getHighLightsByProject(projectID);


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
  public int saveHighLight(int projectID, ProjectHighligths highLight, User user, String justification) {
    if (highLight.getId() == null) {
      highLight.setCreatedBy(Long.parseLong(user.getId() + ""));
    }
    highLight.setModifiedBy(Long.parseLong(user.getId() + ""));
    highLight.setModificationJustification(justification);
    int result = highLightDAO.save(highLight);

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
