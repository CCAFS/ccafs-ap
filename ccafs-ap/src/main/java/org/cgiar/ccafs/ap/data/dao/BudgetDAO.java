package org.cgiar.ccafs.ap.data.dao;

import java.util.Map;

import com.google.inject.ImplementedBy;
import org.cgiar.ccafs.ap.data.dao.mysql.MySQLBudgetDAO;

@ImplementedBy(MySQLBudgetDAO.class)
public interface BudgetDAO {

  /**
   * Get the budget data of a given activity.
   * 
   * @param activityID - activity identifier.
   * @return the Map with the budget data or null
   *         if there is no budget.
   */
  Map<String, String> getBudget(int activityID);

}
