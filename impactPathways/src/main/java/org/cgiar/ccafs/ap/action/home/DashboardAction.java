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
    if (this.getCurrentUser() != null) {

      // ----- Listing Projects -----

      // If user is an Admin.
      if (this.getCurrentUser().isAdmin()) {
        // Show all projects.
        projects = projectManager.getAllProjectsBasicInfo();
      } else if (this.getCurrentUser().isFPL() || this.getCurrentUser().isRPL() || this.getCurrentUser().isCU()) {
        // Getting the list of projects that belongs to the User program or where he is assigned as PO.
        projects = new ArrayList<>();
        List<Integer> ids = projectManager.getProjectIdsEditables(this.getCurrentUser());
        for (Integer projectId : ids) {
          projects.add(projectManager.getProjectBasicInfo(projectId));
        }

        // In addition, add the projects where the user is assigned as Project Leader.
        List<Integer> idsPL = projectManager.getPLProjectIds(this.getCurrentUser());
        for (Integer projectId : idsPL) {
          // Do not add projects that are already added.
          if (!ids.contains(projectId)) {
            projects.add(projectManager.getProjectBasicInfo(projectId));
          }
        }

      } else if (this.getCurrentUser().isPL()) {
        // Getting projects where the user is assigned as PL.
        List<Integer> idsPL = projectManager.getPLProjectIds(this.getCurrentUser());
        projects = new ArrayList<>();
        for (Integer projectId : idsPL) {
          // Do not add projects that are already added.
          projects.add(projectManager.getProjectBasicInfo(projectId));
        }
      }
      // If user is AL or Guest, he/she won't be able to see any project listed.

      // ----- Listing Activities -----

      // If user is an Admin
      if (this.getCurrentUser().isAdmin()) {
        // Admins will be able to see all the activities entered in the system.
        activities = activityManager.getAllActivities();
      } else if (this.getCurrentUser().isFPL() || this.getCurrentUser().isRPL() || this.getCurrentUser().isCU()) {
        // FPLs, RPLs and CUs users can edit activities that belongs to the projects that they are able to edit.
        activities = new ArrayList<>();
        for (Project project : projects) {
          activities.addAll(activityManager.getActivitiesByProject(project.getId()));
        }

      } else if (this.getCurrentUser().isPL()) {
        // PLs can edit all the activities that belong to their projects.
        activities = new ArrayList<>();
        for (Project project : projects) {
          activities.addAll(activityManager.getActivitiesByProject(project.getId()));
        }

        // Then, adding all the activities where the user was assigned as Activity Leader.
        List<Integer> ledIds = activityManager.getLedActivityIds(this.getCurrentUser());
        for (Integer activityId : ledIds) {
          Activity activity = activityManager.getActivityById(activityId);
          if (!activities.contains(activity)) {
            activities.add(activityManager.getActivityById(activityId));
          }
        }
      } else if (this.getCurrentUser().isAL()) {
        List<Integer> ledIds = activityManager.getLedActivityIds(this.getCurrentUser());
        activities = new ArrayList<>();
        for (Integer activityId : ledIds) {
          activities.add(activityManager.getActivityById(activityId));
        }
      }
    }
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
