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

package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLBudgetByMogDAO;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

@ImplementedBy(MySQLBudgetByMogDAO.class)
public interface BudgetByMogDAO {

  /**
   * This method returns the budget contributions assigned for each project's output
   * 
   * @param projectID - Project identifier
   * @return a list of projectBudget objects.
   */
  public List<Map<String, String>> getProjectOutputsBudget(int projectID);

  /**
   * This method saves the budget contributions assigned to each project's output.
   * 
   * @param project - A project object containing the budget by outputs info.
   * @param user - The user who is making the change
   * @param justification
   * @return true if all the information was saved successfully. False otherwise.
   */
  public boolean saveProjectOutputsBudget(Project project, User user, String justification);
}
