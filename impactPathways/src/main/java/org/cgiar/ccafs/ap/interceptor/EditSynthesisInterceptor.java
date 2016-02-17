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
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.security.SecurityContext;

import java.util.Map;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;


/**
 * This interceptor verify if the user has the permissions to edit the project
 * 
 * @author Sebastian Amariles Garcia - CIAT/CCAFS
 */

public class EditSynthesisInterceptor extends AbstractInterceptor {

  private static final long serialVersionUID = -2202897612842611068L;

  public static void setPermissionParameters(ActionInvocation invocation, SecurityContext securityContext,
    ProjectManager projectManager) {
    BaseAction baseAction = (BaseAction) invocation.getAction();
    Map<String, Object> parameters = invocation.getInvocationContext().getParameters();
    // String actionName = ServletActionContext.getActionMapping().getName();

    Map<String, Object> session = invocation.getInvocationContext().getSession();
    User user = (User) session.get(APConstants.SESSION_USER);

    boolean canEditProject = false, hasPermissionToEdit = false;

    // First, check if the user can edit the project
    int liaisonInstitutionID;
    try {
      String liaisonInstitutionParameter = ((String[]) parameters.get(APConstants.LIAISON_INSTITUTION_REQUEST_ID))[0];
      liaisonInstitutionID = Integer.parseInt(liaisonInstitutionParameter);

    } catch (NumberFormatException e) {
      if (user.getLiaisonInstitution() != null) {
        liaisonInstitutionID = user.getLiaisonInstitution().getId();
      } else {
        liaisonInstitutionID = 1;
      }
    }


    // If user is admin, it should have privileges to edit all projects.
    if (securityContext.isAdmin()) {
      canEditProject = true;
    } else {
      // Synteshis wont be able to edit the project if the project has been already submitted.
      if (baseAction.hasSynthesisPermission("update", liaisonInstitutionID)) {
        canEditProject = true;
      }
    }

    // canEditProject = (securityContext.isAdmin()) ? true
    // : (projectsEditable.contains(new Integer(projectID)) && (submission == null));

    boolean editParameter = false;

    if (parameters.get(APConstants.EDITABLE_REQUEST) != null) {
      String stringEditable = ((String[]) parameters.get(APConstants.EDITABLE_REQUEST))[0];
      editParameter = stringEditable.equals("true");

      // If the user is not asking for edition privileges we don't need to validate them.
      if (!editParameter) {
        baseAction.setEditableParameter(hasPermissionToEdit);
      }
    }

    // Check the permission if user want to edit or save the form
    if (editParameter || parameters.get("save") != null) {
      hasPermissionToEdit =
        (securityContext.isAdmin()) ? true : baseAction.hasSynthesisPermission("update", liaisonInstitutionID);
    }

    // Set the variable that indicates if the user can edit the section
    baseAction.setEditableParameter(hasPermissionToEdit && canEditProject);
    baseAction.setCanEdit(canEditProject);
  }

  private SecurityContext securityContext;

  private ProjectManager projectManager;

  @Inject
  public EditSynthesisInterceptor(SecurityContext securityContext) {
    this.securityContext = securityContext;
  }

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    setPermissionParameters(invocation, securityContext, projectManager);
    return invocation.invoke();
  }
}
