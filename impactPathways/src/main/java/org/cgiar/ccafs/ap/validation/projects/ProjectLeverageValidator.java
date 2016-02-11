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
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.validation.BaseValidator;

import com.google.inject.Inject;


/**
 * @author Christian David Garcia Oviedo. - CIAT/CCAFS
 */

public class ProjectLeverageValidator extends BaseValidator {


  @Inject
  public ProjectLeverageValidator() {
    super();

  }

  public void validate(BaseAction action, Project project, String cycle) {
    if (project != null) {
      // Does the project have any nextUser?
      if (project.getLeverages() != null && !project.getLeverages().isEmpty() && project.getLeverages().size() >= 1) {
        // Validate project justification.
        // this.validateProjectJustification(action, project);


        // Loop all the nexte users.
        for (int c = 0; c < project.getLeverages().size(); c++) {
          // Required fields are required for all type of projects.

          this.validateTitleLeverage(action, project.getLeverages().get(c).getTitle(), c);
          this.validatePartner(action, String.valueOf(project.getLeverages().get(c).getInstitution()), c);
          this.validateYear(action, project.getLeverages().get(c).getYear(), c);
          this.validateFlagship(action, project.getLeverages().get(c).getFlagship(), c);
          this.validateBudget(action, project.getLeverages().get(c).getBudget(), c);


        }
      } else {
        // Show problem only for Core projects and Co-funded projects
        if (project.isCoreProject() || project.isCoFundedProject()) {
          this.addMessage("At least One Leverage. ");
          this.addMissingField("project.leverage.empty");
        }
      }

      if (!action.getFieldErrors().isEmpty()) {
        action.addActionError(action.getText("saving.fields.required"));
      } else if (validationMessage.length() > 0) {
        action
          .addActionMessage(" " + action.getText("saving.missingFields", new String[] {validationMessage.toString()}));
      }

      // Saving missing fields.
      this.saveMissingFields(project, cycle, "leverages");
    }
  }


  public void validateBudget(BaseAction action, Double budget, int c) {
    if (budget == null || budget == -1) {
      this.addMessage("Leverage #" + (c + 1) + ": Budget");
      this.addMissingField("project.leverages[" + c + ".budget");
    }
  }


  public void validateFlagship(BaseAction action, Integer flagship, int c) {
    if (flagship == -1 || flagship == null) {
      this.addMessage("Leverage #" + (c + 1) + ": FlagShip");
      this.addMissingField("project.leverages[" + c + ".flagship");
    }
  }


  public void validatePartner(BaseAction action, String partner, int c) {
    if (!(this.isValidString(partner))) {
      this.addMessage("Leverage #" + (c + 1) + ": Partner");
      this.addMissingField("project.leverages[" + c + "].Partner");
    }
  }


  public void validateTitleLeverage(BaseAction action, String title, int c) {
    if (!(this.isValidString(title) && this.wordCount(title) <= 200)) {
      this.addMessage("Leverage #" + (c + 1) + ": Title");
      this.addMissingField("project.leverages[" + c + "].Title");
    }
  }


  public void validateYear(BaseAction action, Integer year, int c) {
    if (year == -1 || year == null) {
      this.addMessage("Leverage #" + (c + 1) + ": Year");
      this.addMissingField("project.leverages[" + c + ".year");
    }
  }


}
