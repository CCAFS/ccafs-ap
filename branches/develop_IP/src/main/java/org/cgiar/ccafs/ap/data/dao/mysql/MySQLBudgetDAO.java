package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.BudgetDAO;
import org.cgiar.ccafs.ap.data.dao.DAOManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLBudgetDAO implements BudgetDAO {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(MySQLBudgetDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLBudgetDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public Map<String, String> getBudget(int activityID) {
    LOG.debug(">> getBudget(activityID={})", activityID);

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
      LOG.error("-- getBudget() > There was an error getting budget for activity {}", activityID, e);
      return null;
    }
    if (budgetData.isEmpty()) {
      return null;
    }

    LOG.debug("<< getBudget():{}", budgetData);
    return budgetData;
  }

  @Override
  public boolean saveBudget(Map<String, String> budgetData) {
    LOG.debug(">> saveBudget(budgetData={})", budgetData);
    boolean saved = false;

    Object[] values = new Object[budgetData.size()];
    values[0] = budgetData.get("id");
    values[1] = budgetData.get("usd");
    values[2] = budgetData.get("cgFund");
    values[3] = budgetData.get("bilateral");
    values[4] = budgetData.get("activityID");

    String query =
      "INSERT INTO activity_budgets (id, usd, cg_funds, bilateral, activity_id) VALUES (?, ?, ?, ?, ?) "
        + " ON DUPLICATE KEY UPDATE usd = VALUES(usd), cg_funds = VALUES(cg_funds), bilateral = VALUES(bilateral)";

    try (Connection con = databaseManager.getConnection()) {
      int rows = databaseManager.makeChangeSecure(con, query, values);
      if (rows < 0) {
        LOG.error("There was a problem saving the budget data.");
        LOG.error("Query: {}", query);
        LOG.error("Values: {}", Arrays.toString(values));
      } else {
        saved = true;
      }
    } catch (SQLException e) {
      LOG.error("-- saveBudget() > There was an error saving the budget data.");
      LOG.error("Query: {}", query);
      LOG.error("Values: {}", Arrays.toString(values));
      LOG.error("Error: ", e);
    }
    LOG.debug("<< saveBudget():{}", saved);
    return saved;
  }
}
