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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Gallego B.
 */
public class ProjectOutcomeManagerImpl implements ProjectOutcomeManager {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ProjectOutcomeManagerImpl.class);

  // DAO's
  private ProjectOutcomeDAO projectOutcomeDAO;

  // Managers
  private ProjectOutcomeManager projectOutcomeManager;


  @Inject
  public ProjectOutcomeManagerImpl(ProjectOutcomeDAO projectOutcomeDAO, ProjectOutcomeManager projectOutcomeManager) {
    this.projectOutcomeDAO = projectOutcomeDAO;
    this.projectOutcomeManager = projectOutcomeManager;
  }

  @Override
  public boolean deleteProjectOutcomeById(int projectOutcomeID) {
    return projectOutcomeDAO.deleteProjectOutcomeById(projectOutcomeID);
  }

  @Override
  public boolean deleteProjectOutcomesByProject(int projectID) {
    return projectOutcomeDAO.deleteProjectOutcomesByProject(projectID);
  }

  @Override
  public List<ProjectOutcome> getProjectOutcomesByProject(int projectID) {
    List<ProjectOutcome> projectOutcomes = new ArrayList<>();
    List<Map<String, String>> projectOutcomeDataList = projectOutcomeDAO.getProjectOutcomesByProject(projectID);
    for (Map<String, String> projectOutcomeData : projectOutcomeDataList) {
      ProjectOutcome projectOutcome = new ProjectOutcome();
      projectOutcome.setId(Integer.parseInt(projectOutcomeData.get("id")));
      projectOutcome.setYear(Integer.parseInt(projectOutcomeData.get("year")));
      projectOutcome.setStatement(projectOutcomeData.get("statement"));
      projectOutcome.setStories(projectOutcomeData.get("stories"));


      // adding information of the object to the array
      projectOutcomes.add(projectOutcome);
    }
    return projectOutcomes;
  }


  @Override
  public List<ProjectOutcome> getProjectOutcomesByYear(int projectID, int year) {
    List<ProjectOutcome> projectOutcomes = new ArrayList<>();
    List<Map<String, String>> projectOutcomeDataList = projectOutcomeDAO.getProjectOutcomesByYear(projectID, year);
    for (Map<String, String> projectOutcomeData : projectOutcomeDataList) {
      ProjectOutcome projectOutcome = new ProjectOutcome();
      projectOutcome.setId(Integer.parseInt(projectOutcomeData.get("id")));
      projectOutcome.setYear(Integer.parseInt(projectOutcomeData.get("year")));
      projectOutcome.setStatement(projectOutcomeData.get("statement"));
      projectOutcome.setStories(projectOutcomeData.get("stories"));

      // adding information of the object to the array
      projectOutcomes.add(projectOutcome);
    }
    return projectOutcomes;
  }

  @Override
  public boolean saveProjectOutcome(int projectID, ProjectOutcome projectOutcome) {
    boolean allSaved = true;
    Map<String, Object> projectOutcomeData = new HashMap<>();
    if (projectOutcome.getId() > 0) {
      projectOutcomeData.put("id", projectOutcome.getId());
    }
    projectOutcomeData.put("year", projectOutcome.getYear());
    projectOutcomeData.put("statement", projectOutcome.getStatement());
    projectOutcomeData.put("stories", projectOutcome.getStories());


    int result = projectOutcomeDAO.saveProjectOutcome(projectID, projectOutcomeData);

    if (result > 0) {
      LOG.debug("saveBudget > New Budget and Project Budget added with id {}", result);
    } else if (result == 0) {
      LOG.debug("saveBudget > Budget with id={} was updated", projectOutcome.getId());
    } else {
      LOG.error("saveBudget > There was an error trying to save/update a Budget from projectId={}", projectID);
      allSaved = false;
    }

    return allSaved;

  }
}
