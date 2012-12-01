package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.BudgetDAO;
import org.cgiar.ccafs.ap.data.dao.DAOManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;


public class MySQLBudgetDAO implements BudgetDAO {

  private DAOManager databaseManager;

  @Inject
  public MySQLBudgetDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public Map<String, String> getBudget(int activityID) {
    Map<String, String> budgetData = new HashMap<>();
    try (Connection con = databaseManager.getConnection()) {
      // Querying budget record.
      String query = "SELECT * FROM activity_budgets WHERE activity_id = " + activityID;
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
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return budgetData;
  }
}
