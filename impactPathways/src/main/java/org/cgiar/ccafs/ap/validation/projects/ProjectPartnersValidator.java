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
import org.cgiar.ccafs.ap.data.model.PartnerPerson;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.validation.BaseValidator;
import org.cgiar.ccafs.ap.validation.model.ProjectValidator;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class ProjectPartnersValidator extends BaseValidator {

  private ProjectValidator projectValidator;

  @Inject
  public ProjectPartnersValidator(ProjectValidator projectValidator) {
    super();
    this.projectValidator = projectValidator;
  }

  public void replaceAll(StringBuilder builder, String from, String to) {
    int index = builder.indexOf(from);
    while (index != -1) {
      builder.replace(index, index + from.length(), to);
      index += to.length(); // Move to the end of the replacement
      index = builder.indexOf(from, index);
    }
  }

  public void validate(BaseAction action, Project project, String cycle) {
    if (project != null) {
      if (!action.isReportingCycle()) {
        this.validateProjectJustification(action, project);
      }
      if (!project.getProjectPartners().isEmpty() && (project.isCoreProject() || project.isCoFundedProject())) {
        if (!this.isValidString(project.getProjectPartners().get(0).getOverall())) {
          this.addMessage(
            action.getText("Please provide Partnerships overall performance over the last reporting period"));
          this.addMissingField("project.partners.overall");
        }
      }
      if (project.isCoreProject() || project.isCoFundedProject()) {
        this.validateLessonsLearn(action, project, "partners");
        if (this.validationMessage.toString().contains("Lessons")) {
          this.replaceAll(validationMessage, "Lessons",
            "Lessons regarding partnerships and possible implications for the coming planning cycle");
        }
        this.validateCCAFSProject(action, project);
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
      this.saveMissingFields(project, cycle, "partners");
    }
  }

  private void validateBilateralProject(BaseAction action, Project project) {
    // So far, if this is a bilateral project the only validation needed is the project leader.
    this.validateInstitutionsEmpty(action, project);
    this.validateProjectLeader(action, project);
    this.validateContactPersons(action, project);
  }

  private void validateCCAFSProject(BaseAction action, Project project) {
    this.validateInstitutionsEmpty(action, project);
    this.validateProjectLeader(action, project);
    this.validateContactPersons(action, project);
  }

  /**
   * This method validates all the required fields within contact person.
   * 
   * @param action this action.
   * @param project the project with the partners on it.
   */
  private void validateContactPersons(BaseAction action, Project project) {
    if (project != null) {
      int c = 0, j = 0;
      for (ProjectPartner partner : project.getProjectPartners()) {
        j = 0;
        // Validating that the partner has a least one contact person
        if (partner.getPartnerPersons() == null || partner.getPartnerPersons().isEmpty()) {
          action.addActionMessage(action.getText("planning.projectPartners.contactPersons.empty",
            new String[] {partner.getInstitution().getName()}));
          this.addMissingField("project.partner[" + c + "].contactPersons.empty");
        } else {
          j = 0;
          // iterating all the contact persons.
          for (PartnerPerson person : partner.getPartnerPersons()) {
            this.validatePersonType(action, c, j, person);
            this.validateUser(action, c, j, person);
            this.validatePersonResponsibilities(action, c, j, person);
            j++;
          }
        }
        c++;
      }
    }
  }

  // Validate that an institution is selected.
  private void validateInstitutionsEmpty(BaseAction action, Project project) {
    int c = 0;
    for (ProjectPartner partner : project.getProjectPartners()) {
      if (partner.getInstitution() == null || partner.getInstitution().getId() == -1) {
        if (partner.getPartnerPersons().size() > 0) {
          action.addFieldError("project.projectPartners[" + c + "].institution", action.getText("validation.required",
            new String[] {action.getText("planning.projectPartners.partner.name")}));
          // No need to add missing fields because field error doesn't allow to save into the database.
        }
      }
      c++;
    }
  }

  private void validatePersonResponsibilities(BaseAction action, int partnerCounter, int personCounter,
    PartnerPerson person) {
    if (!projectValidator.isValidPersonResponsibilities(person.getResponsibilities())) {
      if (person.getUser() != null && person.getUser().getId() != -1) {
        this.addMessage(action.getText("planning.projectPartners.responsibilities.for",
          new String[] {person.getUser().getFirstName() + " " + person.getUser().getLastName()}));
      }
      this.addMissingField(
        "project.projectPartners[" + partnerCounter + "].partnerPersons[" + personCounter + "].responsibilities");
    }

  }

  private void validatePersonType(BaseAction action, int partnerCounter, int personCounter, PartnerPerson person) {
    if (person.getType() == null || person.getType().isEmpty()) {
      action.addFieldError("project.projectPartners[" + partnerCounter + "].partnerPersons[" + personCounter + "].type",
        action.getText("validation.required", new String[] {action.getText("planning.projectPartners.personType")}));
      // No need to add missing fields because field error doesn't allow to save into the database.
    }
  }


  private void validateProjectLeader(BaseAction action, Project project) {
    // All projects must specify the project leader
    if (!projectValidator.isValidLeader(project.getLeader(), project.isBilateralProject())) {
      this.addMessage(action.getText("planning.projectPartners.types.PL").toLowerCase());
      this.addMissingField("project.leader");
    }
  }

  private void validateUser(BaseAction action, int partnerCounter, int personCounter, PartnerPerson person) {
    if (person.getUser() == null || person.getUser().getId() == -1) {
      action.addFieldError("partner-" + partnerCounter + "-person-" + personCounter, action
        .getText("validation.required", new String[] {action.getText("planning.projectPartners.contactPersonEmail")}));
      // No need to add missing fields because field error doesn't allow to save into the database.
    }
  }


}
