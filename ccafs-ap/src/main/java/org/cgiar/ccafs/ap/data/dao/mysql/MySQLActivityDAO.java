package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.ActivityDAO;
import org.cgiar.ccafs.ap.data.dao.DAOManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
          + "th.id as 'theme_id', th.code as 'theme_code', al.id as 'leader_id', al.acronym as 'leader_acronym', al.name as 'leader_name'"
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
        activity.put("leader_acronym", rs.getString("leader_acronym"));
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
          + "th.id as 'theme_id', th.code as 'theme_code', al.id as 'leader_id', al.acronym as 'leader_acronym', al.name as 'leader_name', a.status_description "
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
        activity.put("leader_acronym", rs.getString("leader_acronym"));
        activity.put("leader_name", rs.getString("leader_name"));
        activity.put("status_description", rs.getString("status_description"));
        activities.add(activity);
      }
      rs.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return activities;
  }

  @Override
  public Map<String, String> getActivityDeliverablesInfo(int id) {
    Map<String, String> activity = new HashMap<>();
    try (Connection con = databaseManager.getConnection()) {
      String query =
        "SELECT a.title, a.id, al.id as 'leader_id', 'al.name' as 'leader_name', al.acronym as 'leader_acronym' "
          + "FROM activities a, activity_leaders al " + "WHERE a.activity_leader_id = al.id AND a.id = " + id;
      ResultSet rs = databaseManager.makeQuery(query, con);
      if (rs.next()) {
        activity.put("title", rs.getString("title"));
        activity.put("id", rs.getString("id"));
        activity.put("leader_id", rs.getString("leader_id"));
        activity.put("leader_name", rs.getString("leader_name"));
        activity.put("leader_acronym", rs.getString("leader_acronym"));
      }
      rs.close();
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


  @Override
  public Map<String, String> getActivityStatusInfo(int id) {
    Map<String, String> activity = new HashMap<>();
    try (Connection con = databaseManager.getConnection()) {
      String query =
        "SELECT a.title, a.start_date, a.end_date, a.description, a.status_description, astatus.id as status_id, astatus.name as status_name, "
          + "a.milestone_id, m.code as milestone_code, al.id as 'leader_id', al.acronym as 'leader_acronym', al.name as 'leader_name', "
          + "g.id as 'gender_id', g.description as 'gender_description' "
          + "FROM activities a INNER JOIN milestones m ON a.milestone_id = m.id "
          + "INNER JOIN activity_status astatus ON a.activity_status_id = astatus.id "
          + "INNER JOIN activity_leaders al ON a.activity_leader_id = al.id "
          + "LEFT OUTER JOIN gender_integrations g ON g.activity_id = a.id WHERE a.id = " + id;
      ResultSet rs = databaseManager.makeQuery(query, con);
      if (rs.next()) {
        activity.put("title", rs.getString("title"));
        activity.put("start_date", rs.getString("start_date"));
        activity.put("end_date", rs.getString("end_date"));
        activity.put("description", rs.getString("description"));
        activity.put("status_description", rs.getString("status_description"));
        activity.put("status_id", rs.getString("status_id"));
        activity.put("status_name", rs.getString("status_name"));
        activity.put("milestone_id", rs.getString("milestone_id"));
        activity.put("milestone_code", rs.getString("milestone_code"));
        activity.put("leader_id", rs.getString("leader_id"));
        activity.put("leader_acronym", rs.getString("leader_acronym"));
        activity.put("leader_name", rs.getString("leader_name"));
        activity.put("gender_id", rs.getString("gender_id"));
        activity.put("gender_description", rs.getString("gender_description"));
      }
      rs.close();
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

  @Override
  public boolean isValidId(int id) {
    boolean isValid = false;
    try (Connection con = databaseManager.getConnection()) {
      String query = "SELECT id FROM activities WHERE id = " + id;
      ResultSet rs = databaseManager.makeQuery(query, con);
      isValid = rs.next();
      rs.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return isValid;
  }


  @Override
  public boolean saveStatus(Map<String, String> activityData) {
    boolean problem = false;
    try (Connection connection = databaseManager.getConnection()) {
      // Update activity.
      Object[] values = new Object[3];
      values[0] = activityData.get("activity_status_id");
      values[1] = activityData.get("status_description");
      values[2] = activityData.get("activity_id");
      String activityQueryPrepared =
        "UPDATE activities SET activity_status_id = ?, status_description = ? WHERE id = ?";
      int activityUpdatedResult = databaseManager.makeChangeSecure(connection, activityQueryPrepared, values);
      // Reporting errors in the previous update.
      if (activityUpdatedResult < 0) {
        problem = true;
        LOG.error("There was an error trying to update te status of an activity:");
        LOG.error("  Query: " + activityQueryPrepared);
        LOG.error("  Values: " + Arrays.toString(values));
      }

      // Check if user wants to add/update gender integration
      if (!activityData.get("gender_integrations_description").isEmpty()) {

        // Check if the gender integration records already exist.
        String countQuery =
          "SELECT count(id) FROM gender_integrations WHERE activity_id = " + activityData.get("activity_id");
        ResultSet rs = databaseManager.makeQuery(countQuery, connection);
        if (rs.next()) {
          int genderExistence = rs.getInt(1);
          if (genderExistence == 1) {
            // if gender exists there must be an update statement.
            String genderUpdatePrepared = "UPDATE gender_integrations SET description = ? WHERE activity_id = ?";
            values = new Object[2];
            values[0] = activityData.get("gender_integrations_description");
            values[1] = activityData.get("activity_id");
            int insertedGenderRows = databaseManager.makeChangeSecure(connection, genderUpdatePrepared, values);
            if (insertedGenderRows == 1) {
              // record updated
              LOG.debug("Activity " + activityData.get("activity_id")
                + ": Gender integration description successfully updated.");
            } else {
              // problem.
              problem = true;
              LOG.error("Activity " + activityData.get("activity_id")
                + ": Problem trying to update the gender description.");
              LOG.error("  Query: " + genderUpdatePrepared);
              LOG.error("  Values: " + Arrays.toString(values));
            }
          } else {
            // if gender doesn't exists there must an insert statement.
            String genderInsertPrepared = "INSERT INTO gender_integrations(description, activity_id) VALUES(?, ?)";
            values = new Object[2];
            values[0] = activityData.get("gender_integrations_description");
            values[1] = activityData.get("activity_id");
            int insertedGenderRows = databaseManager.makeChangeSecure(connection, genderInsertPrepared, values);
            if (insertedGenderRows == 1) {
              // record added
              LOG.debug("Activity " + activityData.get("activity_id")
                + ": Gender integration description successfully added.");
            } else {
              // problem.
              problem = true;
              LOG.error("Activity " + activityData.get("activity_id")
                + ": Problem trying to add the gender description.");
              LOG.error("  Query: " + genderInsertPrepared);
              LOG.error("  Values: " + Arrays.toString(values));
            }
          }
        }
        rs.close();
      }

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return !problem;
  }
}