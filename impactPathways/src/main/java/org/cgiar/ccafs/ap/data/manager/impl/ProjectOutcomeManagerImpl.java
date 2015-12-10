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

import org.cgiar.ccafs.ap.data.dao.ProjectOutcomeDAO;
import org.cgiar.ccafs.ap.data.manager.ProjectOutcomeManager;
import org.cgiar.ccafs.ap.data.model.ProjectOutcome;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Gallego B.
 * @author Hernán David Carvajal B.
 */
public class ProjectOutcomeManagerImpl implements ProjectOutcomeManager {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ProjectOutcomeManagerImpl.class);

  // DAO's
  private ProjectOutcomeDAO projectOutcomeDAO;

  @Inject
  public ProjectOutcomeManagerImpl(ProjectOutcomeDAO projectOutcomeDAO) {
    this.projectOutcomeDAO = projectOutcomeDAO;
  }

  @Override
  public ProjectOutcome getProjectOutcomeByYear(int projectID, int year) {
    Map<String, String> projectOutcomeData = projectOutcomeDAO.getProjectOutcomesByYear(projectID, year);

    if (!projectOutcomeData.isEmpty()) {
      ProjectOutcome projectOutcome = new ProjectOutcome();
      projectOutcome.setId(Integer.parseInt(projectOutcomeData.get("id")));
      projectOutcome.setYear(Integer.parseInt(projectOutcomeData.get("year")));
      projectOutcome.setStatement(projectOutcomeData.get("statement"));
      projectOutcome.setAnualProgress(projectOutcomeData.get("anual_progress"));
      return projectOutcome;
    }
    return null;
  }


  @Override
  public Map<String, ProjectOutcome> getProjectOutcomesByProject(int projectID) {
    Map<String, ProjectOutcome> projectOutcomes = new HashMap();
    List<Map<String, String>> projectOutcomeDataList = projectOutcomeDAO.getProjectOutcomesByProject(projectID);
    for (Map<String, String> projectOutcomeData : projectOutcomeDataList) {
      ProjectOutcome projectOutcome = new ProjectOutcome();
      projectOutcome.setId(Integer.parseInt(projectOutcomeData.get("id")));
      projectOutcome.setYear(Integer.parseInt(projectOutcomeData.get("year")));
      projectOutcome.setStatement(projectOutcomeData.get("statement"));
      projectOutcome.setAnualProgress(projectOutcomeData.get("anual_progress"));
      // adding information of the object to the array
      projectOutcomes.put(String.valueOf(projectOutcome.getYear()), projectOutcome);
    }
    return projectOutcomes;
  }

  @Override
  public boolean saveProjectOutcome(int projectID, ProjectOutcome projectOutcome, User user, String justification) {
    boolean allSaved = true;
    Map<String, Object> projectOutcomeData = new HashMap<>();
    if (projectOutcome.getId() > 0) {
      projectOutcomeData.put("id", projectOutcome.getId());
    }
    projectOutcomeData.put("year", projectOutcome.getYear());
    projectOutcomeData.put("statement", projectOutcome.getStatement());
    projectOutcomeData.put("anual_progress", projectOutcome.getAnualProgress());
    projectOutcomeData.put("user_id", user.getId());
    projectOutcomeData.put("modification_justification", justification);

    int result = projectOutcomeDAO.saveProjectOutcome(projectID, projectOutcomeData);

    if (result > 0) {
      LOG.debug("saveProjectOutcome > Project outcome added with id {}", result);
    } else if (result == 0) {
      LOG.debug("saveProjectOutcome > Project outcome with id={} was updated", projectOutcome.getId());
    } else {
      LOG.error("saveProjectOutcome > There was an error trying to save/update a Project outcome from projectId={}",
        projectID);
      allSaved = false;
    }

    return allSaved;

  }
}
