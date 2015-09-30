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
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.validation.BaseValidator;
import org.cgiar.ccafs.ap.validation.model.ProjectValidator;

import com.google.inject.Inject;


/**
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class ProjectCCAFSOutcomesValidator extends BaseValidator {

  private ProjectValidator projectValidator;

  @Inject
  public ProjectCCAFSOutcomesValidator(ProjectValidator projectValidator) {
    super();
    this.projectValidator = projectValidator;
  }

  public void validate(BaseAction action, Project project, String cycle) {
    if (project != null) {
      this.validateProjectJustification(action, project);

      // The projects will be validated according to their type.
      if (project.isCoreProject() || project.isCoFundedProject()) {
        this.validateCoreProject(action, project);
      } else {
        // TODO
      }

      if (!action.getFieldErrors().isEmpty()) {
        action.addActionError(action.getText("saving.fields.required"));
      } else if (validationMessage.length() > 0) {
        action
          .addActionMessage(" " + action.getText("saving.missingFields", new String[] {validationMessage.toString()}));
      }

      // Saving missing fields.
      this.saveMissingFields(project, cycle, "ccafsOutcomes");
    }
  }

  private void validateCoreProject(BaseAction action, Project project) {
    this.validateIndicators(action, project);
  }

  private void validateIndicators(BaseAction action, Project project) {
    if (projectValidator.isValidIndicators(project.getIndicators())) {
      int c = 0;
      for (IPIndicator indicator : project.getIndicators()) {
        this.validateTargetValue(action, indicator.getTarget(), c);
        this.validateTargetNarrative(action, indicator.getDescription(), c);
        c++;
      }
    }

  }

  private void validateTargetNarrative(BaseAction action, String targetNarrative, int c) {
    if (!projectValidator.isValidTargetNarrative(targetNarrative)) {
      // TODO
      this.addMessage("project.indicators[" + c + "].description");
      this.addMissingField("project.indicators[" + c + "].description");
    }
  }

  private void validateTargetValue(BaseAction action, String targetValue, int c) {
    if (!projectValidator.isValidTargetValue(targetValue)) {
      action.addFieldError("project.indicators[" + c + "].target", action.getText("validation.number.format"));
      this.addMissingField("project.indicators[" + c + "].targe");
    }

  }


}
