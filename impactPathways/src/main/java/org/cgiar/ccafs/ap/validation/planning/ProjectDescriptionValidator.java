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
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class ProjectDescriptionValidator extends BaseValidator {

  private ProjectValidator projectValidator;

  @Inject
  public ProjectDescriptionValidator(ProjectValidator projectValidator) {
    super();
    this.projectValidator = projectValidator;
  }

  public void validate(BaseAction action, Project project, String cycle) {
    if (project != null) {
      this.validateProjectJustification(action, project);
      // Required fields are required for all type of projects.
      this.validateRequiredFields(action, project);

      // The projects will be validated according to their type.
      if (project.isCoreProject() || project.isCoFundedProject()) {
        this.validateCoreProject(action, project);
      } else {
        this.validateBilateralProject(action, project);
      }

      if (!action.getFieldErrors().isEmpty()) {
        action.addActionError(action.getText("saving.fields.required"));
      } else if (validationMessage.length() > 0) {
        action
          .addActionMessage(" " + action.getText("saving.missingFields", new String[] {validationMessage.toString()}));
      }

      // Saving missing fields.
      this.saveMissingFields(project, cycle, "description");
    }
  }

  public void validateBilateralContractProposalName(BaseAction action, String proposalName) {
    if (!projectValidator.isValidBilateralContractProposalName(proposalName)) {
      this.addMessage(action.getText("preplanning.projectDescription.uploadBilateral.readText").toLowerCase());
      this.addMissingField("project.bilateralContractProposalName");
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
      if (!projectValidator.isValidProjectWorkplanName(project.getWorkplanName())) {
        this
          .addMessage(action.getText("preplanning.projectDescription.isRequiredUploadworkplan.readText").toLowerCase());
        this.addMissingField("project.workplanName");
      }
    }
  }

  private void validateEndDate(BaseAction action, Date endDate) {
    if (!projectValidator.isValidEndDate(endDate)) {
      this.addMessage(action.getText("preplanning.projectDescription.endDate").toLowerCase());
      this.addMissingField("project.endDate");
    }
  }

  private void validateFlagships(BaseAction action, List<IPProgram> flagships) {
    if (!projectValidator.isValidFlagships(flagships)) {
      this.addMessage(action.getText("preplanning.projectDescription.flagships").toLowerCase());
      this.addMissingField("project.regions");
    }
  }

  private void validateLiaisonContactPerson(BaseAction action, User user) {
    if (!projectValidator.isValidOwner(user)) {
      this.addMessage(action.getText("preplanning.projectDescription.projectownercontactperson").toLowerCase());
      this.addMissingField("project.owner");
    }
  }

  private void validateManagementLiaison(BaseAction action, LiaisonInstitution institution) {
    if (!projectValidator.isValidLiaisonInstitution(institution)) {
      this.addMessage(action.getText("planning.projectDescription.programCreator").toLowerCase());
      this.addMissingField("project.liaisonInstitution");
    }
  }

  private void validateRegions(BaseAction action, List<IPProgram> regions) {
    if (!projectValidator.isValidRegions(regions)) {
      this.addMessage(action.getText("preplanning.projectDescription.regions").toLowerCase());
      this.addMissingField("project.flagships");
    }
  }

  private void validateRequiredFields(BaseAction action, Project project) {
    // Validating Management Liaison (Project owner).
    if (project.getOwner() == null) {
      action.addFieldError("project.owner", action.getText("validation.field.required"));
      this.addMissingField("project.owner");
    }

    // Liaison institution
    if (project.getLiaisonInstitution() == null) {
      action.addFieldError("project.liaisonInstitution", action.getText("validation.field.required"));
      this.addMissingField("project.liaisonInstitution");
    }
  }

  private void validateStartDate(BaseAction action, Date startDate) {
    if (!projectValidator.isValidStartDate(startDate)) {
      this.addMessage(action.getText("preplanning.projectDescription.startDate").toLowerCase());
      this.addMissingField("project.startDate");
    }
  }

  private void validateSummary(BaseAction action, String summary) {
    if (!projectValidator.isValidSummary(summary)) {
      this.addMessage(action.getText("preplanning.projectDescription.projectSummary").toLowerCase());
      this.addMissingField("project.summary");
    }
  }

  private void validateTitle(BaseAction action, String title) {
    if (!projectValidator.isValidTitle(title)) {
      this.addMessage(action.getText("planning.projectDescription.projectTitle").toLowerCase());
      this.addMissingField("project.title");
    }
  }
}
