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

import org.cgiar.ccafs.ap.data.dao.ActivityDAO;
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
 * @author Javier Andrés Gallego B.
 * @author Hernán David Carvajal B.
 */
public class MySQLActivityDAO implements ActivityDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLActivityDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLActivityDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean deleteActivitiesByProject(int projectID, int userID, String justification) {
    LOG.debug(">> deleteActivitiesByProject(projectId={})", projectID);

    StringBuilder query = new StringBuilder();
    query.append("UPDATE FROM activities SET is_active = 0, modified_by = ?, modification_justification = ?");
    query.append("WHERE project_id = ? ");

    Object[] values = new Object[3];
    values[0] = userID;
    values[1] = justification;
    values[2] = projectID;

    int rowsDeleted = databaseManager.saveData(query.toString(), values);
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteActivitiesByProject():{}", true);
      return true;
    }
    LOG.debug("<< deleteActivitiesByProject():{}", false);
    return false;
  }

  @Override
  public boolean deleteActivity(int activityID, int userID, String justification) {
    LOG.debug(">> deleteActivity(id={})", activityID);
    String query = "UPDATE activities SET is_active = 0, modified_by = ?, modification_justification = ? WHERE id = ?";
    Object[] values = new Object[3];
    values[0] = userID;
    values[1] = justification;
    values[2] = activityID;
    int result = databaseManager.saveData(query, values);
    if (result >= 0) {
      LOG.debug("<< deleteActivity():{}", true);
      return true;
    }
    LOG.debug("<< deleteActivity:{}", false);
    return false;
  }

  @Override
  public boolean existActivity(int activityID) {
    LOG.debug(">> existActivity activityID = {} )", activityID);
    StringBuilder query = new StringBuilder();
    query.append("SELECT COUNT(id) FROM activities WHERE id = ");
    query.append(activityID);
    query.append(" AND is_active = 1");
    boolean exists = false;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        if (rs.getInt(1) > 0) {
          exists = true;
        }
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the activity id.", activityID, e.getMessage());
    }
    return exists;
  }

  @Override
  public List<Map<String, String>> getActivitiesByProject(int projectID) {
    LOG.debug(">> getActivitiesByProject projectID = {} )", projectID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT a.*   ");
    query.append("FROM activities as a ");
    query.append("WHERE a.project_id =  ");
    query.append(projectID);
    query.append(" AND a.is_active = 1");

    LOG.debug("-- getActivitiesByProject() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getActivitiesByProjectPartner(int projectPartnerID) {
    LOG.debug(">> getActivitiesByProjectPartner projectPartnerID = {} )", projectPartnerID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT a.* ");
    query.append("FROM activities as a ");
    query.append("WHERE a.leader_id =  ");
    query.append(projectPartnerID);
    query.append(" AND a.is_active = 1");

    LOG.debug("-- getActivitiesByProjectPartner() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public Map<String, String> getActivityById(int activityID) {
    Map<String, String> activityData = new HashMap<String, String>();
    LOG.debug(">> getActivityById( activityID = {} )", activityID);
    StringBuilder query = new StringBuilder();
    query.append("SELECT a.*   ");
    query.append("FROM activities as a ");
    query.append("WHERE a.id =  ");
    query.append(activityID);
    query.append(" AND a.is_active = 1");
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        activityData.put("id", rs.getString("id"));
        activityData.put("title", rs.getString("title"));
        activityData.put("activityStatus", rs.getString("activityStatus"));
        activityData.put("activityProgress", rs.getString("activityProgress"));
        activityData.put("description", rs.getString("description"));
        if (rs.getDate("startDate") != null) {
          activityData.put("startDate", rs.getDate("startDate").toString());
        }
        if (rs.getDate("endDate") != null) {
          activityData.put("endDate", rs.getDate("endDate").toString());
        }
        activityData.put("created", rs.getTimestamp("active_since").getTime() + "");
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the activity {}.", activityID, e);
    }
    LOG.debug("-- getActivityById() > Calling method executeQuery to get the results");
    return activityData;
  }

  @Override
  public List<Map<String, String>> getAllActivities() {
    LOG.debug(">> getAllActivities )");

    StringBuilder query = new StringBuilder();
    query.append("SELECT * ");
    query.append("FROM activities ");
    query.append("WHERE is_active = 1 ");

    LOG.debug("-- getAllActivities() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  private List<Map<String, String>> getData(String query) {
    LOG.debug(">> executeQuery(query='{}')", query);
    List<Map<String, String>> activitiesList = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> activityData = new HashMap<String, String>();
        activityData.put("id", rs.getString("id"));
        activityData.put("title", rs.getString("title"));
        activityData.put("description", rs.getString("description"));
        activityData.put("leader_id", rs.getString("leader_id"));
        activityData.put("activityStatus", rs.getString("activityStatus"));
        activityData.put("activityProgress", rs.getString("activityProgress"));
        if (rs.getDate("startDate") != null) {
          activityData.put("startDate", rs.getDate("startDate").toString());
        }
        if (rs.getDate("endDate") != null) {
          activityData.put("endDate", rs.getDate("endDate").toString());
        }
        activityData.put("created", rs.getTimestamp("active_since").getTime() + "");
        activitiesList.add(activityData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< executeQuery():activitiesList.size={}", activitiesList.size());
    return activitiesList;
  }

  @Override
  public List<Map<String, String>> getProjectActivitiesLedByUser(int projectID, int userID) {
    List<Map<String, String>> activities = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT a.* FROM activities a ");
    query.append("INNER JOIN project_partner_persons ppp ON a.leader_id = ppp.id ");
    query.append("INNER JOIN project_partners pp ON ppp.project_partner_id = pp.id ");
    query.append("WHERE ppp.id = ");
    query.append(userID);
    query.append(" AND pp.project_id = ");
    query.append(projectID);
    query.append(" AND a.is_active = 1");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      Map<String, String> activityData;

      while (rs.next()) {
        activityData = new HashMap<>();
        activityData.put("id", rs.getString("id"));
        activityData.put("title", rs.getString("title"));
        activityData.put("activityStatus", rs.getString("activityStatus"));
        activityData.put("activityProgress", rs.getString("activityProgress"));
        activities.add(activityData);
      }
    } catch (SQLException e) {
      LOG.error("getActivitiesLedByUser() > Exception raised trying to get the activities led by user {}.", userID, e);
    }

    return activities;
  }

  @Override
  public int saveActivity(int projectID, Map<String, Object> activityData, User user, String justification) {
    LOG.debug(">> saveActivity(activityData={})", activityData);
    StringBuilder query = new StringBuilder();

    Object[] values;
    int result = -1;
    if (activityData.get("id") == null) {
      // Insert new activity record
      query.append("INSERT INTO activities (project_id, title, description, startDate, endDate, leader_id, ");
      query.append("modified_by, modification_justification,activityStatus,activityProgress) ");
      query.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?,?, ?) ");
      values = new Object[10];
      values[0] = projectID;
      values[1] = activityData.get("title");
      values[2] = activityData.get("description");
      values[3] = activityData.get("startDate");
      values[4] = activityData.get("endDate");
      values[5] = activityData.get("leader_id");
      values[6] = activityData.get("modified_by");
      values[7] = activityData.get("modification_justification");
      values[8] = activityData.get("activityStatus");
      values[9] = activityData.get("activityProgress");
      result = databaseManager.saveData(query.toString(), values);
      if (result <= 0) {
        LOG.error("A problem happened trying to add a new activity with id={}", activityData.get("id"));
      }
    } else {
      // update activity record
      query.append("UPDATE activities SET title = ?, description = ?, startDate = ?, endDate = ?, ");
      query
        .append("leader_id = ?, modified_by = ?, modification_justification = ? ,activityStatus=?,activityProgress=? ");
      query.append("WHERE id = ? ");
      values = new Object[10];
      values[0] = activityData.get("title");
      values[1] = activityData.get("description");
      values[2] = activityData.get("startDate");
      values[3] = activityData.get("endDate");
      values[4] = activityData.get("leader_id");
      values[5] = activityData.get("modified_by");
      values[6] = activityData.get("modification_justification");
      values[7] = activityData.get("activityStatus");
      values[8] = activityData.get("activityProgress");

      values[9] = activityData.get("id");
      result = databaseManager.saveData(query.toString(), values);
      if (result == -1) {
        LOG.error("A problem happened trying to update the activity identified with the id = {}",
          activityData.get("id"));
      }
    }
    return result;
  }

  @Override
  public int saveActivityLeader(int activityID, int userID) {
    LOG.debug(">> saveActivityLeader(employeeID={}, activityID={})", new Object[] {userID, activityID});
    StringBuilder query = new StringBuilder();
    int result = -1;
    Object[] values;

    // update activity record
    query.append("UPDATE activities SET leader_id = ? ");
    query.append("WHERE id = ? ");
    values = new Object[2];
    values[0] = userID;
    values[1] = activityID;
    result = databaseManager.saveData(query.toString(), values);
    if (result == -1) {
      LOG.error("A problem happened trying to update the activity leader with the id = {}", activityID);
      return -1;
    }

    LOG.debug("<< saveActivityLeader():{}", result);
    return result;
  }

  @Override
  public boolean saveActivityList(int projectID, List<Map<String, Object>> activityArrayMap, User user,
    String justification) {
    LOG.debug(">> saveActivityList(activityArray={})", activityArrayMap);
    StringBuilder query = new StringBuilder();
    boolean saved = true;
    int result = -1;
    for (Map<String, Object> activityData : activityArrayMap) {
      Object[] values;
      if (activityData.get("id") == null) {
        // Insert new activity record
        query.append("INSERT INTO activities (project_id, title, description, startDate, endDate, leader_id ) ");
        query.append("modified_by, modification_justification) ");
        query.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?) ");
        values = new Object[8];
        values[0] = projectID;
        values[1] = activityData.get("title");
        values[2] = activityData.get("description");
        values[3] = activityData.get("startDate");
        values[4] = activityData.get("endDate");
        values[5] = activityData.get("leader_id");
        values[6] = activityData.get("modified_by");
        values[7] = activityData.get("modification_justification");
        result = databaseManager.saveData(query.toString(), values);
        if (result < 0) {
          saved = false;
        }
      } else {
        // update activity record
        query.append("UPDATE activities SET title = ?, description = ?, startDate = ?, endDate = ?, ");
        query.append("leader_id = ?, modified_by = ?, modification_justification = ? ");
        query.append("WHERE id = ? ");
        values = new Object[8];
        values[0] = activityData.get("title");
        values[1] = activityData.get("description");
        values[2] = activityData.get("startDate");
        values[3] = activityData.get("endDate");
        values[4] = activityData.get("leader_id");
        values[5] = activityData.get("id");
        values[6] = activityData.get("modified_by");
        values[7] = activityData.get("modification_justification");
        result = databaseManager.saveData(query.toString(), values);
        if (result <= 0) {
          saved = false;
        }
      }
    }
    return saved;
  }
}