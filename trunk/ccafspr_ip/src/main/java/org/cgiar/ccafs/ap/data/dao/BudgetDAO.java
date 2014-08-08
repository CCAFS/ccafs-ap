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
 * ***************************************************************
 */
package org.cgiar.ccafs.ap.data.dao;

/**
 * @author Javier Andrés Gallego
 * @author Héctor Fabio Tobón R.
 */
import org.cgiar.ccafs.ap.data.dao.mysql.MySQLBudgetDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLBudgetDAO.class)
public interface BudgetDAO {


  /**
   * This method calculates the total of the CCAFS Budget which is the addition of W1+W2+W3+BILATERAL
   * 
   * @param projectID is the project id.
   * @return a decimal number representing the amount of the total CCAFS Budget for that specific project, if no data
   *         found return -1.0.
   */
  public double calculateTotalCCAFSBudget(int projectID);

  /**
   * This method calculates the total of the CCAFS Budget which is the addition of W1+W2+W3+BILATERAL and a given year
   * 
   * @param projectID is the project id.
   * @return a decimal number representing the amount of the total CCAFS Budget for that specific project, if no data
   *         found return -1.0..
   */
  public double calculateTotalCCAFSBudgetByYear(int projectID, int year);

  /**
   * This method returns the total Budget used for that specific project.
   * It consists in the addition of all Windows, plus the Bilateral and the Leveraged.
   * 
   * @param projectID is the project id.
   * @return a decimal number that represents the total amount of money used on that specific project, if no data found
   *         return -1.0..
   */
  public double calculateTotalOverallBudget(int projectID);

  /**
   * This method returns the total Budget used for that specific project and a given year.
   * It consists in the addition of all Windows, plus the Bilateral and the Leveraged.
   * 
   * @param projectID is the project id.
   * @return a decimal number that represents the total amount of money used on that specific project, if no data found
   *         return -1.0..
   */
  public double calculateTotalOverallBudgetByYear(int projectID, int year);

  /**
   * Deletes the information of a Budget associated by a given id
   * 
   * @param budgetId - is the id of a Budget
   * @return true if the elements were deleted successfully. False otherwise
   */
  public boolean deleteBudget(int budgetId);


  /**
   * Deletes the information of the Budgets related by a given project id and an institution id
   * 
   * @param projectID
   * @param institutionID
   * @return true if the elements were deleted successfully. False otherwise
   */
  public boolean deleteBudgetsByInstitution(int projectID, int institutionID);

  /**
   * This method gets all the budget information by a given Project Id
   * 
   * @param projectID - is the Id of the project
   * @return a List of Map of the Budget Information related with the project
   */
  public List<Map<String, String>> getBudgetsByProject(int projectID);

  /**
   * This method gets all the Budget Information by a given project ID and a Budget Type
   * 
   * @param projectID - is the id of the project
   * @param budgetType - is the id of a Budget Type
   * @return a list of Map of the Budgets related with the budget type id and the project id
   */
  public List<Map<String, String>> getBudgetsByType(int projectID, int budgetType);

  /**
   * This method gets all the Budget information by a given project Id and a year
   * 
   * @param projectID - is the id of the project
   * @param year - is the year of the budget
   * @return a list of Map of the Budgets related with the year and the project id
   */
  public List<Map<String, String>> getBudgetsByYear(int projectID, int year);

  /**
   * This method calculates the total of the CCAFS Budget which is the addition of W1+W2+W3
   * 
   * @param projectID is the project id.
   * @return a decimal number representing the amount of the total CCAFS Budget for that specific project.
   */
  public List<Map<String, String>> getCCAFSBudgets(int projectID);

  /**
   * This method brings all the institutions by a given project ID
   * 
   * @param projectID - is the project Id
   * @return a list of Map of Institutions related with the project ID
   */
  public List<Map<String, String>> getLeveragedInstitutions(int projectID);

  /**
   * This method saves the Budget and the Project Budget relation
   * 
   * @param budgetData
   * @return The last inserted id if there was a new record, 0 if the record was updated or -1 if any error happened.
   */
  public int saveBudget(int projectID, Map<String, Object> budgetData);


}
