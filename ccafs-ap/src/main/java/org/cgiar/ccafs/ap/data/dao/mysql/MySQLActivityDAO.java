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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLActivityDAO implements ActivityDAO {

  private static final Logger LOG = LoggerFactory.getLogger(MySQLActivityDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLActivityDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }


  @Override
  public List<Map<String, String>> getActivities(int year) {
    /*
     * TODO - Validate if the list of activities correspond to the planning section or to the reporting section
     * (is_planning)
     */
    List<Map<String, String>> activities = new ArrayList<>();
    try (Connection con = databaseManager.getConnection()) {
      String query =
        "SELECT a.id, a.title, a.start_date, a.end_date, a.description, m.id as 'milestone_id', m.code as 'milestone_code', "
          + "th.id as 'theme_id', th.code as 'theme_code', al.id as 'leader_id', al.name as 'leader_name'"
          + "FROM activities a, milestones m, outputs ou, objectives ob, themes th, logframes lo, activity_leaders al "
          + "WHERE a.milestone_id = m.id " + "AND m.output_id = ou.id " + "AND ou.objective_id = ob.id "
          + "AND ob.theme_id = th.id " + "AND th.logframe_id = lo.id " + "AND a.activity_leader_id = al.id "
          + "AND lo.year = " + year;
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> activity = new HashMap<>();
        activity.put("id", rs.getString("id"));
        activity.put("title", rs.getString("title"));
        activity.put("start_date", rs.getString("start_date"));
        activity.put("end_date", rs.getString("end_date"));
        activity.put("description", rs.getString("description"));
        activity.put("milestone_id", rs.getString("milestone_id"));
        activity.put("milestone_code", rs.getString("milestone_code"));
        activity.put("theme_id", rs.getString("theme_id"));
        activity.put("theme_code", rs.getString("theme_code"));
        activity.put("leader_id", rs.getString("leader_id"));
        activity.put("leader_name", rs.getString("leader_name"));
        activities.add(activity);
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
    return activities;
  }

  @Override
  public List<Map<String, String>> getActivities(int year, int leaderTypeCode) {
    /*
     * TODO - Validate if the list of activities correspond to the planning section or to the reporting section
     * (is_planning)
     */
    List<Map<String, String>> activities = new ArrayList<>();
    try (Connection con = databaseManager.getConnection()) {
      String query =
        "SELECT a.id, a.title, a.start_date, a.end_date, a.description, m.id as 'milestone_id', m.code as 'milestone_code', "
          + "th.id as 'theme_id', th.code as 'theme_code', al.id as 'leader_id', al.name as 'leader_name'"
          + "FROM activities a, milestones m, outputs ou, objectives ob, themes th, logframes lo, activity_leaders al "
          + "WHERE a.milestone_id = m.id " + "AND m.output_id = ou.id " + "AND ou.objective_id = ob.id "
          + "AND ob.theme_id = th.id " + "AND th.logframe_id = lo.id " + "AND a.activity_leader_id = al.id "
          + "AND lo.year = " + year + " " + "AND al.id = " + leaderTypeCode;
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> activity = new HashMap<>();
        activity.put("id", rs.getString("id"));
        activity.put("title", rs.getString("title"));
        activity.put("start_date", rs.getString("start_date"));
        activity.put("end_date", rs.getString("end_date"));
        activity.put("description", rs.getString("description"));
        activity.put("milestone_id", rs.getString("milestone_id"));
        activity.put("milestone_code", rs.getString("milestone_code"));
        activity.put("theme_id", rs.getString("theme_id"));
        activity.put("theme_code", rs.getString("theme_code"));
        activity.put("leader_id", rs.getString("leader_id"));
        activity.put("leader_name", rs.getString("leader_name"));
        activities.add(activity);
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return activities;
  }

  @Override
  public Map<String, String> getActivity(int id) {
    Map<String, String> activity = new HashMap<>();
    try (Connection con = databaseManager.getConnection()) {
      String query =
        "SELECT a.title, a.start_date, a.end_date, a.description, a.status_description, astatus.id as status_id, astatus.name as status_name "
          + "FROM activities a, activity_status astatus "
          + "WHERE astatus.id = a.activity_status_id "
          + "AND a.id = "
          + id;
      ResultSet rs = databaseManager.makeQuery(query, con);
      if (rs.next()) {
        activity.put("title", rs.getString("title"));
        activity.put("start_date", rs.getString("start_date"));
        activity.put("end_date", rs.getString("end_date"));
        activity.put("description", rs.getString("description"));
        activity.put("status_description", rs.getString("status_description"));
        activity.put("status_id", rs.getString("status_id"));
        activity.put("status_name", rs.getString("status_name"));
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    if (activity.isEmpty()) {
      return null;
    } else {
      return activity;
    }
  }


}
