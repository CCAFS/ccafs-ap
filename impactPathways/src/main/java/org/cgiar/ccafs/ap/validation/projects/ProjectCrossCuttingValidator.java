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
import org.cgiar.ccafs.ap.data.model.CrossCuttingContribution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.validation.BaseValidator;
import org.cgiar.ccafs.ap.validation.model.ProjectValidator;

import com.google.inject.Inject;


/**
 * @author Christian David Garcia Oviedo. - CIAT/CCAFS
 */

public class ProjectCrossCuttingValidator extends BaseValidator {

  private ProjectValidator projectValidator;
  private boolean fields = false;

  @Inject
  public ProjectCrossCuttingValidator(ProjectValidator projectValidator) {
    super();
    this.projectValidator = projectValidator;
  }

  private boolean isValidCommunicationEngagement(String communicationEngagement) {
    return this.isValidString(communicationEngagement);
  }

  private boolean isValidGenderSocial(String gender) {
    return this.isValidString(gender);
  }

  public void validate(BaseAction action, Project project, String cycle) {
    if (project != null) {
      // If project is CORE or CO-FUNDED
      if (project.isCoreProject() || project.isCoFundedProject()) {
        this.validateProjectJustification(action, project);
        this.validateLessonsLearn(action, project, "crossCutting");
        this.valideCrossCuting(action, project);

      }
      if (fields) {
        action.addActionError(action.getText("saving.fields.required"));
      } else if (validationMessage.length() > 0) {
        action
          .addActionMessage(" " + action.getText("saving.missingFields", new String[] {validationMessage.toString()}));
      }

      // Saving missing fields.
      this.saveMissingFields(project, cycle, "crossCutting");
    }
  }

  private void valideCrossCuting(BaseAction action, Project project) {
    CrossCuttingContribution cross = project.getCrossCutting();
    if (!this.isValidGenderSocial(cross.getGenderSocial())) {
      this.addMessage(action.getText("reporting.projectDescription.annualreportDonor.readText").toLowerCase());
      this.addMissingField("project.crossCutting.genderSocial");
      fields = true;
    }

    if (!this.isValidCommunicationEngagement(cross.getCommunicationEngagement())) {
      this.addMessage(action.getText("reporting.projectCrossCutting.communicationEngagement").toLowerCase());
      this.addMissingField("project.crossCutting.communicationEngagement");
      fields = true;
    }

  }
}
