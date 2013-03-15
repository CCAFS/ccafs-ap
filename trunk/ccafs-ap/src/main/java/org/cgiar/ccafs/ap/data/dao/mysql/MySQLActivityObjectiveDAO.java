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
  public List<Map<String, String>> getActivityObjectives(int activityID) {
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
      LOG.error("There was an error getting the data from 'activity_objectives' table. \n{}", query, e);
    }
    return activityObjectivesDataList;
  }
}
