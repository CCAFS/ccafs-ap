/*****************************************************************
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
 *****************************************************************/

package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.BudgetByMogDAO;
import org.cgiar.ccafs.utils.db.DAOManager;

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


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class MySQLBudgetByMogDAO implements BudgetByMogDAO {

  private static Logger LOG = LoggerFactory.getLogger(MySQLBudgetByMogDAO.class);
  private DAOManager daoManager;

  @Inject
  public MySQLBudgetByMogDAO(DAOManager daoManager) {
    this.daoManager = daoManager;
  }

  @Override
  public List<Map<String, String>> getProjectOutputsBudget(int projectID) {
    List<Map<String, String>> outputBudgets = new ArrayList<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT pmb.id, pmb.total_contribution, pmb.gender_contribution, pmb.year, ");
    query.append("ie.id as 'output_id', ie.description as 'output_description' ");
    query.append("FROM project_mog_budgets pmb ");
    query.append("INNER JOIN ip_elements ie ON pmb.mog_id = ie.id ");
    query.append("WHERE pmb.project_id = ");
    query.append(projectID);

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> outputBudget = new HashMap<>();
        outputBudget.put("id", rs.getString("id"));
        outputBudget.put("total_contribution", rs.getString("total_contribution"));
        outputBudget.put("gender_contribution", rs.getString("gender_contribution"));
        outputBudget.put("year", rs.getString("year"));
        outputBudget.put("output_id", rs.getString("output_id"));
        outputBudget.put("output_description", rs.getString("output_description"));

        outputBudgets.add(outputBudget);
      }
    } catch (SQLException e) {
      LOG.error("-- getProjectOutputsBudget() > Exception raised trying to get the budgets by mog for project {}.",
        projectID, e);
    }

    return outputBudgets;
  }

  @Override
  public List<Map<String, String>> getProjectOutputsBudgetByYear(int projectID, int budgetTypeID, int year) {
    List<Map<String, String>> outputBudgets = new ArrayList<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT pmb.id, pmb.total_contribution, pmb.gender_contribution, pmb.year, pmb.budget_type, ");
    query.append("ie.id as 'output_id', ie.description as 'output_description' ");
    query.append("FROM project_mog_budgets pmb ");
    query.append("INNER JOIN ip_elements ie ON pmb.mog_id = ie.id ");
    query.append("WHERE pmb.project_id = ");
    query.append(projectID);
    query.append(" AND pmb.budget_type = ");
    query.append(budgetTypeID);
    query.append(" AND pmb.year = ");
    query.append(year);

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> outputBudget = new HashMap<>();
        outputBudget.put("id", rs.getString("id"));
        outputBudget.put("total_contribution", rs.getString("total_contribution"));
        outputBudget.put("gender_contribution", rs.getString("gender_contribution"));
        outputBudget.put("year", rs.getString("year"));
        outputBudget.put("budget_type", rs.getString("budget_type"));
        outputBudget.put("output_id", rs.getString("output_id"));
        outputBudget.put("output_description", rs.getString("output_description"));

        outputBudgets.add(outputBudget);
      }
    } catch (SQLException e) {
      LOG.error("-- getProjectOutputsBudget() > Exception raised trying to get the budgets by mog for project {}.",
        projectID, e);
    }

    return outputBudgets;
  }

  @Override
  public boolean saveProjectOutputsBudget(Map<String, Object> budgetByMOGData, int userID, String justification) {
    if (Integer.parseInt(budgetByMOGData.get("id").toString()) == -1) {

      StringBuilder query = new StringBuilder();
      query.append("INSERT INTO project_mog_budgets (project_id, mog_id, total_contribution, gender_contribution,  ");
      query.append("budget_type, year, created_by, modified_by, modification_justification) ");
      query.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ");

      Object[] values = new Object[9];
      values[0] = budgetByMOGData.get("project_id");
      values[1] = budgetByMOGData.get("mog_id");
      values[2] = budgetByMOGData.get("total_contribution");
      values[3] = budgetByMOGData.get("gender_contribution");
      values[4] = budgetByMOGData.get("budget_type");
      values[5] = budgetByMOGData.get("year");
      values[6] = userID;
      values[7] = userID;
      values[8] = justification;

      int result = daoManager.saveData(query.toString(), values);
      return (result != -1);
    } else {
      StringBuilder query = new StringBuilder();
      query.append(
        "update project_mog_budgets " + "set project_id=?, mog_id=?, total_contribution=?, gender_contribution=?,  ");
      query.append("budget_type=?, year=?, created_by=?, modified_by=?, modification_justification= ? ");
      query.append(" where id=?");

      Object[] values = new Object[10];

      values[0] = budgetByMOGData.get("project_id");
      values[1] = budgetByMOGData.get("mog_id");
      values[2] = budgetByMOGData.get("total_contribution");
      values[3] = budgetByMOGData.get("gender_contribution");
      values[4] = budgetByMOGData.get("budget_type");
      values[5] = budgetByMOGData.get("year");
      values[6] = userID;
      values[7] = userID;
      values[8] = justification;
      values[9] = budgetByMOGData.get("id");

      int result = daoManager.saveData(query.toString(), values);
      return (result != -1);
    }


  }
}
