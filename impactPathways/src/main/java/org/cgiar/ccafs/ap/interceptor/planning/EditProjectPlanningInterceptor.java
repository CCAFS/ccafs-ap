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
import org.cgiar.ccafs.security.SecurityContext;

import java.util.Map;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This interceptor verify if the user has the permissions to edit the project
 * 
 * @author Hernán David Carvajal
 */

public class EditProjectPlanningInterceptor extends AbstractInterceptor {

  private static Logger LOG = LoggerFactory.getLogger(EditProjectPlanningInterceptor.class);
  private static final long serialVersionUID = -6564226368433104079L;

  private SecurityContext securityContext;

  @Inject
  public EditProjectPlanningInterceptor(SecurityContext securityContext) {
    this.securityContext = securityContext;
  }

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    BaseAction baseAction = (BaseAction) invocation.getAction();
    Map<String, Object> parameters = invocation.getInvocationContext().getParameters();

    boolean isEditable = false;

    if (parameters.get(APConstants.EDITABLE_REQUEST) != null) {
      String stringEditable = ((String[]) parameters.get(APConstants.EDITABLE_REQUEST))[0];
      isEditable = stringEditable.equals("true");

      // If the user is not asking for edition privileges we don't need to validate it.
      if (!isEditable) {
        baseAction.setEditable(isEditable);
        return invocation.invoke();
      }

      // Project parameter is already validated in the ValidateProjectParameterInterceptor.
      String projectParameter = ((String[]) parameters.get(APConstants.PROJECT_REQUEST_ID))[0];
      int projectID = Integer.parseInt(projectParameter);

      // securityContext.
    }

    baseAction.setEditable(isEditable);
    return invocation.invoke();
  }
}
