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
 * @author Hernán David Carvajal.
 */
import org.cgiar.ccafs.ap.data.dao.mysql.MySQLBudgetDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLBudgetDAO.class)
public interface BudgetDAO {

  /**
   * This method returns the budget amount of the project depending on budget type given as
   * parameter and the year.
   * 
   * @param projectID - Project identifier
   * @param budgetTypeID - Budget type identifier
   * @param year - Year
   * @return a decimal number representing the amount requested, 0 if nothing found and -1 if some error occurred.
   */
  public double calculateProjectBudgetByTypeAndYear(int projectID, int budgetTypeID, int year);

  /**
   * This method returns the sum of the budgets with type W1W2 + W3_bilateral.
   * 
   * @param projectID is the project id.
   * @return a decimal number representing the amount, if no data found the method
   *         will return 0.0 and if some error happen a -1.0 will be returned.
   */
  public double calculateProjectW1W2W3BilateralBudget(int projectID);

  /**
   * This method returns the sum of the budgets with type W1W2 + W3_bilateral for the given year
   * 
   * @param projectID is the project id.
   * @param year
   * @return a decimal number representing the amount, if no data found the method
   *         will return 0.0 and if some error happen a -1.0 will be returned.
   */
  public double calculateProjectW1W2W3BilateralBudgetByYear(int projectID, int year);

  /**
   * This method calculates the total of the CCAFS Budget which is the addition of W1+W2+W3+BILATERAL
   * 
   * @param projectID is the project id.
   * @return a decimal number representing the amount of the total CCAFS Budget for that specific project, if no data
   *         found the method will return 0.0 and if some error happen a -1.0 will be returned.
   */
  public double calculateTotalCCAFSBudget(int projectID);

  /**
   * This method calculates the total of the CCAFS Budget which is the addition of W1+W2+W3+BILATERAL and a given year
   * 
   * @param projectID is the project id.
   * @param year
   * @return a decimal number representing the amount of the total CCAFS Budget for that specific project in the given
   *         year, if no data found the method will return 0.0 and if some error happen a -1.0 will be returned.
   */
  public double calculateTotalCCAFSBudgetByYear(int projectID, int year);

  /**
   * This method returns the total Budget used for that specific project.
   * It consists in the addition of all Windows, plus the Bilateral and the Leveraged.
   * 
   * @param projectID is the project id.
   * @return a decimal number that represents the total amount of money used on that specific project, if no data found
   *         the method will return 0.0 and if some error happen a -1.0 will be returned.
   */
  public double calculateTotalOverallBudget(int projectID);

  /**
   * This method returns the total Budget used for that specific project and a given year.
   * It consists in the addition of all Windows, plus the Bilateral and the Leveraged.
   * 
   * @param projectID is the project id.
   * @param year
   * @return a decimal number that represents the total amount of money used on that specific project, if no data found
   *         the method will return 0.0 and if some error happen a -1.0 will be returned.
   */
  public double calculateTotalOverallBudgetByYear(int projectID, int year);

  /**
   * This method calculates the total budget of some type for a given project.
   * 
   * @param projectID is the project identifier.
   * @param budgetTypeID budget type identifier.
   * @return a double representing this value, or -1 if some error found.
   */
  public double calculateTotalProjectBudgetByType(int projectID, int budgetTypeID);

  /**
   * This method calculates the total W1 + W2 budget from a given project.
   * 
   * @param projectID is the project identifier.
   * @return a double representing this value, or -1 if some error found.
   */
  public double calculateTotalProjectW1W2(int projectID);

  /**
   * This method calculates the total W1 + W2 budget from a given project in a given year.
   * 
   * @param projectID is the project identifier.
   * @param year is the year.
   * @return a double representing this value, or -1 if some error found.
   */
  public double calculateTotalProjectW1W2ByYear(int projectID, int year);

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
   * This method removes all the budgets identified with the given projectID and year.
   * 
   * @param projectID is the project identifier.
   * @param year is a year.
   * @return true if the elements were deleted successfully. False otherwise.
   */
  public boolean deleteBudgetsByYear(int projectID, int year);

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
   * This method calculates the total of the CCAFS Budget which is the addition of W1+W2+W3+BILATERAL
   * 
   * @param projectID is the project id.
   * @return a decimal number representing the amount of the total CCAFS Budget for that specific project.
   */
  public List<Map<String, String>> getCCAFSBudgets(int projectID);

  /**
   * This method gets all the W1 budget institutions that belong to a specific project.
   * 
   * @param projectID is the project identifier.
   * @return a list of Maps with the information of institutions.
   */
  public List<Map<String, String>> getW1Institutions(int projectID);

  /**
   * This method saves the Budget and the Project Budget relation
   * 
   * @param budgetData
   * @return The last inserted id if there was a new record, 0 if the record was updated or -1 if any error happened.
   */
  public int saveBudget(int projectID, Map<String, Object> budgetData);


}
