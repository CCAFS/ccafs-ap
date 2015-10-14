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
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.validation.BaseValidator;
import org.cgiar.ccafs.ap.validation.model.BudgetValidator;

import com.google.inject.Inject;

/**
 * @author Christian David Garcia -CIAT/CCAFS
 */
public class ProjectBudgetByMOGValidator extends BaseValidator {


  private BudgetValidator budgetValidator;

  @Inject
  public ProjectBudgetByMOGValidator(BudgetValidator budgetValidator) {

    this.budgetValidator = budgetValidator;
  }


  public void validate(BaseAction action, Project project) {
    if (project != null) {
      if (!project.getBudgets().isEmpty()) {

      } else {
        // TODO La lista esta vacia
      }
    }
  }
}
