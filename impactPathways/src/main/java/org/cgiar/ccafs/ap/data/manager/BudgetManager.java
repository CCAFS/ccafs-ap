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
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Héctor Fabio Tobón R.
 * @author Javier Andrés Gallego
 * @author Jorge leonardo Solis
 */
@ImplementedBy(BudgetManagerImpl.class)
public interface BudgetManager {

  /**
   * This method returns the budget gender of the project depending on the budget type given in the
   * parameter and in the year specified.
   * 
   * @param projectID - Project identifier
   * @param budgetTypeID - Budget type identifier
   * @param year - Year
   * @return a decimal number representing the total amount, 0 if nothing found, or -1 if an error occurred.
   */
  public double calculateGenderBudgetByTypeAndYear(int projectID, int budgetTypeID, int year);

  /**
   * This method returns the budget amount of the project depending on the budget type given in the
   * parameter and in the year specified.
   * 
   * @param projectID - Project identifier
   * @param budgetTypeID - Budget type identifier
   * @param year - Year
   * @return a decimal number representing the total amount, 0 if nothing found, or -1 if an error occurred.
   */
  public double calculateProjectBudgetByTypeAndYear(int projectID, int budgetTypeID, int year);

  /**
   * This method calculates the total of the CCAFS Budget which is the addition of W1+W2+W3+BILATERAL for ALL years
   * 
   * @param projectID is the project id.
   * @return a decimal number representing the amount of the total CCAFS Budget for that specific project, if no data
   *         found the method will return 0.0 and if some error happen a -1.0 will be returned.
   */
  public double calculateTotalBudget(int projectID);

  /**
   * This method calculates the total of the CCAFS Budget which is the addition of (W1W2)+(W3BILATERAL) in a given year
   * 
   * @param projectID is the project id.
   * @return a decimal number representing the amount of the total CCAFS Budget for that specific project in the given
   *         year, if no data found the method will return 0.0 and if some error happen a -1.0 will be returned.
   */
  public double calculateTotalBudgetByYear(int projectID, int year);

  /**
   * This method calculates the CCAFS budget for a given project according to the type received by
   * type.
   * 
   * @param projectID - project identifier.
   * @param institutionID - institution identifier.
   * @return a number representing the total value for that specific project by partner, if some error happen a -1.0
   *         will be
   *         returned.
   */
  public double calculateTotalCCAFSBudgetByInstitution(int projectID, int institutionID);

  /**
   * This method calculates the CCAFS budget for a given project according to the type received by
   * type.
   * 
   * @param projectID - project identifier.
   * @param institutionID - institution identifier.
   * @param budgetTypeID - budget identifier.
   * @return a number representing the total value for that specific project by partner and type budget, if some error
   *         happen a -1.0 will be returned.
   */
  public double calculateTotalCCAFSBudgetByInstitutionAndType(int projectID, int institutionID, int budgetTypeID);

  /**
   * This method calculates the CCAFS budget for a given project according to the type received by
   * type.
   * 
   * @param projectID - project identifier.
   * @param budgetTypeID - budget identifier.
   * @return a number representing thetotal value for that specific project, if some error happen a -1.0 will be
   *         returned.
   */
  public double calculateTotalCCAFSBudgetByType(int projectID, int budgetTypeID);


  /**
   * This method calculates the total of the Gender Budget which is the addition of W1+W2+W3+BILATERAL for ALL years
   * 
   * @param projectID is the project id.
   * @return a decimal number representing the amount of the total Gender percentage for that specific project, if no
   *         data
   *         found the method will return 0.0 and if some error happen a -1.0 will be returned.
   */
  public double calculateTotalGenderBudget(int projectID);

  /**
   * This method calculates the CCAFS budget for a given project according to the type received by
   * type.
   * 
   * @param projectID - project identifier.
   * @param institutionID - institution identifier.
   * @param budgetTypeID - budget identifier.
   * @return a number representing the gender value for that specific project by partner and type budget, if some error
   *         happen a -1.0 will be returned.
   */
  double calculateTotalGenderBudgetByInstitutionAndType(int projectID, int institutionID, int budgetTypeID);

