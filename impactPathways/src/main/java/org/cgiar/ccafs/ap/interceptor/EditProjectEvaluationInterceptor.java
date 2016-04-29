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

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.ServletActionContext;

/**
 * This interceptor verify if the user has the permissions to evaluate this project.
 * 
 * @author Hermes Jimenez - CIAT/CCAFS
 */
public class EditProjectEvaluationInterceptor extends AbstractInterceptor implements Serializable {


  /**
   * Automatic serial version
   */
  private static final long serialVersionUID = 7380069326375973586L;

  /**
   * This method validate the user privileges in the Project Evaluation sections
   * 
   * @param invocation - is a ActionInvocation object; represents the execution state of an Action
   * @param securityContext - represent the security instance
   * @param projectManager - instance to manage the project.
   */
  public static void setPermissionParameters(ActionInvocation invocation, SecurityContext securityContext,
    ProjectManager projectManager) {

    BaseAction baseAction = (BaseAction) invocation.getAction();
    Map<String, Object> parameters = invocation.getInvocationContext().getParameters();
    String actionName = ServletActionContext.getActionMapping().getName();

    Map<String, Object> session = invocation.getInvocationContext().getSession();
    User user = (User) session.get(APConstants.SESSION_USER);

    boolean canEditProject = false, hasPermissionToEdit = false;

    // First, check if the user can edit the project
    String projectParameter = ((String[]) parameters.get(APConstants.PROJECT_REQUEST_ID))[0];
    int projectID = Integer.parseInt(projectParameter);
    Project project = projectManager.getProjectBasicInfo(projectID);


    int currentCycleYear;
    if (baseAction.isReportingCycle()) {
      currentCycleYear = baseAction.getCurrentReportingYear();
    } else {
      currentCycleYear = baseAction.getCurrentPlanningYear();
    }
    Submission submission = project.isSubmitted(currentCycleYear, baseAction.getCycleName());

    // If user is admin, it should have privileges to edit all projects.
    if (securityContext.isAdmin()) {
      canEditProject = true;
    } else {
      // Get the identifiers of the projects that the user can edit and validate if that list contains the projectID.
      // TODO Cambiar el método para que extraiga los proyectos en donde el usuario es, o PL, o ML, o FPL, RPL.
      // <Analizar más en detalle>
      List<Integer> projectsEditable = projectManager.getProjectIdsEditables(user.getId());
      // Projects wont be able to edit the project if the project has been already submitted.
      if ((projectsEditable.contains(new Integer(projectID)) && baseAction.hasProjectPermission("update", projectID))) {
        // [assumption] in this case, if the project is submit, the user can perform a evaluation.
        if (submission != null) {
          canEditProject = true;
        }
      }
    }

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
      hasPermissionToEdit = (securityContext.isAdmin()) ? true : baseAction.hasProjectPermission("update", projectID);
    }


    // Set the variable that indicates if the user can edit the section
    baseAction.setEditableParameter(hasPermissionToEdit && canEditProject);
    baseAction.setCanEdit(canEditProject);
  }

  private SecurityContext securityContext;

  private ProjectManager projectManager;

  @Inject
  /**
   * Create a new EditProjectEvaluationInterceptor Object
   * 
   * @param securityContext - the security instance
   * @param projectManager - a instance to manage the project
   */
  public EditProjectEvaluationInterceptor(SecurityContext securityContext, ProjectManager projectManager) {
    super();
    this.securityContext = securityContext;
    this.projectManager = projectManager;
  }

  /**
   * @return a instance to manage the project
   */
  public ProjectManager getProjectManager() {
    return projectManager;
  }

  /**
   * @return the security instance
   */
  public SecurityContext getSecurityContext() {
    return securityContext;
  }

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    try {
      setPermissionParameters(invocation, securityContext, projectManager);
      return invocation.invoke();
    } catch (Exception e) {
      BaseAction action = (BaseAction) invocation.getAction();
      return action.NOT_FOUND;
    }
  }

  /**
   * @param projectManager - instance to manage the project
   */
  public void setProjectManager(ProjectManager projectManager) {
    this.projectManager = projectManager;
  }

  /**
   * @param securityContex - the security instancet
   */
  public void setSecurityContext(SecurityContext securityContext) {
    this.securityContext = securityContext;
  }

}
