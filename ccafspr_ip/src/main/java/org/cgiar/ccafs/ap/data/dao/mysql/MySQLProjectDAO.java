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
        ProjectData.put("start_date", rs.getString("start_date"));
        ProjectData.put("end_date", rs.getString("end_date"));
        ProjectData.put("project_leader_id", rs.getString("project_leader_id"));
        ProjectData.put("project_leader_id", rs.getString("project_leader_id"));

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
  public List<Map<String, String>> getProject(int programID) {
    LOG.debug(">> getProject programID = {} )", programID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT p.*   ");
    // query.append("et.id as 'element_type_id', et.name as 'element_type_name', ");
    // query.append("pro.id as 'program_id', pro.acronym as 'program_acronym' ");
    query.append("FROM projects as p ");
    query.append("INNER JOIN project_focuses pf ON p.id = pf.project_id ");
    query.append("INNER JOIN ip_programs ipr ON pf.program_id=ipr.id ");
    query.append("WHERE ipr.id='1' ");
    // query.append(programID);


    LOG.debug("-- getProject() > Calling method executeQuery to get the results");
    return getData(query.toString());
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
    // TODO Pending function to define, until we can obtain the session credentials

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
  public List<Map<String, String>> getProjectType(int projectId, int typeProgramId) {

    LOG.debug(">> getProjectType( programID = {} )", projectId, typeProgramId);

    StringBuilder query = new StringBuilder();
    query.append("SELECT ipr.type_id, ipr.acronym   ");
    query.append("FROM `projects` as p ");
    query.append("INNER JOIN project_focuses pf ON p.id = pf.project_id ");
    query.append("INNER JOIN ip_programs ipr    ON pf.program_id=ipr.id ");
    query.append("WHERE ipr.id='1' ");
    // query.append(programID);


    LOG.debug("-- getProjectOwnerId() > Calling method executeQuery to get the results");
    return getData(query.toString());
  }


  private int saveData(String query, Object[] data) {
    int generatedId = -1;

    try (Connection con = databaseManager.getConnection()) {
      int ipElementAdded = databaseManager.makeChangeSecure(con, query, data);
      if (ipElementAdded > 0) {
        // get the id assigned to this new record.
        ResultSet rs = databaseManager.makeQuery("SELECT LAST_INSERT_ID()", con);
        if (rs.next()) {
          generatedId = rs.getInt(1);
        }
        rs.close();

      }
    } catch (SQLException e) {
      LOG.error("-- saveData() > There was a problem saving information into the database. \n{}", e);
    }
    return generatedId;
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

    int result = saveData(query.toString(), values);
    LOG.debug("<< saveProject():{}", result);
    return result;
  }

  @Override
  public int saveProjectFlagships(Map<String, Object> projectData) {
    LOG.debug(">> saveProject(projectData={})", projectData);

    StringBuilder query = new StringBuilder();
    query.append("INSERT INTO project_focuses (id, project_id, programid) ");
    query.append("VALUES (?, ?, ?) ");
    // query.append("ON DUPLICATE KEY UPDATE description = VALUES(description), program_id = VALUES(program_id)");

    Object[] values = new Object[3];
    values[0] = projectData.get("id");
    values[1] = projectData.get("title");
    values[2] = projectData.get("summary");

    int result = saveData(query.toString(), values);
    LOG.debug("<< saveProjectFlagship():{}", result);
    return result;
  }

  @Override
  public int saveProjectRegions(Map<String, Object> projectData) {
    LOG.debug(">> saveProject(projectData={})", projectData);

    StringBuilder query = new StringBuilder();
    query.append("INSERT INTO project_focuses (id, project_id, programid) ");
    query.append("VALUES (?, ?, ?) ");
    // query.append("ON DUPLICATE KEY UPDATE description = VALUES(description), program_id = VALUES(program_id)");

    Object[] values = new Object[3];
    values[0] = projectData.get("id");
    values[1] = projectData.get("title");
    values[2] = projectData.get("summary");

    int result = saveData(query.toString(), values);
    LOG.debug("<< saveProjectRegions():{}", result);
    return result;
  }


}
