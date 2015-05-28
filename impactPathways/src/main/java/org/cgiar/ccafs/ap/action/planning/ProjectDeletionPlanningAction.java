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
 * @author Hernán David Carvajal
 */

public class ProjectDeletionPlanningAction extends BaseAction {

  private static Logger LOG = LoggerFactory.getLogger(ProjectDeletionPlanningAction.class);
  private static final long serialVersionUID = -7483864386660493162L;

  // Managers
  private ProjectManager projectManager;

  // Model
  private int projectID;
  private Project project;

  @Inject
  public ProjectDeletionPlanningAction(APConfig config, ProjectManager projectManager) {
    super(config);
    this.projectManager = projectManager;
  }

  @Override
  public String execute() throws Exception {
    if (securityContext.canDeleteProject() && project.isErasable(config.getPlanningCurrentYear())) {
      projectManager.deleteProject(project.getId());
    } else {
      return BaseAction.NOT_AUTHORIZED;
    }

    return super.execute();
  }

  public int getProjectID() {
    return projectID;
  }

  @Override
  public void prepare() throws Exception {
    try {
      projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));
    } catch (NumberFormatException e) {
      LOG.error("-- prepare() > There was an error parsing the project identifier '{}'.", projectID, e);
      projectID = -1;
      return; // Stop here and go to execute method.
    }

    project = projectManager.getProjectBasicInfo(projectID);
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

}
