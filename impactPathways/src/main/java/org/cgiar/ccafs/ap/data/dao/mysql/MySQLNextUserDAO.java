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
 * ***************************************************************
 */
package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.NextUserDAO;
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
 * @author Javier Andrés Gallego B.
 */
public class MySQLNextUserDAO implements NextUserDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLNextUserDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLNextUserDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean deleteNextUserById(int nextUserID, int userID, String justification) {
    LOG.debug(">> deleteNextUserById(id={})", nextUserID);

    String query = "UPDATE next_users SET is_active = 0, modified_by = ?, modification_justification = ? WHERE id = ?";
    Object[] values = new Object[3];
    values[0] = userID;
    values[1] = justification;
    values[2] = nextUserID;

    int rowsDeleted = databaseManager.saveData(query, values);
    if (rowsDeleted == 0) {
      LOG.debug("<< deleteNextUserById():{}", true);
      return true;
    }

    LOG.debug("<< deleteNextUserById:{}", false);
    return false;
  }

  @Override
  public boolean deleteNextUsersByDeliverableId(int deliverableID, int userID, String justification) {
    LOG.debug(">> deleteNextUserByDeliverableId(deliverableID={})", deliverableID);

    StringBuilder query = new StringBuilder();
    query.append("UPDATE next_users SET is_active = 0, modified_by = ?, modification_justification = ? ");
    query.append("WHERE deliverable_id = ? ");
    Object[] values = new Object[3];
    values[0] = userID;
    values[1] = justification;
    values[2] = deliverableID;

    int rowsDeleted = databaseManager.saveData(query.toString(), values);
    if (rowsDeleted == 0) {
      LOG.debug("<< deleteNextUserByDeliverableId():{}", true);
      return true;
    }
    LOG.debug("<< deleteNextUserByDeliverableId():{}", false);
    return false;
  }

  private List<Map<String, String>> getData(String query) {
    LOG.debug(">> executeQuery(query='{}')", query);
    List<Map<String, String>> nextUsersList = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> nextUserData = new HashMap<String, String>();
        nextUserData.put("id", rs.getString("id"));
        nextUserData.put("deliverable_id", rs.getString("deliverable_id"));
        nextUserData.put("user", rs.getString("user"));
        nextUserData.put("expected_changes", rs.getString("expected_changes"));
        nextUserData.put("strategies", rs.getString("strategies"));

        nextUsersList.add(nextUserData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< executeQuery():nextUsersList.size={}", nextUsersList.size());
    return nextUsersList;
  }


  @Override
  public Map<String, String> getNextUserById(int nextUserID) {
    Map<String, String> nextUserData = new HashMap<String, String>();
    LOG.debug(">> getNextUserById( activityID = {} )", nextUserID);
    StringBuilder query = new StringBuilder();
    query.append("SELECT nu.*   ");
    query.append("FROM next_users as nu ");
    query.append("WHERE nu.id=  ");
    query.append(nextUserID);
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        nextUserData.put("id", rs.getString("id"));
        nextUserData.put("deliverable_id", rs.getString("deliverable_id"));
        nextUserData.put("user", rs.getString("user"));
        nextUserData.put("expected_changes", rs.getString("expected_changes"));
        nextUserData.put("strategies", rs.getString("strategies"));

      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the next User {}.", nextUserID, e);
    }
    LOG.debug("-- getNextUserById() > Calling method executeQuery to get the results");
    return nextUserData;
  }

  @Override
  public List<Map<String, String>> getNextUsersByDeliverable(int deliverableID) {
    LOG.debug(">> getNextUsersByDeliverable projectID = {} )", deliverableID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT nu.*   ");
    query.append("FROM next_users as nu ");
    query.append("INNER JOIN deliverables d ON nu.deliverable_id = d.id ");
    query.append("WHERE nu.deliverable_id =  ");
    query.append(deliverableID);
    query.append(" AND nu.is_active = 1");


    LOG.debug("-- getNextUsersByDeliverable() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public int saveNextUser(int deliverableID, Map<String, Object> nextUserData) {
    LOG.debug(">> saveNextUser(activityData={})", nextUserData);
    StringBuilder query = new StringBuilder();
    int result = -1;
    Object[] values;
    if (nextUserData.get("id") == null) {
      // Insert new next user record.
      query.append(
        "INSERT INTO next_users (deliverable_id, user, expected_changes, strategies, created_by, modified_by, modification_justification) ");
      query.append("VALUES (?, ?, ?, ?, ?, ?, ?) ");
      values = new Object[7];
      values[0] = deliverableID;
      values[1] = nextUserData.get("user");
      values[2] = nextUserData.get("expected_changes");
      values[3] = nextUserData.get("strategies");
      // Logs
      values[4] = nextUserData.get("created_by");
      values[5] = nextUserData.get("modified_by");
      values[6] = nextUserData.get("modification_justification");
    } else {
      // Updating existing deliverable record
      query.append(
        "UPDATE next_users SET user = ?, expected_changes = ?, strategies = ?, modified_by = ?, modification_justification = ? ");
      query.append("WHERE id = ? ");
      values = new Object[6];
      values[0] = nextUserData.get("user");
      values[1] = nextUserData.get("expected_changes");
      values[2] = nextUserData.get("strategies");
      // Logs
      values[3] = nextUserData.get("modified_by");
      values[4] = nextUserData.get("modification_justification");

      values[5] = nextUserData.get("id");
    }
    result = databaseManager.saveData(query.toString(), values);
    if (result == -1) {
      LOG.error("A problem happened trying to add a new next user with deliverable id={}", deliverableID);
      return -1;
    }
    LOG.debug("<< saveNextUser():{}", result);
    return result;
  }


}
