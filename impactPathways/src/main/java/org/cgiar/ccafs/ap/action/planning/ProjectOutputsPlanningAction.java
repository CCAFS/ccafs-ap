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

package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.utils.APConfig;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class ProjectOutputsPlanningAction extends BaseAction {

  private static final long serialVersionUID = 5246876705706611499L;
  private static Logger LOG = LoggerFactory.getLogger(ProjectOutputsPlanningAction.class);

  // Managers
  private ProjectManager projectManager;

  // Model
  private Project project;
  private int projectID;


  @Inject
  public ProjectOutputsPlanningAction(APConfig config, ProjectManager projectManager) {
    super(config);
    this.projectManager = projectManager;
  }

  @Override
  public void prepare() throws Exception {
    projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));

    // Getting the activity information
    project = projectManager.getProject(projectID);

    // Get the project outputs from database
    project.setOutputs(projectManager.getProjectOutputs(projectID));
  }

}
