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
package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.ProjectOutcomeManagerImpl;
import org.cgiar.ccafs.ap.data.model.BudgetType;
import org.cgiar.ccafs.ap.data.model.ProjectOutcome;

import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andrés Gallego
 */
@ImplementedBy(ProjectOutcomeManagerImpl.class)
public interface ProjectOutcomeManager {


  /**
   * This method removes a specific budget value from the database.
   * 
   * @param projectOutcomeID is the budget identifier.
   * @return true if the budget was successfully deleted, false otherwise.
   */
  public boolean deleteProjectOutcomeById(int projectOutcomeID);

  /**
   * This method removes a set of budgets that belongs to a specific project and institution.
   * 
   * @param projectID is the project identifier.
   * @param institutionID is the institution identifier.
   * @return true if the set of budgets were successfully deleted, false otherwise.
   */
  public boolean deleteProjectOutcomesByProject(int projectID);

  /**
   * This method gets all the budget information that belongs to a project in a specific year.
   * 
   * @param projectID is the project identifier.
   * @param year is the year.
   * @return a List of Budget objects.
   */
  public ProjectOutcome getProjectOutcomeByYear(int projectID, int year);

  /**
   * This method gets all the budget information that belongs to the same type of a specific project.
   * Type can be Window 1, Window 2, Window 3, Bilateral or Leveraged.
   * 
   * @param projectID is the id that identifies the project.
   * @param type is the type of budget see ({@link BudgetType}
   * @return a list of Budget objects.
   */
  public Map<String, ProjectOutcome> getProjectOutcomesByProject(int projectID);

  /**
   * This method saves the information of the given budget that belong to a specific project into the database.
   * 
   * @param projectID is the project identifier.
   * @param budget is an object that represents a budget.
   * @return true if the budget was saved successfully, false otherwise.
   */
  public boolean saveProjectOutcome(int projectID, ProjectOutcome projectOutcomeData);


}
