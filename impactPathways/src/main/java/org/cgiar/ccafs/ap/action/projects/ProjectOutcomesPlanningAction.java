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
import org.cgiar.ccafs.ap.data.manager.ProjectOutcomeManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectOutcome;
import org.cgiar.ccafs.ap.validation.projects.ProjectOutcomeValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Hernán David Carvajal B.
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */
public class ProjectOutcomesPlanningAction extends BaseAction {

  private static final long serialVersionUID = -3179251766947184219L;

  // Manager
  private ProjectManager projectManager;
  private ProjectOutcomeManager projectOutcomeManager;
  private ProjectOutcomeValidator validator;
  private HistoryManager historyManager;

  private int currentPlanningYear;
  private int midOutcomeYear;
  private int projectID;
  private Project project;

  @Inject
  public ProjectOutcomesPlanningAction(APConfig config, ProjectManager projectManager,
    ProjectOutcomeManager projectOutcomeManager, ProjectOutcomeValidator validator, HistoryManager historyManager) {
    super(config);
    this.projectManager = projectManager;
    this.projectOutcomeManager = projectOutcomeManager;
    this.validator = validator;
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
    currentPlanningYear = this.config.getPlanningCurrentYear();
    midOutcomeYear = this.config.getMidOutcomeYear();

    projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));
    project = projectManager.getProject(projectID);

    // Load the project outcomes
    Map<String, ProjectOutcome> projectOutcomes = new HashMap<>();

    for (int year = this.getCurrentPlanningYear() - 1; year <= midOutcomeYear; year++) {
      ProjectOutcome projectOutcome = projectOutcomeManager.getProjectOutcomeByYear(projectID, year);
      if (projectOutcome == null) {
        projectOutcome = new ProjectOutcome(-1);
        projectOutcome.setYear(year);
      }

      projectOutcomes.put(String.valueOf(year), projectOutcome);
    }
    project.setOutcomes(projectOutcomes);

    // Getting the Project lessons for this section.
    this.setProjectLessons(lessonManager.getProjectComponentLesson(projectID, this.getActionName(),
      this.getCurrentPlanningYear()));

    // Initializing Section Statuses:
    this.initializeProjectSectionStatuses(project, "Planning");

    // Getting the last history
    super.setHistory(historyManager.getProjectOutcomeHistory(project.getId()));
  }

  @Override
  public String save() {
    if (securityContext.canUpdateProjectOutcomes(projectID)) {
      boolean success = true;

      if (!this.isNewProject()) {
        super.saveProjectLessons(projectID);
      }

      // Saving Project Outcome
      for (int year = currentPlanningYear; year <= midOutcomeYear; year++) {
        ProjectOutcome outcome = project.getOutcomes().get(String.valueOf(year));
        success =
          success
            && projectOutcomeManager.saveProjectOutcome(projectID, outcome, this.getCurrentUser(),
              this.getJustification());
      }

      if (success) {
        // Get the validation messages and append them to the save message if any
        Collection<String> messages = this.getActionMessages();
        if (!messages.isEmpty()) {
          String validationMessage = messages.iterator().next();
          this.setActionMessages(null);
          this.addActionWarning(this.getText("saving.saved") + validationMessage);
        } else {
          this.addActionMessage(this.getText("saving.success",
            new String[] {this.getText("planning.projectOutcome.title")}));
        }
        return SUCCESS;
      } else {
        this.addActionError(this.getText("saving.problem"));
        return INPUT;
      }
    }
    return BaseAction.NOT_AUTHORIZED;
  }

  public void setCurrentPlanningYear(int currentPlanningYear) {
    this.currentPlanningYear = currentPlanningYear;
  }

  public void setMidOutcomeYear(int midOutcomeYear) {
    this.midOutcomeYear = midOutcomeYear;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

  @Override
  public void validate() {
    if (save) {
      validator.validate(this, project, midOutcomeYear, currentPlanningYear, "Planning");
    }
  }
}