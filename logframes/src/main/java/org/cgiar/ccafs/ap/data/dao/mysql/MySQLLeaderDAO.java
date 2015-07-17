package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.LeaderDAO;

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


public class MySQLLeaderDAO implements LeaderDAO {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MySQLLeaderDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLLeaderDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public Map<String, String> getActivityLeader(int activityID) {
    LOG.debug(">> getActivityLeader(activityID={})", activityID);
    Map<String, String> leaderData = new HashMap<>();
    String query =
      "SELECT al.id, al.name, al.acronym, lt.id as 'leader_type_id', lt.name 'leader_type_name', "
        + "r.id as 'region_id', r.name as 'region_name', t.id as 'theme_id', t.code as 'theme_code' "
        + "FROM activities a " + "INNER JOIN activity_leaders al ON a.activity_leader_id = al.id "
        + "INNER JOIN leader_types lt ON al.led_activity_id = lt.id " + "LEFT JOIN themes t ON al.theme_id = t.id "
        + "LEFT JOIN regions r ON al.region_id = r.id " + "WHERE a.id = " + activityID;
    try (Connection conn = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, conn);
      if (rs.next()) {
        leaderData.put("id", rs.getString("id"));
        leaderData.put("name", rs.getString("name"));
        leaderData.put("acronym", rs.getString("acronym"));
        leaderData.put("leader_type_id", rs.getString("leader_type_id"));
        leaderData.put("leader_type_name", rs.getString("leader_type_name"));
        leaderData.put("region_id", rs.getString("region_id"));
        leaderData.put("region_name", rs.getString("region_name"));
        leaderData.put("theme_id", rs.getString("theme_id"));
        leaderData.put("theme_code", rs.getString("theme_code"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getActivityLeader() > There was an error getting the activity leader for activity {}.", activityID,
        e);
    }
    LOG.debug("<< getActivityLeader():{}", leaderData.toString());
    return leaderData;
  }

  @Override
  public List<Map<String, String>> getAllLeaders() {
    LOG.debug(">> getAllLeaders()");

    List<Map<String, String>> leadersData = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT al.id, al.acronym, al.name, lt.id as 'leader_type_id', lt.name as 'leader_type_name' ");
    query.append("FROM activity_leaders al ");
    query.append("INNER JOIN leader_types lt ON lt.id = al.led_activity_id");
    try (Connection conn = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), conn);
      while (rs.next()) {
        Map<String, String> data = new HashMap<>();
        data.put("id", rs.getString("id"));
        data.put("acronym", rs.getString("acronym"));
        data.put("name", rs.getString("name"));
        data.put("leader_type_id", rs.getString("leader_type_id"));
        data.put("leader_type_name", rs.getString("leader_type_name"));
        leadersData.add(data);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getAllLeaders() > There was an error getting the activity leaders list.", e);
    }

    LOG.debug("<< getAllLeaders():leadersData.size={}", leadersData.size());
    return leadersData;
  }

  @Override
  public Map<String, String> getUserLeader(int userID) {
    LOG.debug(">> getUserLeader(userID={})", userID);

    Map<String, String> leaderData = new HashMap<>();
    String query =
      "SELECT al.id, al.name, lt.id as leader_type_id, lt.name as leader_type_name, "
        + "r.id as 'region_id', r.name as 'region_name', t.id as 'theme_id', t.code as 'theme_code' "
        + "FROM users u, activity_leaders al, leader_types lt "
        + "INNER JOIN activity_leaders al ON activity_leader_id = al.id "
        + "INNER JOIN leader_types lt ON al.led_activity_id = lt.id " + "LEFT JOIN themes t ON al.theme_id = t.id "
        + "LEFT JOIN regions r ON al.region_id = r.id " + "WHERE u.id = " + userID;
    try (Connection conn = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, conn);
      if (rs.next()) {
        leaderData.put("id", rs.getString("id"));
        leaderData.put("name", rs.getString("name"));
        leaderData.put("leader_type_id", rs.getString("leader_type_id"));
        leaderData.put("leader_type_name", rs.getString("leader_type_name"));
        leaderData.put("region_id", rs.getString("region_id"));
        leaderData.put("region_name", rs.getString("region_name"));
        leaderData.put("theme_id", rs.getString("theme_id"));
        leaderData.put("theme_code", rs.getString("theme_code"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getUserLeader() > There was an error getting the user leader related to the user {}.", userID, e);
    }

    LOG.debug("<< getUserLeader():{}", leaderData.toString());
    return leaderData;
  }
}