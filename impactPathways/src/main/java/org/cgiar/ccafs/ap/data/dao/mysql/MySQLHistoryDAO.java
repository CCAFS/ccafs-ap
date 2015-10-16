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

import org.cgiar.ccafs.ap.data.dao.HistoryDAO;
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
 * @author Carlos Alberto Martínez M - CIAT/CCAFS
 */

public class MySQLHistoryDAO implements HistoryDAO {

  private static Logger LOG = LoggerFactory.getLogger(MySQLHistoryDAO.class);
  private DAOManager daoManager;

  @Inject
  public MySQLHistoryDAO(DAOManager daoManager) {
    this.daoManager = daoManager;
  }

  @Override
  public List<Map<String, String>> getActivitiesHistory(int projectID) {
    String dbName = this.getDatabaseName();

    StringBuilder query = new StringBuilder();
    query.append("SELECT u.id as 'user_id', u.first_name, u.last_name, u.email, t.action, ");
    query.append("t.active_since, t.modification_justification ");
    query.append("FROM ");
    query.append(dbName);
    query.append("_history.activities t ");
    query.append("INNER JOIN users u ON t.modified_by = u.id ");
    query.append("WHERE project_id = ");
    query.append(projectID);
    query.append(" GROUP BY u.email, t.action, t.modification_justification, ");
    // This line group the results that have the value of active_since in a range of +/- 2 seconds
    query.append(" UNIX_TIMESTAMP(t.active_since) DIV 2 ");
    query.append(" ORDER BY t.active_since DESC ");
    query.append(" LIMIT 0, 5 ");

    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getCCAFSOutcomesHistory(int projectID) {
    String dbName = this.getDatabaseName();

    StringBuilder query = new StringBuilder();
    query.append("SELECT * FROM ( ");

    query.append("  SELECT u.id as 'user_id', u.first_name, u.last_name, u.email, t.action, ");
    query.append("  t.active_since, t.modification_justification ");
    query.append("  FROM ");
    query.append(dbName);
    query.append("_history.ip_project_contributions t ");
    query.append("  INNER JOIN users u ON t.modified_by = u.id ");
    query.append("  WHERE project_id = ");
    query.append(projectID);

    query.append(" UNION ");

    query.append("  SELECT u.id as 'user_id', u.first_name, u.last_name, u.email, t.action, ");
    query.append("  t.active_since, t.modification_justification ");
    query.append("  FROM ");
    query.append(dbName);
    query.append("_history.ip_project_indicators t ");
    query.append("  INNER JOIN users u ON t.modified_by = u.id ");
    query.append("  WHERE project_id = ");
    query.append(projectID);

    query.append(") temp ");
    query.append("  GROUP BY email, action, modification_justification, ");
    // This line group the results that have the value of active_since in a range of +/- 2 seconds
    query.append(" UNIX_TIMESTAMP(active_since) DIV 2 ");
    query.append(" ORDER BY active_since DESC ");
    query.append(" LIMIT 0, 5 ");
    return this.getData(query.toString());

  }

  private List<Map<String, String>> getData(String query) {
    List<Map<String, String>> historyData = new ArrayList<>();
    try (Connection con = daoManager.getConnection()) {

      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> data = new HashMap<>();
        data.put("user_id", rs.getString("user_id"));
        data.put("first_name", rs.getString("first_name"));
        data.put("last_name", rs.getString("last_name"));
        data.put("email", rs.getString("email"));
        data.put("date", rs.getString("active_since"));
        data.put("action", rs.getString("action"));
        data.put("justification", rs.getString("modification_justification"));

        historyData.add(data);
      }

    } catch (SQLException e) {
      LOG.error("There was an exception trying to get the version history.", e);
    }
    return historyData;
  }

