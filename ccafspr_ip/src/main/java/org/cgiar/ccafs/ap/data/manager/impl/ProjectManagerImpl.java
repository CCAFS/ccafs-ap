package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ProjectDAO;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Employee;
import org.cgiar.ccafs.ap.data.model.IPProgramTypes;
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
  public List<Project> getProject(Object object) {
	// TODO - Pending to validate what kind of parameter should be used in this method.
	Employee projectLeader = (Employee) object;
    List<Map<String, String>> projectDataList = projectDAO.getProject(projectLeader.getId());
    List<Project> projectsList = new ArrayList<>();

    for (Map<String, String> elementData : projectDataList) {
      Project project = new Project();
      project.setId(Integer.parseInt(elementData.get("id")));
      project.setTitle(elementData.get("title"));

      List<Map<String, String>> projectTypesData = projectDAO.getProjectType(projectLeader.getId(), 1);
      for (Map<String, String> typeData : projectTypesData) {
        IPProgramTypes programType = new IPProgramTypes();
        programType.setId(Integer.parseInt(elementData.get("id")));
        programType.setAcronym(elementData.get("acronym"));
        programType.setTypeId(Integer.parseInt(elementData.get("type_id")));
      }

      projectsList.add(project);
    }
    return projectsList;
  }
  /*
   * private List<Project> setDataToProjectObjects(List<Map<String, String>> projectDataList) {
   * return projectsList;
   * }
   */


  @Override
  public List<Project> getAllProjects() {
	// TODO Auto-generated method stub
	return new ArrayList<Project>();
  }


}
