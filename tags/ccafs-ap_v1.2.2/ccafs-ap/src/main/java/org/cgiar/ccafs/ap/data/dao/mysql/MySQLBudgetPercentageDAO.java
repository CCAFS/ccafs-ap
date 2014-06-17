/*
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
 */

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
    LOG.debug(">> getBudgetPercentage(id={}).", id);

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
      LOG.error("-- getBudgetPercentage() > There was an error getting the budget percentage identified by {}", id, e);
    }

    if (bpData.isEmpty()) {
      LOG.warn("-- getBudgetPercentage() > The budget percentage list is empty");
    }

    LOG.debug("<< getBudgetPercentage():{}.", bpData);
    return bpData;
  }

  @Override
  public List<Map<String, String>> getBudgetPercentages() {
    LOG.debug(">> getBudgetPercentages()");

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
      LOG.error("-- getBudgetPercentages() > There was an error getting the budget percentage list. \n{}", e);
    }

    if (bpDataList.size() <= 0) {
      LOG.warn("-- getBudgetPercentages() > The budget percentage list is empty");
    }

    LOG.debug("<< getBudgetPercentages():bpDataList.size={}", bpDataList.size());
    return bpDataList;
  }

}
