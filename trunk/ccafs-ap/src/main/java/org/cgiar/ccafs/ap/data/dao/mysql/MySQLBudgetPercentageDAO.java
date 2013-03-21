package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.BudgetPercentageDAO;
import org.cgiar.ccafs.ap.data.dao.DAOManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLBudgetPercentageDAO implements BudgetPercentageDAO {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(MySQLBudgetPercentageDAO.class);

  DAOManager databaseManager;

  @Inject
  public MySQLBudgetPercentageDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public Map<String, String> getBudgetPercentage(String id) {
    LOG.trace("Getting the budget percentage identified by {}.", id);

    Map<String, String> bpData = new HashMap<>();
    String query = "SELECT * FROM budget_percentages WHERE id = " + id;

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      if (rs.next()) {
        bpData.put("id", rs.getString("id"));
        bpData.put("percentage", rs.getString("percentage"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("There was an error getting the budget percentage. \n{}", query, e);
    }

    if (bpData.isEmpty()) {
      LOG.warn("The budget percentage list is empty");
    }
    return bpData;
  }

  @Override
  public List<Map<String, String>> getBudgetPercentages() {
    LOG.trace("Getting the budget percentage list");

    List<Map<String, String>> bpDataList = new ArrayList<>();
    String query = "SELECT * FROM budget_percentages";
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> bpData = new HashMap<>();
        bpData.put("id", rs.getString("id"));
        bpData.put("percentage", rs.getString("percentage"));
        bpDataList.add(bpData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("There was an error getting the budget percentage list. \n{}", query, e);
    }

    if (bpDataList.size() <= 0) {
      LOG.warn("The budget percentage list is empty");
    }
    return bpDataList;
  }

}
