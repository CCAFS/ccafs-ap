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
import org.cgiar.ccafs.ap.data.model.CRPContribution;
import org.cgiar.ccafs.ap.data.model.ComponentLesson;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.validation.BaseValidator;
import org.cgiar.ccafs.ap.validation.model.OtherContributionValidator;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 * @author Christian David Garcia - CIAT/CCAFS
 */

public class ProjectIPOtherContributionValidator extends BaseValidator {

  private static final long serialVersionUID = -3912724967470718068L;
  private OtherContributionValidator otherContributionValidator;


  @Inject
  public ProjectIPOtherContributionValidator(OtherContributionValidator otherContributionValidator) {

    this.otherContributionValidator = otherContributionValidator;
  }

  public void validate(BaseAction action, Project project) {
    if (project != null) {
      this.validateProjectJustification(action, project);

      if (project.getIpOtherContribution() == null) {
        this.addMissingField("project.ipOtherContribution");
        this.saveMissingFields(project, "Planning", "otherContributions");
      } else {
        // Validate only if the project is CCAFS Core or Co Funded Project
        if (project.isCoreProject() || project.isCoFundedProject()) {

          this.validateContribution(action, project.getIpOtherContribution().getContribution());
          this.validateAdditionalContribution(action, project.getIpOtherContribution().getAdditionalContribution());
          int i = 0;

          for (CRPContribution crp_contribuntion : project.getIpOtherContribution().getCrpContributions()) {
            this.validateNatureCollaboration(action, crp_contribuntion.getNatureCollaboration(), i);
            i++;
          }
          this.validateLessons(action, project);
          this.validateJustification(action, project);

        }

        if (validationMessage.length() > 0) {
          String msg = " " + action.getText("saving.missingFields", new String[] {validationMessage.toString()});
          action.addActionMessage(msg);
        }

        // Saving missing fields.
        this.saveMissingFields(project, "Planning", "otherContributions");
      }
    }
  }

  private void validateAdditionalContribution(BaseAction action, String additionalContribution) {
    if (!otherContributionValidator.isValidAdditionalContribution(additionalContribution)) {
      this.addMessage(
        action.getText("planning.impactPathways.otherContributions.additionalcontribution.readText").toLowerCase());
      this.addMissingField("project.ipOtherContribution.additionalcontribution");
    }
  }


  private void validateContribution(BaseAction action, String contribution) {
    if (!otherContributionValidator.isValidContribution(contribution)) {
      this.addMessage(action.getText("planning.impactPathways.otherContributions.contribution.readText").toLowerCase());
      this.addMissingField("project.ipOtherContribution.contribution");
    }
  }


  private void validateJustification(BaseAction action, Project project) {
    if (!project.isNew(config.getCurrentPlanningStartDate())) {

      if (!this.isValidString(action.getJustification()) && this.wordCount(action.getJustification()) > 100) {
        this.addMessage(action.getText("validation.justification").toLowerCase());
        this.addMissingField("project.justification");
      }
    }
  }


  private void validateLessons(BaseAction action, Project project) {

    if (!project.isNew(config.getCurrentPlanningStartDate())) {
      ComponentLesson lesson = action.getProjectLessons();

      if (lesson.getLessons() != null) {
        if (!this.isValidString(lesson.getLessons()) && this.wordCount(lesson.getLessons()) <= 100) {

          this.addMessage(action.getText("planning.impactPathways.otherContributions.lessons.readText").toLowerCase());
          this.addMissingField("project.lessons");
          develop
        }
      } else {
        this.addMessage(action.getText("planning.impactPathways.otherContributions.lessons.readText").toLowerCase());
        this.addMissingField("project.lessons");
      }
    }
  }

  private void validateNatureCollaboration(BaseAction action, String natureCollaboration, int i) {
    if (!otherContributionValidator.isValidCrpCollaborationNature(natureCollaboration)) {
      this.addMessage(
        action.getText("planning.impactPathways.otherContributions.collaborationNature.readText").toLowerCase());
      this.addMissingField("project.ipOtherContribution.crps.[" + i + "].collaborationNature");
    }
  }


}
