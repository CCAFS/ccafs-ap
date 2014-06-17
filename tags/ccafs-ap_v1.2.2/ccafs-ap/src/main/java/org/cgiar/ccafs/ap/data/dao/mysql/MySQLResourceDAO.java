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
import org.cgiar.ccafs.ap.data.dao.ResourceDAO;

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


public class MySQLResourceDAO implements ResourceDAO {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MySQLResourceDAO.class);
  DAOManager databaseManager;

  @Inject
  public MySQLResourceDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getResources(int activityID) {
    LOG.debug(">> getResources(activityID={})", activityID);
    List<Map<String, String>> resorcesDataList = new ArrayList<>();
    String query = "SELECT id, name FROM resources WHERE activity_id=" + activityID;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> resourcesData = new HashMap<String, String>();
        resourcesData.put("id", rs.getString("id"));
        resourcesData.put("name", rs.getString("name"));
        resorcesDataList.add(resourcesData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getResources() > There was an error getting the resources associated with activity {}", activityID,
        e);
    }
    LOG.debug("<< getResources():resorcesDataList.size={}", resorcesDataList.size());
    return resorcesDataList;
  }

  @Override
  public boolean removeResources(int activityID) {
    LOG.debug(">> removeResources(activityID={})", activityID);
    boolean problem = false;
    String removeQuery = "DELETE FROM resources WHERE activity_id = " + activityID;
    try (Connection connection = databaseManager.getConnection()) {
      int rows = databaseManager.makeChange(removeQuery, connection);
      if (rows < 0) {
        problem = true;
      }
    } catch (SQLException e) {
      LOG.error("-- removeResources() > There was an error deleting the resources related to activity {}.", activityID,
        e);
    }

    LOG.debug("<< removeResources():{}", !problem);
    return !problem;
  }

  @Override
  public boolean saveResource(Map<String, String> resourceData) {
    LOG.debug(">> saveResource(resourceData={})", resourceData);
    boolean saved = false;
    String query = "INSERT INTO resources (id, name, activity_id) VALUES (?, ?, ?)";
    Object[] values = new Object[3];
    values[0] = resourceData.get("id");
    values[1] = resourceData.get("name");
    values[2] = resourceData.get("activity_id");

    try (Connection con = databaseManager.getConnection()) {
      int rows = databaseManager.makeChangeSecure(con, query, values);
      if (rows < 0) {
        LOG.warn("-- saveResource() > There was an error saving the resource. \n Query: {}. \n Values: {}", query,
          values);
      } else {
        saved = true;
      }
    } catch (SQLException e) {
      LOG.error("-- saveResource() > There was an error saving the resource into the db.", e);
    }

    LOG.debug("<< saveResource():{}", saved);
    return saved;
  }

}