  /**
   * This method calculates the total of the Gender Budget which is the percentage of the amount in a given year
   * 
   * @param projectID is the project identifier.
   * @param year is the year.
   * @return a double representing this value, or -1 if some error found.
   */
  public double calculateTotalGenderBudgetByYear(int projectID, int year);

  /**
   * This method calculates the gender budget percentage for a given project according to the type received by
   * parameter.
   * 
   * @param projectID - project identifier.
   * @return a number representing the percentage for that specific project, if if some error happen a -1.0 will be
   *         returned.
   */
  public double calculateTotalGenderPercentageByType(int projectID, int budgetTypeID);


  /**
   * This method calculates the percentage of budget going to gender according to the type, year and type received by
   * parameter.
   * 
   * @param projectID is the project identifier.
   * @param year is the year.
   * @return a double representing this value, or -1 if some error found.
   */
  public double calculateTotalGenderPercentageByYearAndType(int projectID, int year, int budgetTypeID);

  /**
   * This method calculates the total budget of some type for a given project.
   * 
   * @param projectID is the project identifier.
   * @param budgetTypeID budget type identifier.
   * @return a double representing this value, or -1 if some error found.
   */
  public double calculateTotalProjectBudgetByType(int projectID, int budgetTypeID);

  /**
   * This method removes a specific budget value from the database.
   * 
   * @param budgetId is the budget identifier.
   * @param user is the user that is making the deletion.
   * @param justification is the justification statement.
   * @return true if the budget was successfully deleted, false otherwise.
   */
  public boolean deleteBudget(int budgetId, User user, String justification);

  /**
   * This method removes a set of budgets that belong to a specific project and institution.
   * 
   * @param projectID is the project identifier.
   * @param institution is the institution identifier.
   * @param user is the user who is making the deletion.
   * @param justification is the justification statement.
   * @return true if the set of budgets were successfully deleted, false otherwise.
   */
  public boolean deleteBudgetsByInstitution(int projectID, Institution institution, User user, String justification);

  /**
   * This method removes a set of budgets that belong to a specific project and year.
   * 
   * @param projectID is the project identifier.
   * @param year is the year.
   * @param user is the person who is making the deletion.
   * @param justifications is the justification statement.
   * @return true if the set of budgets were successfully deleted, false otherwise.
   */
  public boolean deleteBudgetsByYear(int projectID, int year, User user, String justification);

  /**
   * This method deletes all the budgets that were declared for a year in which the project it is not active.
   * 
   * @return False if any error occurred. True otherwise.
   */
  public boolean deleteBudgetsFromUnexistentYears(int projectID);

  /**
   * This method deletes all the budgets that belong to some institution which has NOT link with the project to which
   * the budget belongs to.
   * This method only deletes budgets with years >= currentYear
   * 
   * @return False if any error occurred. True otherwise.
   */
  public boolean deleteBudgetsWithNoLinkToInstitutions(int projectID, int currentYear);

  /**
   * This method deletes all the cofounded budgets that correspond between two projects that has not a link anymore.
   * 
   * @return False if any error occurred. True otherwise.
   */
  public boolean deleteCofoundedBudgetsWithNoLink(int projectID);

  /**
   * This method gets all the budget information by a given Project Id in ALL years.
   * 
   * @param projectID - is the Id of the project
   * @return a List of Budget with the budget Information related with the project
   */
  public List<Budget> getBudgetsByProject(Project projectID);

  /**
   * This method gets all the budget information that belongs to a project in a specific year.
   * 
   * @param projectID is the project identifier.
   * @param year is the year.
   * @return a List of Budget objects.
   */
  public List<Budget> getBudgetsByYear(int projectID, int year);

  /**
   * This method saves the information of the given budget that belong to a specific project into the database.
   * 
   * @param projectID is the project identifier.
   * @param budget is an object that represents a budget.
   * @param user - The use who is making the change
   * @param justification
   * @return true if the budget was saved successfully, false otherwise.
   */
  public boolean saveBudget(int projectID, Budget budget, User user, String justification);


}
