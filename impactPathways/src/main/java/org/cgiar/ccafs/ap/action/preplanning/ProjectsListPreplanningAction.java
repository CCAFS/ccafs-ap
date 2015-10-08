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
package org.cgiar.ccafs.ap.action.preplanning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProjectsListPreplanningAction extends BaseAction {


  private static final long serialVersionUID = 2845677913596494699L;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ProjectsListPreplanningAction.class);

  // Manager
  private ProjectManager projectManager;

  // Model for the back-end
  private List<Project> projects;
  private List<Project> allProjects;


  // Model for the front-end
  private int projectID;


  private double totalBudget;


  @Inject
  public ProjectsListPreplanningAction(APConfig config, ProjectManager projectManager) {
    super(config);
    this.projectManager = projectManager;
    this.totalBudget = 0;
  }

  @Override
  public String add() {
    // Create new project and redirect to project description using the new projectId assigned by the database.
    projectID = this.createNewProject();
    if (projectID > 0) {
      // Let's redirect the user to the Project Description section.
      return BaseAction.SUCCESS;
    }
    // Let's redirect the user to the error page.
    return BaseAction.ERROR;
  }


  private int createNewProject() {
    Project newProject = new Project(-1);
    newProject.setOwner(this.getCurrentUser());
    // IPProgram userProgram = this.getCurrentUser().getCurrentInstitution().getProgram();
    // if (userProgram != null) {
    // // Now the creator is a liaison isntitution not a ipProgram
    // // newProject.setProgramCreator(userProgram);
    // } else {
    // LOG
    // .error(
    // "-- execute() > the current user identify with id={} and institution_id={} does not belong to a specific
    // program!",
    // new Object[] {this.getCurrentUser().getId(), this.getCurrentUser().getCurrentInstitution().getId()});
    // }
    newProject.setCreated(new Date().getTime());
    return projectManager.saveProjectDescription(newProject, this.getCurrentUser(), this.getJustification());

  }


  public List<Project> getAllProjects() {
    return allProjects;
  }

  public int getProjectID() {
    return projectID;
  }

  // @Override
  // public String execute() throws Exception {
  // // If there are not projects to be listed.
  // if (projects.size() <= 0) {
  // // Create new project and redirect to project description using the new projectId assigned by the database.
  // projectID = this.createNewProject();
  // if (projectID > 0) {
  // // Let's redirect the user to the Project Description section.
  // return BaseAction.INPUT;
  // }
  // addActionError(getText("preplanning.projects.creatingProject.error"));
  // // An error happened, lets redirect it to the list, even if there are not projects.
  // }
  // // If user clicks on Add button.
  // if (add) {
  // return add();
  // }
  //
  // return BaseAction.SUCCESS;
  // }

  public List<Project> getProjects() {
    return projects;
  }

  public double getTotalBudget() {
    return totalBudget;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    // Depending on the user that is logged-in, the list of projects will be displayed. - currentUser.

    allProjects = projectManager.getAllProjectsBasicInfo();

    // Getting the list of projects that the user's program created and also those where the users is the project owner.
    List<Integer> projectIds = projectManager.getProjectIdsEditables(this.getCurrentUser().getId());

    projects = new ArrayList<>();
    for (Integer projectId : projectIds) {
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
