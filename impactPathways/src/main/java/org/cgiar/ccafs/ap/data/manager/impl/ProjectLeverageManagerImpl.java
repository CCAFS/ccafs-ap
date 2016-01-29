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


import org.cgiar.ccafs.ap.data.dao.ProjectLevarageDAO;
import org.cgiar.ccafs.ap.data.manager.ProjectLeverageManager;
import org.cgiar.ccafs.ap.data.model.ProjectLeverage;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.Date;
import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Christian Garcia
 */
public class ProjectLeverageManagerImpl implements ProjectLeverageManager {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ProjectLeverageManagerImpl.class);

  // DAO's
  private ProjectLevarageDAO projectLeveragesDAO;


  @Inject
  public ProjectLeverageManagerImpl(ProjectLevarageDAO projectLeveragesDAO) {
    this.projectLeveragesDAO = projectLeveragesDAO;
  }

  @Override
  public boolean deleteProjectLeverage(int leverageID, User user, String justification) {
    boolean problem = false;
    // Deleting leverage.
    boolean deleted = projectLeveragesDAO.deleteLeverage(leverageID, user.getId(), justification);
    if (!deleted) {
      problem = true;
    }


    return !problem;
  }


  @Override
  public boolean deleteProjectLeveragesByProject(int projectID) {
    return projectLeveragesDAO.deleteLeveragesByProject(projectID);
  }


  @Override
  public boolean existProjectLeverage(int leverageID) {
    return projectLeveragesDAO.existLeverage(leverageID);
  }

  @Override
  public ProjectLeverage getProjectLeverageById(int leverageID) {

    ProjectLeverage leverage = projectLeveragesDAO.find(leverageID);


    return leverage;


  }


  @Override
  public List<ProjectLeverage> getProjectLeverageProject(int projectID) {
    List<ProjectLeverage> leverageList = projectLeveragesDAO.getLeveragesByProject(projectID);


    return leverageList;
  }


  @Override
  public int saveProjectLeverage(int projectID, ProjectLeverage leverage, User user, String justification) {
    if (leverage.getId() == -1) {
      leverage.setCreatedBy(Long.parseLong(user.getId() + ""));
      leverage.setActiveSince(new Date());
      leverage.setId(null);
    }
    leverage.setIsActive(true);
    leverage.setModifiedBy(Long.parseLong(user.getId() + ""));
    leverage.setModificationJustification(justification);
    int result = projectLeveragesDAO.save(leverage);

    if (result > 0) {
      LOG.debug("saveProjectLeverage > New ProjectLeverage added with id {}", result);
    } else if (result == 0) {
      LOG.debug("saveProjectLeverage > ProjectLeverage with id={} was updated", leverage.getId());
    } else {
      LOG.error("saveProjectLeverage > There was an error trying to save/update a ProjectLeverage from projectId={}",
        projectID);
    }
    return result;
  }


}
