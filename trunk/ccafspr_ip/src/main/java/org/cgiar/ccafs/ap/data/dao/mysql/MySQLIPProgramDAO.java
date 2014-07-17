package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.IPProgramDAO;

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

public class MySQLIPProgramDAO implements IPProgramDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLIPProgramDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLIPProgramDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }


  private List<Map<String, String>> getData(String query) {
    LOG.debug(">> executeQuery(query='{}')", query);
    List<Map<String, String>> ProgramList = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> ProgramData = new HashMap<String, String>();
        ProgramData.put("id", rs.getString("id"));
        ProgramData.put("name", rs.getString("name"));
        ProgramData.put("acronym", rs.getString("acronym"));
        ProgramData.put("region_id", rs.getString("region_id"));
        ProgramData.put("type_id", rs.getString("type_id"));


        ProgramList.add(ProgramData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
    }

    LOG.debug("<< executeQuery():ProgramList.size={}", ProgramList.size());
    return ProgramList;
  }


  @Override
  public List<Map<String, String>> getProgramType(int projectId, int typeProgramId) {

    LOG.debug(">> getProjectType( programID = {} )", projectId, typeProgramId);

    StringBuilder query = new StringBuilder();
    query.append("SELECT ipr.type_id, ipr.acronym   ");
    query.append("FROM ip_programs as ipr  ");
    query.append("INNER JOIN project_focuses pf  ON pf.program_id=ipr.id  ");
    query.append("WHERE pf.project_id='1' ");
    // query.append(programID);
    query.append("ORDER BY ipr.type_id, ipr.acronym ");


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
  public int saveProjectFlagships(Map<String, Object> projectData) {
    LOG.debug(">> saveProject(projectData={})", projectData);

    StringBuilder query = new StringBuilder();
    query.append("INSERT INTO project_focuses (id, project_id, program_id) ");
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
    query.append("INSERT INTO project_focuses (id, project_id, program_id) ");
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
