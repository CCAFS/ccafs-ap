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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLBudgetPercentageDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLBudgetPercentageDAO.class)
public interface BudgetPercentageDAO {

  /**
   * Get the information related to the budget percentage identified by id
   * 
   * @param id - BudgetPercentage identifier
   * @return a map with the information
   */
  public Map<String, String> getBudgetPercentage(String id);

  /**
   * Get the budget percentages list
   * 
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getBudgetPercentages();

}
