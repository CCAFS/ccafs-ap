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

package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLBudgetDAO;

import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLBudgetDAO.class)
public interface BudgetDAO {

  /**
   * Get the budget data of a given activity.
   * 
   * @param activityID - activity identifier.
   * @return the Map with the budget data or null
   *         if there is no budget.
   */
  public Map<String, String> getBudget(int activityID);

  /**
   * Save budget information into the DAO
   * 
   * @param budgetData - data to be saved
   * @return true if the data was successfully saved, false otherwise.
   */
  public boolean saveBudget(Map<String, String> budgetData);

}
