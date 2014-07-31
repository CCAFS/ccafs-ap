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
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Project;

import java.util.Date;
import java.util.List;

import org.cgiar.ccafs.ap.data.model.IPProgram;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProjectsListAction extends BaseAction {


  private static final long serialVersionUID = 2845677913596494699L;

  // Manager
  private ProjectManager projectManager;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ProjectsListAction.class);

  // Model for the back-end
  private List<Project> projects;

  // Model for the front-end
  private int projectID;

  @Inject
  public ProjectsListAction(APConfig config, ProjectManager projectManager) {
    super(config);
    this.projectManager = projectManager;
  }

  @Override
  public String execute() throws Exception {
    if (projects.size() <= 0) {
      // Create new project and redirect to project description using the new projectId assigned by the database.
      Project newProject = new Project(-1);
      newProject.setOwner(this.getCurrentUser());
      newProject.setCreated(new Date().getTime());
      projectID = projectManager.saveProjectDescription(newProject);
      if (projectID > 0) {
        // Let's redirect the user to the Project Description section.
        return BaseAction.INPUT;
      }
    }
    // An error happened, lets redirect it to the list, even if there are not projects.
    // TODO HT - Here we should show an error message.
    return BaseAction.SUCCESS;
  }

  public int getProjectID() {
    return projectID;
  }

  public List<Project> getProjects() {
    return projects;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    // Depending on the user that is logged-in, the list of projects will be displayed. - currentUser.

    // Getting project list.
    IPProgram userProgram = this.getCurrentUser().getCurrentInstitution().getProgram();
    projects = projectManager.getProjectsByProgram(userProgram.getId());

  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }
}
