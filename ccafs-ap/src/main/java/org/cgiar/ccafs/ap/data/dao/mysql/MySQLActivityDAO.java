/*
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
 */

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
    LOG.debug(">> getActivities(year={} )", year);
    List<Map<String, String>> activities = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT a.id, a.activity_id, a.title, a.start_date, a.end_date, a.description, ");
    query.append("(av.activity_id IS NOT NULL) as 'is_validated', m.id as 'milestone_id', ");
    query.append("m.code as 'milestone_code', th.id as 'theme_id', th.code as 'theme_code', ");
    query.append("al.id as 'leader_id', al.acronym as 'leader_acronym', al.name as 'leader_name' ");
    query.append("FROM activities a ");
    query.append("LEFT JOIN activity_validations av ON a.id = av.activity_id ");
    query.append("LEFT JOIN milestones m ON a.milestone_id = m.id ");
    query.append("LEFT JOIN outputs op ON m.output_id = op.id ");
    query.append("LEFT JOIN objectives ob ON op.objective_id = ob.id ");
    query.append("LEFT JOIN themes th ON ob.theme_id = th.id ");
    query.append("LEFT JOIN logframes l ON th.logframe_id = l.id ");
    query.append("INNER JOIN activity_leaders al ON a.activity_leader_id = al.id ");
    query.append("WHERE a.year = ");
    query.append(year);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> activity = new HashMap<>();
        activity.put("id", rs.getString("id"));
        activity.put("activity_id", rs.getString("activity_id"));
        activity.put("title", rs.getString("title"));
        activity.put("start_date", rs.getString("start_date"));
        activity.put("end_date", rs.getString("end_date"));
        activity.put("description", rs.getString("description"));
        activity.put("is_validated", rs.getString("is_validated"));
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
      LOG.error("--  getActivities() > There was an error getting the activity list for year {}.", year, e);
      return null;
    }

    LOG.debug("<< getActivities():activities.size={}", activities.size());
    return activities;
  }

  @Override
  public List<Map<String, String>> getActivities(int year, int leaderTypeCode) {
    LOG.debug(">> getActivities(year={}, leaderTypeCode={})", year, leaderTypeCode);

    List<Map<String, String>> activities = new ArrayList<>();
    StringBuilder query = new StringBuilder();

    query.append("SELECT a.id, a.activity_id, a.title, a.start_date, a.end_date, a.description, ");
    query.append("(av.activity_id IS NOT NULL) as 'is_validated', ");
    query.append("m.id as 'milestone_id', m.code as 'milestone_code', ast.id as 'status_id', ");
    query.append("ast.name as 'status_name', th.id as 'theme_id', th.code as 'theme_code', ");
    query.append("al.id as 'leader_id', al.acronym as 'leader_acronym', al.name as 'leader_name', ");
    query.append("a.status_description FROM activities a ");
    query.append("LEFT JOIN activity_validations av ON a.id = av.activity_id ");
    query.append("LEFT JOIN milestones m ON a.milestone_id = m.id ");
    query.append("LEFT JOIN outputs ou ON m.output_id = ou.id ");
    query.append("LEFT JOIN objectives ob ON ou.objective_id = ob.id ");
    query.append("LEFT JOIN themes th ON ob.theme_id = th.id ");
    query.append("LEFT JOIN logframes lo ON th.logframe_id = lo.id ");
    query.append("INNER JOIN activity_leaders al ON a.activity_leader_id = al.id ");
    query.append("LEFT JOIN activity_status ast ON a.activity_status_id = ast.id ");
    query.append("WHERE 1 ");


    if (year != 0) {
      query.append(" AND a.year = ");
      query.append(year);
    }

    if (leaderTypeCode != 0) {
      query.append(" AND al.id = ");
      query.append(leaderTypeCode);
    }

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> activity = new HashMap<>();
        activity.put("id", rs.getString("id"));
        activity.put("activity_id", rs.getString("activity_id"));
        activity.put("title", rs.getString("title"));
        activity.put("start_date", rs.getString("start_date"));
        activity.put("end_date", rs.getString("end_date"));
        activity.put("description", rs.getString("description"));
        activity.put("is_validated", rs.getString("is_validated"));
        activity.put("status_id", rs.getString("status_id"));
        activity.put("status_name", rs.getString("status_name"));
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
      Object[] paramArray = {year, leaderTypeCode, e};
      LOG.error("-- getActivities() >There was an error getting the activity list for year {} and activity leader {}.",
        paramArray);
    }

    LOG.debug("<< getActivities():activities.size={}", activities.size());
    return activities;
  }

  @Override
  public List<Map<String, String>> getActivitiesForRSS(int year, int limit) {
    LOG.debug(">> getActivitiesForRSS(year={}, limit={})", year, limit);

    List<Map<String, String>> activities = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query
      .append("SELECT a.id, activity_id, a.title, a.start_date, a.end_date, a.description, a.date_added, a.is_global, ");
    query.append("is_commissioned, m.id as 'milestone_id', m.code as 'milestone_code'");
    query.append("FROM activities a ");
    query.append("INNER JOIN activity_leaders al ON al.id = a.activity_leader_id ");
    query.append("INNER JOIN milestones m ON m.id = a.milestone_id ");
    query.append("WHERE a.year = ");
    query.append(year);
    query.append(" ORDER BY a.id");
    if (limit > 0) {
      query.append(" LIMIT ");
      query.append(limit);
    }
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> activity = new HashMap<>();
        activity.put("id", rs.getString("id"));
        activity.put("activity_id", rs.getString("activity_id"));
        activity.put("title", rs.getString("title"));
        activity.put("start_date", rs.getString("start_date"));
        activity.put("end_date", rs.getString("end_date"));
        activity.put("description", rs.getString("description"));
        activity.put("is_global", rs.getString("is_global"));
        activity.put("is_commissioned", rs.getString("is_commissioned"));
        activity.put("milestone_id", rs.getString("milestone_id"));
        activity.put("milestone_code", rs.getString("milestone_code"));
        activity.put("date_added", rs.getString("date_added"));
        activities.add(activity);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getActivitiesForRSS() >There was an error getting the activity list for year {} with limit {}.",
        year, limit);
    }

    LOG.debug("<< getActivitiesForRSS():activities.size={}", activities.size());
    return activities;
  }

  @Override
  public List<Map<String, String>> getActivitiesToContinue(int year) {
    LOG.debug(">> getActivitiesToContinue(year={}) ", year);
    List<Map<String, String>> activitiesToContinue = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT a.id, a.activity_id, a.title FROM activities a ");
    query.append("LEFT JOIN activities a2 ON a.id =  a2.continuous_activity_id ");
    query.append("WHERE a2.continuous_activity_id IS NULL ");
    query.append("AND a.year = ");
    query.append(year);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> activity = new HashMap<>();
        activity.put("id", rs.getString("id"));
        activity.put("activity_id", rs.getString("activity_id"));
        activity.put("title", rs.getString("title"));

        activitiesToContinue.add(activity);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getActivitiesToContinue() > There was an exception trying to get the activity list.", e);
    }

    LOG.debug("<< getActivitiesToContinue():activitiesToContinue.size={} ", activitiesToContinue.size());
    return activitiesToContinue;
  }

  @Override
  public List<Map<String, String>> getActivitiesToContinue(int year, int leaderID) {
    LOG.debug(">> getActivitiesToContinue(year={}, leader={}) ", year, leaderID);
    List<Map<String, String>> activitiesToContinue = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT a.id, a.activity_id, a.title FROM activities a ");
    query.append("LEFT JOIN activities a2 ON a.id =  a2.continuous_activity_id ");
    query.append("WHERE a.year = ");
    query.append(year);
    query.append(" AND a.activity_leader_id = ");
    query.append(leaderID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> activity = new HashMap<>();
        activity.put("id", rs.getString("id"));
        activity.put("activity_id", rs.getString("activity_id"));
        activity.put("title", rs.getString("title"));

        activitiesToContinue.add(activity);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getActivitiesToContinue() > There was an exception trying to get the activity list.", e);
    }

    LOG.debug("<< getActivitiesToContinue():activitiesToContinue.size={} ", activitiesToContinue.size());
    return activitiesToContinue;
  }

  @Override
  public List<Map<String, String>> getActivityListByYear(int year) {
    LOG.debug(">> getActivityListByYear(year={})", year);
    List<Map<String, String>> activitiesData = new ArrayList<>();
    StringBuilder query =
      new StringBuilder("SELECT a.id, a.title, a.activity_id, (av.activity_id IS NOT NULL) as 'is_validated', ");
    query.append(" GROUP_CONCAT(cp.name SEPARATOR '::') AS 'contact_person_names',");
    query.append(" GROUP_CONCAT(cp.email SEPARATOR '::') AS 'contact_person_emails', m.code as 'milestone_code',");
    query.append(" al.id as 'leader_id', al.name as 'leader_name', al.acronym as 'leader_acronym'");
    query.append(" FROM activities a");
    query.append(" LEFT JOIN contact_person cp ON cp.activity_id = a.id");
    query.append(" LEFT JOIN activity_validations av ON a.id = av.activity_id");
    query.append(" LEFT JOIN milestones m ON m.id = a.milestone_id");
    query.append(" INNER JOIN activity_leaders al ON al.id = a.activity_leader_id");
    query.append(" WHERE a.year = ");
    query.append(year);
    query.append(" GROUP BY a.id");
    try (Connection connection = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        Map<String, String> activityData = new HashMap<>();
        activityData.put("id", rs.getString("id"));
        activityData.put("activity_id", rs.getString("activity_id"));
        activityData.put("title", rs.getString("title"));
        activityData.put("is_validated", rs.getString("is_validated"));
        activityData.put("milestone_code", rs.getString("milestone_code"));
        activityData.put("contact_person_names", rs.getString("contact_person_names"));
        activityData.put("contact_person_emails", rs.getString("contact_person_emails"));
        activityData.put("leader_id", rs.getString("leader_id"));
        activityData.put("leader_name", rs.getString("leader_name"));
        activityData.put("leader_acronym", rs.getString("leader_acronym"));
        activitiesData.add(activityData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getActivityListByYear() > There was an error getting the list of planning activities for year {}.",
        year);
      e.printStackTrace();
    }

    LOG.debug("<< getActivityListByYear():activitiesData.size={}", activitiesData.size());
    return activitiesData;
  }


  @Override
  public Map<String, String> getActivityStatusInfo(int id) {
    LOG.debug(">> getActivityStatusInfo(id={})", id);
    Map<String, String> activity = new HashMap<>();
    String query =
      "SELECT a.activity_id, a.title, a.start_date, a.end_date, a.description, a.status_description, a.is_global, astatus.id as status_id, "
        + "a.has_partners, astatus.name as status_name, a.is_commissioned, a.continuous_activity_id, a.milestone_id, "
        + "m.code as milestone_code, al.id as 'leader_id', al.acronym as 'leader_acronym', al.name as 'leader_name', "
        + "g.id as 'gender_id', g.description as 'gender_description', "
        + "(SELECT a2.activity_id FROM activities a2 WHERE a2.id = a.continuous_activity_id) as 'countinuos_activity_activityID' "
        + "FROM activities a LEFT JOIN milestones m ON a.milestone_id = m.id "
        + "LEFT JOIN activity_status astatus ON a.activity_status_id = astatus.id "
        + "LEFT JOIN activity_leaders al ON a.activity_leader_id = al.id "
        + "LEFT OUTER JOIN gender_integrations g ON g.activity_id = a.id WHERE a.id = " + id;

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      if (rs.next()) {
        activity.put("activity_id", rs.getString("activity_id"));
        activity.put("title", rs.getString("title"));
        activity.put("start_date", rs.getString("start_date"));
        activity.put("end_date", rs.getString("end_date"));
        activity.put("description", rs.getString("description"));
        activity.put("is_global", rs.getString("is_global"));
        activity.put("status_description", rs.getString("status_description"));
        activity.put("is_commissioned", rs.getString("is_commissioned"));
        activity.put("has_partners", rs.getString("has_partners"));
        activity.put("continuous_activity_id", rs.getString("continuous_activity_id"));
        activity.put("countinuos_activity_activityID", rs.getString("countinuos_activity_activityID"));
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
      LOG.warn("There was no information fetched from the database for activity {}", id);
      LOG.debug("<< getActivityStatusInfo():null");
      return null;
    } else {
      LOG.debug("<< getActivityStatusInfo():{}", activity);
      return activity;
    }
  }


  @Override
  public int getActivityYear(int activityID) {
    LOG.debug(">> getActivityYear(activityID={})", activityID);
    int year = 0;
    String query = "SELECT a.year FROM activities a" + " WHERE a.id = " + activityID;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      if (rs.next()) {
        year = Integer.parseInt(rs.getString("year"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("--getActivityYear() > There was an error getting the year of the activity {}.", activityID, e);
    }

    LOG.debug("<< getActivityYear():{}", year);
    return year;
  }


  @Override
  public List<Map<String, String>> getPlanningActivityList(int year, int leaderId) {
    LOG.debug(">> getPlanningActivityList(year={}, leaderId={})", year, leaderId);
    List<Map<String, String>> activitiesData = new ArrayList<>();
    StringBuilder query =
      new StringBuilder("SELECT a.id, a.activity_id, a.title, (av.activity_id IS NOT NULL) as 'is_validated', ");
    query.append(" GROUP_CONCAT(cp.name SEPARATOR '::') AS 'contact_person_names',");
    query.append(" GROUP_CONCAT(cp.email SEPARATOR '::') AS 'contact_person_emails', m.code as 'milestone_code',");
    query.append(" al.id as 'leader_id', al.name as 'leader_name', al.acronym as 'leader_acronym'");
    query.append(" FROM activities a");
    query.append(" LEFT JOIN contact_person cp ON cp.activity_id = a.id");
    query.append(" LEFT JOIN activity_validations av ON a.id = av.activity_id");
    query.append(" LEFT JOIN milestones m ON m.id = a.milestone_id");
    query.append(" INNER JOIN activity_leaders al ON al.id = a.activity_leader_id");
    query.append(" WHERE a.year = ");
    query.append(year);
    query.append(" AND al.id = ");
    query.append(leaderId);
    query.append(" GROUP BY a.id");
    try (Connection connection = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        Map<String, String> activityData = new HashMap<>();
        activityData.put("id", rs.getString("id"));
        activityData.put("activity_id", rs.getString("activity_id"));
        activityData.put("title", rs.getString("title"));
        activityData.put("is_validated", rs.getString("is_validated"));
        activityData.put("milestone_code", rs.getString("milestone_code"));
        activityData.put("contact_person_names", rs.getString("contact_person_names"));
        activityData.put("contact_person_emails", rs.getString("contact_person_emails"));
        activityData.put("leader_id", rs.getString("leader_id"));
        activityData.put("leader_name", rs.getString("leader_name"));
        activityData.put("leader_acronym", rs.getString("leader_acronym"));
        activitiesData.add(activityData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG
        .error(
          "-- getPlanningActivityList() > There was an error getting the list of planning activities for year {} and leader {}.",
          year, leaderId);
      e.printStackTrace();
    }

    LOG.debug("<< getPlanningActivityList():activitiesData.size={}", activitiesData.size());
    return activitiesData;
  }


  @Override
  public List<Map<String, String>> getPlanningActivityListForRPL(int year, int leaderId, int regionId) {
    LOG.debug(">> getPlanningActivityListForRPL(year={}, leaderId={})", year, leaderId);
    List<Map<String, String>> activitiesData = new ArrayList<>();
    StringBuilder query = new StringBuilder("SELECT * FROM ( ");
    query.append("SELECT a.id, a.activity_id, a.title, (av.activity_id IS NOT NULL) as 'is_validated', ");
    query.append("GROUP_CONCAT(cp.name SEPARATOR '::') AS 'contact_person_names', ");
    query.append("GROUP_CONCAT(cp.email SEPARATOR '::') AS 'contact_person_emails', m.code as 'milestone_code', ");
    query.append("al.id as 'leader_id', al.name as 'leader_name', al.acronym as 'leader_acronym' ");
    query.append("FROM activities a ");
    query.append("LEFT JOIN contact_person cp ON cp.activity_id = a.id ");
    query.append("LEFT JOIN activity_validations av ON a.id = av.activity_id ");
    query.append("LEFT JOIN milestones m ON m.id = a.milestone_id ");
    query.append("INNER JOIN activity_leaders al ON al.id = a.activity_leader_id ");
    query.append("LEFT JOIN country_locations cl ON a.id = cl.activity_id ");
    query.append("LEFT JOIN countries co ON cl.country_iso2 = co.iso2 ");
    query.append("LEFT JOIN region_locations rl ON a.id = rl.activity_id ");
    query.append("LEFT JOIN regions r ON rl.region_id = r.id OR co.region_id = r.id ");
    query.append(" WHERE a.year = ");
    query.append(year);
    query.append(" AND r.id = ");
    query.append(regionId);
    query.append(" GROUP BY a.id ");
    query.append("UNION  ");
    query.append("SELECT a.id, a.activity_id, a.title, (av.activity_id IS NOT NULL) as 'is_validated', ");
    query.append("GROUP_CONCAT(cp.name SEPARATOR '::') AS 'contact_person_names', ");
    query.append("GROUP_CONCAT(cp.email SEPARATOR '::') AS 'contact_person_emails', m.code as 'milestone_code', ");
    query.append("al.id as 'leader_id', al.name as 'leader_name', al.acronym as 'leader_acronym' ");
    query.append("FROM activities a ");
    query.append("LEFT JOIN contact_person cp ON cp.activity_id = a.id ");
    query.append("LEFT JOIN activity_validations av ON a.id = av.activity_id ");
    query.append("LEFT JOIN milestones m ON m.id = a.milestone_id ");
    query.append("INNER JOIN activity_leaders al ON al.id = a.activity_leader_id ");
    query.append("WHERE a.year = ");
    query.append(year);
    query.append(" AND al.id = ");
    query.append(leaderId);
    query.append(" GROUP BY a.id ");
    query.append(") total GROUP BY id ORDER BY 'leader_id'");
    try (Connection connection = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        Map<String, String> activityData = new HashMap<>();
        activityData.put("id", rs.getString("id"));
        activityData.put("activity_id", rs.getString("activity_id"));
        activityData.put("title", rs.getString("title"));
        activityData.put("is_validated", rs.getString("is_validated"));
        activityData.put("milestone_code", rs.getString("milestone_code"));
        activityData.put("contact_person_names", rs.getString("contact_person_names"));
        activityData.put("contact_person_emails", rs.getString("contact_person_emails"));
        activityData.put("leader_id", rs.getString("leader_id"));
        activityData.put("leader_name", rs.getString("leader_name"));
        activityData.put("leader_acronym", rs.getString("leader_acronym"));
        activitiesData.add(activityData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG
        .error(
          "-- getPlanningActivityListForRPL() > There was an error getting the list of planning activities for year {} and leader {}.",
          year, leaderId);
      e.printStackTrace();
    }

    LOG.debug("<< getPlanningActivityListForRPL():activitiesData.size={}", activitiesData.size());
    return activitiesData;
  }


  @Override
  public List<Map<String, String>> getPlanningActivityListForTL(int year, int leaderId, int themeCode) {
    LOG.debug(">> getPlanningActivityListForTL(year={}, themeCode={})", year, themeCode);
    List<Map<String, String>> activitiesData = new ArrayList<>();
    // We going to make a UNION of both queries: one to get the activities of leader and other to
    // get the activities under the theme given.

    StringBuilder query = new StringBuilder("SELECT * FROM ( ");
    query.append("SELECT a.id, a.activity_id, a.title, (av.activity_id IS NOT NULL) as 'is_validated', ");
    query.append("GROUP_CONCAT(cp.name SEPARATOR '::') AS 'contact_person_names', ");
    query.append("GROUP_CONCAT(cp.email SEPARATOR '::') AS 'contact_person_emails', m.code as 'milestone_code', ");
    query.append("al.id as 'leader_id', al.name as 'leader_name', al.acronym as 'leader_acronym' ");
    query.append("FROM activities a ");
    query.append("LEFT JOIN contact_person cp ON cp.activity_id = a.id ");
    query.append("LEFT JOIN activity_validations av ON a.id = av.activity_id ");
    query.append("LEFT JOIN milestones m ON m.id = a.milestone_id ");
    query.append("LEFT JOIN outputs op ON m.output_id = op.id ");
    query.append("LEFT JOIN objectives ob ON op.objective_id = ob.id ");
    query.append("LEFT JOIN themes t ON ob.theme_id = t.id ");
    query.append("INNER JOIN activity_leaders al ON al.id = a.activity_leader_id ");
    query.append("WHERE a.year = ");
    query.append(year);
    query.append(" AND t.code = ");
    query.append(themeCode);
    query.append(" GROUP BY a.id ");
    query.append("UNION  ");
    query.append("SELECT a.id, a.activity_id, a.title, (av.activity_id IS NOT NULL) as 'is_validated', ");
    query.append("GROUP_CONCAT(cp.name SEPARATOR '::') AS 'contact_person_names', ");
    query.append("GROUP_CONCAT(cp.email SEPARATOR '::') AS 'contact_person_emails', m.code as 'milestone_code', ");
    query.append("al.id as 'leader_id', al.name as 'leader_name', al.acronym as 'leader_acronym' ");
    query.append("FROM activities a ");
    query.append("LEFT JOIN contact_person cp ON cp.activity_id = a.id ");
    query.append("LEFT JOIN activity_validations av ON a.id = av.activity_id ");
    query.append("LEFT JOIN milestones m ON m.id = a.milestone_id ");
    query.append("LEFT JOIN outputs op ON m.output_id = op.id ");
    query.append("LEFT JOIN objectives ob ON op.objective_id = ob.id ");
    query.append("LEFT JOIN themes t ON ob.theme_id = t.id ");
    query.append("INNER JOIN activity_leaders al ON al.id = a.activity_leader_id ");
    query.append("WHERE a.year = ");
    query.append(year);
    query.append(" AND al.id = ");
    query.append(leaderId);
    query.append(" GROUP BY a.id ");
    query.append(") total GROUP BY id ORDER BY 'leader_id'");

    try (Connection connection = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        Map<String, String> activityData = new HashMap<>();
        activityData.put("id", rs.getString("id"));
        activityData.put("activity_id", rs.getString("activity_id"));
        activityData.put("title", rs.getString("title"));
        activityData.put("is_validated", rs.getString("is_validated"));
        activityData.put("milestone_code", rs.getString("milestone_code"));
        activityData.put("contact_person_names", rs.getString("contact_person_names"));
        activityData.put("contact_person_emails", rs.getString("contact_person_emails"));
        activityData.put("leader_id", rs.getString("leader_id"));
        activityData.put("leader_name", rs.getString("leader_name"));
        activityData.put("leader_acronym", rs.getString("leader_acronym"));
        activitiesData.add(activityData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG
        .error(
          "-- getPlanningActivityListForTL() > There was an error getting the list of planning activities for year {} and leader {}.",
          year, themeCode);
      e.printStackTrace();
    }

    LOG.debug("<< getPlanningActivityListForTL():activitiesData.size={}", activitiesData.size());
    return activitiesData;
  }

  @Override
  public Map<String, String> getSimpleActivity(int id) {
    LOG.debug(">> getSimpleActivity(id={})", id);

    Map<String, String> activity = new HashMap<>();
    String query =
      "SELECT a.title, a.id, a.activity_id, a.year, al.id as 'leader_id', 'al.name' as 'leader_name', al.acronym as 'leader_acronym' "
        + "FROM activities a, activity_leaders al " + "WHERE a.activity_leader_id = al.id AND a.id = " + id;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      if (rs.next()) {
        activity.put("title", rs.getString("title"));
        activity.put("id", rs.getString("id"));
        activity.put("activity_id", rs.getString("activity_id"));
        activity.put("year", rs.getString("year"));
        activity.put("leader_id", rs.getString("leader_id"));
        activity.put("leader_name", rs.getString("leader_name"));
        activity.put("leader_acronym", rs.getString("leader_acronym"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getSimpleActivity() > There was an error getting the basic information for activity {}.", id, e);
    }

    if (activity.isEmpty()) {
      LOG.debug("<< getSimpleActivity():null");
      return null;
    } else {
      LOG.debug("<< getSimpleActivity():{}", activity);
      return activity;
    }
  }

  @Override
  public List<Map<String, String>> getTitles(int year) {
    LOG.debug(">> getTitles(year={})", year);
    List<Map<String, String>> activityTitles = new ArrayList<>();
    StringBuilder query = new StringBuilder("SELECT a.id, a.activity_id, a.title");
    query.append(" FROM activities a");
    query.append(" WHERE a.year = ");
    query.append(year);
    try (Connection connection = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        Map<String, String> activityData = new HashMap<>();
        activityData.put("id", rs.getString("id"));
        activityData.put("activity_id", rs.getString("activity_id"));
        activityData.put("title", rs.getString("title"));
        activityTitles.add(activityData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error(
        "-- getTitles() > There was an error trying to get the list of activity titles that belong to the year {}.",
        year, e);
    }

    LOG.debug("<< getTitles():activityTitles.size={}", activityTitles.size());
    return activityTitles;
  }


  @Override
  public List<Map<String, String>> getTitles(int year, int activityLeaderId) {
    LOG.debug(">> getTitles(year={}, activityLeaderId={})", year, activityLeaderId);
    List<Map<String, String>> activityTitles = new ArrayList<>();
    StringBuilder query = new StringBuilder("SELECT a.id, a.activity_id, a.title");
    query.append(" FROM activities a");
    query.append(" WHERE a.year = ");
    query.append(year);
    query.append(" AND a.activity_leader_id = ");
    query.append(activityLeaderId);
    try (Connection connection = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        Map<String, String> activityData = new HashMap<>();
        activityData.put("id", rs.getString("id"));
        activityData.put("activity_id", rs.getString("activity_id"));
        activityData.put("title", rs.getString("title"));
        activityTitles.add(activityData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error(
        "-- getTitles() > There was an error trying to get the list of activity titles that belong to the year {}.",
        year, e);
    }

    LOG.debug("<< getTitles():activityTitles.size={}", activityTitles.size());
    return activityTitles;
  }


  @Override
  public boolean hasPartners(int activityID) {
    LOG.debug(">> hasPartners(activityID={})", activityID);
    boolean hasPartners = false;
    String query = "SELECT has_partners FROM activities a WHERE a.id = " + activityID;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      if (rs.next()) {
        hasPartners = rs.getBoolean("has_partners");
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- hasPartners() > There was an error checking if the activity {} has partners.", activityID, e);
    }

    LOG.debug("<< hasPartners():{}", hasPartners);
    return hasPartners;
  }


  @Override
  public boolean isValidatedActivity(int activityID) {
    LOG.debug(">> isValidatedActivity(activityID={})", activityID);
    boolean isValidated = false;
    String query = "SELECT * FROM activity_validations av INNER JOIN activities WHERE av.activity_id = " + activityID;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      if (rs.next()) {
        isValidated = true;
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- isValidatedActivity() > There was an error checking if an activity was validated.", e);
    }

    LOG.debug("<< isValidatedActivity():{}", isValidated);
    return isValidated;
  }


  @Override
  public boolean isValidId(int id) {
    LOG.debug(">> isValidId(id={})", id);
    boolean isValid = false;
    String query = "SELECT id FROM activities WHERE id = " + id;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      isValid = rs.next();
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- isValidId() > There was an error checking if an activity is valid. \n{}", query, e);
    }

    LOG.debug("<< isValidId():{}", isValid);
    return isValid;
  }


  @Override
  public boolean saveHasPartners(int activityID, boolean hasPartners) {
    LOG.debug(">> saveHasPartners(activityID = {}, hasPartners = {})", activityID, hasPartners);
    boolean saved = false;
    String query = "UPDATE activities SET has_partners = ? WHERE id = ?";
    Object[] values = new Object[2];
    values[0] = hasPartners;
    values[1] = activityID;
    try (Connection con = databaseManager.getConnection()) {
      int rows = databaseManager.makeChangeSecure(con, query, values);
      if (rows < 0) {
        LOG.warn("-- saveHasPartners() > There was a problem updating the attribute has_partners of activity {}.",
          activityID);
      } else {
        saved = true;
      }
    } catch (SQLException e) {
      LOG.error("-- saveHasPartners() > There was an error updating the attribute has_partners of activity {}.",
        activityID, e);
    }

    LOG.debug("<< saveHasPartners():{}", saved);
    return saved;
  }


  @Override
  public int saveSimpleActivity(Map<String, Object> activityData) {
    LOG.debug(">> saveSimpleActivity(activityData={})", activityData);
    int activityID = -1;
    try (Connection con = databaseManager.getConnection()) {
      String addQuery =
        "INSERT INTO activities (title, start_date, end_date, year, description, has_partners, activity_leader_id, continuous_activity_id, is_commissioned, milestone_id) VALUES (?,?,?,?,?,?,?,?,?, ?)";
      Object[] values = new Object[10];
      values[0] = activityData.get("title");
      values[1] = activityData.get("start_date");
      values[2] = activityData.get("end_date");
      values[3] = activityData.get("year");
      values[4] = activityData.get("description");
      values[5] = activityData.get("has_partners");
      values[6] = activityData.get("activity_leader_id");
      values[7] = activityData.get("continuous_activity_id");
      values[8] = activityData.get("is_commissioned");
      values[9] = activityData.get("milestone_id");
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
      LOG.error("-- saveSimpleActivity() > There was an error trying to add a new activity titled: '{}'",
        activityData.get("title"), e);
    }
    LOG.debug("<< saveSimpleActivity():{}", activityID);
    return activityID;
  }


  @Override
  public boolean saveStatus(Map<String, String> activityData) {
    LOG.debug(">> saveStatus(activityData={})", activityData);
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
        LOG.warn("-- saveStatus() > There was an error trying to update te status of an activity:");
        LOG.warn("  Query: {}", activityQueryPrepared);
        LOG.warn("  Values: {}", Arrays.toString(values));
      }

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
            LOG.debug("-- saveStatus() > Activity {}: Gender integration description successfully updated.",
              activityData.get("activity_id"));
          } else {
            // problem.
            problem = true;
            LOG.warn("-- saveStatus() > Activity {}: Problem trying to update the gender description.",
              activityData.get("activity_id"));
            LOG.error("  Query: {}", genderUpdatePrepared);
            LOG.error("  Values: {}", Arrays.toString(values));
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
            LOG.debug("-- saveStatus() > Activity {}: Gender integration description successfully added.",
              activityData.get("activity_id"));
          } else {
            // problem.
            problem = true;
            LOG.warn("-- saveStatus() > Activity {}: Problem trying to add the gender description.",
              activityData.get("activity_id"));
            LOG.warn("  Query: {}", genderInsertPrepared);
            LOG.warn("  Values: {}", Arrays.toString(values));
          }
        }
      }
      rs.close();


    } catch (SQLException e) {
      LOG.error("-- saveStatus() > There was an error saving an activity status. \n{}", e);
    }

    LOG.debug("<< saveStatus():{}", !problem);
    return !problem;
  }


  @Override
  public boolean updateGlobalAttribute(int activityID, boolean isGlobal) {
    LOG.debug(">> updateGlobalAttribute(activityID = {}, isGlobal = {})", activityID, isGlobal);
    boolean saved = false;
    String query = "UPDATE activities SET is_global = ? WHERE id = ?";
    Object[] values = new Object[2];
    values[0] = isGlobal;
    values[1] = activityID;
    try (Connection con = databaseManager.getConnection()) {
      int rows = databaseManager.makeChangeSecure(con, query, values);
      if (rows < 0) {
        LOG.warn("-- updateGlobalAttribute() > There was a problem updating the attribute is global of activity {}.",
          activityID);
      } else {
        saved = true;
      }
    } catch (SQLException e) {
      LOG.error("-- updateGlobalAttribute() > There was an error updating the attribute is global of activity {}.",
        activityID, e);
    }

    LOG.debug("<< updateGlobalAttribute():{}", saved);
    return saved;
  }


  @Override
  public boolean updateMainInformation(Map<String, String> activityData) {
    LOG.debug(">> updateMainInformation(activityData={})", activityData);
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
        LOG
          .warn(
            "-- updateMainInformation() > There was a problem updating the main information for an activity. See query below. \n{}",
            query);
        LOG.warn("  Query: {}", query);
        LOG.warn("  Values: {}", Arrays.toString(values));
      } else {
        added = true;
        // If the activity was updated successfully save the gender integration description
        // Delete the record in the database
        query = "DELETE FROM gender_integrations WHERE `activity_id` = ?";
        int rowsDeleted = databaseManager.makeChangeSecure(con, query, new Object[] {activityData.get("id")});
        if (rowsDeleted < -1) {
          LOG
            .warn(
              "-- updateMainInformation() > There was a problem deleting the gender integration description for activity {}",
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
            LOG.warn("-- updateMainInformation() > There was a problem saving the gender integration description.");
            LOG.warn("Query: {}", query);
            LOG.warn("Values: {}", values);
          }
        }
      }
    } catch (SQLException e) {
      LOG.error("-- updateMainInformation() > There was an error updating the main information for an activity.", e);
      LOG.error("  Query: {}", query);
      LOG.error("  Values: {}", Arrays.toString(values));
    }

    LOG.debug("<< updateMainInformation():{}", added);
    return added;
  }


  @Override
  public boolean validateActivity(int activityID, boolean isValidated) {
    LOG.debug(">> validateActivity(activityID = {}, isValidated={} )", activityID);
    boolean saved = false;
    String query;

    if (isValidated) {
      query = "INSERT INTO activity_validations (`activity_id`) VALUES (?)";
    } else {
      query = "DELETE FROM activity_validations WHERE `activity_id` = ?; ";
    }

    Object[] values = new Object[1];
    values[0] = activityID;
    try (Connection con = databaseManager.getConnection()) {
      int rows = databaseManager.makeChangeSecure(con, query, values);
      if (rows < 0) {
        String message =
          "-- validateActivity() > There was a problem inserting a record into the table activity_validations for activity {}.";
        LOG.warn(message, activityID);
      } else {
        saved = true;
      }
    } catch (SQLException e) {
      String message =
        "-- validateActivity() > There was an error inserting a record into the table activity_validations for activity {}.";
      LOG.error(message, activityID, e);
    }

    LOG.debug("<< validateActivity():{}", saved);
    return saved;
  }
}