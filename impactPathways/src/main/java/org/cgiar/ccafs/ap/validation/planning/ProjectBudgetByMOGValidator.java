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
import org.cgiar.ccafs.ap.data.model.OutputBudget;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.validation.BaseValidator;

import com.google.inject.Inject;

/**
 * @author Christian David Garcia -CIAT/CCAFS
 */
public class ProjectBudgetByMOGValidator extends BaseValidator {


  @Inject
  public ProjectBudgetByMOGValidator() {


  }


  public void validate(BaseAction action, Project project, String cycle) {
    double ccafsBudgetTotalPorcentage = 0;
    double bilateralBudgeTotalPorcentage = 0;
    double ccafsBudgetGenderPorcentage = 0;
    double bilateralBudgeGenderPorcentage = 0;
    if (project != null) {
      for (OutputBudget budgetbyMog : project.getOutputsBudgets()) {
        if (budgetbyMog.getType().isCCAFSBudget()) {
          ccafsBudgetTotalPorcentage = ccafsBudgetTotalPorcentage + budgetbyMog.getTotalContribution();
          ccafsBudgetGenderPorcentage = ccafsBudgetGenderPorcentage + budgetbyMog.getGenderContribution();
        }
        if (budgetbyMog.getType().isBilateral()) {
          bilateralBudgeTotalPorcentage = bilateralBudgeTotalPorcentage + budgetbyMog.getTotalContribution();
          bilateralBudgeGenderPorcentage = bilateralBudgeGenderPorcentage + budgetbyMog.getGenderContribution();
        }
      }

      if (project.isCoreProject() || project.isCoFundedProject()) {
        if (!(ccafsBudgetTotalPorcentage == 100 && ccafsBudgetGenderPorcentage == 100)) {
          this.addMessage(("Invalid, Percentage Distribution"));
          this.addMissingField("project.budgetbyMog.invalidPorcentage");
        }
      }
      if (project.isBilateralProject()) {
        if (!(bilateralBudgeGenderPorcentage == 100 && bilateralBudgeTotalPorcentage == 100)) {
          this.addMessage(("Invalid, Percentage Distribution"));
          this.addMissingField("project.budgetbyMog.invalidPorcentage");
        }
      }
    }
    if (validationMessage.length() > 0) {
      action
        .addActionMessage(" " + action.getText("saving.missingFields", new String[] {validationMessage.toString()}));
    }
    // Saving missing fields
    this.saveMissingFields(project, cycle, "budgetByMog");
  }
}
