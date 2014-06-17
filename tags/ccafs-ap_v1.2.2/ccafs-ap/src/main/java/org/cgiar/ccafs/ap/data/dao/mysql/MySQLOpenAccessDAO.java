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
import org.cgiar.ccafs.ap.data.dao.OpenAccessDAO;

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


public class MySQLOpenAccessDAO implements OpenAccessDAO {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MySQLPartnerDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLOpenAccessDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public Map<String, String> getOpenAccess(String id) {
    LOG.debug(">> getOpenAccess(id='{}')", id);
    Map<String, String> oaData = new HashMap<>();
    String query = "SELECT * FROM open_access WHERE id = " + id;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      if (rs.next()) {
        oaData.put("id", rs.getString("id"));
        oaData.put("name", rs.getString("name"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getOpenAccess() > There was an error getting the data for open access {}", id, e);
    }

    LOG.debug("<< getOpenAccess():{}", oaData.toString());
    return oaData;
  }

  @Override
  public List<Map<String, String>> getOpenAccessOptions() {
    LOG.debug(">> getOpenAccessOptions()");
    List<Map<String, String>> openAccessDataList = new ArrayList<>();
    String query = "SELECT * FROM open_access";
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> openAccessData = new HashMap<String, String>();
        openAccessData.put("id", rs.getString("id"));
        openAccessData.put("name", rs.getString("name"));
        openAccessDataList.add(openAccessData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getOpenAccessOptions() > There was an error getting the open access list. ", e);
    }

    LOG.debug("<< getOpenAccessOptions():openAccessDataList.size={}", openAccessDataList.size());
    return openAccessDataList;
  }

}
