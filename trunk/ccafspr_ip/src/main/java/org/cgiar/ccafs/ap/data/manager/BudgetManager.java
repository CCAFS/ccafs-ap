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

import java.util.List;

import org.cgiar.ccafs.ap.data.model.Institution;

import org.cgiar.ccafs.ap.data.model.BudgetType;
import org.cgiar.ccafs.ap.data.model.Budget;
import org.cgiar.ccafs.ap.data.manager.impl.BudgetManagerImpl;
import com.google.inject.ImplementedBy;

/**
 * @author Héctor Fabio Tobón R.
 * @author Javier Andrés Gallego
 */
@ImplementedBy(BudgetManagerImpl.class)
public interface BudgetManager {

  /**
   * This method gets all the budget information that belongs to the same type of a specific project.
   * Type can be Window 1, Window 2, Window 3, Bilateral or Leveraged.
   *
   * @param projectID is the id that identifies the project.
   * @param type is the type of budget see ({@link BudgetType}
   * @return a list of Budget objects.
   */
  public List<Budget> getBudgetsByType(int projectID, BudgetType type);

  /**
   * This method gets all the budget information that belongs to a project in a specific year.
   *
   * @param projectID is the project identifier.
   * @param year is the year.
   * @return a List of Budget objects.
   */
  public List<Budget> getBudgetsByYear(int projectID, int year);

  /**
   * This method returns all the institutions that have leveraged funds with the specified project.
   * 
   * @param projectID is the project id.
   * @return a List of Institution Objects.
   */
  public List<Institution> getLeveragedInstitutions(int projectID);

  /**
   * This method calculates the total of the CCAFS Budget which is the addition of W1+W2+W3
   *
   * @param projectID is the project id.
   * @return a decimal number representing the amount of the total CCAFS Budget for that specific project.
   */
  public double getTotalCCAFSBudget(int projectID);

  /**
   * This method returns the total Budget used for that specific project.
   * It consists in the addition of all Windows, plus the Bilateral and the Leveraged.
   *
   * @param projectID is the project id.
   * @return a decimal number that represents the total amount of money used on that specific project.
   */
  public double getTotalOverallBudget(int projectID);


}
