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
 * ***************************************************************
 */
package org.cgiar.ccafs.ap.action.home;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This action will be in charge of managing all the P&R Dashboard after the user logins.
 * 
 * @author Héctor Fabio Tobón R.
 */
public class DashboardAction extends BaseAction {

  private static final long serialVersionUID = -8002068803922618439L;

  // Logging
  private static final Logger LOG = LoggerFactory.getLogger(DashboardAction.class);

  // Test Objects
  private List<Project> projects;
  private Project project;
  private Activity activity;

  // Manager
  private ProjectManager projectManager;

  @Inject
  public DashboardAction(APConfig config, ProjectManager projectManager) {
    super(config);
    this.projectManager = projectManager;
  }

  public Activity getActivity() {
    return activity;
  }


  public Project getProject() {
    return project;
  }


  public List<Project> getProjects() {
    return projects;
  }


  @Override
  public void prepare() throws Exception {
    if (this.getCurrentUser() != null) {

      // ----- Listing Projects -----
      if (securityContext.isAdmin()) {
        projects = projectManager.getAllProjectsBasicInfo(APConstants.REPORTING_SECTION);
      } else {


        List<Project> allProjects = projectManager.getAllProjectsBasicInfo(APConstants.REPORTING_SECTION);
        List<Integer> editableProjectsIds = projectManager.getProjectIdsEditables(this.getCurrentUser().getId());
        projects = new ArrayList<>();
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

    }
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public void setProject(Project project) {
    this.project = project;
  }


  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }


}
