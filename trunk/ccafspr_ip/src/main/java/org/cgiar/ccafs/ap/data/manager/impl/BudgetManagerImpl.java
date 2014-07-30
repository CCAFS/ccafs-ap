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
package org.cgiar.ccafs.ap.data.manager.impl;

import java.util.List;
import org.cgiar.ccafs.ap.data.model.Budget;
import org.cgiar.ccafs.ap.data.model.BudgetType;
import org.cgiar.ccafs.ap.data.model.Institution;

import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Héctor Fabio Tobón R.
 */
public class BudgetManagerImpl implements BudgetManager {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(BudgetManagerImpl.class);

  @Inject
  public BudgetManagerImpl() {

  }

  @Override
  public double calculateTotalCCAFSBudget(int projectID) {
    // TODO JG - To complete
    return 0;
  }

  @Override
  public double calculateTotalOverallBudget(int projectID) {
    // TODO JG - To complete
    return 0;
  }

  @Override
  public boolean deleteBudget(int budgetId) {
    // TODO JG - To complete
    return false;
  }

  @Override
  public boolean deleteBudgetsByInstitution(int projectID, int institutionID) {
    // TODO JG - To complete
    return false;
  }

  @Override
  public List<Budget> getBudgetsByType(int projectID, BudgetType type) {
    // TODO JG - To complete
    return null;
  }

  @Override
  public List<Budget> getBudgetsByYear(int projectID, int year) {
    // TODO JG - To complete
    return null;
  }

  @Override
  public List<Institution> getLeveragedInstitutions(int projectID) {
    // TODO JG - To complete
    return null;
  }

  @Override
  public boolean saveBudget(int projectID, Budget budget) {
    // TODO JG - To complete
    return false;
  }


}
