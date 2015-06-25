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
import org.cgiar.ccafs.ap.data.manager.ProjectOutcomeManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectOutcome;
import org.cgiar.ccafs.ap.validation.planning.ProjectOutcomeValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hernán David Carvajal B.
 */
public class ProjectOutcomeAction extends BaseAction {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ProjectOutcomeAction.class);
  private static final long serialVersionUID = -3179251766947184219L;

  // Manager
  private ProjectManager projectManager;
  private ProjectOutcomeManager projectOutcomeManager;
  private ProjectOutcomeValidator validator;

  private int currentPlanningYear;
  private int midOutcomeYear;
  private int projectID;
  private Project project;

  @Inject
  public ProjectOutcomeAction(APConfig config, ProjectManager projectManager,
    ProjectOutcomeManager projectOutcomeManager, ProjectOutcomeValidator validator) {
    super(config);
    this.projectManager = projectManager;
    this.projectOutcomeManager = projectOutcomeManager;
    this.validator = validator;
  }


  public int getCurrentPlanningYear() {
    return currentPlanningYear;
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
    for (int year = currentPlanningYear; year <= midOutcomeYear; year++) {
      ProjectOutcome projectOutcome = projectOutcomeManager.getProjectOutcomeByYear(projectID, year);
      if (projectOutcome == null) {
        projectOutcome = new ProjectOutcome(-1);
        projectOutcome.setYear(year);
      }

      projectOutcomes.put(String.valueOf(year), projectOutcome);
    }
    project.setOutcomes(projectOutcomes);

  }

  @Override
  public String save() {
    if (this.isSaveable()) {
      boolean success = true;

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
    } else {
      return BaseAction.ERROR;
    }
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
      validator.validate(this, project, midOutcomeYear, currentPlanningYear);
    }
  }
}