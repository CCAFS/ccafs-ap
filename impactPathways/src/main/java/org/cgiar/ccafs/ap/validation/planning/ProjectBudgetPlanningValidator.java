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
import org.cgiar.ccafs.ap.data.model.Budget;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.validation.BaseValidator;
import org.cgiar.ccafs.ap.validation.model.BudgetValidator;
import org.cgiar.ccafs.ap.validation.model.ProjectValidator;

import java.util.List;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class ProjectBudgetPlanningValidator extends BaseValidator {

  private ProjectValidator projectValidator;
  private BudgetValidator budgetValidator;
  private double TotalAnnualAmountBilateral = 0;

  @Inject
  public ProjectBudgetPlanningValidator(ProjectValidator projectValidator, BudgetValidator budgetValidator) {
    this.projectValidator = projectValidator;
    this.budgetValidator = budgetValidator;
  }

  public void validate(BaseAction action, Project project, String cycle) {
    if (project != null) {
      this.validateProjectJustification(action, project);

      // The projects will be validated according to their type
      if (!project.isBilateralProject()) {
        if (projectValidator.isValidBudget(project.getBudgets())) {
          this.validateProjectBudgets(action, project.getBudgets());
        }
      } else {

        // It is a Core o CO-Funded Project
        this.validateProjectBudgets(action, project.getBudgets());
      }

      if (validationMessage.length() > 0) {
        action
          .addActionMessage(" " + action.getText("saving.missingFields", new String[] {validationMessage.toString()}));
      }

      this.saveMissingFields(project, "Planning", "budgetByPartner");
    }
  }

  private void validateProjectBudgets(BaseAction action, List<Budget> budgets) {
    for (Budget budget : budgets) {
      if (!budgetValidator.isValidAmount(budget.getAmount())) {
        this.addMessage(action.getText("planning.projectBudget.annualBudget"));
        this.addMissingField("planning.projectBudget.annualBudget");
      }

      if (!budgetValidator.isValidGenderPercentage(budget.getGenderPercentage())) {
        this.addMessage(action.getText("planning.projectBudget.genderPercentage"));
        this.addMissingField("planning.projectBudget.annualBudget");
      }
    }

  }
}