  private String getDatabaseName() {
    String query = "SELECT DATABASE() as dbName;";

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query, con);
      if (rs.next()) {
        return rs.getString("dbName");
      }
    } catch (SQLException e) {
      LOG.error("getDatabaseName() > Error getting the database name.", e);
    }
    return null;
  }

  @Override
  public List<Map<String, String>> getProjectBudgetByMogHistory(int projectID) {
    String dbName = this.getDatabaseName();

    StringBuilder query = new StringBuilder();
    query.append("SELECT u.id as 'user_id', u.first_name, u.last_name, u.email, t.action, ");
    query.append("t.active_since, t.modification_justification ");
    query.append("FROM ");
    query.append(dbName);
    query.append("_history.project_mog_budgets t ");
    query.append("INNER JOIN users u ON t.modified_by = u.id ");
    query.append("WHERE project_id = ");
    query.append(projectID);
    query.append(" GROUP BY u.email, t.action, t.modification_justification, ");
    // This line group the results that have the value of active_since in a range of +/- 2 seconds
    query.append(" UNIX_TIMESTAMP(t.active_since) DIV 2 ");
    query.append(" ORDER BY t.active_since DESC, t.action DESC ");
    query.append(" LIMIT 0, 5 ");

    return this.getData(query.toString());

  }

  @Override
  public List<Map<String, String>> getProjectBudgetHistory(int projectID) {
    String dbName = this.getDatabaseName();

    StringBuilder query = new StringBuilder();
    query.append("SELECT * FROM ( ");

    query.append("  SELECT u.id as 'user_id', u.first_name, u.last_name, u.email, t.action, ");
    query.append("  t.active_since, t.modification_justification ");
    query.append("  FROM ");
    query.append(dbName);
    query.append("_history.project_budgets t ");
    query.append("  INNER JOIN users u ON t.modified_by = u.id ");
    query.append("  WHERE project_id = ");
    query.append(projectID);

    query.append("  UNION SELECT u.id as 'user_id', u.first_name, u.last_name, u.email, t.action, ");
    query.append("  t.active_since, t.modification_justification ");
    query.append("  FROM ");
    query.append(dbName);
    query.append("_history.project_budgets t ");
    query.append("  INNER JOIN users u ON t.modified_by = u.id ");
    query.append("  WHERE cofinance_project_id = ");
    query.append(projectID);
    query.append(") temp ");
    query.append("  GROUP BY email, action, modification_justification, ");
    // This line group the results that have the value of active_since in a range of +/- 2 seconds
    query.append(" UNIX_TIMESTAMP(active_since) DIV 2 ");
    query.append(" ORDER BY active_since DESC ");
    query.append(" LIMIT 0, 5 ");
    return this.getData(query.toString());

  }

  @Override
  public List<Map<String, String>> getProjectDeliverablesHistory(int deliverableID) {
    String dbName = this.getDatabaseName();

    StringBuilder query = new StringBuilder();
    query.append("SELECT * FROM ( ");

    query.append("  SELECT u.id as 'user_id', u.first_name, u.last_name, u.email, t.action, ");
    query.append("  t.active_since, t.modification_justification ");
    query.append("  FROM ");
    query.append(dbName);
    query.append("_history.deliverables t ");
    query.append("  INNER JOIN users u ON t.modified_by = u.id ");
    query.append("  WHERE t.record_id = ");
    query.append(deliverableID);

    query.append(" UNION ");

    query.append("  SELECT u.id as 'user_id', u.first_name, u.last_name, u.email, t.action, ");
    query.append("  t.active_since, t.modification_justification ");
    query.append("  FROM ");
    query.append(dbName);
    query.append("_history.next_users t ");
    query.append("  INNER JOIN users u ON t.modified_by = u.id ");
    query.append("  WHERE t.deliverable_id = ");
    query.append(deliverableID);

    query.append(" UNION ");

    query.append("  SELECT u.id as 'user_id', u.first_name, u.last_name, u.email, t.action, ");
    query.append("  t.active_since, t.modification_justification ");
    query.append("  FROM ");
    query.append(dbName);
    query.append("_history.deliverable_partnerships t ");
    query.append("  INNER JOIN users u ON t.modified_by = u.id ");
    query.append("  WHERE t.deliverable_id = ");
    query.append(deliverableID);

    query.append(") temp ");
    query.append("  GROUP BY email, action, modification_justification, ");
    // This line group the results that have the value of active_since in a range of +/- 2 seconds
    query.append(" UNIX_TIMESTAMP(active_since) DIV 2 ");
    query.append(" ORDER BY active_since DESC ");
    query.append(" LIMIT 0, 5 ");
    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getProjectDescriptionHistory(int projectID) {
    String dbName = this.getDatabaseName();

    StringBuilder query = new StringBuilder();
    query.append("SELECT * FROM ( ");
    query.append("  SELECT u.id as 'user_id', u.first_name, u.last_name, u.email, t.action, ");
    query.append("  t.active_since, t.modification_justification ");
    query.append("  FROM ");
    query.append(dbName);
    query.append("_history.projects t ");
    query.append("  INNER JOIN users u ON t.modified_by = u.id ");
    query.append("  WHERE record_id = ");
    query.append(projectID);

    query.append(" UNION ");

    query.append("  SELECT u.id as 'user_id', u.first_name, u.last_name, u.email, t.action, ");
    query.append("  t.active_since, t.modification_justification ");
    query.append("  FROM ");
    query.append(dbName);
    query.append("_history.project_focuses t ");
    query.append("  INNER JOIN users u ON t.modified_by = u.id ");
    query.append("  WHERE project_id = ");
    query.append(projectID);

    query.append(") temp ");
    query.append("  GROUP BY email, action, modification_justification, ");
    // This line group the results that have the value of active_since in a range of +/- 2 seconds
    query.append(" UNIX_TIMESTAMP(active_since) DIV 2 ");
    query.append(" ORDER BY active_since DESC ");
    query.append(" LIMIT 0, 5 ");
    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getProjectIPOtherContributionHistory(int projectID) {
    String dbName = this.getDatabaseName();

    StringBuilder query = new StringBuilder();
    query.append("SELECT * FROM ( ");

    query.append("  SELECT u.id as 'user_id', u.first_name, u.last_name, u.email, t.action, ");
    query.append("  t.active_since, t.modification_justification ");
    query.append("  FROM ");
    query.append(dbName);
    query.append("_history.project_other_contributions t ");
    query.append("  INNER JOIN users u ON t.modified_by = u.id ");
    query.append("  WHERE project_id = ");
    query.append(projectID);

    query.append(" UNION ");

    query.append("  SELECT u.id as 'user_id', u.first_name, u.last_name, u.email, t.action, ");
    query.append("  t.active_since, t.modification_justification ");
    query.append("  FROM ");
    query.append(dbName);
    query.append("_history.project_crp_contributions t ");
    query.append("  INNER JOIN users u ON t.modified_by = u.id ");
    query.append("  WHERE project_id = ");
    query.append(projectID);

    query.append(") temp ");
    query.append("  GROUP BY email, action, modification_justification, ");
    // This line group the results that have the value of active_since in a range of +/- 2 seconds
    query.append(" UNIX_TIMESTAMP(active_since) DIV 2 ");
    query.append(" ORDER BY active_since DESC ");
    query.append(" LIMIT 0, 5 ");

    return this.getData(query.toString());


  }

  @Override
  public List<Map<String, String>> getProjectLocationsHistory(int projectID) {
    String dbName = this.getDatabaseName();

    StringBuilder query = new StringBuilder();
    query.append("SELECT u.id as 'user_id', u.first_name, u.last_name, u.email, t.action, ");
    query.append("t.active_since, t.modification_justification ");
    query.append("FROM ");
    query.append(dbName);
    query.append("_history.project_locations t ");
    query.append("INNER JOIN users u ON t.modified_by = u.id ");
    query.append("WHERE project_id = ");
    query.append(projectID);
    query.append(" GROUP BY u.email, t.action, t.modification_justification, ");
    // This line group the results that have the value of active_since in a range of +/- 2 seconds
    query.append(" UNIX_TIMESTAMP(t.active_since) DIV 2 ");
    query.append(" ORDER BY t.active_since DESC ");
    query.append(" LIMIT 0, 5 ");

    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getProjectOutcomeHistory(int projectID) {
    String dbName = this.getDatabaseName();

    StringBuilder query = new StringBuilder();
    query.append("SELECT u.id as 'user_id', u.first_name, u.last_name, u.email, t.action, ");
    query.append("t.active_since, t.modification_justification ");
    query.append("FROM ");
    query.append(dbName);
    query.append("_history.project_outcomes t ");
    query.append("INNER JOIN users u ON t.modified_by = u.id ");
    query.append("WHERE project_id = ");
    query.append(projectID);
    query.append(" GROUP BY u.email, t.action, t.modification_justification, ");
    // This line group the results that have the value of active_since in a range of +/- 2 seconds
    query.append(" UNIX_TIMESTAMP(t.active_since) DIV 2 ");
    query.append(" ORDER BY t.active_since DESC");
    query.append(" LIMIT 0, 5 ");

    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getProjectOutputsHistory(int projectID) {
    String dbName = this.getDatabaseName();

    StringBuilder query = new StringBuilder();
    query.append("SELECT u.id as 'user_id', u.first_name, u.last_name, u.email, t.action, ");
    query.append("t.active_since, t.modification_justification ");
    query.append("FROM ");
    query.append(dbName);
    query.append("_history.ip_project_contribution_overviews t ");
    query.append("INNER JOIN users u ON t.modified_by = u.id ");
    query.append("WHERE project_id = ");
    query.append(projectID);
    query.append(" GROUP BY u.email, t.action, t.modification_justification, ");
    // This line group the results that have the value of active_since in a range of +/- 2 seconds
    query.append(" UNIX_TIMESTAMP(t.active_since) DIV 2 ");
    query.append(" ORDER BY t.active_since DESC ");
    query.append(" LIMIT 0, 5 ");

    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getProjectPartnerHistory(int projectID) {
    String dbName = this.getDatabaseName();

    StringBuilder query = new StringBuilder();
    query.append("SELECT * FROM ( ");

    query.append("  SELECT u.id as 'user_id', u.first_name, u.last_name, u.email, t.action, ");
    query.append("  t.active_since, t.modification_justification ");
    query.append("  FROM ");
    query.append(dbName);
    query.append("_history.project_partners t ");
    query.append("  INNER JOIN users u ON t.modified_by = u.id ");
    query.append("  WHERE project_id = ");
    query.append(projectID);

    query.append(" UNION ");

    query.append(
      "        SELECT u.id as 'user_id', u.first_name, u.last_name, u.email, t.action,   t.active_since, t.modification_justification   ");
    query.append(" FROM  ");
    query.append(dbName);
    query.append("_history.project_partner_persons t inner join ");

    query.append(" project_partners on project_partners.id= t.project_partner_id ");
    query.append(" INNER JOIN users u ON t.modified_by = u.id ");


    query.append(" UNION ");

    query.append(
      "        SELECT u.id as 'user_id', u.first_name, u.last_name, u.email, t.action,   t.active_since, t.modification_justification   ");
    query.append(" FROM  ");
    query.append(dbName);
    query.append("_history.project_partner_contributions t   ");


    query.append(" INNER JOIN users u ON t.modified_by = u.id ");


    query.append(") temp ");
    query.append("  GROUP BY email, action, modification_justification, ");
    // This line group the results that have the value of active_since in a range of +/- 2 seconds
    query.append(" UNIX_TIMESTAMP(active_since) DIV 2 ");
    query.append(" ORDER BY active_since DESC ");
    query.append(" LIMIT 0, 5 ");


    return this.getData(query.toString());
  }
}
