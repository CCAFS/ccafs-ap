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
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectStatusEnum;
import org.cgiar.ccafs.ap.validation.BaseValidator;
import org.cgiar.ccafs.ap.validation.model.ActivityValidator;

import java.util.Date;

import com.google.inject.Inject;


/**
 * @author Carlos Alberto Martínez M. - CIAT/CCAFS
 */

public class ActivitiesListValidator extends BaseValidator {

  private ActivityValidator activityValidator;

  @Inject
  public ActivitiesListValidator(ActivityValidator activityValidator) {
    super();
    this.activityValidator = activityValidator;
  }

  public void validate(BaseAction action, Project project, String cycle) {
    if (project != null) {
      // Does the project have any activity?
      if (project.getActivities() != null && !project.getActivities().isEmpty()) {
        // Validate project justification.
        this.validateProjectJustification(action, project);

        if (project.isCoreProject() || project.isCoFundedProject()) {
          this.validateLessonsLearn(action, project, "activities");
        }

        // Loop all the activities.
        for (int c = 0; c < project.getActivities().size(); c++) {
          // Required fields are required for all type of projects.

          if (cycle.equals(APConstants.REPORTING_SECTION)) {
            this.validateStatus(action, project.getActivities().get(c).getActivityStatus(), c);

            if (project.getActivities().get(c).getActivityStatus() > 0) {
              switch (ProjectStatusEnum.getValue(project.getActivities().get(c).getActivityStatus())) {
                case Ongoing:
                case Extended:
                case Cancelled:
                  this.validateStatusDescription(action, project.getActivities().get(c).getActivityProgress(), c);

                  break;

                default:
                  break;
              }
            }

          } else {
            this.validateRequiredFields(action, project.getActivities().get(c), c);
            this.validateTitle(action, project.getActivities().get(c).getTitle(), c);
            this.validateDescription(action, project.getActivities().get(c).getDescription(), c);
            this.validateStartDate(action, project.getActivities().get(c).getStartDate(), c);
            this.validateEndDate(action, project.getActivities().get(c).getEndDate(), c);
          }


        }
      } else {
        // Show problem only for Core projects and Co-funded projects
        if (project.isCoreProject() || project.isCoFundedProject()) {
          this.addMessage(action.getText("saving.fields.atLeastOne",
            new String[] {action.getText("planning.activity").toLowerCase()}));
          this.addMissingField("project.activities.empty");
        }
      }

      if (!action.getFieldErrors().isEmpty()) {
        action.addActionError(action.getText("saving.fields.required"));
      } else if (validationMessage.length() > 0) {
        action
          .addActionMessage(" " + action.getText("saving.missingFields", new String[] {validationMessage.toString()}));
      }

      // Saving missing fields.
      this.saveMissingFields(project, cycle, "activities");
    }
  }

  private void validateDescription(BaseAction action, String description, int c) {
    if (!activityValidator.isValidDescription(description)) {
      this.addMessage("Activity #" + (c + 1) + ": Description");
      this.addMissingField("project.activities[" + c + "].description");
    }
  }

  private void validateEndDate(BaseAction action, Date endDate, int c) {
    if (!activityValidator.isValidEndDate(endDate)) {
      this.addMessage("Activity #" + (c + 1) + ": End date");
      this.addMissingField("project.activities[" + c + "].endDate");
    }
  }

  public void validateRequiredFields(BaseAction action, Activity activity, int c) {
    // Validating leader
    if (!activityValidator.isValidLeader(activity.getLeader())) {
      action.addFieldError("project.activities[" + c + "].leader", action.getText("validation.field.required"));
      this.addMissingField("project.activities[" + c + "].leader");
    }
  }

  private void validateStartDate(BaseAction action, Date startDate, int c) {
    if (!activityValidator.isValidStartDate(startDate)) {
      this.addMessage("Activity #" + (c + 1) + ": Start date");
      this.addMissingField("project.activities[" + c + "].startDate");
    }
  }

  public void validateStatus(BaseAction action, int status, int c) {
    if (!activityValidator.isValidStatus(status)) {
      this.addMessage("Activity #" + (c + 1) + ": Status");
      this.addMissingField("project.activities[" + c + "].activityStatus");
    }
  }

  public void validateStatusDescription(BaseAction action, String status, int c) {
    if (!this.isValidString(status)) {
      this.addMessage("Activity #" + (c + 1) + ": Status Description");
      this.addMissingField("project.activities[" + c + "].activityStatusDescription");
    }
  }

  public void validateTitle(BaseAction action, String title, int c) {
    if (!activityValidator.isValidTitle(title)) {
      this.addMessage("Activity #" + (c + 1) + ": Title ");
      this.addMissingField("project.activities[" + c + "].title");
    }
  }
}
