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

import java.util.Map;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This interceptor is used to validate if the project parameter is well passed.
 * 
 * @author Héctor Fabio Tobón R.
 */
public class ValidateProjectParameterInterceptor extends AbstractInterceptor {

  private static final long serialVersionUID = 4280129408412730529L;
  private static final Logger LOG = LoggerFactory.getLogger(ValidateProjectParameterInterceptor.class);

  // Managers
  private ProjectManager projectManager;

  @Inject
  public ValidateProjectParameterInterceptor(ProjectManager projectManager) {
    this.projectManager = projectManager;
  }

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    LOG.debug("=> ValidateProjectParameterInterceptor");

    String actionName = ServletActionContext.getActionMapping().getName();
    // if user is not in project list or creating a project.
    if (!actionName.equals("projectsList") && !actionName.equals("addNewCoreProject")
      && !actionName.equals("addNewBilateralProject") && !actionName.equals("addCoFundedProject")
      && !actionName.equals("projectsEvaluation")) {
      Map<String, Object> parameters = invocation.getInvocationContext().getParameters();
      // Validate if project parameter exists in the URL.
      if (parameters.get(APConstants.PROJECT_REQUEST_ID) != null) {
        String projectParameter = ((String[]) parameters.get(APConstants.PROJECT_REQUEST_ID))[0];
        // Validate if it is a number.
        if (StringUtils.isNumeric(projectParameter)) {
          int projectID = Integer.parseInt(projectParameter);
          // If project doesn't exist.
          if (!projectManager.existProject(projectID)) {
            return BaseAction.NOT_FOUND;
          }
          // If project exists, continue!
          return invocation.invoke();
        } else {
          // If parameter is not a number.
          return BaseAction.NOT_FOUND;
        }
      } else {
        // if parameter does not exist.
        return BaseAction.NOT_FOUND;
      }
    } else {
      // if user is in project list, do nothing!
      return invocation.invoke();
    }
  }

}
