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

import org.cgiar.ccafs.ap.data.manager.impl.BudgetManagerImpl;
import org.cgiar.ccafs.ap.data.model.Budget;
import org.cgiar.ccafs.ap.data.model.BudgetType;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.Project;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Héctor Fabio Tobón R.
 * @author Javier Andrés Gallego
 */
@ImplementedBy(BudgetManagerImpl.class)
public interface BudgetManager {

  /**
   * This method calculates the total of the Activity Budget
   *
   * @param activityID is the activity id
   * @return a decimal number representing the amount of the total Activity Budget for that specific activity, if no
   *         data
   *         found the method will return 0.0 and if some error happen a -1.0 will be returned.
   */
  public double calculateTotalActivityBudget(int activityID);

  /**
   * This method calculates the total of the Activity Budget by a given year and an activity ID
   *
   * @param activityID is the activity id
   * @param year is a year
   * @return a decimal number representing the amount of the total CCAFS Budget for that specific project in the given
   *         year, if no data found the method will return 0.0 and if some error happen a -1.0 will be returned.
   */
  public double calculateTotalActivityBudgetByYear(int activityID, int year);

  /**
   * This method calculates the total of the CCAFS Budget which is the addition of W1+W2+W3+BILATERAL and a given year
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
   * @return a decimal number representing the amount of the total CCAFS Budget for that specific project in the given
   *         year, if no data found the method will return 0.0 and if some error happen a -1.0 will be returned.
   */
  public double calculateTotalCCAFSBudgetByYear(int projectID, int year);

  /**
   * This method returns the total Budget used for that specific project and a given year.
   * It consists in the addition of all Windows, plus the Bilateral and the Leveraged.
   *
   * @param projectID is the project id.
   * @return a decimal number that represents the total amount of money used on that specific project. If no data found
   *         the method will return 0.0 and if some error happen a -1.0 will be returned.
   */
  public double calculateTotalOverallBudget(int projectID);

  /**
   * This method returns the total Budget used for that specific project and a given year.
   * It consists in the addition of all Windows, plus the Bilateral and the Leveraged.
   *
   * @param projectID is the project id.
   * @return a decimal number that represents the total amount of money used on that specific project. If no data found
   *         the method will return 0.0 and if some error happen a -1.0 will be returned.
   */
  public double calculateTotalOverallBudgetByYear(int projectID, int year);

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
   * This method removes all the Activity budgets identified with the given projectID and year.
   *
   * @param activityID is the activity identifier
   * @param year is a year
   * @return true if the elements were deleted successfully. False otherwise.
   */
  public boolean deleteActivityBudgetByYear(int activityID, int year);

  /**
   * This method removes all the Activity Budgets associated by a given activity ID
   *
   * @param activityID is the ID of the activity
   * @return true if the elements were deleted successfully. False otherwise
   */
  public boolean deleteActivityBudgetsByActivityID(int activityID);

  /**
   * This method removes a set of budgets that belong to a specific activity and institution.
   *
   * @param activityID is the activity identifier.
   * @param institutionID is the institution identifier.
   * @return true if the set of budgets were successfully deleted, false otherwise.
   */
  public boolean deleteActivityBudgetsByInstitution(int activityID, int institutionID);

  /**
   * This method removes a specific budget value from the database.
   *
   * @param budgetId is the budget identifier.
   * @return true if the budget was successfully deleted, false otherwise.
   */
  public boolean deleteBudget(int budgetId);

  /**
   * This method removes a set of budgets that belong to a specific project and institution.
   *
   * @param projectID is the project identifier.
   * @param institutionID is the institution identifier.
   * @return true if the set of budgets were successfully deleted, false otherwise.
   */
  public boolean deleteBudgetsByInstitution(int projectID, int institutionID);

  /**
   * This method removes a set of budgets that belong to a specific project and year.
   *
   * @param projectID is the project identifier.
   * @param year is the year.
   * @return true if the set of budgets were successfully deleted, false otherwise.
   */
  public boolean deleteBudgetsByYear(int projectID, int year);

  /**
   * This method gets all the budget information related by a given activityID
   *
   * @param activityID
   * @param budgetType
   * @return a list of Budget with the information related with the activity
   */
  public List<Budget> getActivityBudgetsByType(int activityID, int budgetType);

  /**
   * This method gets all the budget information that belongs to an activity in a specific year.
   *
   * @param activityID is the activity identifier.
   * @param year is the year.
   * @return a List of Budget objects.
   */
  public List<Budget> getActivityBudgetsByYear(int activityID, int year);

  /**
   * This method gets all the institutions that are currently saved in the activity budget section.
   * Only the institutions for activity are returned.
   *
   * @param activityID is the activity identifier.
   * @return a list of Institution objects.
   */
  public List<Institution> getActivityInstitutionsBudgets(int activityID);

  /**
   * This method gets all the budget information by a given Project Id
   *
   * @param projectID - is the Id of the project
   * @return a List of Budget with the budget Information related with the project
   */
  public List<Budget> getBudgetsByProject(Project projectID);

  /**
   * This method gets all the budget information that belongs to the same type of a specific project.
   * Type can be Window 1, Window 2, Window 3, Bilateral or Leveraged.
   *
   * @param projectID is the id that identifies the project.
   * @param type is the type of budget see ({@link BudgetType}
   * @return a list of Budget objects.
   */
  public List<Budget> getBudgetsByType(int projectID, int budgetType);

  /**
   * This method gets all the budget information that belongs to a project in a specific year.
   *
   * @param projectID is the project identifier.
   * @param year is the year.
   * @return a List of Budget objects.
   */
  public List<Budget> getBudgetsByYear(int projectID, int year);

  /**
   * This method gets the CCAFS Budget which is the information of budget type W1+W2+W3
   *
   * @param projectID is the project id.
   * @return a List of CCAFS Budgets for that specific project.
   */
  public List<Budget> getCCAFSBudgets(int projectID);

  /**
   * This method returns all the institutions that have leveraged funds with the specified project.
   *
   * @param projectID is the project id.
   * @return a List of Institution Objects.
   */
  public List<Institution> getLeveragedInstitutions(int projectID);

  /**
   * This method returns all the institutions that have leveraged funds with the specified project and year.
   *
   * @param projectID is the project identifier.
   * @param year is number representing a year.
   * @return a List of Institution Objects.
   */
  public List<Institution> getLeveragedInstitutions(int projectID, int year);

  /**
   * This method gets all the institutions that are currently saved in the budget section.
   * Only the institutions for budget W1 are going to be returned.
   *
   * @param projectID is the project Identifier.
   * @return a List of Institutions.
   */
  public List<Institution> getW1Institutions(int projectID);

  /**
   * @param activityID
   * @param activityBudget
   * @return
   */
  public boolean saveActivityBudget(int activityID, Budget activityBudget);

  /**
   * This method saves the information of the given budget that belong to a specific project into the database.
   *
   * @param projectID is the project identifier.
   * @param budget is an object that represents a budget.
   * @return true if the budget was saved successfully, false otherwise.
   */
  public boolean saveBudget(int projectID, Budget budget);


}
