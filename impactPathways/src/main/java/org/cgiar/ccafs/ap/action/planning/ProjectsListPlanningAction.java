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
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProjectsListPlanningAction extends BaseAction {


  private static final long serialVersionUID = 2845677913596494699L;

  // Manager
  private ProjectManager projectManager;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ProjectsListPlanningAction.class);

  // Model for the back-end
  private List<Project> projects;
  private List<Project> allProjects;


  // Model for the front-end
  private int projectID;
  private double totalBudget;

  @Inject
  public ProjectsListPlanningAction(APConfig config, ProjectManager projectManager) {
    super(config);
    this.projectManager = projectManager;
    this.totalBudget = 0;
  }

  @Override
  public String add() {

    if (!isUserAuthorizedToCreateProjects()) {
      return NOT_AUTHORIZED;
    }

    // Create new project and redirect to project description using the new projectId assigned by the database.
    projectID = this.createNewProject();
    if (projectID > 0) {
      // Let's redirect the user to the Project Description section.
      return SUCCESS;
    }
    // Let's redirect the user to the Project Description section.
    return ERROR;
  }

  private int createNewProject() {
    Project newProject = new Project(-1);
    newProject.setOwner(this.getCurrentUser());
    IPProgram userProgram = this.getCurrentUser().getCurrentInstitution().getProgram();
    if (userProgram != null) {
      newProject.setProgramCreator(userProgram);
    } else {
      LOG
        .error(
          "-- execute() > the current user identify with id={} and institution_id={} does not belong to a specific program!",
          new Object[] {this.getCurrentUser().getId(), this.getCurrentUser().getCurrentInstitution().getId()});
    }
    newProject.setCreated(new Date().getTime());
    return projectManager.saveProjectDescription(newProject);
  }

  public List<Project> getAllProjects() {
    return allProjects;
  }

  public Date getCurrentPlanningStartDate() {
    return config.getCurrentPlanningStartDate();
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

  private boolean isUserAuthorizedToCreateProjects() {
    return getCurrentUser().isAdmin() || getCurrentUser().isCU() || getCurrentUser().isFPL()
      || getCurrentUser().isRPL();
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    getCurrentPlanningStartDate();
    projects = new ArrayList<>();
    allProjects = projectManager.getAllProjectsBasicInfo();

    // Depending on the user that is logged-in, the list of projects will be displayed. - currentUser.
    // If the user belongs to a specific CCAFS Program.
    List<Integer> projectIds = null;
    if (this.getCurrentUser().getCurrentInstitution().getProgram() != null) {
      // Getting the list of project ids that the user's program created, or those where the user is the project owner.
      projectIds = projectManager.getProjectIdsEditables(this.getCurrentUser());

      for (Integer projectId : projectIds) {
        Project temp = new Project(projectId);
        int index = allProjects.indexOf(temp);
        if (index != -1) {
          projects.add(allProjects.remove(index));
        }
      }
    }

    // Getting the list of project ids that the user is assigned as Project Leader.
    List<Integer> projectIdsAsPL = projectManager.getPLProjectIds(this.getCurrentUser());


    // We should remove from the allProjects list the project
    // led by the user and also we should add them to a another list
    for (Integer projectId : projectIdsAsPL) {
      Project temp = new Project(projectId);
      int index = allProjects.indexOf(temp);
      if (index != -1) {
        projects.add(allProjects.remove(index));
      }
    }

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
