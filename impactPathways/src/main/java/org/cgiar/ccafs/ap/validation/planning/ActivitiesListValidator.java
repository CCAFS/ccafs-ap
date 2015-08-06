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
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.LiaisonInstitution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.validation.BaseValidator;
import org.cgiar.ccafs.ap.validation.model.ProjectValidator;

import java.util.Date;
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
    if (project != null) {
      this.validateProjectJustification(action, project);
      this.validateLeader(action, project.getActivities());
      // The projects will be validated according to their type
      // if (project.isCoreProject()) {
      // this.validateCoreProject(action, project);
      // } else {
      // this.validateBilateralProject(action, project);
      // }

      if (validationMessage.length() > 0) {
        action
        .addActionMessage(" " + this.getText("saving.missingFields", new String[] {validationMessage.toString()}));
      }
    }
  }

  public void validateBilateralContractProposalName(BaseAction action, String proposalName) {
    if (!projectValidator.isValidBilateralContractProposalName(proposalName)) {
      // TODO - Add the i18n key
      this.addMessage(this.getText("preplanning.projectDescription.uploadBilateral.readText").toLowerCase());
    }
  }

  private void validateBilateralProject(BaseAction action, Project project) {
    this.validateTitle(action, project.getTitle());
    this.validateStartDate(action, project.getStartDate());
    this.validateEndDate(action, project.getEndDate());
    this.validateBilateralContractProposalName(action, project.getBilateralContractProposalName());
  }

  private void validateCoreProject(BaseAction action, Project project) {
    this.validateTitle(action, project.getTitle());
    this.validateManagementLiaison(action, project.getLiaisonInstitution());
    this.validateLiaisonContactPerson(action, project.getOwner());
    this.validateStartDate(action, project.getStartDate());
    this.validateEndDate(action, project.getEndDate());
    this.validateSummary(action, project.getSummary());
    this.validateRegions(action, project.getRegions());
    this.validateFlagships(action, project.getFlagships());

    if (project.isWorkplanRequired()) {
      projectValidator.isValidProjectWorkplanName(project.getWorkplanName());
    }
  }

  public void validateEndDate(BaseAction action, Date endDate) {
    if (!projectValidator.isValidEndDate(endDate)) {
      this.addMessage(this.getText("preplanning.projectDescription.endDate").toLowerCase());
    }
  }

  public void validateFlagships(BaseAction action, List<IPProgram> flagships) {
    if (!projectValidator.isValidFlagships(flagships)) {
      this.addMessage(this.getText("preplanning.projectDescription.flagships").toLowerCase());
    }
  }

  public void validateLeader(BaseAction action, List<Activity> activities) {
    for (int i = 0; i < activities.size(); i++) {
      if (!projectValidator.isValidLeader(activities.get(i).getLeader())) {
        this.addMessage(this.getText("preplanning.projectDescription.leader").toLowerCase());
      }
    }
  }

  public void validateLiaisonContactPerson(BaseAction action, User user) {
    if (!projectValidator.isValidOwner(user)) {
      this.addMessage(this.getText("preplanning.projectDescription.projectownercontactperson").toLowerCase());
    }
  }

  public void validateManagementLiaison(BaseAction action, LiaisonInstitution institution) {
    if (!projectValidator.isValidLiaisonInstitution(institution)) {
      this.addMessage(this.getText("planning.projectDescription.programCreator").toLowerCase());
    }
  }

  public void validateRegions(BaseAction action, List<IPProgram> regions) {
    if (!projectValidator.isValidRegions(regions)) {
      this.addMessage(this.getText("preplanning.projectDescription.regions").toLowerCase());
    }
  }

  public void validateStartDate(BaseAction action, Date startDate) {
    if (!projectValidator.isValidStartDate(startDate)) {
      this.addMessage(this.getText("preplanning.projectDescription.startDate").toLowerCase());
    }
  }

  public void validateSummary(BaseAction action, String summary) {
    if (!projectValidator.isValidSummary(summary)) {
      this.addMessage(this.getText("preplanning.projectDescription.projectSummary").toLowerCase());
    }
  }

  public void validateTitle(BaseAction action, String title) {
    if (!projectValidator.isValidTitle(title)) {
      this.addMessage(this.getText("planning.projectDescription.projectTitle").toLowerCase());
    }
  }
}
