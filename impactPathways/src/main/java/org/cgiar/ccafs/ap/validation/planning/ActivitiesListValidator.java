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

package org.cgiar.ccafs.ap.validation.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.validation.BaseValidator;
import org.cgiar.ccafs.ap.validation.model.ProjectValidator;

import java.util.List;

import com.google.inject.Inject;


/**
 * @author Carlos Alberto Martínez M. - CIAT/CCAFS
 */

public class ActivitiesListValidator extends BaseValidator {

  private static final long serialVersionUID = -4871185832403702671L;
  private ProjectValidator projectValidator;

  @Inject
  public ActivitiesListValidator(ProjectValidator projectValidator) {
    super();
    this.projectValidator = projectValidator;
  }

  public void validate(BaseAction action, Project project) {
    if (project != null && project.getActivities() != null) {
      boolean problem = this.validateRequiredFields(action, project.getActivities());
      this.validateProjectJustification(action, project);

      // The projects will be validated according to their type
      // if (project.isCoreProject()) {
      // this.validateCoreProject(action, project);
      // } else {
      // this.validateBilateralProject(action, project);
      // }

      if (problem) {
        action.addActionError(action.getText("saving.fields.required"));
      }
      // else {
      // this.validateOptionalFields(action, deliverable);
      // }
    }
  }

  public boolean validateRequiredFields(BaseAction action, List<Activity> activities) {
    boolean problem = false;
    Activity activity;
    for (int c = 0; c < activities.size(); c++) {
      activity = activities.get(c);

      // Validating title.
      if (!this.isValidString(activity.getTitle())) {
        action.addFieldError("project.activities[" + c + "].title", action.getText("validation.field.required"));
        problem = true;
      }
      // Validating description
      if (!this.isValidString(activity.getDescription())) {
        action.addFieldError("project.activities[" + c + "].description", action.getText("validation.field.required"));
        problem = true;
      }

      // Validating start date
      if (activity.getStartDate() == null) {
        action.addFieldError("project.activities[" + c + "].startDate", action.getText("validation.field.required"));
        problem = true;
      }
      // Validating end date
      if (activity.getEndDate() == null) {
        action.addFieldError("project.activities[" + c + "].endDate", action.getText("validation.field.required"));
        problem = true;
      }

      // Validating leader
      if (activity.getLeader() == null) {
        action.addFieldError("project.activities[" + c + "].leader", action.getText("validation.field.required"));
        problem = true;
      }
    }
    return problem;
  }

  public void validateTitle(BaseAction action, String title) {

  }
}
