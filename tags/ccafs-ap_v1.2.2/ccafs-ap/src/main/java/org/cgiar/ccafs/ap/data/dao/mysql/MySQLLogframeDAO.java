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
import org.cgiar.ccafs.ap.data.dao.LogframeDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLLogframeDAO implements LogframeDAO {

  private static final Logger LOG = LoggerFactory.getLogger(MySQLLogframeDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLLogframeDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public Map<String, String> getLogframe(int id) {
    LOG.debug(">> getLogframe(id={})", id);

    Map<String, String> logframe = new HashMap<>();
    String query = "SELECT * FROM logframes WHERE id = " + id;

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      if (rs.next()) {
        logframe.put("id", rs.getString("id"));
        logframe.put("year", rs.getString("year"));
        logframe.put("name", rs.getString("name"));
      }
    } catch (SQLException e) {
      LOG.error("-- getLogframe() > There was an error getting the 'logframe' {}.", id, e);
      return null;
    }

    LOG.debug("<< getLogframe():{}", logframe.toString());
    return logframe;
  }

  @Override
  public Map<String, String> getLogframeByYear(int year) {
    LOG.debug(">> getLogframeByYear(year={})", year);
    Map<String, String> logframe = new HashMap<>();
    String query = "SELECT * FROM logframes WHERE year = " + year;

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      if (rs.next()) {
        logframe.put("id", rs.getString("id"));
        logframe.put("year", rs.getString("year"));
        logframe.put("name", rs.getString("name"));
      }
    } catch (SQLException e) {
      LOG.error("-- getLogframeByYear() > There was an error getting the 'logframes' for year {}", year, e);
      return null;
    }

    LOG.debug("<< getLogframeByYear():{}", logframe.toString());
    return logframe;
  }

}
