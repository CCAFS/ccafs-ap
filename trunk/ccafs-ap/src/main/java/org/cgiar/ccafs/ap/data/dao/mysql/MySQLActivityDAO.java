package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.ActivityDAO;
import org.cgiar.ccafs.ap.data.dao.DAOManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class MySQLActivityDAO implements ActivityDAO {

  private DAOManager databaseManager;

  @Inject
  public MySQLActivityDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getAllActivities() {
    List<Map<String, String>> activities = new ArrayList<>();
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery("SELECT * FROM activities", con);
      while (rs.next()) {
        Map<String, String> activity = new HashMap<>();
        activity.put("id", rs.getString("id"));
        activity.put("title", rs.getString("title"));
        activity.put("start_date", rs.getString("start_date"));
        activity.put("end_date", rs.getString("end_date"));
        activity.put("description", rs.getString("description"));
        activities.add(activity);
      }
      return activities;
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
  }

}
