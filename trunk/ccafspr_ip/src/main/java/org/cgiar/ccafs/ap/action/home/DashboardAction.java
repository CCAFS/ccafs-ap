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
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Project;

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
  private List<Activity> activities;
  private Project project;
  private Activity activity;


  // Manager
  private ProjectManager projectManager;
  private ActivityManager activityManager;


  @Inject
  public DashboardAction(APConfig config, ProjectManager projectManager, ActivityManager activityManager) {
    super(config);
    this.activityManager = activityManager;
    this.projectManager = projectManager;
  }


  @Override
  public String execute() throws Exception {
    return SUCCESS;
  }


  public List<Activity> getActivities() {
    return activities;
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
    super.prepare();

    if (this.getCurrentUser() != null) {

      // ----- Listing Projects -----

      // If user is admin.
      if (this.getCurrentUser().isAdmin()) {
        // Show all projects.
        projects = projectManager.getAllProjects();
      } else if (this.getCurrentUser().isFPL() || this.getCurrentUser().isFPL() || this.getCurrentUser().isCU()) {
        // Getting the list of projects that belongs to the User program or where they are assigned as PO.
        projects = new ArrayList<>();
        List<Integer> ids = projectManager.getProjectIdsEditables(this.getCurrentUser());
        for (Integer projectId : ids) {
          projects.add(projectManager.getProject(projectId));
        }

        // In addition, add the projects where they are assigned as Project Leader.
        List<Integer> idsPL = projectManager.getPLProjectIds(this.getCurrentUser());
        for (Integer projectId : idsPL) {
          // Do not add projects that are alraedy added.
          if (!ids.contains(idsPL)) {
            projects.add(projectManager.getProject(projectId));
          }
        }

      } else if (this.getCurrentUser().isPL()) {
        List<Integer> ids = projectManager.getPLProjectIds(this.getCurrentUser());
        // TODO
      }
    }
    if (projects != null) {
      activities = activityManager.getActivitiesByProject(projects.get(0).getId());
    }
// System.out.println("PREPARE: ");
// System.out.println("CURRENT USER" + this.getCurrentUser());
  }

  public void setActivities(List<Activity> activities) {
    this.activities = activities;
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
