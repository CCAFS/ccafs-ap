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
import org.cgiar.ccafs.ap.validation.BaseValidator;

import java.util.List;

import com.google.inject.Inject;


/**
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class ProjectDeliverableValidator extends BaseValidator {


  @Inject
  public ProjectDeliverableValidator() {
    super();
  }

  public void validate(BaseAction action, Deliverable deliverable) {
    if (deliverable != null) {
      boolean problem = this.validateRequiredFields(action, deliverable);

      // Responsible is not required.
      if (deliverable.getResponsiblePartner().getPartner() == null) {
        deliverable.setResponsiblePartner(null);
      }
      // Adding general error.
      if (problem) {
        action.addActionError(action.getText("saving.fields.required"));
      } else {
        this.validateOptionalFields(action, deliverable);
      }

    }
  }

  // This method is used to validate all the deliverables.
  public void validate(BaseAction action, List<Deliverable> deliverables) {

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
