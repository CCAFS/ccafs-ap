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
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;
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
    query.append("SELECT pmb.id, pmb.total_contribution, pmb.gender_contribution, ");
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
  public boolean saveProjectOutputsBudget(Project project, User user, String justification) {
    boolean saved = false;
    return saved;
  }

}
