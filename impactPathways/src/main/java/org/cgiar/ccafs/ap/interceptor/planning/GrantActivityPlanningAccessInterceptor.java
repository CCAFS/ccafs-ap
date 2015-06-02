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
package org.cgiar.ccafs.ap.interceptor.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.security.SecurityContext;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This interceptor will validate if the user who is trying to edit a specific activity is able to: Edit it or just read
 * it.
 * 
 * @author Héctor Fabio Tobón R.
 */
public class GrantActivityPlanningAccessInterceptor extends AbstractInterceptor {

  private static final long serialVersionUID = 715145701059810847L;
  private static final Logger LOG = LoggerFactory.getLogger(GrantActivityPlanningAccessInterceptor.class);

  @Inject
  protected SecurityContext securityContext;

  // Managers
  ActivityManager activityManager;
  ProjectManager projectManager;

  @Inject
  public GrantActivityPlanningAccessInterceptor(ActivityManager activityManager, ProjectManager projectManager) {
    this.activityManager = activityManager;
    this.projectManager = projectManager;
  }

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    LOG.debug("=> GrantActivityPlanningAccessInterceptor");
    Map<String, Object> session = invocation.getInvocationContext().getSession();
    // String actionName = ServletActionContext.getActionMapping().getName();
    Map<String, Object> parameters = invocation.getInvocationContext().getParameters();
    // Project parameter is validated in the ValidateProjectParameterInterceptor.
    String activityParameter = ((String[]) parameters.get(APConstants.ACTIVITY_REQUEST_ID))[0];
    int activityID = Integer.parseInt(activityParameter);
    // User session is validated in the RequireUserInterceptor.
    User user = (User) session.get(APConstants.SESSION_USER);
    BaseAction baseAction = (BaseAction) invocation.getAction();
    // Listing all activities that the user is able to edit.
    // And getting activity list that belongs to the program that you belongs to.
    if (securityContext.isAdmin()) {
      // Admins are able to see all fields editable and save any information.
      baseAction.setFullEditable(true);
      baseAction.setSaveable(true);
    } else if (securityContext.isFPL() || securityContext.isRPL() || securityContext.isCU()) {
      // If the user is a FPL, RPL or CU, let's figure out if he/she can have the enough privileges to edit the
      // activity.
      List<Integer> idsAllowedToEdit = activityManager.getActivityIdsEditable(user);
      if (idsAllowedToEdit.contains(new Integer(activityID))) {
        baseAction.setFullEditable(true);
        baseAction.setSaveable(true);
      } else {
        // let's figure out if the user is the leader of the project in which the activity belongs to.
        Project project = projectManager.getProjectFromActivityId(activityID);
        if (project != null) {
          User projectLeader = projectManager.getProjectLeader(project.getId());
          if (projectLeader != null) {
            // If the user is the project leader, he is able to fully edit the activity.
            if (projectLeader.getId() == user.getId()) {
              baseAction.setFullEditable(true);
              baseAction.setSaveable(true);
            } else {
              // If the user is not the project leader.
              // Let's check if the user is the Activity Leader of the current activity
              User activityLeader = activityManager.getActivityLeader(activityID);
              // If user is assigned as activity leader of the current activity.
              if (activityLeader != null && user.getEmployeeId() == activityLeader.getEmployeeId()) {
                baseAction.setFullEditable(true);
                baseAction.setSaveable(true);
              } else {
                // If user is not the activity leader of the current activity, he/she is not able to edit it.
                baseAction.setFullEditable(true);
                baseAction.setSaveable(false);
              }
            }
          } else {
            // If the project doesn't have project leader associated the PL can not edit it.
            baseAction.setFullEditable(true);
            baseAction.setSaveable(false);
          }
        } else {
          // If the activity does not belong to a project, no user is able to edit it.
          baseAction.setFullEditable(true);
          baseAction.setSaveable(false);
        }
      }
    } else if (securityContext.isPL()) {
      // If user is a PL, let's figure out if the user is the leader of the project in which the activity belongs to.
      Project project = projectManager.getProjectFromActivityId(activityID);
      if (project != null) {
        User projectLeader = projectManager.getProjectLeader(project.getId());
        if (projectLeader != null) {
          // If the user is the project leader, he is able to fully edit the activity.
          if (projectLeader.getId() == user.getId()) {
            baseAction.setFullEditable(true);
            baseAction.setSaveable(true);
          } else {
            // If the user is not the project leader.
            // Let's check if the user is the Activity Leader of the current activity
            User activityLeader = activityManager.getActivityLeader(activityID);
            // If user is assigned as activity leader of the current activity.
            if (activityLeader != null && user.getEmployeeId() == activityLeader.getEmployeeId()) {
              baseAction.setFullEditable(true);
              baseAction.setSaveable(true);
            } else {
              // If user is not the activity leader of the current activity, he/she is not able to edit it.
              baseAction.setFullEditable(true);
              baseAction.setSaveable(false);
            }
          }
        } else {
          // If the project doesn't have project leader associated the PL can not edit it.
          baseAction.setFullEditable(true);
          baseAction.setSaveable(false);
        }
      } else {
        // If the activity does not belong to a project, no user is able to edit it.
        baseAction.setFullEditable(true);
        baseAction.setSaveable(false);
      }
    } else if (securityContext.isAL()) {
      // User is AL or Guest.
      User activityLeader = activityManager.getActivityLeader(activityID);
      // If activity leader is still as expected.
      if (activityLeader != null) {
        // If user is assigned as activity leader of the current activity.
        if (user.getEmployeeId() == activityLeader.getEmployeeId()) {
          baseAction.setFullEditable(true);
          baseAction.setSaveable(true);
        } else {
          // If user is not the activity leader of the current activity, he/she is not able to edit it.
          baseAction.setFullEditable(true);
          baseAction.setSaveable(false);
        }
      } else {
        // Activity has its leader marked as expected.
        baseAction.setFullEditable(true);
        baseAction.setSaveable(false);
      }
    } else {
      // User is Guest.
      baseAction.setFullEditable(true);
      baseAction.setSaveable(false);
    }

    return invocation.invoke();
  }

}
