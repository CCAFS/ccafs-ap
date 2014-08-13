/*****************************************************************
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
 * ***************************************************************
 */
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

/**
 * @author Héctor Fabio Tobón R.
 * @author Javier Andrés Gallego.
 */
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
    List<Map<String, String>> projectList = new ArrayList<>();
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> projectData = new HashMap<String, String>();
        projectData.put("id", rs.getString("id"));
        projectData.put("title", rs.getString("title"));
        projectData.put("summary", rs.getString("summary"));
        if (rs.getDate("start_date") != null) {
          projectData.put("start_date", rs.getDate("start_date").toString());
        }
        if (rs.getDate("end_date") != null) {
          projectData.put("end_date", rs.getDate("end_date").toString());
        }
        projectData.put("project_leader_id", rs.getString("project_leader_id"));
        projectData.put("program_creator_id", rs.getString("program_creator_id"));
        projectData.put("project_owner_id", rs.getString("project_owner_id"));
        projectData.put("created", rs.getTimestamp("created").getTime() + "");

        projectList.add(projectData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
    }

    LOG.debug("<< executeQuery():ProjectList.size={}", projectList.size());
    return projectList;
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
        expectedProjectLeaderData.put("contact_first_name", rs.getString("contact_first_name"));
        expectedProjectLeaderData.put("contact_last_name", rs.getString("contact_last_name"));
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
    query.append("SELECT p.*, emp.id as 'owner_id', emp.institution_id as 'owner_institution_id'");
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
        if (rs.getDate("start_date") != null) {
          projectData.put("start_date", rs.getDate("start_date").toString());
        }
        if (rs.getDate("end_date") != null) {
          projectData.put("end_date", rs.getDate("end_date").toString());
        }
        projectData.put("project_leader_id", rs.getString("project_leader_id"));
        projectData.put("program_creator_id", rs.getString("program_creator_id"));
        projectData.put("project_owner_id", rs.getString("owner_id"));
        // projectData.put("project_owner_institution_id", rs.getString("owner_institution_id"));
        projectData.put("created", rs.getTimestamp("created").getTime() + "");
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the project for the user {}.", projectID, e);
    }
    LOG.debug("-- getProject() > Calling method executeQuery to get the results");
    return projectData;
  }

  @Override
  public List<Integer> getProjectIdsEditables(int programID, int ownerID) {
    LOG.debug(">> getProjectIdsEditables(projectID={}, ownerId={})", new Object[] {programID, ownerID});
    List<Integer> projectIds = new ArrayList<>();
    try (Connection connection = databaseManager.getConnection()) {
      StringBuilder query = new StringBuilder();
      query.append("SELECT p.id FROM projects p WHERE p.program_creator_id = ");
      query.append(programID);
      query.append(" OR p.project_owner_id = ");
      query.append(ownerID);
      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        projectIds.add(rs.getInt(1));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getProjectIdsEditables() > There was an error getting the data for projectID={}, ownerId={}.",
        new Object[] {programID, ownerID}, e);
      return null;
    }
    LOG.debug("<< getProjectIdsEditables():{}", projectIds);
    return projectIds;
  }

  @Override
  public Map<String, String> getProjectLeader(int projectID) {
    LOG.debug(">> getProjectLeader(projectID={})", projectID);
    Map<String, String> projectLeaderData = new HashMap<>();
    try (Connection connection = databaseManager.getConnection()) {

      StringBuilder query = new StringBuilder();
      query.append("SELECT u.id, pe.first_name, pe.last_name, u.email, e.institution_id, ");
      query.append("e.id as employee_id ");
      query.append("FROM users u  ");
      query.append("INNER JOIN persons pe  ON u.person_id=pe.id ");
      query.append("INNER JOIN employees e ON u.id=e.user_id ");
      query.append("INNER JOIN projects p ON e.id=p.project_leader_id ");
      query.append("WHERE p.id= ");
      query.append(projectID);

      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      if (rs.next()) {
        projectLeaderData.put("id", rs.getString("id"));
        projectLeaderData.put("first_name", rs.getString("first_name"));
        projectLeaderData.put("last_name", rs.getString("last_name"));
        projectLeaderData.put("email", rs.getString("email"));
        projectLeaderData.put("institution_id", rs.getString("institution_id"));
        projectLeaderData.put("employee_id", rs.getString("employee_id"));
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
  public List<Map<String, String>> getProjectsByProgram(int programID) {
    LOG.debug(">> getProjects programID = {} )", programID);
    StringBuilder query = new StringBuilder();

    query.append("SELECT p.* ");
    query.append("FROM projects as p ");
    query.append("INNER JOIN employees emp ON emp.id = p.project_owner_id ");
    query.append("INNER JOIN institutions i ON i.id = emp.institution_id ");
    query.append("INNER JOIN ip_programs ip ON ip.id = i.program_id ");
    query.append("WHERE ip.id = ");
    query.append(programID);

    LOG.debug("-- getProjects() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getProjectsOwning(int institutionId, int userId) {
    List<Map<String, String>> projectList = new ArrayList<>();
    LOG.debug(">> getProjectsOwning institutionId = {} )", institutionId);

    StringBuilder query = new StringBuilder();
    query.append("SELECT p.*, emp.user_id as 'owner_user_id', emp.institution_id as 'owner_institution_id' ");
    query.append("FROM projects p ");
    query.append("INNER JOIN employees emp ON p.project_owner_id=emp.id ");
    query.append("WHERE emp.user_id= ");
    query.append(userId);
    query.append(" AND emp.institution_id= ");
    query.append(institutionId);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> projectData = new HashMap<String, String>();
        projectData.put("id", rs.getString("id"));
        projectData.put("title", rs.getString("title"));
        projectData.put("summary", rs.getString("summary"));
        if (rs.getDate("start_date") != null) {
          projectData.put("start_date", rs.getDate("start_date").toString());
        }
        if (rs.getDate("end_date") != null) {
          projectData.put("end_date", rs.getDate("end_date").toString());
        }
        projectData.put("project_leader_id", rs.getString("project_leader_id"));
        projectData.put("program_creator_id", rs.getString("program_creator_id"));
        projectData.put("project_owner_id", rs.getString("project_owner_id"));
        projectData.put("project_owner_user_id", rs.getString("owner_user_id"));
        projectData.put("project_owner_institution_id", rs.getString("owner_institution_id"));
        projectData.put("created", rs.getTimestamp("created").getTime() + "");

        projectList.add(projectData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
    }

    LOG.debug("<< executeQuery():ProjectList.size={}", projectList.size());
    return projectList;
  }

  @Override
  public int saveExpectedProjectLeader(int projectId, Map<String, Object> expectedProjectLeaderData) {
    LOG.debug(">> saveExpectedProjectLeader(projectData={})", projectId);
    StringBuilder query = new StringBuilder();
    int result = -1;
    int newId = -1;
    if (expectedProjectLeaderData.get("id") == null) {
      // Add the record into the database and assign it to the projects table (column expected_project_leader_id).
      query
      .append("INSERT INTO expected_project_leaders (contact_first_name, contact_last_name, contact_email, institution_id) ");
      query.append("VALUES (?, ?, ?, ?) ");
      Object[] values = new Object[4];
      values[0] = expectedProjectLeaderData.get("contact_first_name");
      values[1] = expectedProjectLeaderData.get("contact_last_name");
      values[2] = expectedProjectLeaderData.get("contact_email");
      values[3] = expectedProjectLeaderData.get("institution_id");
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
      query
      .append("UPDATE expected_project_leaders SET contact_first_name = ?, contact_last_name = ?, contact_email = ?, institution_id = ? ");
      query.append("WHERE id = ?");
      Object[] values = new Object[5];
      values[0] = expectedProjectLeaderData.get("contact_first_name");
      values[1] = expectedProjectLeaderData.get("contact_last_name");
      values[2] = expectedProjectLeaderData.get("contact_email");
      values[3] = expectedProjectLeaderData.get("institution_id");
      values[4] = expectedProjectLeaderData.get("id");
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
    int result = -1;
    StringBuilder query = new StringBuilder();
    if (projectData.get("id") == null) {
      // Insert a new project record.
      query.append("INSERT INTO projects (project_owner_id, program_creator_id) ");
      query.append("VALUES (?, ?) ");

      Object[] values = new Object[2];
      values[0] = projectData.get("project_owner_id");
      values[1] = projectData.get("program_creator_id");
      result = databaseManager.saveData(query.toString(), values);
      LOG.debug("<< saveProject():{}", result);
    } else {
      // Update project.
      query.append("UPDATE projects SET title = ?, summary = ?, start_date = ?, end_date = ?, ");
      query.append("project_owner_id = ? ");
      query.append("WHERE id = ?");
      Object[] values = new Object[6];
      values[0] = projectData.get("title");
      values[1] = projectData.get("summary");
      values[2] = projectData.get("start_date");
      values[3] = projectData.get("end_date");
      values[4] = projectData.get("project_owner_id");
      values[5] = projectData.get("id");
      result = databaseManager.saveData(query.toString(), values);
    }
    LOG.debug(">> saveProject(projectData={})", projectData);
    return result;
  }

}
