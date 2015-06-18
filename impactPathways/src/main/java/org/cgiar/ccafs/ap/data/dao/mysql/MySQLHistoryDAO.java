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
 */

public class MySQLHistoryDAO implements HistoryDAO {

  private static Logger LOG = LoggerFactory.getLogger(MySQLHistoryDAO.class);
  private DAOManager daoManager;

  @Inject
  public MySQLHistoryDAO(DAOManager daoManager) {
    this.daoManager = daoManager;
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
  public List<Map<String, String>> getHistoryList(String tableName, int record_id) {
    List<Map<String, String>> historyData = new ArrayList<>();
    String dbName = this.getDatabaseName();

    StringBuilder query = new StringBuilder();
    query.append("SELECT u.id as 'user_id', u.first_name, u.last_name, u.email, t.action, ");
    query.append("t.active_since, t.modification_justification ");
    query.append("FROM ");
    query.append(dbName);
    query.append("_history.");
    query.append(tableName);
    query.append(" t ");
    query.append("INNER JOIN users u ON t.modified_by = u.id ");
    query.append("WHERE record_id = ");
    query.append(record_id);
    query.append(" ORDER BY t.active_since DESC ");

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
      LOG.error("There was an exception trying to get log history for table {}.", tableName, e);
    }
    return historyData;
  }
}