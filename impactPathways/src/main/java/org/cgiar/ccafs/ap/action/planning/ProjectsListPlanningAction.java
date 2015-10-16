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
package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.LiaisonInstitution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProjectsListPlanningAction extends BaseAction {


  private static final long serialVersionUID = 2845677913596494699L;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ProjectsListPlanningAction.class);

  // Manager
  private ProjectManager projectManager;

  // Model for the back-end
  private List<Project> projects;
  private List<Project> allProjects;


  // Model for the front-end
  private int projectID;
  private double totalBudget;
  private LinkedHashMap<Integer, Boolean> projectStatuses;

  @Inject
  public ProjectsListPlanningAction(APConfig config, ProjectManager projectManager) {
    super(config);
    this.projectManager = projectManager;
    this.totalBudget = 0;
  }

  public String addBilateralProject() {
    if (!securityContext.canAddBilateralProject()) {
      return NOT_AUTHORIZED;
    }

    // Create new project and redirect to project description using the new projectId assigned by the database.
    projectID = this.createNewProject(false);
    return (projectID > 0) ? SUCCESS : ERROR;
  }

  public String addCoreProject() {
    if (!securityContext.canAddCoreProject()) {
      return NOT_AUTHORIZED;
    }

    // Create new project and redirect to project description using the new projectId assigned by the database.
    projectID = this.createNewProject(true);
    return (projectID > 0) ? SUCCESS : ERROR;
  }

  /**
   * This method validates if a project can be deleted or not.
   * Keep in mind that a project can be deleted if it was created in the current planning cycle.
   * 
   * @param projectID is the project identifier.
   * @return true if the project can be deleted, false otherwise.
   */
  public boolean canDelete(int projectID) {
    // First, loop all projects that the user is able to edit.
    for (Project project : this.getProjects()) {
      if (project.getId() == projectID) {
        if (project.isNew(this.config.getCurrentPlanningStartDate())) {
          return true;
        }
      }
    }

    // If nothing returned yet, we need to loop the second list which is the list of projects that the user is not able
    // to edit.
    for (Project project : this.getAllProjects()) {
      if (project.getId() == projectID) {
        if (project.isNew(this.config.getCurrentPlanningStartDate())) {
          return true;
        }
      }
    }
    // If nothing found, return false.
    return false;
  }

  private int createNewProject(boolean isCoreProject) {
    Project newProject = new Project(-1);

    if (isCoreProject) {
      newProject.setType(APConstants.PROJECT_CORE);
    } else {
      newProject.setType(APConstants.PROJECT_BILATERAL);
    }

    newProject.setOwner(this.getCurrentUser());
    LiaisonInstitution liaisonInstitution = this.getCurrentUser().getLiaisonInstitution();
    if (liaisonInstitution != null) {
      newProject.setLiaisonInstitution(liaisonInstitution);
    } else {
      LOG.error("-- execute() > the user identified with id={} and is not linked to any liaison institution!",
        this.getCurrentUser().getId());
      return -1;
    }

    newProject.setCreated(new Date().getTime());
    return projectManager.saveProjectDescription(newProject, this.getCurrentUser(), this.getJustification());
  }

  @Override
  public String delete() {
    // Deleting project.
    if (this.canDelete(projectID)) {
      boolean deleted = projectManager.deleteProject(projectID, this.getCurrentUser(),
        this.getJustification() == null ? "Project deleted" : this.getJustification());
      if (deleted) {
        this.addActionMessage(
          this.getText("deleting.success", new String[] {this.getText("planning.project").toLowerCase()}));
      } else {
        this.addActionError(
          this.getText("deleting.problem", new String[] {this.getText("planning.project").toLowerCase()}));
      }
    } else {
      this.addActionError(this.getText("planning.projects.cannotDelete"));
    }
    return SUCCESS;
  }

  public List<Project> getAllProjects() {
    return allProjects;
  }

  /**
   * TODO HT
   * @param projectID
   * @return
   */
  public boolean getCompleteProject(int projectID) {
    if(projects.contains(new Project(projectID))) {
      return projectStatuses.get(projectID);      
    }
    return false;
  }

  public Date getCurrentPlanningStartDate() {
    return config.getCurrentPlanningStartDate();
  }

  public String getEditParameter() {
    return APConstants.EDITABLE_REQUEST;
  }

  public int getProjectID() {
    return projectID;
  }

  public String getProjectRequest() {
    return APConstants.PROJECT_REQUEST_ID;
  }

  public List<Project> getProjects() {
    return projects;
  }

  public double getTotalBudget() {
    return totalBudget;
  }

  @Override
  public void prepare() throws Exception {
    projects = new ArrayList<>();

    if (securityContext.isAdmin()) {
      projects = projectManager.getAllProjectsBasicInfo();
      allProjects = new ArrayList<>();
    } else {
      allProjects = projectManager.getAllProjectsBasicInfo();
      List<Integer> editableProjectsIds = projectManager.getProjectIdsEditables(this.getCurrentUser().getId());

      // We should remove from the allProjects list the project
      // led by the user and also we should add them to a another list
      for (Integer projectId : editableProjectsIds) {
        Project temp = new Project(projectId);
        int index = allProjects.indexOf(temp);
        if (index != -1) {
          projects.add(allProjects.remove(index));
        }
      }
    }

    // Validating if projects are complete or not.
    projectStatuses = new LinkedHashMap<>();
    for (Project project : projects) {
      this.initializeProjectSectionStatuses(project, "Planning");
      projectStatuses.put(project.getId(), this.isComplete());
    }

    this.cleanSectionStatuses();
  }

  public void setAllProjects(List<Project> allProjects) {
    this.allProjects = allProjects;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

  public void setTotalBudget(double totalBudget) {
    this.totalBudget = totalBudget;
  }
}
