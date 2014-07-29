package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.dao.ProjectDAO;
import org.cgiar.ccafs.ap.data.dao.ProjectFocusesDAO;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
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
  public List<Project> getAllProjects(int programId) {
    List<Map<String, String>> projectDataList = projectDAO.getProjects(programId);
    List<Project> projectsList = new ArrayList<>();
    DateFormat dateformatter = new SimpleDateFormat(APConstants.DATE_FORMAT);

    for (Map<String, String> elementData : projectDataList) {

      Project project = new Project();
      project.setId(Integer.parseInt(elementData.get("id")));
      project.setTitle(elementData.get("title"));
      project.setSummary(elementData.get("summary"));
      // Format to the Dates of the project
      String sDate = elementData.get("start_date");
      String eDate = elementData.get("end_date");
      try {
        Date startDate = dateformatter.parse(sDate);
        Date endDate = dateformatter.parse(eDate);
        project.setStartDate(startDate);
        project.setEndDate(endDate);
      } catch (ParseException e) {
        e.printStackTrace();
      }


      projectsList.add(project);
    }
    return projectsList;
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
      Project project = new Project();
      project.setId(Integer.parseInt(projectData.get("id")));
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
        e.printStackTrace();
      }
      project.setOwner(userManager.getUser(Integer.parseInt(projectData.get("project_owner_user_id"))));
      project.getOwner().setCurrentInstitution(
        institutionManager.getInstitution(Integer.parseInt(projectData.get("project_owner_institution_id"))));
      // traer el project_leader

      return project;
    }
    return null;
  }

  /*
   * private List<Project> setDataToProjectObjects(List<Map<String, String>> projectDataList) {
   * return projectsList;
   * }
   */

  @Override
  public List<Project> getProjectFocuses(int projectID, int typeID) {
    List<Map<String, String>> projectFocusesDataList = projectFocusesDAO.getProjectFocuses(projectID, typeID);
    List<Project> projectsList = new ArrayList<>();


    for (Map<String, String> projectFocusesData : projectFocusesDataList) {

      Project project = new Project();
      project.setId(Integer.parseInt(projectFocusesData.get("id")));
      project.setTitle(projectFocusesData.get("title"));
      project.setSummary(projectFocusesData.get("summary"));
      Region region = new Region();
      region.setId(Integer.parseInt(projectFocusesData.get("region_id")));
      region.setName(projectFocusesData.get("region_name"));
      region.setCode(projectFocusesData.get("region_code"));


      projectsList.add(project);
    }
    return projectsList;
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


}
