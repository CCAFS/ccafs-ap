package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.LeaderDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
    Map<String, String> leaderData = new HashMap<>();
    String query =
      "SELECT al.id, al.name, lt.id as leader_type_id, lt.name leader_type_name "
        + "FROM activities a, activity_leaders al, leader_types lt "
        + "WHERE a.activity_leader_id = al.id AND al.led_activity_id = lt.id AND a.id = " + activityID;
    try (Connection conn = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, conn);
      if (rs.next()) {
        leaderData.put("id", rs.getString("id"));
        leaderData.put("name", rs.getString("name"));
        leaderData.put("leader_type_id", rs.getString("leader_type_id"));
        leaderData.put("leader_type_name", rs.getString("leader_type_name"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("There was an error getting the activity leader of an activity. \n{}", query, e);
    }
    return leaderData;
  }

  @Override
  public Map<String, String> getUserLeader(int userID) {
    Map<String, String> leaderData = new HashMap<>();
    String query =
      "SELECT al.id, al.name, lt.id as leader_type_id, lt.name as leader_type_name "
        + "FROM users u, activity_leaders al, leader_types lt "
        + "WHERE u.activity_leader_id = al.id AND al.led_activity_id = lt.id AND u.id = " + userID;
    try (Connection conn = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, conn);
      if (rs.next()) {
        leaderData.put("id", rs.getString("id"));
        leaderData.put("name", rs.getString("name"));
        leaderData.put("leader_type_id", rs.getString("leader_type_id"));
        leaderData.put("leader_type_name", rs.getString("leader_type_name"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("There was an error getting the user leader related to an user. \n{}", query, e);
    }
    return leaderData;
  }


}
