/*
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
 */

package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.BudgetManagerImpl;
import org.cgiar.ccafs.ap.data.model.Budget;

import com.google.inject.ImplementedBy;

@ImplementedBy(BudgetManagerImpl.class)
public interface BudgetManager {

  /**
   * Get the budget of a given activity.
   * 
   * @param activityID - activity identifier.
   * @return a Budget object.
   */
  public Budget getBudget(int activityID);

  /**
   * Save into the DAO the budget data corresponding to the given activity
   * 
   * @param budget Budget object with the information to save
   * @param activityID - Activity identifier
   * @return true if was successfully saved, false otherwise
   */
  public boolean saveBudget(Budget budget, int activityID);

}
