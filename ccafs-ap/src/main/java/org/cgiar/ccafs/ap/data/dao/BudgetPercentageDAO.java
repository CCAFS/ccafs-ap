package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLBudgetPercentageDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLBudgetPercentageDAO.class)
public interface BudgetPercentageDAO {

  /**
   * Get the information related to the budget percentage identified by id
   * 
   * @param id - BudgetPercentage identifier
   * @return a map with the information
   */
  public Map<String, String> getBudgetPercentage(String id);

  /**
   * Get the budget percentages list
   * 
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getBudgetPercentages();

}
