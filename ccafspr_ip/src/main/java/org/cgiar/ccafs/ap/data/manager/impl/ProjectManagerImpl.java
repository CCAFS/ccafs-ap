package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.dao.ProjectDAO;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.Project;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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


}
