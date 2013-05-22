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
    List<Map<String, String>> activities = new ArrayList<>();
    String query =
      "SELECT a.id, a.title, a.start_date, a.end_date, a.description, m.id as 'milestone_id', m.code as 'milestone_code', "
        + "th.id as 'theme_id', th.code as 'theme_code', al.id as 'leader_id', al.acronym as 'leader_acronym', al.name as 'leader_name'"
        + "FROM activities a, milestones m, outputs ou, objectives ob, themes th, logframes lo, activity_leaders al "
        + "WHERE a.milestone_id = m.id " + "AND m.output_id = ou.id " + "AND ou.objective_id = ob.id "
        + "AND ob.theme_id = th.id " + "AND th.logframe_id = lo.id " + "AND a.activity_leader_id = al.id "
        + "AND lo.year = " + year;
    try (Connection con = databaseManager.getConnection()) {
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
      LOG.error("There was an error getting the activity list for a year. \n{}", query, e);
      return null;
    }
    return activities;
  }

  @Override
  public List<Map<String, String>> getActivities(int year, int leaderTypeCode) {
    List<Map<String, String>> activities = new ArrayList<>();
    String query =
      "SELECT a.id, a.title, a.start_date, a.end_date, a.description, m.id as 'milestone_id', m.code as 'milestone_code', "
        + "th.id as 'theme_id', th.code as 'theme_code', al.id as 'leader_id', al.acronym as 'leader_acronym', al.name as 'leader_name', a.status_description "
        + "FROM activities a, milestones m, outputs ou, objectives ob, themes th, logframes lo, activity_leaders al "
        + "WHERE a.milestone_id = m.id " + "AND m.output_id = ou.id " + "AND ou.objective_id = ob.id "
        + "AND ob.theme_id = th.id " + "AND th.logframe_id = lo.id " + "AND a.activity_leader_id = al.id "
        + "AND lo.year = " + year + " " + "AND al.id = " + leaderTypeCode;
    try (Connection con = databaseManager.getConnection()) {
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
      LOG.error("There was an error getting the activity list for a year and activity leader given. \n{}", query, e);
    }
    return activities;
  }

  @Override
  public List<Map<String, String>> getActivitiesForRSS(int year, int limit) {
    List<Map<String, String>> activities = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT a.id, a.title, a.start_date, a.end_date, a.description, a.date_added, a.is_global, ");
    query.append("m.id as 'milestone_id', m.code as 'milestone_code'");
    query.append("FROM activities a ");
    query.append("INNER JOIN activity_leaders al ON al.id = a.activity_leader_id ");
    query.append("INNER JOIN milestones m ON m.id = a.milestone_id ");
    query.append("INNER JOIN outputs o ON o.id = m.output_id ");
    query.append("INNER JOIN objectives obj ON obj.id = o.objective_id ");
    query.append("INNER JOIN themes t ON t.id = obj.theme_id ");
    query.append("INNER JOIN logframes l on l.id = t.logframe_id " + "WHERE l.year = ");
    query.append(year);
    query.append(" ORDER BY a.date_added DESC");
    if (limit > 0) {
      query.append(" LIMIT ");
      query.append(limit);
    }
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> activity = new HashMap<>();
        activity.put("id", rs.getString("id"));
        activity.put("title", rs.getString("title"));
        activity.put("start_date", rs.getString("start_date"));
        activity.put("end_date", rs.getString("end_date"));
        activity.put("description", rs.getString("description"));
        activity.put("is_global", rs.getString("is_global"));
        activity.put("milestone_id", rs.getString("milestone_id"));
        activity.put("milestone_code", rs.getString("milestone_code"));
        activity.put("date_added", rs.getString("date_added"));
        activities.add(activity);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("There was an error getting the activity list for create an rss feed. \n{}", query.toString(), e);
    }
    return activities;
  }

  @Override
  public Map<String, String> getActivityStatusInfo(int id) {
    Map<String, String> activity = new HashMap<>();
    String query =
      "SELECT a.title, a.start_date, a.end_date, a.description, a.status_description, a.is_global, astatus.id as status_id, "
        + "astatus.name as status_name, a.is_commissioned, a.continuous_activity_id, a.milestone_id, "
        + "m.code as milestone_code, al.id as 'leader_id', al.acronym as 'leader_acronym', al.name as 'leader_name', "
        + "g.id as 'gender_id', g.description as 'gender_description' "
        + "FROM activities a LEFT JOIN milestones m ON a.milestone_id = m.id "
        + "LEFT JOIN activity_status astatus ON a.activity_status_id = astatus.id "
        + "LEFT JOIN activity_leaders al ON a.activity_leader_id = al.id "
        + "LEFT OUTER JOIN gender_integrations g ON g.activity_id = a.id WHERE a.id = " + id;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      if (rs.next()) {
        activity.put("title", rs.getString("title"));
        activity.put("start_date", rs.getString("start_date"));
        activity.put("end_date", rs.getString("end_date"));
        activity.put("description", rs.getString("description"));
        activity.put("is_global", rs.getString("is_global"));
        activity.put("status_description", rs.getString("status_description"));
        activity.put("is_commissioned", rs.getString("is_commissioned"));
        activity.put("continuous_activity_id", rs.getString("continuous_activity_id"));
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
      LOG.error("There was an error getting the status of an activity. \n{}", query, e);
    }

    if (activity.isEmpty()) {
      return null;
    } else {
      return activity;
    }
  }


  @Override
  public List<Map<String, String>> getPlanningActivityList(int year, int leaderId) {
    List<Map<String, String>> activitiesData = new ArrayList<>();
    StringBuilder query =
      new StringBuilder("SELECT a.id, a.title, GROUP_CONCAT(cp.name SEPARATOR '::') AS 'contact_person_names',");
    query.append(" GROUP_CONCAT(cp.email SEPARATOR '::') AS 'contact_person_emails', m.code as 'milestone_code'");
    query.append(" FROM activities a");
    query.append(" LEFT JOIN contact_person cp ON cp.activity_id = a.id");
    query.append(" INNER JOIN milestones m ON m.id = a.milestone_id");
    query.append(" INNER JOIN outputs o ON o.id = m.output_id");
    query.append(" INNER JOIN objectives ob ON ob.id = o.objective_id");
    query.append(" INNER JOIN themes t ON t.id = ob.theme_id");
    query.append(" INNER JOIN logframes l ON l.id = t.logframe_id");
    query.append(" INNER JOIN activity_leaders al ON al.id = a.activity_leader_id");
    query.append(" WHERE l.year = ");
    query.append(year);
    query.append(" AND al.id = ");
    query.append(leaderId);
    query.append(" GROUP BY a.id");
    try (Connection connection = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        Map<String, String> activityData = new HashMap<>();
        activityData.put("id", rs.getString("id"));
        activityData.put("title", rs.getString("title"));
        activityData.put("milestone_code", rs.getString("milestone_code"));
        activityData.put("contact_person_names", rs.getString("contact_person_names"));
        activityData.put("contact_person_emails", rs.getString("contact_person_emails"));
        activitiesData.add(activityData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("There was an error trying to get the basic information for an activity. \n{}", query.toString(), e);
      e.printStackTrace();
    }

    return activitiesData;
  }

  @Override
  public Map<String, String> getSimpleActivity(int id) {
    Map<String, String> activity = new HashMap<>();
    String query =
      "SELECT a.title, a.id, al.id as 'leader_id', 'al.name' as 'leader_name', al.acronym as 'leader_acronym' "
        + "FROM activities a, activity_leaders al " + "WHERE a.activity_leader_id = al.id AND a.id = " + id;
    try (Connection con = databaseManager.getConnection()) {
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
      LOG.error("There was an error getting the basic information for an activity. \n{}", query, e);
    }

    if (activity.isEmpty()) {
      return null;
    } else {
      return activity;
    }
  }


  @Override
  public List<Map<String, String>> getTitles(int year, int leaderTypeCode) {
    List<Map<String, String>> activityTitles = new ArrayList<>();
    StringBuilder query = new StringBuilder("SELECT a.id, a.title");
    query.append(" FROM activities a");
    query.append(" INNER JOIN activity_leaders al ON al.id = a.activity_leader_id");
    query.append(" INNER JOIN milestones m ON m.id = a.milestone_id");
    query.append(" INNER JOIN outputs o ON o.id = m.output_id");
    query.append(" INNER JOIN objectives obj ON obj.id = o.objective_id");
    query.append(" INNER JOIN themes t ON t.id = obj.theme_id");
    query.append(" INNER JOIN logframes l ON l.id = t.logframe_id");
    query.append(" WHERE l.year = ");
    query.append(year);
    query.append(" AND al.id = ");
    query.append(leaderTypeCode);
    try (Connection connection = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        Map<String, String> activityData = new HashMap<>();
        activityData.put("id", rs.getString("id"));
        activityData.put("title", rs.getString("title"));
        activityTitles.add(activityData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("There was an error trying to get the list of activities that belong to the year " + year
        + " and the leader id " + leaderTypeCode + "\n{}", query.toString(), e);
    }
    return activityTitles;
  }


  @Override
  public boolean isValidId(int id) {
    boolean isValid = false;
    String query = "SELECT id FROM activities WHERE id = " + id;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      isValid = rs.next();
      rs.close();
    } catch (SQLException e) {
      LOG.error("There was an error checking if an activity is valid. \n{}", query, e);
    }
    return isValid;
  }


  @Override
  public int saveSimpleActivity(Map<String, Object> activityData) {
    int activityID = -1;
    try (Connection con = databaseManager.getConnection()) {
      String addQuery =
        "INSERT INTO activities (title, start_date, end_date, description, activity_leader_id, continuous_activity_id, is_commissioned, milestone_id) VALUES (?,?,?,?,?,?,?,?)";
      Object[] values = new Object[8];
      values[0] = activityData.get("title");
      values[1] = activityData.get("start_date");
      values[2] = activityData.get("end_date");
      values[3] = activityData.get("description");
      values[4] = activityData.get("activity_leader_id");
      values[5] = activityData.get("continuous_activity_id");
      values[6] = activityData.get("is_commissioned");
      values[7] = activityData.get("milestone_id");
      int activityAdded = databaseManager.makeChangeSecure(con, addQuery, values);
      if (activityAdded > 0) {
        // Get the generated id of the added record.
        ResultSet rs = databaseManager.makeQuery("SELECT LAST_INSERT_ID()", con);
        if (rs.next()) {
          activityID = rs.getInt(1);
        }
        rs.close();
      }
    } catch (SQLException e) {
      LOG.error("There was an error trying to add a new activity titled: \"{}\"", activityData.get("title"), e);
    }
    return activityID;
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
      LOG.error("There was an error saving an activity status. \n{}", e);
    }
    return !problem;
  }


  @Override
  public boolean updateGlobalAttribute(int activityID, boolean isGlobal) {
    boolean saved = false;
    String query = "UPDATE activities SET is_global = ? WHERE id = ?";
    Object[] values = new Object[2];
    values[0] = isGlobal;
    values[1] = activityID;
    try (Connection con = databaseManager.getConnection()) {
      int rows = databaseManager.makeChangeSecure(con, query, values);
      if (rows < 0) {
        LOG.warn("There was a problem updating the attribute is global of activity {}.", activityID);
      } else {
        saved = true;
      }
    } catch (SQLException e) {
      LOG.error("There was an error updating the attribute is global of activity {}.", activityID, e);
    }
    return saved;
  }

  @Override
  public boolean updateMainInformation(Map<String, String> activityData) {
    boolean added = false;
    Object[] values = new Object[6];
    values[0] = activityData.get("title");
    values[1] = activityData.get("description");
    values[2] = activityData.get("start_date");
    values[3] = activityData.get("end_date");
    values[4] = activityData.get("milestone_id");
    values[5] = activityData.get("id");

    String query =
      "UPDATE activities SET title = ?, description = ?, start_date = ?, end_date = ?, milestone_id = ? WHERE id = ?";

    try (Connection con = databaseManager.getConnection()) {
      int updatedResult = databaseManager.makeChangeSecure(con, query, values);
      if (updatedResult < 0) {
        LOG.warn("There was a problem updating the main information for an activity. See query below. \n{}", query);
        LOG.warn("  Query: " + query);
        LOG.warn("  Values: " + Arrays.toString(values));
      } else {
        added = true;
        // If the activity was updated successfully save the gender integration description
        // Delete the record in the database
        query = "DELETE FROM gender_integrations WHERE `activity_id` = ?";
        int rowsDeleted = databaseManager.makeChangeSecure(con, query, new Object[] {activityData.get("id")});
        if (rowsDeleted < -1) {
          LOG.warn("There was a problem deleting the gender integration description for activity {}",
            activityData.get("id"));
        }
        if (activityData.get("genderDescription") != null) {
          values = new Object[2];
          values[0] = activityData.get("genderDescription");
          values[1] = activityData.get("id");
          query =
            "INSERT INTO gender_integrations (description, activity_id) VALUES (?, ?) "
              + "ON DUPLICATE KEY UPDATE description = VALUES(description)";
          int rowsAffected = databaseManager.makeChangeSecure(con, query, values);
          if (rowsAffected < 0) {
            LOG.warn("There was a problem saving the gender integration description.");
            LOG.warn("Query: {}", query);
            LOG.warn("Values: {}", values);
          }
        }
      }
    } catch (SQLException e) {
      LOG.error("There was an error updating the main information for an activity. See query below. \n{}", query, e);
      LOG.error("  Query: " + query);
      LOG.error("  Values: " + Arrays.toString(values));
    }
    return added;
  }
}