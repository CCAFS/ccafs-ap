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
import org.cgiar.ccafs.ap.data.model.CRP;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.validation.BaseValidator;
import org.cgiar.ccafs.ap.validation.model.OtherContributionValidator;
import org.cgiar.ccafs.ap.validation.model.ProjectValidator;

import java.util.List;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class ProjectIPOtherContributionValidator extends BaseValidator {

  private static final long serialVersionUID = -3912724967470718068L;
  private ProjectValidator projectValidator;
  private OtherContributionValidator otherContributionValidator;

  @Inject
  public ProjectIPOtherContributionValidator(ProjectValidator projectValidator,
    OtherContributionValidator otherContributionValidator) {
    this.projectValidator = projectValidator;
    this.otherContributionValidator = otherContributionValidator;
  }

  public void validate(BaseAction action, Project project) {
    if (project != null) {
      this.validateProjectJustification(action, project);

      // Validate only if the project is CCAFS Core
      if (project.isCoreProject()) {

        this.validateContribution(action, project.getIpOtherContribution().getContribution());
        this.validateAdditionalContribution(action, project.getIpOtherContribution().getAdditionalContribution());
        // this.validateCrpCollaborationNature(action, project.getIpOtherContribution().getCrpCollaborationNature());
        // this.validateCrpContributions(action, project.getCrpContributions());
      }

      if (validationMessage.length() > 0) {
        String msg = " " + action.getText("saving.missingFields", new String[] {validationMessage.toString()});
        action.addActionMessage(msg);
      }
    }
  }

  private void validateAdditionalContribution(BaseAction action, String additionalContribution) {
    if (!otherContributionValidator.isValidAdditionalContribution(additionalContribution)) {
      this.addMessage(action.getText("planning.impactPathways.otherContributions.additionalcontribution.readText")
        .toLowerCase());
    }
  }

  private void validateContribution(BaseAction action, String contribution) {
    if (!otherContributionValidator.isValidContribution(contribution)) {
      this.addMessage(action.getText("planning.impactPathways.otherContributions.contribution.readText").toLowerCase());
    }
  }

  private void validateCrpCollaborationNature(BaseAction action, String crpCollaborationNature) {
    if (!otherContributionValidator.isValidContribution(crpCollaborationNature)) {
      this.addMessage(action.getText("planning.impactPathways.otherContributions.collaborationNature.readText")
        .toLowerCase());
    }
  }

  private void validateCrpContributions(BaseAction action, List<CRP> crpContributions) {
    if (!projectValidator.isValidCrpContributions(crpContributions)) {
      this.addMessage(action.getText("planning.impactPathways.otherContributions.collaboratingCRPs.readText")
        .toLowerCase());
    }

  }
}
