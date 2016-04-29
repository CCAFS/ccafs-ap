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
import org.cgiar.ccafs.ap.data.model.ProjectEvaluation;
import org.cgiar.ccafs.ap.validation.BaseValidator;

import com.google.inject.Inject;

/**
 * @author Hermes Jimenez - CIAT/CCAFS
 */
public class ProjectEvaluationValidator extends BaseValidator {

  private ProjectEvaluation projectEvaluation;
  public boolean hasErrors = true;

  @Inject
  public ProjectEvaluationValidator(ProjectEvaluation projectEvaluation) {
    super();
    this.projectEvaluation = projectEvaluation;
  }

  /**
   * Validate if the Project evaluation information is correct
   * 
   * @param action - is the ActionSuport object, normally used the base action
   * @param project - a project object
   * @param projectEvaluation - is a project evaluation information to validate
   * @param cycle - is the cycle of the action, in this case is Reporting
   */
  public void validate(BaseAction action, Project project, ProjectEvaluation projectEvaluation, String cycle) {
    if (projectEvaluation != null) {
      hasErrors = false;
      // validate the correct values depend of the atribute (String, number, date...)
      this.validateRankingOutputs(action, projectEvaluation);
      this.validateRankingOutcomes(action, projectEvaluation);
      this.validateRankingResponseTeam(action, projectEvaluation);
      this.validateRankingPartCom(action, projectEvaluation);
      this.validateRankingQuality(action, projectEvaluation);
      this.validateGeneralComments(action, projectEvaluation);
      this.validateCommunicationProducts(action, projectEvaluation);
      this.validateAnyActionRequeried(action, projectEvaluation);
      this.validateProjectHighlights(action, projectEvaluation);
      this.validateOutcomeCaseStudies(action, projectEvaluation);
      this.validateRecommendations(action, projectEvaluation);

      // Get the field specific errors associated with this action.
      if (!action.getFieldErrors().isEmpty()) {
        action.addActionError(action.getText("saving.fields.required"));
      } else if (validationMessage.length() > 0) {
        action
          .addActionMessage(" " + action.getText("saving.missingFields", new String[] {validationMessage.toString()}));
      }

      // Saving missing fields
      this.saveMissingFields(project, cycle, "Evaluation");
    }
  }

  /**
   * validate Any Action Requeried field if is a valid String value.
   * 
   * @param action - is the ActionSuport object, normally used the base action
   * @param projectEvaluation - is a project evaluation information to validate
   */
  private void validateAnyActionRequeried(BaseAction action, ProjectEvaluation projectEvaluation) {
    if (!this.isValidString(projectEvaluation.getAnyActionRequeried())) {
      hasErrors = true;
      this.addMessage("Any Action Requeried");
    }
  }

  /**
   * validate Communication Products field if is a valid String value
   * 
   * @param action - is the ActionSuport object, normally used the base action
   * @param projectEvaluation - is a project evaluation information to validate
   */
  private void validateCommunicationProducts(BaseAction action, ProjectEvaluation projectEvaluation) {
    if (!this.isValidString(projectEvaluation.getCommunicationProducts())) {
      hasErrors = true;
      this.addMessage("Communication Products");
    }
  }

  /**
   * validate General Comments field if is a valid String value
   * 
   * @param action - is the ActionSuport object, normally used the base action
   * @param projectEvaluation - is a project evaluation information to validate
   */
  private void validateGeneralComments(BaseAction action, ProjectEvaluation projectEvaluation) {
    if (!this.isValidString(projectEvaluation.getGeneralComments())) {
      hasErrors = true;
      this.addMessage("General Comments");
    }
  }

  /**
   * validate Outcome Case Studies field if is a valid String value
   * 
   * @param action - is the ActionSuport object, normally used the base action
   * @param projectEvaluation - is a project evaluation information to validate
   */
  private void validateOutcomeCaseStudies(BaseAction action, ProjectEvaluation projectEvaluation) {
    if (!this.isValidString(projectEvaluation.getOutcomeCaseStudies())) {
      hasErrors = true;
      this.addMessage("Outcome Case Studies");
    }
  }

  /**
   * validate Project Highlights field if is a valid String value
   * 
   * @param action - is the ActionSuport object, normally used the base action
   * @param projectEvaluation - is a project evaluation information to validate
   */
  private void validateProjectHighlights(BaseAction action, ProjectEvaluation projectEvaluation) {
    if (!this.isValidString(projectEvaluation.getProjectHighlights())) {
      hasErrors = true;
      this.addMessage("Project Highlights");
    }
  }

  /**
   * validate Ranking Outcomes field if is a valid number value,
   * in this case the number must be greater than zero
   * 
   * @param action - is the ActionSuport object, normally used the base action
   * @param projectEvaluation - is a project evaluation information to validate
   */
  private void validateRankingOutcomes(BaseAction action, ProjectEvaluation projectEvaluation) {
    if (!(projectEvaluation.getRankingOutcomes() > 0)) {
      hasErrors = true;
      this.addMessage("Ranking Outcomes");
    }
  }

  /**
   * validate Ranking Outputs field if is a valid number value,
   * in this case the number must be greater than zero
   * 
   * @param action - is the ActionSuport object, normally used the base action
   * @param projectEvaluation - is a project evaluation information to validate
   */
  private void validateRankingOutputs(BaseAction action, ProjectEvaluation projectEvaluation) {
    if (!(projectEvaluation.getRankingOutputs() > 0)) {
      hasErrors = true;
      this.addMessage("Ranking Outputs");
    }
  }

  /**
   * validate Ranking Partenrship Comunnication field if is a valid number value,
   * in this case the number must be greater than zero
   * 
   * @param action - is the ActionSuport object, normally used the base action
   * @param projectEvaluation - is a project evaluation information to validate
   */
  private void validateRankingPartCom(BaseAction action, ProjectEvaluation projectEvaluation) {
    if (!(projectEvaluation.getRankingParternshipComunnication() > 0)) {
      hasErrors = true;
      this.addMessage("Ranking Parternship Comunnication");
    }
  }

  /**
   * validate Ranking Quality field if is a valid number value,
   * in this case the number must be greater than zero
   * 
   * @param action - is the ActionSuport object, normally used the base action
   * @param projectEvaluation - is a project evaluation information to validate
   */
  private void validateRankingQuality(BaseAction action, ProjectEvaluation projectEvaluation) {
    if (!(projectEvaluation.getRankingQuality() > 0)) {
      hasErrors = true;
      this.addMessage("Ranking Quality");
    }
  }

  /**
   * validate Ranking Response Team field if is a valid number value,
   * in this case the number must be greater than zero
   * 
   * @param action - is the ActionSuport object, normally used the base action
   * @param projectEvaluation - is a project evaluation information to validate
   */
  private void validateRankingResponseTeam(BaseAction action, ProjectEvaluation projectEvaluation) {
    if (!(projectEvaluation.getRankingResponseTeam() > 0)) {
      hasErrors = true;
      this.addMessage("Ranking Response Team");
    }
  }

  /**
   * validate Recommendations field if is a valid String value
   * 
   * @param action - is the ActionSuport object, normally used the base action
   * @param projectEvaluation - is a project evaluation information to validate
   */
  private void validateRecommendations(BaseAction action, ProjectEvaluation projectEvaluation) {
    if (!this.isValidString(projectEvaluation.getRecommendations())) {
      hasErrors = true;
      this.addMessage("Recommendations");
    }
  }


}