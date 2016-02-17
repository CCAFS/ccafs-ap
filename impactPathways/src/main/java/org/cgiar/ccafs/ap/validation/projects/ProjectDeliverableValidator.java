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
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverableDissemination;
import org.cgiar.ccafs.ap.data.model.DeliverablesRanking;
import org.cgiar.ccafs.ap.data.model.NextUser;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectStatusEnum;
import org.cgiar.ccafs.ap.validation.BaseValidator;
import org.cgiar.ccafs.ap.validation.model.DeliverableValidator;
import org.cgiar.ccafs.ap.validation.model.ProjectValidator;

import java.util.List;

import com.google.inject.Inject;


/**
 * This class will validate all the deliverables in a project. It has two validate methods. One that validates the list
 * of deliverables, and another one that validates a specific deliverable.
 * 
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class ProjectDeliverableValidator extends BaseValidator {

  // Validators
  private ProjectValidator projectValidator;
  private DeliverableValidator deliverableValidator;


  public String cycle;

  @Inject
  public ProjectDeliverableValidator(ProjectValidator projectValidator, DeliverableValidator deliverableValidator) {
    super();
    this.projectValidator = projectValidator;
    this.deliverableValidator = deliverableValidator;
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
  public void validate(BaseAction action, Project project, Deliverable deliverable, String cycle, int indexTab) {
    if (deliverable != null) {
      this.cycle = cycle;
      this.missingFields.setLength(0);

      if (action.isPlanningCycle()) {
        this.validateProjectJustification(action, deliverable);
      }
      if (project.isCoreProject() || project.isCoFundedProject()) {
        if (indexTab == -1) {
          this.validateAsCoreProject(action, project, deliverable);
        } else {
          switch (indexTab) {
            case 0:
              this.validateAsCoreProject(action, project, deliverable);
              break;
            case 1:
              this.validateAsCoreProject(action, project, deliverable);
              break;
            case 2:
              this.validateAsCoreProject(action, project, deliverable);
              break;
            case 3:
              this.validateAsCoreProject(action, project, deliverable);
              break;
            default:
              break;
          }
        }

      } else {
        // Deliverables are not needed, but if there is one added, it will be validated completely.
        // this.validateAsCoreProject(action, project, deliverable);
      }

      if (!action.getFieldErrors().isEmpty()) {
        action.addActionError(action.getText("saving.fields.required"));
      } else if (validationMessage.length() > 0) {
        action
          .addActionMessage(" " + action.getText("saving.missingFields", new String[] {validationMessage.toString()}));
      }
      int i = 0;
      // Saving missing fields.
      this.saveMissingFields(project, deliverable, cycle, "deliverable");
    }
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
      if (!projectValidator.hasDeliverables(project.getDeliverables())) {
        this.addMissingField("projects.deliverables.empty");
        // No need to add a message since we don't have a save button in the deliverableList section.
      }
    } else {
      // Do not validate if the project has deliverables added.
    }
    this.saveMissingFields(project, cycle, "deliverablesList");
  }

  private void validateAsCoreProject(BaseAction action, Project project, Deliverable deliverable) {
    if (deliverable != null) {
      this.validateRequiredFields(action, project, deliverable);
    }
  }

  private void validateComplianceCheck(BaseAction action, DeliverablesRanking ranking, int i) {
    if (ranking.getProcessData() == null) {
      this.addMessage("Deliverable  Ranking  Process of data quality ");
      this.addMissingField("projects.deliverable(" + i + ").ranking.processData");
    }

    if (ranking.getDictionary() == null) {
      this.addMessage("Deliverable  Ranking  Data Dictionary ");
      this.addMissingField("projects.deliverable(" + i + ").ranking.dataDictionary");
    }

    if (ranking.getTooldata() == null) {
      this.addMessage("Deliverable  Ranking  Tool data ");
      this.addMissingField("projects.deliverable(" + i + ").ranking.toolData");
    }
  }

  private void validateDismmination(BaseAction action, DeliverableDissemination dissimination, int i) {

    if (dissimination.getIsOpenAccess() == null) {
      this.addMessage("Deliverable   Is open acess");
      this.addMissingField("projects.deliverable(" + i + ").disimmination.opencAcess");
    }
    if (dissimination.getAlreadyDisseminated() == null) {
      this.addMessage("Deliverable Is Already Disseminated");
      this.addMissingField("projects.deliverable(" + i + ").disimmination.alreadyDisseminated");
    }
  }

  private void validateNextUsers(BaseAction action, Deliverable deliverable, List<NextUser> nextUsers) {
    int c = 0;
    for (NextUser nextUser : nextUsers) {
      // Validating Next User name.
      if (!deliverableValidator.isValidNextUserName(nextUser.getUser())) {
        action.addFieldError("deliverable.nextUsers[" + c + "].user", action.getText("validation.field.required"));
        this.addMessage("projects.deliverable(" + deliverable.getId() + ").nextUser(" + nextUser.getId() + ").user");
      }

      // Validating Knowledge, attitute, skills and practice changes expected
      if (!deliverableValidator.isValidNextUserExpectedChanges(nextUser.getExpectedChanges())) {
        action.addFieldError("deliverable.nextUsers[" + c + "].expectedChanges",
          action.getText("validation.field.required"));
        this.addMessage(
          "projects.deliverable(" + deliverable.getId() + ").nextUser(" + nextUser.getId() + ").expectedChanges");
      }

      // Validating strategies
      if (!deliverableValidator.isValidNextUserStrategies(nextUser.getStrategies())) {
        action.addFieldError("deliverable.nextUsers[" + c + "].strategies",
          action.getText("validation.field.required"));
        this.addMessage(
          "projects.deliverable(" + deliverable.getId() + ").nextUser(" + nextUser.getId() + ").strategies");
      }

      c++;
    }

  }

  private void validateRanking(BaseAction action, DeliverablesRanking ranking, Deliverable deliverable) {
    int i = deliverable.getId();
    if (ranking.getAddress() == null) {
      this.addMessage("Deliverable  Ranking Address gender");
      this.addMissingField("projects.deliverable(" + i + ").ranking.adress");
    }
    if (ranking.getPotential() == null) {
      this.addMessage("Deliverable  Ranking Potential");
      this.addMissingField("projects.deliverable(" + i + ").ranking.potential");
    }
    if (ranking.getLevel() == null) {
      this.addMessage("Deliverable  Ranking  Level of shared ");
      this.addMissingField("projects.deliverable(" + i + ").ranking.leverShared");
    }
    if (ranking.getPersonalPerspective() == null) {
      this.addMessage("Deliverable  Ranking Personal Perspective");
      this.addMissingField("projects.deliverable(" + i + ").ranking.personalPerspective");
    }

    if (deliverable.isDataType()) {
      this.validateComplianceCheck(action, deliverable.getRanking(), deliverable.getId());
    }
  }


  private void validateRequiredFields(BaseAction action, Project project, Deliverable deliverable) {
    this.validateStandar(action, project, deliverable);

    // Validating that deliverable status if is reporting section
    if (cycle.equals(APConstants.REPORTING_SECTION)) {
      if (!deliverableValidator.isValidStatus(deliverable.getStatus())) {
        // action.addFieldError("deliverable.year", action.getText("validation.field.required"));
        this.addMessage("Deliverable Status");
        this.addMissingField("projects.deliverable(" + deliverable.getId() + ").status");
      }

      if (deliverable.getStatus() > 0) {
        switch (ProjectStatusEnum.getValue(deliverable.getStatus())) {
          case Ongoing:
          case Extended:
          case Cancelled:
            if (!this.isValidString(deliverable.getStatusDescription())) {
              // action.addFieldError("deliverable.year", action.getText("validation.field.required"));
              this.addMessage("Deliverable  Status Description");
              this.addMissingField("projects.deliverable(" + deliverable.getId() + ").statusDescription");
            }

            break;

          default:
            break;
        }
      }


      if (deliverable.getRanking() != null) {
        this.validateRanking(action, deliverable.getRanking(), deliverable);
      } else {
        this.addMessage("Deliverable (" + deliverable.getId() + ") Ranking Section");
        this.addMissingField("projects.deliverable(" + deliverable.getId() + ").ranking");
      }


      if (deliverable.getDissemination() != null) {
        this.validateDismmination(action, deliverable.getDissemination(), deliverable.getId());
      } else {
        this.addMessage("Deliverable (" + deliverable.getId() + ") Dissemination Section");
        this.addMissingField("projects.deliverable(" + deliverable.getId() + ").dissemination");
      }


    }
  }

  private void validateStandar(BaseAction action, Project project, Deliverable deliverable) {

    // Validating the title
    if (!deliverableValidator.isValidTitle(deliverable.getTitle())) {
      // action.addFieldError("deliverable.title", action.getText("validation.field.required"));
      this.addMessage("projects.deliverable(" + deliverable.getId() + ").title");
      this.addMissingField("projects.deliverable(" + deliverable.getId() + ").title");
    }

    // Validating that a MOG is selected.
    if (!deliverableValidator.isValidMOG(deliverable.getOutput())) {
      // action.addFieldError("deliverable.output", action.getText("validation.field.required"));
      this.addMessage("projects.deliverable(" + deliverable.getId() + ").output");
      this.addMissingField("projects.deliverable(" + deliverable.getId() + ").output");
    }

    // Validating that a year is selected.
    if (!deliverableValidator.isValidYear(deliverable.getYear())) {
      // action.addFieldError("deliverable.year", action.getText("validation.field.required"));
      this.addMessage("projects.deliverable(" + deliverable.getId() + ").year");
      this.addMissingField("projects.deliverable(" + deliverable.getId() + ").year");
    }

    // Validating that some sub-type is selected.
    if (!deliverableValidator.isValidType(deliverable.getType())) {
      // Indicate problem in the missing field.
      // action.addFieldError("deliverable.type", action.getText("validation.field.required"));
      this.addMessage("projects.deliverable(" + deliverable.getId() + ").type");
      this.addMissingField("projects.deliverable(" + deliverable.getId() + ").type");

    }

    // Validating type other was filled.
    if (!deliverableValidator.isValidTypeOther(deliverable.getType(), deliverable.getTypeOther())) {
      // Indicate problem in the missing field.
      // action.addFieldError("deliverable.typeOther", action.getText("validation.field.required"));
      this.addMessage("projects.deliverable(" + deliverable.getId() + ").typeOther");
      this.addMissingField("projects.deliverable(" + deliverable.getId() + ").typeOther");
    }
    // Deliverables has to have at least one next user.
    // if (cycle.equals(APConstants.PLANNING_SECTION)) {
    if (!deliverableValidator.hasNextUsers(deliverable.getNextUsers())) {
      this.addMessage(action.getText("planning.projectDeliverable.nextUsers.emptyText"));
      this.addMissingField("projects.deliverable(" + deliverable.getId() + ").nextUsers.empty");
    } else {
      // Validate each next user added.
      this.validateNextUsers(action, deliverable, deliverable.getNextUsers());
      // }
    }


    // Validating that the deliverable has a responsible.
    if (!deliverableValidator.hasResponsible(deliverable.getResponsiblePartner())) {
      this.addMessage(action.getText("planning.projectDeliverable.indicateResponsablePartner.readText"));
      this.addMissingField("projects.deliverable(" + deliverable.getId() + ").responsible");
    }

  }


}
