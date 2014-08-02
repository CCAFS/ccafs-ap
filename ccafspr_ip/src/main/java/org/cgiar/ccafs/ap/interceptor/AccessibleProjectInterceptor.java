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

import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;
import java.util.Map;

import org.cgiar.ccafs.ap.data.manager.ProjectManager;

import org.apache.commons.lang3.StringUtils;
import org.cgiar.ccafs.ap.action.BaseAction;
import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AccessibleProjectInterceptor extends AbstractInterceptor {

  private static final long serialVersionUID = -1521958999615608308L;

  private static final Logger LOG = LoggerFactory.getLogger(AccessibleProjectInterceptor.class);

  // Managers
  ProjectManager projectManager;

  @Inject
  public AccessibleProjectInterceptor(ProjectManager projectManager) {
    this.projectManager = projectManager;
  }

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    Map<String, Object> session = invocation.getInvocationContext().getSession();
    Map<String, Object> parameters = invocation.getInvocationContext().getParameters();
    String projectParameter = ((String[]) parameters.get(APConstants.PROJECT_REQUEST_ID))[0];
    User user = (User) session.get(APConstants.SESSION_USER);
    if (user != null) {
      if (projectParameter != null) {
        // Listing all projects that the user is able to edit.

        // Getting project list that belongs to the program that you belongs to.
        List<Integer> idsAllowedToEdit = projectManager.getProjectIdsEditables(user);
        System.out.println(idsAllowedToEdit);
        if (StringUtils.isNumeric(projectParameter)) {
          int projectID = Integer.parseInt(projectParameter);
          if (idsAllowedToEdit.contains(new Integer(projectID))) {
            invocation.invoke();
          }
        }
        LOG
        .info(
          "User identify with id={}, email={}, role={} tried to access the project with id={}. And it is not authorized to edit it.",
          new Object[] {user.getId(), user.getEmail(), user.getRole().getName(), projectParameter});
        return BaseAction.NOT_AUTHORIZED;
      } else {
        // There is not a projectID parameter, thus, the project was not found.
        // Redirecting to Not Found page.
        return BaseAction.NOT_FOUND;
      }
    }
    // User is not logged.
    return BaseAction.NOT_LOGGED;
  }


}
