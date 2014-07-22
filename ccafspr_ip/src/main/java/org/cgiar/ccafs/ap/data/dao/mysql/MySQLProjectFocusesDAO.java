package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.ProjectFocusesDAO;

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

public class MySQLProjectFocusesDAO implements ProjectFocusesDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLProjectFocusesDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLProjectFocusesDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public int createProjectFocuses(Map<String, Object> projectFocusesData) {
    LOG.debug(">> createProjectFocuses(projectData={})", projectFocusesData);
    StringBuilder query = new StringBuilder();
    query.append("INSERT INTO project_focuses (id, project_id, program_id) ");
    query.append("VALUES (?, ?, ?) ");
    // query.append("ON DUPLICATE KEY UPDATE description = VALUES(description), program_id = VALUES(program_id)");

    Object[] values = new Object[3];
    values[0] = projectFocusesData.get("id");
    values[1] = projectFocusesData.get("project_id");
    values[2] = projectFocusesData.get("program_id");
    int result = saveData(query.toString(), values);
    LOG.debug("<< saveProjectFlagship():{}", result);
    return result;
  }


  private List<Map<String, String>> getData(String query) {
    LOG.debug(">> executeQuery(query='{}')", query);
    List<Map<String, String>> ipElementList = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> ipElementData = new HashMap<String, String>();
        ipElementData.put("id", rs.getString("id"));
        ipElementData.put("description", rs.getString("description"));
        ipElementData.put("element_type_id", rs.getString("element_type_id"));
        ipElementData.put("element_type_name", rs.getString("element_type_name"));
        ipElementData.put("program_id", rs.getString("program_id"));
        ipElementData.put("program_acronym", rs.getString("program_acronym"));
        ipElementData.put("program_element_id", rs.getString("program_element_id"));

        ipElementList.add(ipElementData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
    }

    LOG.debug("<< executeQuery():ipElementList.size={}", ipElementList.size());
    return ipElementList;
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
}
