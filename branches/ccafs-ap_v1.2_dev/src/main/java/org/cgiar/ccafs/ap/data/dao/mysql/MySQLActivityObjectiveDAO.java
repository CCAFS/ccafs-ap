package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.ActivityObjectiveDAO;
import org.cgiar.ccafs.ap.data.dao.DAOManager;

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


public class MySQLActivityObjectiveDAO implements ActivityObjectiveDAO {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MySQLActivityObjectiveDAO.class);
  DAOManager databaseManager;

  @Inject
  public MySQLActivityObjectiveDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean deleteActivityObjectives(int activityID) {
    LOG.debug(">> deleteActivityObjectives(activityID={})", activityID);
    boolean deleted = false;
    String query = "DELETE FROM activity_objectives WHERE activity_id = ?";
    Object[] values = new Object[1];
    values[0] = activityID;
    try (Connection con = databaseManager.getConnection()) {
      int rows = databaseManager.makeChangeSecure(con, query, values);
      if (rows < 0) {
        LOG
          .warn(
            "-- deleteActivityObjectives() > There was a problem deleting the objectives related to the activity {}. \n{}",
            activityID, query);
      } else {
        deleted = true;
      }
    } catch (SQLException e) {
      LOG.error(
        "-- deleteActivityObjectives() > There was an error deleting the objectives related to the activity {}",
        activityID, e);
    }
    LOG.debug("<< deleteActivityObjectives():{}", deleted);
    return deleted;
  }

  @Override
  public List<Map<String, String>> getActivityObjectives(int activityID) {
    LOG.debug(">> getActivityObjectives(activityID={})", activityID);
    List<Map<String, String>> activityObjectivesDataList = new ArrayList<>();
    String query = "SELECT id, description FROM activity_objectives WHERE activity_id = " + activityID;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> activityObjectivesData = new HashMap<>();
        activityObjectivesData.put("id", rs.getString("id"));
        activityObjectivesData.put("description", rs.getString("description"));
        activityObjectivesDataList.add(activityObjectivesData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getActivityObjectives() > There was an error getting the activity objectives for activity {}",
        activityID, e);
    }
    LOG.debug("<< getActivityObjectives():activityObjectivesDataList.size={}", activityObjectivesDataList.size());
    return activityObjectivesDataList;
  }

  @Override
  public boolean saveActivityObjectives(Map<String, String> objectives, int activityID) {
    LOG.debug(">> saveActivityObjectives(objectives={}, activityID={})", objectives, activityID);
    boolean saved = false;
    String query =
      "INSERT INTO activity_objectives (id, description, activity_id) VALUES (?, ?, " + activityID + ") "
        + "ON DUPLICATE KEY UPDATE description = VALUES(description), activity_id = VALUES(activity_id)";
    Object[] values = new Object[2];
    values[0] = objectives.get("id");
    values[1] = objectives.get("description");
    try (Connection con = databaseManager.getConnection()) {
      int rows = databaseManager.makeChangeSecure(con, query, values);
      if (rows > 0) {
        saved = true;
      }
    } catch (SQLException e) {
      LOG.error("-- saveActivityObjectives() > There was an error saving objectives for activity {}.", activityID);
    }

    LOG.debug("<< saveActivityObjectives():{}", saved);
    return saved;
  }
}
