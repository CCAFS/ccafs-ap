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
    int result = databaseManager.saveData(query.toString(), values);
    LOG.debug("<< saveProjectFlagship():{}", result);
    return result;
  }

  @Override
  public List<Map<String, String>> getProjectFocuses(int projectID, int typeID) {
    LOG.debug(">> getProjectFocuses projectID = {}, typeID ={} )", projectID, typeID);
    List<Map<String, String>> projectFocusesDataList = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query
      .append("SELECT ipr.id, ipr.name, ipr.acronym, le.id as region_id, le.name as region_name, le.code as region_code ");
    query.append("FROM project_focuses pf ");
    query.append("INNER JOIN ip_programs ipr ON ipr.id = pf.program_id ");
    query.append("LEFT JOIN loc_elements le   ON le.id = ipr.region_id ");
    query.append("WHERE pf.project_id = ");
    query.append(projectID);
    query.append("AND ipr.type_id= ");
    query.append(typeID);
    query.append("ORDER BY ipr.name");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> projectFocusesData = new HashMap<String, String>();
        projectFocusesData.put("id", rs.getString("id"));
        projectFocusesData.put("name", rs.getString("name"));
        projectFocusesData.put("acronym", rs.getString("acronym"));
        projectFocusesData.put("region_id", rs.getString("region_id"));
        projectFocusesData.put("region_name", rs.getString("region_name"));
        projectFocusesData.put("region_code", rs.getString("region_code"));

        projectFocusesDataList.add(projectFocusesData);
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the project focuses {} ", projectID, e);
    }

    return projectFocusesDataList;
  }

}
