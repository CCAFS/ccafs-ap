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

import org.cgiar.ccafs.ap.data.dao.BudgetOverheadDAO;
import org.cgiar.ccafs.utils.db.DAOManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class MySQLBudgetOverheadDAO implements BudgetOverheadDAO {

  private DAOManager daoManager;
  private static Logger LOG = LoggerFactory.getLogger(MySQLBudgetOverheadDAO.class);


  @Inject
  public MySQLBudgetOverheadDAO(DAOManager daoManager) {
    this.daoManager = daoManager;
  }

  @Override
  public Map<String, String> getProjectBudgetOverhead(int projectID) {
    Map<String, String> overheadData = new HashMap<>();
    String query = "SELECT * FROM project_budget_overheads WHERE project_id = " + projectID + " AND is_active = TRUE";

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query, con);
      if (rs.next()) {
        overheadData.put("id", rs.getString("id"));
        overheadData.put("cost_recovered", rs.getString("cost_recovered"));
        overheadData.put("contracted_overhead", rs.getString("contracted_overhead"));
      }
    } catch (SQLException e) {
      LOG.error("getProjectBudgetOverhead() > There was an error getting the overhead for project {}.", projectID, e);
    }
    return overheadData;
  }

  @Override
  public boolean saveProjectBudgetOverhead(Map<String, Object> projectData) {
    StringBuilder query = new StringBuilder();
    query.append("INSERT INTO project_budget_overheads (project_id, cost_recovered, contracted_overhead, ");
    query.append("created_by, modified_by, modification_justification) VALUES (?, ?, ?, ?, ?, ?) ");
    query.append("ON DUPLICATE KEY UPDATE cost_recovered= VALUES(cost_recovered),  ");
    query.append("contracted_overhead=VALUES(contracted_overhead), modified_by=VALUES(modified_by),  ");
    query.append("modification_justification=VALUES(modification_justification) ");

    Object[] values = new Object[6];
    values[0] = projectData.get("project_id");
    values[1] = projectData.get("cost_recovered");
    values[2] = projectData.get("contracted_overhead");
    values[3] = projectData.get("user_id");
    values[4] = projectData.get("user_id");
    values[5] = projectData.get("justification");

    int result = daoManager.saveData(query.toString(), values);
    return result != -1;
  }
}