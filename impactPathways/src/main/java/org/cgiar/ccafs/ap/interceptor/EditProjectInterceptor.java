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

package org.cgiar.ccafs.ap.interceptor;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.Submission;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.security.SecurityContext;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.ServletActionContext;


/**
 * This interceptor verify if the user has the permissions to edit the project
 * 
 * @author Hernán David Carvajal
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class EditProjectInterceptor extends AbstractInterceptor {

  private static final long serialVersionUID = -2202897612842611068L;

  private SecurityContext securityContext;
  private ProjectManager projectManager;

  @Inject
  public EditProjectInterceptor(SecurityContext securityContext, ProjectManager projectManager) {
    this.securityContext = securityContext;
    this.projectManager = projectManager;
  }

  public static void setPermissionParameters(ActionInvocation invocation, SecurityContext securityContext,
    ProjectManager projectManager) {
    BaseAction baseAction = (BaseAction) invocation.getAction();
    Map<String, Object> parameters = invocation.getInvocationContext().getParameters();
    String actionName = ServletActionContext.getActionMapping().getName();

    Map<String, Object> session = invocation.getInvocationContext().getSession();
    User user = (User) session.get(APConstants.SESSION_USER);

    boolean canEditProject = false, hasPermissionToEdit = false;

    if (!actionName.equals("projectsList") && !actionName.equals("addNewCoreProject")
      && !actionName.equals("addNewBilateralProject") && !actionName.equals("addCoFundedProject")) {
      // First, check if the user can edit the project
      String projectParameter = ((String[]) parameters.get(APConstants.PROJECT_REQUEST_ID))[0];
      int projectID = Integer.parseInt(projectParameter);
      Project project = projectManager.getProjectBasicInfo(projectID);
      Submission submission = project.isSubmitted(baseAction.getCurrentPlanningYear(), baseAction.getCycleName());

      // If user is admin, it should have privileges to edit all projects.
      if (securityContext.isAdmin()) {
        canEditProject = true;
      } else {
        // Get the identifiers of the projects that the user can edit and validate if that list contains the projectID.
        List<Integer> projectsEditable = projectManager.getProjectIdsEditables(user.getId());
        // Projects wont be able to edit the project if the project has been already submitted.
        if ((projectsEditable.contains(new Integer(projectID)) && securityContext.canEditProjectPlanningSection(
          actionName, projectID))) {
          if (submission == null) {
            canEditProject = true;
          }
        }
      }

      // canEditProject = (securityContext.isAdmin()) ? true
      // : (projectsEditable.contains(new Integer(projectID)) && (submission == null));

      boolean editParameter = false;

      // Validate if the project is new. If so, the interface will be displayed as editable always.
      if (project.isNew(baseAction.getConfig().getCurrentPlanningStartDate())) {
        editParameter = true;
      } else {
        if (parameters.get(APConstants.EDITABLE_REQUEST) != null) {
          String stringEditable = ((String[]) parameters.get(APConstants.EDITABLE_REQUEST))[0];
          editParameter = stringEditable.equals("true");

          // If the user is not asking for edition privileges we don't need to validate them.
          if (!editParameter) {
            baseAction.setEditableParameter(hasPermissionToEdit);
          }
        }
      }

      // Check the permission if user want to edit or save the form
      if (editParameter || parameters.get("save") != null) {
        hasPermissionToEdit =
          (securityContext.isAdmin()) ? true : securityContext.canEditProjectPlanningSection(actionName, projectID);
      }
    }

    // Set the variable that indicates if the user can edit the section
    baseAction.setEditableParameter(hasPermissionToEdit && canEditProject);
    baseAction.setCanEdit(canEditProject);
  }

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    setPermissionParameters(invocation, securityContext, projectManager);
    return invocation.invoke();
  }
}
