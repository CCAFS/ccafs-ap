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
import org.cgiar.ccafs.ap.data.model.CRPContribution;
import org.cgiar.ccafs.ap.data.model.OtherContribution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjecteOtherContributions;
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

  private boolean isFullSectionEmpty(BaseAction action, OtherContribution ipOtherContribution) {
    if ((ipOtherContribution.getContribution() == null || ipOtherContribution.getContribution().isEmpty())
      && (ipOtherContribution.getAdditionalContribution() == null
        || ipOtherContribution.getAdditionalContribution().isEmpty())
      && ipOtherContribution.getCrpContributions().isEmpty()) {
      return true;
    }
    return false;
  }

  public void validate(BaseAction action, Project project, String cycle) {
    if (project != null) {

      // this.validateProjectJustification(action, project);


      if (project.getIpOtherContribution() == null) {
        // Do nothing as this section is full optional.
      } else {
        // Validate only if the project is CCAFS Core or Co Funded Project
        if (project.isCoreProject() || project.isCoFundedProject()) {

          if (!this.isFullSectionEmpty(action, project.getIpOtherContribution())) {
            this.validateContribution(action, project.getIpOtherContribution().getContribution());
            this.validateAdditionalContribution(action, project.getIpOtherContribution().getAdditionalContribution());
            int i = 0;
            for (CRPContribution crp_contribuntion : project.getIpOtherContribution().getCrpContributions()) {
              if (cycle.equals(APConstants.PLANNING_SECTION)) {
                this.validateNatureCollaboration(action, crp_contribuntion.getNatureCollaboration(), i);
              }

              this.validateArchived(action, crp_contribuntion.getExplainAchieved(), i);
              i++;
            }
          }
          // Validate Lessons learn
          // this.validateLessonsLearn(action, project, "otherContributions");

          if (cycle.equals(APConstants.REPORTING_SECTION)) {
            int c = 1;
            for (ProjecteOtherContributions projectOtherContributions : project.getOtherContributions()) {

              try {
                this.validateRegion(action, Integer.parseInt(projectOtherContributions.getRegion()), c);
              } catch (NumberFormatException e) {
                this.validateRegion(action, 0, c);
              }


              try {
                this.validateDescription(action, projectOtherContributions.getDescription(), c);
              } catch (NumberFormatException e) {
                this.validateRegion(action, 0, c);
              }
              try {
                this.validateIndicator(action, Integer.parseInt(projectOtherContributions.getIndicators()), c);
              } catch (NumberFormatException e) {
                this.validateIndicator(action, 0, c);
              }
              c++;
            }

          }


        }

      }

      if (!action.getFieldErrors().isEmpty()) {
        action.addActionError(action.getText("saving.fields.required"));
      } else if (validationMessage.length() > 0) {
        action
          .addActionMessage(" " + action.getText("saving.missingFields", new String[] {validationMessage.toString()}));
      }


      // Saving missing fields.
      this.saveMissingFields(project, cycle, "otherContributions");
    }

  }

  private void validateAdditionalContribution(BaseAction action, String additionalContribution) {
    if (!otherContributionValidator.isValidAdditionalContribution(additionalContribution)) {
      this.addMessage(
        action.getText("planning.impactPathways.otherContributions.additionalcontribution.readText").toLowerCase());
      this.addMissingField("project.ipOtherContribution.additionalcontribution");
    }
  }


  private void validateArchived(BaseAction action, String archived, int i) {

    if (!this.isValidString(archived)) {
      this.addMessage("Achieved outcome contributions");;
      this.addMissingField("project.ipOtherContribution.crps.[" + i + "].achieved");
    }
  }


  private void validateContribution(BaseAction action, String contribution) {
    if (!otherContributionValidator.isValidContribution(contribution)) {
      this.addMessage(action.getText("planning.impactPathways.otherContributions.contribution.readText").toLowerCase());
      this.addMissingField("project.ipOtherContribution.contribution");
    }
  }


  private void validateDescription(BaseAction action, String description, int c) {
    if (!this.isValidString(description)) {
      this.addMessage(action.getText("Project Other Contribution [" + c + "] Description  "));
      this.addMissingField("project.projectOtherContribution.indicator");
    }
  }


  private void validateIndicator(BaseAction action, int indicator, int c) {
    if (!(indicator > 0)) {
      this.addMessage(action.getText("Project Other Contribution [" + c + "] Indicator  "));
      this.addMissingField("project.projectOtherContribution.indicator");
    }
  }

  private void validateNatureCollaboration(BaseAction action, String natureCollaboration, int i) {
    if (!otherContributionValidator.isValidCrpCollaborationNature(natureCollaboration)) {
      this.addMessage(
        action.getText("planning.impactPathways.otherContributions.collaborationNature.readText").toLowerCase());
      this.addMissingField("project.ipOtherContribution.crps.[" + i + "].collaborationNature");
    }

  }

  private void validateRegion(BaseAction action, int region, int c) {
    if (!(region > 0)) {
      this.addMessage(action.getText("Project Other Contribution [" + c + "] Region  "));
      this.addMissingField("project.projectOtherContribution.region");
    }
  }

}
