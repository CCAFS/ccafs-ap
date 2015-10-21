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


  @Inject
  public ProjectBudgetPlanningValidator(ProjectValidator projectValidator, BudgetValidator budgetValidator) {
    this.projectValidator = projectValidator;
    this.budgetValidator = budgetValidator;
  }

  public void validate(BaseAction action, Project project, String cycle) {
    if (project != null) {
      this.validateProjectJustification(action, project);
      if (projectValidator.isValidBudget(project.getBudgets())) {

        if (project.isBilateralProject()) {
          this.validateProjectBudgetsBilateral(action, project.getBudgets(), project);
          if (project.getOverhead().isBilateralCostRecovered() == false) {
            if (!(project.getOverhead().getContractedOverhead() > 0
              && project.getOverhead().getContractedOverhead() <= 100)) {
              this.addMessage("Invalid Overhead Value for " + project.getTitle());
              this.addMissingField("project.overhead.contractedOverhead");

            }

          }
          double totalBudget = project.getTotalBilateralBudget();
          this.validateProjectCofinancing(action, project.getLinkedProjects(), totalBudget);
        } else {
          this.validateProjectBudgetsCore(action, project.getBudgets(), project);
        }

      }
      // The projects will be validated according to their type

      if (validationMessage.length() > 0) {
        action
          .addActionMessage(" " + action.getText("saving.missingFields", new String[] {validationMessage.toString()}));
      }

      this.saveMissingFields(project, "Planning", "budget");
    }
  }

  private void validateProjectBudgetsBilateral(BaseAction action, List<Budget> budgets, Project project) {
    for (Budget budget : budgets) {

      if (budget.getType().isBilateral() && budget.getCofinancingProject() == null) {
        if (!budgetValidator.isValidAmountNoZero(budget.getAmount())) {
          this.addMessage(action.getText("Invalid Annual Budget"));
          this.addMissingField("planning.projectBudget.annualBudget for " + project.getTitle());

        }

        if (!budgetValidator.isValidGenderPercentage(budget.getGenderPercentage())) {
          this.addMessage("Gender % of annual  budget");
          this.addMissingField("planning.projectBudget.annualBudget for " + project.getTitle());
        }


      }


    }

  }

  private void validateProjectBudgetsCore(BaseAction action, List<Budget> budgets, Project project) {
    for (Budget budget : budgets) {
      if (!budgetValidator.isValidAmount(budget.getAmount())) {
        this.addMessage(action.getText("Invalid Annual Budget for " + project.getTitle()));
        this.addMissingField("planning.projectBudget.annualBudget");
      }

      if (!budgetValidator.isValidGenderPercentage(budget.getGenderPercentage())) {
        this.addMessage("Gender % of annual  budget");
        this.addMissingField("planning.projectBudget.annualBudget for " + project.getTitle());
      }


    }

  }


  private void validateProjectCofinancing(BaseAction action, List<Project> linkedProjects, double totalAmount) {

    double totalCofinancing = 0;
    for (Project linkedProjec : linkedProjects) {
      Budget budget = linkedProjec.getAnualContribution();
      // if (budget.getCofinancingProject() != null) {


      if (!budgetValidator.isValidAmountNoZero(budget.getAmount())) {
        this.addMessage("Contribution has a invalid value for " + linkedProjec.getTitle());
        this.addMissingField("planning.projectBudget.annualBudget");
      } else {
        totalCofinancing = totalCofinancing + budget.getAmount();
      }

      // }
    }


    if (!(totalCofinancing <= totalAmount)) {
      this.addMessage("Invalid Distribution for CO-Financing Projects ");
      this.addMissingField("planning.projectBudget.totalCofinancing");
    }

  }
}
