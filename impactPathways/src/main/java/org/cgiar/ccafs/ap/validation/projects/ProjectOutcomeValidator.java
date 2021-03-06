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

package org.cgiar.ccafs.ap.validation.projects;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.validation.BaseValidator;
import org.cgiar.ccafs.ap.validation.model.ProjectValidator;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class ProjectOutcomeValidator extends BaseValidator {

  private ProjectValidator projectValidator;

  @Inject
  public ProjectOutcomeValidator(ProjectValidator projectValidator) {
    super();
    this.projectValidator = projectValidator;
  }

  public void validate(BaseAction action, Project project, int midOutcomeYear, int currentPlanningYear, String cycle) {

    if (project != null) {
      // The projects will be validated according to their type
      if (project.isCoreProject() || project.isCoFundedProject()) {
        if (!cycle.equals(APConstants.REPORTING_SECTION)) {
          this.validateProjectJustification(action, project);
          this.validateLessonsLearn(action, project, "outcomes");
        }
        this.validateCoreProject(action, project, midOutcomeYear, currentPlanningYear, cycle);
      } else {
        // We don't validate the project outcomes for the bilateral projects.
      }

      if (action.getActionErrors().isEmpty()) {
        if (!action.getFieldErrors().isEmpty()) {
          action.addActionError(action.getText("saving.fields.required"));
        } else if (validationMessage.length() > 0) {
          action.addActionMessage(
            " " + action.getText("saving.missingFields", new String[] {validationMessage.toString()}));
        }
      }

      // Saving missing fields.
      this.saveMissingFields(project, cycle, "outcomes");
    }
  }

  private void validateCoreProject(BaseAction action, Project project, int midOutcomeYear, int currentPlanningYear,
    String cycle) {
    String message = null;
    if (cycle.equals(APConstants.REPORTING_SECTION)) {
      midOutcomeYear = currentPlanningYear;
    }
    // Validate for each year.
    for (int year = currentPlanningYear; year <= midOutcomeYear; year++) {
      // Validate only two years ahead and the last year which is 2019.
      if (year < (currentPlanningYear + 2) || year == midOutcomeYear) {
        // Validate the outcome statement
        if (!cycle.equals(APConstants.REPORTING_SECTION)) {
          if (!projectValidator.hasValidOutcomeStatement(project.getOutcomes(), year)) {
            if (year == midOutcomeYear) {
              message = action.getText("planning.projectOutcome.statement.readText");
            } else {
              message = action.getText("planning.projectOutcome.annualProgress.readText", new String[] {year + ""});
            }
            this.addMessage(message.toLowerCase());
            this.addMissingField("project.outcomes[" + year + "].statement");
          }


        }

      }

    }

    if (cycle.equals(APConstants.REPORTING_SECTION)) {
      if (!projectValidator.hasValidAnualProgress(project.getOutcomes(), action.getCurrentReportingYear())) {
        if (action.getCurrentReportingYear() == midOutcomeYear) {
          message = action.getText("planning.projectOutcome.annualProgress.readText");
        } else {
          message = action.getText("planning.projectOutcome.annualProgress.readText",
            new String[] {action.getCurrentReportingYear() + ""});
        }
        this.addMissingField("project.outcomes[" + action.getCurrentReportingYear() + "].annualProgress");
      }
      if (message != null) {
        this.addMessage(message.toLowerCase());
      }

      if (!projectValidator.hasValidCommunication(project.getOutcomes(), action.getCurrentReportingYear())) {
        if (action.getCurrentReportingYear() == midOutcomeYear) {
          message = action.getText("Communication and engagement are missing or are incorrect");
        } else {
          message = action.getText("Communication and engagement are missing or are incorrect",
            new String[] {action.getCurrentReportingYear() + ""});
        }
        this.addMissingField("project.outcomes[" + action.getCurrentReportingYear() + "].annualProgress");
      }
      if (message != null) {
        this.addMessage(message.toLowerCase());
      }


    }
  }
}
