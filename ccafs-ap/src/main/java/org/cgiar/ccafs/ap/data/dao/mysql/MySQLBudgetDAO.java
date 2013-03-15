package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.BudgetDAO;
import org.cgiar.ccafs.ap.data.dao.DAOManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLBudgetDAO implements BudgetDAO {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MySQLBudgetDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLBudgetDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public Map<String, String> getBudget(int activityID) {
    Map<String, String> budgetData = new HashMap<>();
    // Querying budget record.
    String query = "SELECT * FROM activity_budgets WHERE activity_id = " + activityID;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      if (rs.next()) {
        budgetData.put("id", rs.getString("id"));
        budgetData.put("usd", rs.getString("usd"));
        budgetData.put("cg_funds_id", rs.getString("cg_funds"));
        budgetData.put("bilateral_id", rs.getString("bilateral"));
      }
      rs.close();
      if (budgetData.get("cg_funds_id") != null) {
        query = "SELECT * FROM budget_percentages WHERE id = " + budgetData.get("cg_funds_id");
        rs = databaseManager.makeQuery(query, con);
        if (rs.next()) {
          budgetData.put("cg_funds_percentage", rs.getString("percentage"));
        }
        rs.close();
      }
      if (budgetData.get("bilateral_id") != null) {
        query = "SELECT * FROM budget_percentages WHERE id = " + budgetData.get("bilateral_id");
        rs = databaseManager.makeQuery(query, con);
        if (rs.next()) {
          budgetData.put("bilateral_percentage", rs.getString("percentage"));
        }
        rs.close();
      }

    } catch (SQLException e) {
      LOG.error("There was an error getting an activity budget", query, e);
      return null;
    }
    if (budgetData.isEmpty()) {
      return null;
    }

    return budgetData;
  }
}
