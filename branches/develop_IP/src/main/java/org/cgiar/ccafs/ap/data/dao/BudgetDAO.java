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
