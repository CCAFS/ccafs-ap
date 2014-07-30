package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLBudgetDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLBudgetDAO.class)
public interface BudgetDAO {

  /**
   * @param budgetId
   * @return
   */
  public boolean deleteBudget(int budgetId);

  /**
   * @param projectID
   * @param institutionID
   * @return
   */
  public boolean deleteBudgetsByInstitution(int projectID, int institutionID);

  /**
   * @param projectID
   * @param budgetType
   * @return
   */
  public List<Map<String, String>> getBudgetsByType(int projectID, int budgetType);

  /**
   * @param projectID
   * @param year
   * @return
   */
  public List<Map<String, String>> getBudgetsByYear(int projectID, int year);

  /**
   * @param projectID
   * @return
   */
  public List<Map<String, String>> getLeveragedInstitutions(int projectID);

  /**
   * @param budgetData
   * @return
   */
  public int saveBudget(Map<String, Object> budgetData);


}
