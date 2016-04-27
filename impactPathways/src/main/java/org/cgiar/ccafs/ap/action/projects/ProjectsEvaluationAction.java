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
package org.cgiar.ccafs.ap.action.projects;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.security.data.manager.UserRoleManagerImpl;
import org.cgiar.ccafs.security.data.model.UserRole;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProjectsEvaluationAction extends BaseAction {


  private static final long serialVersionUID = 2845677913596494699L;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ProjectsEvaluationAction.class);

  // Manager
  private ProjectManager projectManager;
  private UserRoleManagerImpl userRoleManager;
  // Model for the back-end
  private List<Project> projects;
  private List<Project> allProjects;


  @Inject
  public ProjectsEvaluationAction(APConfig config, ProjectManager projectManager, UserRoleManagerImpl userRoleManager) {
    super(config);
    this.projectManager = projectManager;
    this.userRoleManager = userRoleManager;
  }


  public List<Project> getAllProjects() {
    return allProjects;
  }


  public List<Project> getProjects() {
    return projects;
  }


  @Override
  public void prepare() throws Exception {
    projects = new ArrayList<>();
    String section = this.getCycleName();


    List<UserRole> roles = userRoleManager.getUserRolesByUserID(String.valueOf(this.getCurrentUser().getId()));
    for (UserRole userRole : roles) {

      projects = projectManager.getProjectEvaluationInfo(this.getCurrentReportingYear(), userRole.getId(),
        this.getCurrentUser().getId());
    }


  }

  public void setAllProjects(List<Project> allProjects) {
    this.allProjects = allProjects;
  }


}
