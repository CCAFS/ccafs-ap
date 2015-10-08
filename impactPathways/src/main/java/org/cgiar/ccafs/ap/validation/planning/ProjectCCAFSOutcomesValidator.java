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
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.validation.BaseValidator;
import org.cgiar.ccafs.ap.validation.model.ProjectValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


/**
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class ProjectCCAFSOutcomesValidator extends BaseValidator {

  private ProjectValidator projectValidator;
  private Map<IPElement, List<IPIndicator>> indicatorsMap; // Outcomes vs indicators
  private Map<Integer, Boolean> yearsToValidate;;

  @Inject
  public ProjectCCAFSOutcomesValidator(ProjectValidator projectValidator) {
    super();
    this.projectValidator = projectValidator;

    this.yearsToValidate = new HashMap<>();

  }

  // This method populates the maps. The idea is to have the information organized per outcome. In that way it is easier
  // to validate.
  private void populateOutcomeMaps(BaseAction action, Project project) {
    indicatorsMap = new HashMap<>();
    for (IPIndicator indicator : project.getIndicators()) {
      // Insert new Outcome if it doesn't exist.
      if (indicator.getId() != 0) {
        if (indicatorsMap.get(indicator.getOutcome()) == null) {
          indicatorsMap.put(indicator.getOutcome(), new ArrayList<IPIndicator>());
        }
        // Adding the indicator to the list.
        indicatorsMap.get(indicator.getOutcome()).add(indicator);
      }
    }
  }

  public void validate(BaseAction action, Project project, String cycle) {
    if (project != null) {
      // Projects that are Core, Co-Funded and Bilateral stand-alone needs to fill this section.
      if (project.isCoreProject() || project.isCoFundedProject() || project.isBilateralStandAlone()) {
        this.validateProjectJustification(action, project);
        this.validateLessonsLearn(action, project, "ccafsOutcomes");
        this.populateOutcomeMaps(action, project);
        this.validateCoreProject(action, project);
      } else {
        // If project is bilateral but is co-financing other core projects, thus, there is not need to validate this
        // section.
      }

      if (action.getActionErrors().isEmpty()) {
        if (!action.getFieldErrors().isEmpty()) {
          action.addActionError(action.getText("saving.fields.required"));
        } else if (validationMessage.length() > 0) {
          action.addActionMessage(
            " " + action.getText("saving.missingFields", new String[] {validationMessage.toString()}));
        }
      }

      // Saving missing fields.
      this.saveMissingFields(project, cycle, "ccafsOutcomes");
    }
  }

  private void validateCoreProject(BaseAction action, Project project) {
    this.validateIndicators(action, project);
    this.validateMOGs(action, project);
  }

  // This method validates the indicators selected in the ccafs outcomes section.
  private void validateIndicators(BaseAction action, Project project) {
    if (projectValidator.isValidIndicators(project.getIndicators())) {
      int c = 0; // Not the best solution with this counter. But at least it works.
      // Looping the map.
      for (IPElement outcome : indicatorsMap.keySet()) {
        // getting the outcome acronym
        StringBuilder outcomeAcronym = new StringBuilder();
        outcomeAcronym.append(outcome.getProgram().getAcronym());
        outcomeAcronym.append(" - ");
        outcomeAcronym.append(outcome.getType().getName());
        if (indicatorsMap.get(outcome).isEmpty()) {
          action.addActionError(action.getText("planning.projectImpactPathways.outcome.indicators.empty",
            new String[] {outcomeAcronym.toString()}));
          // setting the missing field and writing it in parenthesis because we are referring to the outcome id.
          this.addMissingField("project.outcome(" + outcome.getId() + ").indicators.empty");
        } else {
          // Populating years to validate.
          yearsToValidate.put(config.getPlanningCurrentYear(), false); // 2016
          yearsToValidate.put(config.getPlanningCurrentYear() + 1, false); // 2017
          yearsToValidate.put(config.getMidOutcomeYear(), false); // 2019

          for (IPIndicator indicator : indicatorsMap.get(outcome)) {
            // Validate only those indicators for 2016, 2017 and 2019.
            if (yearsToValidate.keySet().contains(indicator.getYear())) {
              this.validateTargetValue(action, indicator.getTarget(), c);
              this.validateTargetNarrative(action, indicator.getDescription(), outcomeAcronym.toString(),
                indicator.getYear(), c);

              // Marking that the year was validated.
              if (yearsToValidate.get(indicator.getYear()) != null) {
                yearsToValidate.put(indicator.getYear(), true);
              }
            }
            c++;
          }

          // Checking that all the years were validated as could happen that a project doesn't have all the indicators
          // for all the years.
          for (int year : yearsToValidate.keySet()) {
            if (!yearsToValidate.get(year)) {
              this.addMissingField("project.outcome(" + outcome.getId() + ").indicator(" + year + ").empty");
            }
          }
        }
      }
    } else {
      action.addActionError(action.getText("planning.projectImpactPathways.indicators.empty"));
      this.addMissingField("project.indicators.empty");
    }
  }

  // For now, we are just validating that at least there is a MOPG selected.
  private void validateMOGs(BaseAction action, Project project) {
    if (!projectValidator.isValidOutputs(project.getOutputs())) {
      this.addMessage(action.getText("planning.projectImpactPathways.outputs.empty"));
      this.addMissingField("project.outputs.empty");
    }

  }

  private void validateTargetNarrative(BaseAction action, String targetNarrative, String outcomeAcronym, int year,
    int c) {
    if (!projectValidator.isValidTargetNarrative(targetNarrative)) {
      this.addMessage("Target narrative for '" + outcomeAcronym + "' in '" + year + "' year");
      this.addMissingField("project.indicators[" + c + "].description");
    }
  }

  private void validateTargetValue(BaseAction action, String targetValue, int c) {
    if (!projectValidator.isValidTargetValue(targetValue)) {
      action.addFieldError("project.indicators[" + c + "].target", action.getText("validation.number.format"));
      this.addMissingField("project.indicators[" + c + "].target");
    }

  }


}
