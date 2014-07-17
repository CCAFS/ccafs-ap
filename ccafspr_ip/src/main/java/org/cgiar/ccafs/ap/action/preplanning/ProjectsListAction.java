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
import org.cgiar.ccafs.ap.data.model.Employee;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProjectsListAction extends BaseAction {


  private static final long serialVersionUID = 2845677913596494699L;

  // Manager
  private ProjectManager projectManager;

  private static Logger LOG = LoggerFactory.getLogger(ProjectsListAction.class);

  // Model
  private List<Project> projects;

  @Inject
  public ProjectsListAction(APConfig config, ProjectManager projectManager) {
    super(config);
    this.projectManager = projectManager;
  }


  public List<Project> getProjects() {
    return projects;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    // Depending on the user that is logged-in, the list of projects will be displayed.
    User fakeUser = new User();
    fakeUser.setId(100);
    fakeUser.setEmail("user@email.org");
    fakeUser.setRole("RPL");
    Employee projectLeader = new Employee(1, new User());


    // Getting project list.
    // projects = projectManager.getAllProjects();
    projects = projectManager.getProject(projectLeader);

    System.out.println(projects);

  }
}
