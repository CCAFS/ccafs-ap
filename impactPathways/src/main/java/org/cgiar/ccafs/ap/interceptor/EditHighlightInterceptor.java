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
import org.cgiar.ccafs.ap.data.manager.HighLightManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectHighlights;
import org.cgiar.ccafs.ap.security.SecurityContext;

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

public class EditHighlightInterceptor extends AbstractInterceptor {

  private static Logger LOG = LoggerFactory.getLogger(EditHighlightInterceptor.class);
  private static final long serialVersionUID = -4760428594099107017L;

  private SecurityContext securityContext;
  private ProjectManager projectManager;
  private HighLightManager deliverableManager;

  @Inject
  public EditHighlightInterceptor(SecurityContext securityContext, ProjectManager projectManager,
    HighLightManager deliverableManager) {
    this.securityContext = securityContext;
    this.projectManager = projectManager;
    this.deliverableManager = deliverableManager;
  }

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    Map<String, Object> parameters = invocation.getInvocationContext().getParameters();
    String editParameterStr = ((String[]) parameters.get(APConstants.HIGHLIGHT_REQUEST_ID))[0];
    int deliverableID = Integer.parseInt(editParameterStr);
    ProjectHighlights highlt = deliverableManager.getHighLightById(deliverableID);
    // Get the identifiers of the projects that the user can edit and validate if that list contains the projectID.
    Project project = projectManager.getProject(highlt.getProject_id());

    // Add the project ID in the invocations parameters and validate if the user has permission to edit the project.
    parameters.put(APConstants.PROJECT_REQUEST_ID, new String[] {String.valueOf(project.getId())});
    invocation.getInvocationContext().setParameters(parameters);

    EditProjectInterceptor.setPermissionParameters(invocation, securityContext, projectManager);
    return invocation.invoke();
  }
}
