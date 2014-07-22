package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ProjectDAO;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class ProjectManagerImpl implements ProjectManager {

  // DAOs
  private ProjectDAO projectDAO;

  @Inject
  public ProjectManagerImpl(ProjectDAO projectDAO) {
    this.projectDAO = projectDAO;
  }


  @Override
  public List<Project> getAllProjects(Project projectId) {
    // TODO - Pending to validate what kind of parameter should be used in this method.
    System.out.println("----- Project ID: " + projectId.getId());
    List<Map<String, String>> projectDataList = projectDAO.getProjects(projectId.getId());
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
    // TODO - Pending to validate what kind of parameter should be used in this method.
    System.out.println("----- Project ID: " + projectId);

    Map<String, String> projectData = projectDAO.getProject(projectId);
    if (!projectData.isEmpty()) {
      Project project = new Project();
      project.setId(Integer.parseInt(projectData.get("id")));
      project.setTitle(projectData.get("title"));
      project.setSummary(projectData.get("summary"));
      project.setStartDate(projectData.get("start_date"));
      project.setEndDate(projectData.get("end_date"));
      // traer el project_leader y project_owner

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
