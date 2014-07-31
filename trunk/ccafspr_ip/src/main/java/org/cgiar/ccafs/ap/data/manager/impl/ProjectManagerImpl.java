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
import org.cgiar.ccafs.ap.data.dao.ProjectDAO;
import org.cgiar.ccafs.ap.data.dao.ProjectFocusesDAO;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.Region;
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


public class ProjectManagerImpl implements ProjectManager {

  // DAOs
  private ProjectDAO projectDAO;
  private ProjectFocusesDAO projectFocusesDAO;

  // Managers
  private UserManager userManager;
  private InstitutionManager institutionManager;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ProjectManagerImpl.class);

  @Inject
  public ProjectManagerImpl(ProjectDAO projectDAO, ProjectFocusesDAO projectFocusesDAO, UserManager userManager,
    InstitutionManager institutionManager) {
    this.projectDAO = projectDAO;
    this.projectFocusesDAO = projectFocusesDAO;
    this.userManager = userManager;
    this.institutionManager = institutionManager;
  }

  @Override
  public User getExpectedProjectLeader(int projectId) {
    Map<String, String> pData = projectDAO.getExpectedProjectLeader(projectId);
    if (!pData.isEmpty()) {
      User projectLeader = new User();
      projectLeader.setId(Integer.parseInt(pData.get("id")));
      projectLeader.setFirstName(pData.get("contact_name"));
      projectLeader.setEmail(pData.get("contact_email"));
      // Getting Project leader institution and saving it in currentInstitution.
      projectLeader.setCurrentInstitution(institutionManager.getInstitution(Integer.parseInt(pData
        .get("institution_id"))));

      return projectLeader;
    }
    return null;
  }

  @Override
  public Project getProject(int projectId) {
    DateFormat dateformatter = new SimpleDateFormat(APConstants.DATE_FORMAT);
    Map<String, String> projectData = projectDAO.getProject(projectId);
    if (!projectData.isEmpty()) {
      Project project = new Project(Integer.parseInt(projectData.get("id")));
      project.setTitle(projectData.get("title"));
      project.setSummary(projectData.get("summary"));
      // Format to the Dates of the project
      String sDate = projectData.get("start_date");
      String eDate = projectData.get("end_date");
      try {
        Date startDate = dateformatter.parse(sDate);
        Date endDate = dateformatter.parse(eDate);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
      } catch (ParseException e) {
        LOG.error("There was an error formatting the dates", e);
      }
      project.setOwner(userManager.getUser(Integer.parseInt(projectData.get("project_owner_user_id"))));
      project.getOwner().setCurrentInstitution(
        institutionManager.getInstitution(Integer.parseInt(projectData.get("project_owner_institution_id"))));
      project.setCreated(Long.parseLong(projectData.get("created")));
      // traer el project_leader

      return project;
    }
    return null;
  }


  @Override
  public List<IPProgram> getProjectFocuses(int projectID, int typeID) {
    List<Map<String, String>> projectFocusesDataList = projectFocusesDAO.getProjectFocuses(projectID, typeID);
    List<IPProgram> projectFocusesList = new ArrayList<>();

    for (Map<String, String> projectFocusesData : projectFocusesDataList) {
      IPProgram ipProgram = new IPProgram();
      ipProgram.setId(Integer.parseInt(projectFocusesData.get("program_id")));
      ipProgram.setName(projectFocusesData.get("program_name"));
      ipProgram.setAcronym(projectFocusesData.get("program_acronym"));
      // Region
      if (projectFocusesData.get("region_id") != null) {
        Region region = new Region();
        region.setId(Integer.parseInt(projectFocusesData.get("region_id")));
        region.setName(projectFocusesData.get("region_name"));
        region.setCode(projectFocusesData.get("region_code"));
        ipProgram.setRegion(region);
      }

      projectFocusesList.add(ipProgram);
    }
    return projectFocusesList;
  }

  @Override
  public User getProjectLeader(int projectId) {
    Map<String, String> pData = projectDAO.getProjectLeader(projectId);
    if (!pData.isEmpty()) {
      User projectLeader = new User();
      projectLeader.setId(Integer.parseInt(pData.get("id")));
      projectLeader.setUsername((pData.get("username")));
      projectLeader.setFirstName(pData.get("firstName"));
      projectLeader.setLastName(pData.get("lastName"));
      projectLeader.setEmail(pData.get("email"));
      // Getting Project leader institution and saving it in currentInstitution.
      projectLeader.setCurrentInstitution(institutionManager.getInstitution(Integer.parseInt(pData
        .get("institution_id"))));

      return projectLeader;
    }
    return null;
  }

  @Override
  public List<Project> getProjectsByProgram(int programId) {
    List<Map<String, String>> projectDataList = projectDAO.getProjectsByProgram(programId);
    List<Project> projectsList = new ArrayList<>();
    DateFormat dateformatter = new SimpleDateFormat(APConstants.DATE_FORMAT);

    for (Map<String, String> elementData : projectDataList) {

      Project project = new Project(Integer.parseInt(elementData.get("id")));
      project.setTitle(elementData.get("title"));
      project.setSummary(elementData.get("summary"));
      // Format to the Dates of the project
      String sDate = elementData.get("start_date");
      String eDate = elementData.get("end_date");
      if (sDate != null && eDate != null) {
        try {
          Date startDate = dateformatter.parse(sDate);
          Date endDate = dateformatter.parse(eDate);
          project.setStartDate(startDate);
          project.setEndDate(endDate);
        } catch (ParseException e) {
          LOG.error("There was an error formatting the dates", e);
        }
      }
      project.setCreated(Long.parseLong(elementData.get("created")));
      projectsList.add(project);
    }
    return projectsList;
  }

  @Override
  public boolean saveExpectedProjectLeader(int projectId, User expectedLeader) {
    boolean saved = true;
    Map<String, Object> expectedProjectLeaderData = new HashMap<>();

    if (expectedLeader.getId() > 0) {
      expectedProjectLeaderData.put("id", expectedLeader.getId());
    }
    // First Name is used for the Contact Name.
    expectedProjectLeaderData.put("contact_name", expectedLeader.getFirstName());
    expectedProjectLeaderData.put("contact_email", expectedLeader.getEmail());
    // Current institution is used for project leader institution.
    expectedProjectLeaderData.put("institution_id", expectedLeader.getCurrentInstitution().getId());

    int result = projectDAO.saveExpectedProjectLeader(projectId, expectedProjectLeaderData);
    if (result == -1) {
      saved = false;
    } else if (result == 0) {
      LOG.debug("saveExpectedProjectLeader > Expected project leader with id={} was updated", expectedLeader.getId());
    } else {
      LOG.debug("saveExpectedProjectLeader > Expected project leader with id={} was added", result);
    }

    return saved;
  }

  @Override
  public int saveProjectDescription(Project project) {
    Map<String, Object> projectData = new HashMap<>();
    if (project.getId() == -1) {
      // This is a new project. we need to add it to the database.
      // Getting the employee id.
      int ownerId = userManager.getEmployeeID(project.getOwner());
      projectData.put("project_owner_id", ownerId);
    } else {
      // Update project
      // TODO HT - To Complete.
    }

    int result = projectDAO.saveProject(projectData);
    return result;
  }


}
