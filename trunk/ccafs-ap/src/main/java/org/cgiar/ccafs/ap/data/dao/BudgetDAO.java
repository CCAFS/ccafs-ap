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
   * @return the Map with the budget data.
   */
  Map<String, String> getBudget(int activityID);

}
