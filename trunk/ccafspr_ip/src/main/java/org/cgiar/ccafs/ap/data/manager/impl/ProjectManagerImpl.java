package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ProjectDAO;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cgiar.ccafs.ap.data.manager.InstitutionManager;

import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.inject.Inject;


public class ProjectManagerImpl implements ProjectManager {

  // DAOs
  private ProjectDAO projectDAO;

  // Managers
  private UserManager userManager;
  private InstitutionManager institutionManager;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ProjectManagerImpl.class);

  @Inject
  public ProjectManagerImpl(ProjectDAO projectDAO, UserManager userManager, InstitutionManager institutionManager) {
    this.projectDAO = projectDAO;
    this.userManager = userManager;
    this.institutionManager = institutionManager;
  }


  @Override
  public List<Project> getAllProjects(int programId) {
    List<Map<String, String>> projectDataList = projectDAO.getProjects(programId);
    List<Project> projectsList = new ArrayList<>();

    for (Map<String, String> elementData : projectDataList) {
      Project project = new Project();
      project.setId(Integer.parseInt(elementData.get("id")));
      project.setTitle(elementData.get("title"));
      project.setSummary(elementData.get("summary"));
      project.setStartDate(elementData.get("startDate"));
      project.setEndDate(elementData.get("endDate"));

      projectsList.add(project);
    }
    return projectsList;
  }


  @Override
  public Project getProject(int projectId) {

    Map<String, String> projectData = projectDAO.getProject(projectId);
    if (!projectData.isEmpty()) {
      Project project = new Project();
      project.setId(Integer.parseInt(projectData.get("id")));
      project.setTitle(projectData.get("title"));
      project.setSummary(projectData.get("summary"));
      project.setStartDate(projectData.get("start_date"));
      project.setEndDate(projectData.get("end_date"));
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


}
