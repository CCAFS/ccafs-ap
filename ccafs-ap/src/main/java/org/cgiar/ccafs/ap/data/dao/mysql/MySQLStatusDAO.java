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

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.StatusDAO;

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


public class MySQLStatusDAO implements StatusDAO {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MySQLStatusDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLStatusDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getStatusList() {
    LOG.debug(">> getStatusList()");
    List<Map<String, String>> statusList = new ArrayList<>();
    try (Connection con = databaseManager.getConnection()) {
      String query = "SELECT * from activity_status";
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> statusData = new HashMap<>();
        statusData.put("id", rs.getString("id"));
        statusData.put("name", rs.getString("name"));
        statusList.add(statusData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getStatusList() > There was an error getting the activity status list.", e);
    }

    LOG.debug("<< getStatusList():statusList.size={}", statusList.size());
    return statusList;
  }

}
