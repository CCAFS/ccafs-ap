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
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.security.SecurityContext;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This interceptor will validate if the user who is trying to edit a specific project is able to: Fully edit, partially
 * edit or just read the project.
 * 
 * @author Héctor Fabio Tobón R.
 */
public class GrantProjectPlanningAccessInterceptor extends AbstractInterceptor {

  private static final long serialVersionUID = 3416451095136457226L;
  private static final Logger LOG = LoggerFactory.getLogger(GrantProjectPlanningAccessInterceptor.class);

  @Inject
  protected SecurityContext securityContext;

  // Managers
  ProjectManager projectManager;

  @Inject
  public GrantProjectPlanningAccessInterceptor(ProjectManager projectManager) {
    this.projectManager = projectManager;
  }

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    LOG.debug("=> GrantProjectPlanningAccessInterceptor");
    Map<String, Object> session = invocation.getInvocationContext().getSession();
    Map<String, Object> parameters = invocation.getInvocationContext().getParameters();
    // User session is validated in the RequireUserInterceptor.
    User user = (User) session.get(APConstants.SESSION_USER);
    BaseAction baseAction = (BaseAction) invocation.getAction();

    String actionName = ServletActionContext.getActionMapping().getName();
    if (!actionName.equals("projectsList")) {
      // Project parameter is validated in the ValidateProjectParameterInterceptor.
      String projectParameter = ((String[]) parameters.get(APConstants.PROJECT_REQUEST_ID))[0];
      int projectID = Integer.parseInt(projectParameter);
      // Listing all projects that the user is able to edit.
      // Getting project list that belongs to the program that you belongs to.
      if (securityContext.isAdmin()) {
        // Admins are able to see all fields editable and save any information.
        baseAction.setFullEditable(true);
        baseAction.setSaveable(true);
      } else if (securityContext.isFPL() || securityContext.isRPL() || securityContext.isCU()) {
        // If the user is a FPL or RPL, let's figure out if he/she can have the enough privileges to edit the
        // project.
        List<Integer> idsAllowedToEdit = projectManager.getProjectIdsEditables(user);
        if (idsAllowedToEdit.contains(new Integer(projectID))) {
          baseAction.setFullEditable(true);
          baseAction.setSaveable(true);
        } else {
          // User will see the the fields enable but without any save/delete button.
          baseAction.setFullEditable(true);
          baseAction.setSaveable(false);
        }
      } else if (securityContext.isPL()) {
        User projectLeader = projectManager.getProjectLeader(projectID);
        if (projectLeader != null) {
          // If the user is the project leader, he is able to save and partially edit.
          if (projectLeader.getId() == user.getId()) {
            baseAction.setFullEditable(false);
            baseAction.setSaveable(true);
          } else {
            // If the user is not the project leader. Thus, he is able to see but not to edit.
            baseAction.setFullEditable(true);
            baseAction.setSaveable(false);
          }
        } else {
          // If the project doesn't have project leader associated.
          baseAction.setFullEditable(true);
          baseAction.setSaveable(false);
        }
      } else {
        // User is AL or Guest.
        baseAction.setFullEditable(true);
        baseAction.setSaveable(false);
      }
    } else {
      // if the user is in the projects list section.
      // If user is Admin, FPL or RPL, he/she will be able to add new projects.
      if (securityContext.isAdmin() || securityContext.isFPL() || securityContext.isRPL()) {
        baseAction.setFullEditable(true);
        baseAction.setSaveable(true);
      } else {
        // If the user is a PL, AL or Guest, he/she won't be able to add new projects.
        baseAction.setFullEditable(true);
        baseAction.setSaveable(false);
      }
    }
    return invocation.invoke();
  }


}
