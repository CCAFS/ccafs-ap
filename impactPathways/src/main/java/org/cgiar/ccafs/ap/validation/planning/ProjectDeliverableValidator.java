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
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.validation.BaseValidator;
import org.cgiar.ccafs.ap.validation.model.ProjectValidator;

import com.google.inject.Inject;


/**
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class ProjectDeliverableValidator extends BaseValidator {

  // Validators
  private ProjectValidator projectValidator;


  @Inject
  public ProjectDeliverableValidator(ProjectValidator projectValidator) {
    super();
    this.projectValidator = projectValidator;
  }

  /**
   * This validation will be done at deliverable level. Thus, it will validate if the given deliverable has all the
   * required fields filled in the system.
   * 
   * @param action
   * @param project a project with its basic information.
   * @param deliverable a deliverable with all the information.
   * @param cycle Planning or Reporting
   */
  public void validate(BaseAction action, Project project, Deliverable deliverable, String cycle) {
    if (deliverable != null) {
      this.validateProjectJustification(action, deliverable);

      if (project.isCoreProject() || project.isCoFundedProject()) {
        // this.validateAsCoreProject(action, project, deliverable);
      } else {
        // Deliverables are not needed, but if there is one added, it will be validated completely.
        // this.validateAsBilateralProject(action, project, deliverable);
      }

      if (!action.getFieldErrors().isEmpty()) {
        action.addActionError(action.getText("saving.fields.required"));
      } else if (validationMessage.length() > 0) {
        action
        .addActionMessage(" " + action.getText("saving.missingFields", new String[] {validationMessage.toString()}));
      }

      // Saving missing fields.
      this.saveMissingFields(project, deliverable, cycle, "description");
    }


    // // Responsible is not required.
    // if (deliverable.getResponsiblePartner().getPartner() == null) {
    // deliverable.setResponsiblePartner(null);
    // }
    // Adding general error.
  }

  /**
   * This validation will be done at project level. Thus, it will validate if the project has or not deliverables added.
   * 
   * @param action
   * @param project a project object with deliverables on it.
   * @param cycle Planning or Reporting.
   */
  public void validate(BaseAction action, Project project, String cycle) {
    // Core and Co-funded projects has to have at least one deliverable.
    if (project.isCoreProject() || project.isCoFundedProject()) {
      if (projectValidator.hasDeliverables(project.getDeliverables())) {
        this.addMissingField("projects.deliverables.empty");
        // No need to add a message since we don't have a save button in the deliverableList section.
      }
    } else {
      // Do not validate if the project has deliverables added.
    }
    this.saveMissingFields(project, cycle, "deliverablesList");
  }

  private void validateAsCoreProject(BaseAction action, Project project, Deliverable deliverable) {
    if (deliverable == null) {
      // If deliverable is null, the project doesn't have deliverables.
    }

  }

  private void validateOptionalFields(BaseAction action, Deliverable deliverable) {
    // Deliverable Title.


    // Deliverable responsible partner - contact name.
    // if (deliverable.getResponsiblePartner() != null && (deliverable.getResponsiblePartner().getUser() == null
    // || deliverable.getResponsiblePartner().getUser().getId() == -1)) {
    // this.addMessage(this.getText("planning.projectDeliverable.responsible.contactEmail"));
    // }

    // Next Users - TODO
    // for (int c = 0; c < deliverable.getNextUsers().size(); c++) {
    // if (!this.isValidString(deliverable.getNextUsers().get(c).getUser())) {
    // this.addMessage("");
    // }
    // }

    // Deliverable partnerships - contact name
    // for (int c = 0; c < deliverable.getOtherPartners().size(); c++) {
    // if (deliverable.getOtherPartners().get(c).getUser() == null
    // || deliverable.getOtherPartners().get(c).getUser().getId() == -1) {
    // this.addMessage(
    // this.getText("planning.deliverables.otherPartner.contactEmail", new String[] {String.valueOf(c)}));
    // }
    // }

    if (this.validationMessage.length() > 0) {
      action
        .addActionWarning(" " + action.getText("saving.missingFields", new String[] {validationMessage.toString()}));
    }
  }

  private boolean validateRequiredFields(BaseAction action, Deliverable deliverable) {
    boolean problem = false;

    // Validating the title
    if (!this.isValidString(deliverable.getTitle())) {
      action.addFieldError("deliverable.title", action.getText("validation.field.required"));
      problem = true;
    }

    // Validating that a MOG is selected.
    if (deliverable.getOutput() == null) {
      action.addFieldError("deliverable.output", action.getText("validation.field.required"));
      problem = true;
    }

    // Validating that a year is selected.
    if (deliverable.getYear() == -1) {
      action.addFieldError("deliverable.year", action.getText("validation.field.required"));
      problem = true;
    }

    // Validating that some sub-type is selected.
    if (deliverable.getType() == null) {
      // Indicate problem in the missing field.
      action.addFieldError("deliverable.type", action.getText("validation.field.required"));
      problem = true;
    }

    if (deliverable.getType() != null && deliverable.getType().getId() == APConstants.DELIVERABLE_SUBTYPE_OTHER_ID
      && !this.isValidString(deliverable.getTypeOther())) {
      // Indicate problem in the missing field.
      action.addFieldError("deliverable.typeOther", action.getText("validation.field.required"));
      problem = true;
    }

    // Validating that some year is selected.
    if (deliverable.getYear() == -1) {
      // Indicate problem in the missing field.
      action.addFieldError("deliverable.year", action.getText("validation.field.required"));
      problem = true;
    }

    // Validating institutions in the partnerships section as they are required.
    // if (deliverable.getResponsiblePartner() != null && deliverable.getResponsiblePartner().getInstitution() == null)
    // {
    // action.addFieldError("deliverable.responsiblePartner.institution", this.getText("validation.field.required"));
    // problem = true;
    // }
    //
    // for (int c = 0; c < deliverable.getOtherPartners().size(); c++) {
    // if (deliverable.getOtherPartners().get(c).getInstitution() == null) {
    // action.addFieldError("deliverable.otherPartners[" + c + "].institution",
    // this.getText("validation.field.required"));
    // problem = true;
    // }
    // }

    return problem;
  }

}
