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
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectOutcomeManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectOutcome;

import java.util.Calendar;
import java.util.Date;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Galllego B.
 */
public class ProjectOutcomeAction extends BaseAction {


  private static final long serialVersionUID = 2845677913596494699L;

  // Manager
  private ProjectOutcomeManager projectOutcomeManager;
  private ProjectManager projectManager;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ProjectOutcomeAction.class);

  // Model for the back-end
  private Project project;


  // Model for the front-end
  private int projectID;


  @Inject
  public ProjectOutcomeAction(APConfig config, ProjectOutcomeManager projectOutcomeManager,
    ProjectManager projectManager) {
    super(config);
    this.projectOutcomeManager = projectOutcomeManager;
    this.projectManager = projectManager;
  }

  public Project getProject() {
    return project;
  }


  public int getProjectID() {
    return projectID;
  }


  @Override
  public void prepare() throws Exception {
    super.prepare();
    try {
      projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));
    } catch (NumberFormatException e) {
      LOG.error("-- prepare() > There was an error parsing the project identifier '{}'.", projectID, e);
      projectID = -1;
      return; // Stop here and go to execute method.
    }

    project = projectManager.getProject(projectID);
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    ProjectOutcome projectOutcome =
      projectOutcomeManager.getProjectOutcomeByYear(projectID, calendar.get(Calendar.YEAR));
    if (projectOutcome == null) {
      projectOutcome = new ProjectOutcome(-1);
      projectOutcome.setYear(calendar.get(Calendar.YEAR));
    }
    project.setOutcome(projectOutcome);
  }


  @Override
  public String save() {
    boolean success = true;
    // Saving Project Outcome

    boolean saved = projectOutcomeManager.saveProjectOutcome(projectID, project.getOutcome());
    if (!saved) {
      success = false;
    }


    return INPUT;

  }


  public void setProject(Project project) {
    this.project = project;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }
}
