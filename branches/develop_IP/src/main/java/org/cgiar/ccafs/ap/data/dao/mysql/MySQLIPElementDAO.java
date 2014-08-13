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

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySQLIPElementDAO implements IPElementDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLIPElementDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLIPElementDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean deleteIpElements(int programId, int typeId) {
    LOG.debug(">> deleteIpElements(programId={}, typeId={})", programId, typeId);

    String deleteQuery = "DELETE FROM ip_elements WHERE program_id = ? AND element_type_id = ?";
    try (Connection connection = databaseManager.getConnection()) {
      int rowsDeleted = databaseManager.makeChangeSecure(connection, deleteQuery, new Object[] {programId, typeId});
      if (rowsDeleted >= 0) {
        LOG.debug("<< deleteIpElements():{}", true);
        return true;
      }
    } catch (SQLException e) {
      LOG.error("-- deleteIpElements() > There was a problem deleting the ipElements of program {} and type {}.",
        new Object[] {programId, typeId, e});
    }

    LOG.debug("<< deleteIpElements():{}", false);
    return false;
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

  @Override
  public List<Map<String, String>> getIPElement(int programID) {
    LOG.debug(">> getIPElement( programID = {} )", programID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT e.id, e.description,  ");
    query.append("et.id as 'element_type_id', et.name as 'element_type_name', ");
    query.append("pro.id as 'program_id', pro.acronym as 'program_acronym' ");
    query.append("FROM ip_elements e ");
    query.append("INNER JOIN ip_element_types et ON e.element_type_id = et.id ");
    query.append("INNER JOIN ip_programs pro ON e.program_id = pro.id ");
    query.append("WHERE program_id = ");
    query.append(programID);


    LOG.debug("-- getIPElementsByProgram() > Calling method executeQuery to get the results");
    return getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getIPElement(int programID, int elementTypeID) {
    LOG.debug(">> getIPElement( programID = {}, elementTypeID = {} )", programID, elementTypeID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT e.id, e.description,  ");
    query.append("et.id as 'element_type_id', et.name as 'element_type_name', ");
    query.append("pro.id as 'program_id', pro.acronym as 'program_acronym' ");
    query.append("FROM ip_elements e ");
    query.append("INNER JOIN ip_element_types et ON e.element_type_id = et.id ");
    query.append("INNER JOIN ip_programs pro ON e.program_id = pro.id ");
    query.append("WHERE program_id = ");
    query.append(programID);
    query.append(" AND et.id = ");
    query.append(elementTypeID);


    LOG.debug("-- getIPElementsByProgram() > Calling method executeQuery to get the results");
    return getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getIPElementList() {
    LOG.debug(">> getIPElementList( )");

    StringBuilder query = new StringBuilder();
    query.append("SELECT e.id, e.description,  ");
    query.append("et.id as 'element_type_id', et.name as 'element_type_name', ");
    query.append("pro.id as 'program_id', pro.acronym as 'program_acronym' ");
    query.append("FROM ip_elements e ");
    query.append("INNER JOIN ip_element_types et ON e.element_type_id = et.id ");
    query.append("INNER JOIN ip_programs pro ON e.program_id = pro.id ");

    LOG.debug("-- getIPElementList () > Calling method executeQuery to get the results");
    return getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getParentsOfIPElement(int ipElementID) {
    LOG.debug(">> getParentsOfIPElement( ipElementID = {} )", ipElementID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT e.id, e.description,  ");
    query.append("et.id as 'element_type_id', et.name as 'element_type_name', ");
    query.append("pro.id as 'program_id', pro.acronym as 'program_acronym' ");
    query.append("FROM ip_elements e ");
    query.append("INNER JOIN ip_relationships r ON e.id = r.parent_id AND r.child_id = ");
    query.append(ipElementID);
    query.append(" INNER JOIN ip_element_types et ON e.element_type_id = et.id ");
    query.append("INNER JOIN ip_programs pro ON e.program_id = pro.id ");

    LOG.debug("-- getIPElementsByProgram() > Calling method executeQuery to get the results");
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
  public int saveIPElements(Map<String, Object> ipElementData) {
    LOG.debug(">> saveIPElements(ipElementData={})", ipElementData);

    StringBuilder query = new StringBuilder();
    query.append("INSERT INTO ip_elements (id, description, program_id, element_type_id) ");
    query.append("VALUES (?, ?, ?, ?) ");
    query.append("ON DUPLICATE KEY UPDATE description = VALUES(description), program_id = VALUES(program_id)");

    Object[] values = new Object[4];
    values[0] = ipElementData.get("id");
    values[1] = ipElementData.get("description");
    values[2] = ipElementData.get("program_id");
    values[3] = ipElementData.get("element_type_id");

    int result = saveData(query.toString(), values);
    LOG.debug("<< saveIPElements():{}", result);
    return result;
  }
}