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
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.model.OutputBudget;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.validation.BaseValidator;
import org.cgiar.ccafs.ap.validation.model.BudgetValidator;

import com.google.inject.Inject;

/**
 * @author Christian David Garcia -CIAT/CCAFS
 */
public class ProjectBudgetByMOGValidator extends BaseValidator {

  private BudgetManager budgetManager;
  private BudgetValidator budgetValidator;

  @Inject
  public ProjectBudgetByMOGValidator(BudgetValidator budgetValidator, BudgetManager budgetManager) {

    this.budgetValidator = budgetValidator;
    this.budgetManager = budgetManager;
  }

  public void validate(BaseAction action, Project project, String cycle) {
    double ccafsBudgetTotalPorcentage = 0;
    double bilateralBudgeTotalPorcentage = 0;
    double ccafsBudgetGenderPorcentage = 0;
    double ccafsBudgetByYear = 0;
    double bilateralBudgetByYear = 0;
    double bilateralBudgeGenderPorcentage = 0;
    if (project != null) {

      int year = config.getPlanningCurrentYear();
      ccafsBudgetByYear = budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(), 1, year);
      bilateralBudgetByYear = budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(), 2, year);

      if (project.isCoreProject() || project.isCoFundedProject() || project.isBilateralStandAlone()) {

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
          if ((ccafsBudgetTotalPorcentage != 100) && ccafsBudgetByYear > 0) {
            this.addMessage(("Invalid Percentage Distribution  W1/W2 "));
            this.addMissingField("project.budgetbyMog.invalidPorcentage");
          }
        }
        if (project.isBilateralProject()) {
          if (bilateralBudgeTotalPorcentage != 100 && bilateralBudgetByYear > 0) {
            this.addMessage(("Invalid Percentage Distribution W3/Bilateral budge"));
            this.addMissingField("project.budgetbyMog.invalidPorcentage");
          }
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
