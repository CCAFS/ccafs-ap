package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.IPElementDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySQLIPElementDAO implements IPElementDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLIPElementDAO.class);

  private DAOManager databaseManager;

  public MySQLIPElementDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getIPElementByProgram(int programID) {
    LOG.debug(">> getIPElementByProgram( programID = {} )", programID);

    List<Map<String, String>> ipElementList = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT e.id, e.description,  ");
    query.append("et.id as 'element_type_id', et.name as 'element_type_name' ");
    query.append("pro.id as 'program_id', pro.acronym as 'program_acronym' ");
    query.append("FROM ip_elements ");
    query.append("INNER JOIN ip_element_types et ON e.element_type_id = et.id ");
    query.append("INNER JOIN ip_programs pro ON e.program_id = pro.id ");
    query.append("WHERE program_id = ");
    query.append(programID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> ipElementData = new HashMap<String, String>();
        ipElementData.put("id", rs.getString("id"));
        ipElementData.put("description", rs.getString("description"));
        ipElementData.put("element_type_id", rs.getString("element_type_id"));
        ipElementData.put("element_type_name", rs.getString("element_type_name"));
        ipElementData.put("program_id", rs.getString("program_id"));
        ipElementData.put("program_acronym", rs.getString("program_acronym"));

        ipElementList.add(ipElementData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- getIPElementByProgram() > Exception raised trying ";
      exceptionMessage += "to get the ip elements corresponding to the program " + programID;

      LOG.error(exceptionMessage);
    }

    LOG.debug("<< getIPElementsByProgram():ipElementList.size={}", ipElementList.size());
    return ipElementList;
  }
}
