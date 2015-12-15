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
package org.cgiar.ccafs.ap.action.projects;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.HistoryManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.utils.APConfig;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Hernán David Carvajal B.
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */
public class ProjectCrossCuttingAction extends BaseAction {

  private static final long serialVersionUID = -3179251766947184219L;

  // Manager
  private ProjectManager projectManager;
  private HistoryManager historyManager;

  private int midOutcomeYear;
  private int projectID;
  private Project project;

  @Inject
  public ProjectCrossCuttingAction(APConfig config, ProjectManager projectManager, HistoryManager historyManager) {
    super(config);
    this.projectManager = projectManager;
    this.historyManager = historyManager;
  }

  public int getMidOutcomeYear() {
    return midOutcomeYear;
  }

  public Project getProject() {
    return project;
  }

  public int getProjectID() {
    return projectID;
  }

  public boolean isNewProject() {
    return project.isNew(config.getCurrentPlanningStartDate());
  }

  @Override
  public String next() {
    String result = this.save();
    if (result.equals(BaseAction.SUCCESS)) {
      return BaseAction.NEXT;
    } else {
      return result;
    }
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    midOutcomeYear = this.config.getMidOutcomeYear();

    projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));
    project = projectManager.getProject(projectID);

    // Getting the Project lessons for this section.
    int evaluatingYear = 0;
    if (this.getCycleName().equals(APConstants.REPORTING_SECTION)) {
      evaluatingYear = this.getCurrentReportingYear();
    } else {
      evaluatingYear = this.getCurrentPlanningYear();
    }
    // Getting the Project lessons for this section.
    this.setProjectLessons(
      lessonManager.getProjectComponentLesson(projectID, this.getActionName(), evaluatingYear, this.getCycleName()));

    // Initializing Section Statuses:
    this.initializeProjectSectionStatuses(project, this.getCycleName());

    // Getting the last history
    super.setHistory(historyManager.getProjectOutcomeHistory(project.getId()));
  }

  @Override
  public String save() {
    return SUCCESS;
  }

  public void setMidOutcomeYear(int midOutcomeYear) {
    this.midOutcomeYear = midOutcomeYear;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

  @Override
  public void validate() {

  }
}