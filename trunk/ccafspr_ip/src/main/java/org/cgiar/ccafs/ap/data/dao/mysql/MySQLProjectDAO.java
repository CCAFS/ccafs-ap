package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.ProjectDAO;

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

public class MySQLProjectDAO implements ProjectDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLProjectDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLProjectDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  private List<Map<String, String>> getData(String query) {
    LOG.debug(">> executeQuery(query='{}')", query);
    List<Map<String, String>> ProjectList = new ArrayList<>();
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> ProjectData = new HashMap<String, String>();
        ProjectData.put("id", rs.getString("id"));
        ProjectData.put("title", rs.getString("title"));
        ProjectData.put("summary", rs.getString("summary"));
        ProjectData.put("start_date", rs.getDate("start_date").toString());
        ProjectData.put("end_date", rs.getDate("end_date").toString());
        ProjectData.put("project_leader_id", rs.getString("project_leader_id"));
        ProjectData.put("project_owner_id", rs.getString("project_owner_id"));

        ProjectList.add(ProjectData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
    }

    LOG.debug("<< executeQuery():ProjectList.size={}", ProjectList.size());
    return ProjectList;
  }

  @Override
  public Map<String, String> getExpectedProjectLeader(int projectID) {
    LOG.debug(">> getExpectedProjectLeader(projectID={})", projectID);
    Map<String, String> expectedProjectLeaderData = new HashMap<>();
    try (Connection connection = databaseManager.getConnection()) {
      StringBuilder query = new StringBuilder();
      query.append("SELECT epl.* ");
      query.append("FROM expected_project_leaders epl ");
      query.append("INNER JOIN projects p ON p.expected_project_leader_id = epl.id ");
      query.append("WHERE p.id= ");
      query.append(projectID);

      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      if (rs.next()) {
        expectedProjectLeaderData.put("id", rs.getString("id"));
        expectedProjectLeaderData.put("contact_name", rs.getString("contact_name"));
        expectedProjectLeaderData.put("contact_email", rs.getString("contact_email"));
        expectedProjectLeaderData.put("institution_id", rs.getString("institution_id"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getExpectedProjectLeader() > There was an error getting the data for expeted project leader {}.",
        projectID, e);
      return null;
    }
    LOG.debug("<< getExpectedProjectLeader():{}", expectedProjectLeaderData);
    return expectedProjectLeaderData;
  }


  @Override
  public Map<String, String> getProject(int projectID) {
    LOG.debug(">> getProject projectID = {} )", projectID);
    Map<String, String> projectData = new HashMap<String, String>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT p.*, emp.user_id as 'owner_user_id', emp.institution_id as 'owner_institution_id'");
    query.append("FROM projects as p ");
    query.append("INNER JOIN employees emp ON emp.id = p.project_owner_id ");
    query.append("WHERE p.id = ");
    query.append(projectID);
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        projectData.put("id", rs.getString("id"));
        projectData.put("title", rs.getString("title"));
        projectData.put("summary", rs.getString("summary"));
        projectData.put("start_date", rs.getDate("start_date").toString());
        projectData.put("end_date", rs.getDate("end_date").toString());
        projectData.put("project_leader_id", rs.getString("project_leader_id"));
        projectData.put("project_owner_user_id", rs.getString("owner_user_id"));
        projectData.put("project_owner_institution_id", rs.getString("owner_institution_id"));
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the project for the user {}.", projectID, e);
    }
    LOG.debug("-- getProject() > Calling method executeQuery to get the results");
    return projectData;
  }

  @Override
  public Map<String, String> getProjectLeader(int projectID) {
    LOG.debug(">> getProjectLeader(projectID={})", projectID);
    Map<String, String> projectLeaderData = new HashMap<>();
    try (Connection connection = databaseManager.getConnection()) {

      StringBuilder query = new StringBuilder();
      query.append("SELECT u.id, u.username, pe.first_name, pe.last_name, pe.email, e.institution_id ");
      query.append("FROM users u  ");
      query.append("INNER JOIN persons pe  ON u.person_id=pe.id ");
      query.append("INNER JOIN employees e ON u.id=e.user_id ");
      query.append("INNER JOIN projects p ON e.id=p.project_leader_id ");
      query.append("WHERE p.id= ");
      query.append(projectID);

      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      if (rs.next()) {
        projectLeaderData.put("id", rs.getString("id"));
        projectLeaderData.put("username", rs.getString("username"));
        projectLeaderData.put("first_name", rs.getString("first_name"));
        projectLeaderData.put("last_name", rs.getString("last_name"));
        projectLeaderData.put("email", rs.getString("email"));
        projectLeaderData.put("institution_id", rs.getString("institution_id"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getProjectLeader() > There was an error getting the data for user {}.", projectID, e);
      return null;
    }
    LOG.debug("<< getProjectLeader():{}", projectLeaderData);
    return projectLeaderData;
  }

  @Override
  public List<Map<String, String>> getProjectOwnerContact(int institutionId) {
    LOG.debug(">> getProjectOwnerContact( programID = {} )", institutionId);

    StringBuilder query = new StringBuilder();
    query.append("SELECT p.*   ");
    query.append("FROM `ccafs_employees` as ce ");
    // query.append("INNER JOIN users u ON u.id = ce.users_id ");
    // query.append("INNER JOIN persons p ON p.id = u.person_id ");
    query.append("INNER JOIN persons p ON p.id = u.person_id ");
    query.append("WHERE ce.institution_id='1' ");
    // query.append(institutionId);


    LOG.debug("-- getProjectOwnerContact() > Calling method executeQuery to get the results");
    return getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getProjectOwnerId(int programId) {
    LOG.debug(">> getProjectOwnerId( programID = {} )", programId);

    StringBuilder query = new StringBuilder();
    query.append("SELECT p.*   ");
    query.append("FROM `projects` as p ");
    query.append("INNER JOIN project_focuses pf ON p.id = pf.project_id ");
    query.append("INNER JOIN ip_programs ipr    ON pf.program_id=ipr.id ");
    query.append("WHERE ipr.id='1' ");
    // query.append(programID);


    LOG.debug("-- getProjectOwnerId() > Calling method executeQuery to get the results");
    return getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getProjects(int programID) {
    LOG.debug(">> getProjects programID = {} )", programID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT p.*   ");
    // query.append("et.id as 'element_type_id', et.name as 'element_type_name', ");
    // query.append("pro.id as 'program_id', pro.acronym as 'program_acronym' ");
    query.append("FROM projects as p ");
    query.append("INNER JOIN project_focuses pf ON p.id = pf.project_id ");
    query.append("INNER JOIN ip_programs ipr ON pf.program_id=ipr.id ");
    query.append("WHERE ipr.id= ");
    query.append(programID);


    LOG.debug("-- getProjects() > Calling method executeQuery to get the results");
    return getData(query.toString());
  }

  @Override
  public int saveExpectedProjectLeader(int projectId, Map<String, Object> expectedProjectLeaderData) {
    LOG.debug(">> saveExpectedProjectLeader(projectData={})", projectId);
    StringBuilder query = new StringBuilder();
    int result = -1;
    int newId = -1;
    if (expectedProjectLeaderData.get("id") == null) {
      // Add the record into the database and assign it to the projects table (column expected_project_leader_id).
      query.append("INSERT INTO expected_project_leaders (contact_name, contact_email, institution_id) ");
      query.append("VALUES (?, ?, ?) ");
      Object[] values = new Object[3];
      values[0] = expectedProjectLeaderData.get("contact_name");
      values[1] = expectedProjectLeaderData.get("contact_email");
      values[2] = expectedProjectLeaderData.get("institution_id");
      newId = databaseManager.saveData(query.toString(), values);
      if (newId <= 0) {
        LOG.error("A problem happened trying to add a new expected project leader in project with id={}", projectId);
        return -1;
      } else {
        // Now we need to assign the new id in the table projects (column expected_project_leader_id).
        query.setLength(0); // Clearing query.
        query.append("UPDATE projects SET expected_project_leader_id = ");
        query.append(newId);
        query.append(" WHERE id = ");
        query.append(projectId);

        try (Connection conn = databaseManager.getConnection()) {
          result = databaseManager.makeChange(query.toString(), conn);
          if (result == 0) {
            // Great!, record was updated.
            result = newId;
          }
        } catch (SQLException e) {
          LOG.error("error trying to create a connection to the database. ", e);
          return -1;
        }
      }
    } else {
      // UPDATE the record into the database.
      query.append("UPDATE expected_project_leaders SET contact_name = ?, contact_email = ?, institution_id = ? ");
      query.append("WHERE id = ?");
      Object[] values = new Object[4];
      values[0] = expectedProjectLeaderData.get("contact_name");
      values[1] = expectedProjectLeaderData.get("contact_email");
      values[2] = expectedProjectLeaderData.get("institution_id");
      values[3] = expectedProjectLeaderData.get("id");
      result = databaseManager.saveData(query.toString(), values);
      if (result == -1) {
        LOG.error("A problem happened trying to update an expected project leader identified with the id = {}",
          expectedProjectLeaderData.get("id"));
        return -1;
      }
    }
    return result;
  }

  @Override
  public int saveProject(Map<String, Object> projectData) {
    LOG.debug(">> saveProject(projectData={})", projectData);

    StringBuilder query = new StringBuilder();
    query.append("INSERT INTO projects (id, title, summary, start_date,end_date,project_leader_id,project_owner_id) ");
    query.append("VALUES (?, ?, ?, ?, ?, ?, ?) ");
    // query.append("ON DUPLICATE KEY UPDATE description = VALUES(description), program_id = VALUES(program_id)");

    Object[] values = new Object[7];
    values[0] = projectData.get("id");
    values[1] = projectData.get("title");
    values[2] = projectData.get("summary");
    values[3] = projectData.get("start_date");
    values[4] = projectData.get("end_date");
    values[5] = projectData.get("project_leader_id");
    values[6] = projectData.get("project_owner_id");

    int result = databaseManager.saveData(query.toString(), values);
    LOG.debug("<< saveProject():{}", result);
    return result;
  }

}
