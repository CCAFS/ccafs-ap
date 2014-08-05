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

import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.IPElementDAO;

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

/**
 * @author Héctor Fabio Tobón R.
 */
public class MySQLIPElementDAO implements IPElementDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLIPElementDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLIPElementDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public int createIPElement(Map<String, Object> ipElementData) {
    LOG.debug(">> createIPElement(ipElementData)", ipElementData);

    StringBuilder query = new StringBuilder();
    query.append("INSERT INTO ip_elements (id, description, element_type_id ) ");
    query.append("VALUES (?, ?, ?) ");
    query.append("ON DUPLICATE KEY UPDATE description=VALUES(description), ");
    query.append("element_type_id=VALUES(element_type_id)");

    Object[] values = new Object[3];
    values[0] = ipElementData.get("id");
    values[1] = ipElementData.get("description");
    values[2] = ipElementData.get("element_type_id");

    int result = databaseManager.saveData(query.toString(), values);
    LOG.debug("<< createIPElement():{}", result);
    return result;

  }

  @Override
  public boolean deleteIPElement(int ipElementID) {
    LOG.debug(">> deleteIPElement(ipElementID={})", ipElementID);

    StringBuilder query = new StringBuilder();
    query.append("DELETE FROM ip_elements ");
    query.append("WHERE id = ? ");

    int rowsDeleted = databaseManager.delete(query.toString(), new Object[] {ipElementID});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteIPElement():{}", true);
      return true;
    }

    LOG.debug("<< deleteIPElement():{}", false);
    return false;
  }

  @Override
  public boolean deleteIpElements(int programId, int typeId) {
    LOG.debug(">> deleteIpElements(programId={}, typeId={})", programId, typeId);

    StringBuilder query = new StringBuilder();
    query.append("DELETE ipe.* FROM ip_program_elements ");
    query.append("INNER JOIN ip_program_element_relation_types iet ON ipe.relation_type_id = iet.id ");
    query.append("WHERE ipe.creator_id = ? AND ipe.element_type_id = ?");

    int rowsDeleted = databaseManager.delete(query.toString(), new Object[] {programId, typeId});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteIpElements():{}", true);
      return true;
    }

    LOG.debug("<< deleteIpElements():{}", false);
    return false;
  }

  @Override
  public boolean deleteProgramElement(int programElementID) {
    LOG.debug(">> deleteProgramElement(programElementID={})", programElementID);

    StringBuilder query = new StringBuilder();
    query.append("DELETE FROM ip_program_elements ");
    query.append("WHERE id = ? ");

    int rowsDeleted = databaseManager.delete(query.toString(), new Object[] {programElementID});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteProgramElement():{}", true);
      return true;
    }

    LOG.debug("<< deleteProgramElement():{}", false);
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

  @Override
  public Map<String, String> getElementCreator(int ipElementID) {
    Map<String, String> programData = new HashMap<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT ipp.id, ipp.name, ipp.type_id as 'program_type_id' ");
    query.append("FROM ip_programs ipp ");
    query.append("INNER JOIN ip_program_elements ipe ON ipp.id = ipe.program_id ");
    query.append("INNER JOIN ip_program_element_relation_types ipt ON ipe.relation_type_id = ipt.id ");
    query.append("WHERE ipe.element_id = ");
    query.append(ipElementID);
    query.append(" AND ipt.id = ");
    query.append(APConstants.PROGRAM_ELEMENT_RELATION_CREATION);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        programData.put("id", rs.getString("id"));
        programData.put("name", rs.getString("name"));
        programData.put("program_type_id", rs.getString("program_type_id"));
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- getElementCreator() > Exception raised trying to get the program ";
      exceptionMessage += "which created the ipElement " + ipElementID;

      LOG.error(exceptionMessage, e);
    }
    return programData;
  }

  @Override
  public List<Map<String, String>> getIPElement(int programID) {
    LOG.debug(">> getIPElement( programID = {} )", programID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT e.id, e.description,  pel.id as 'program_element_id',  ");
    query.append("et.id as 'element_type_id', et.name as 'element_type_name', ");
    query.append("pro.id as 'program_id', pro.acronym as 'program_acronym' ");
    query.append("FROM ip_elements e ");
    query.append("INNER JOIN ip_element_types et ON e.element_type_id = et.id ");
    query.append("INNER JOIN ip_program_elements pel ON e.id = pel.element_id ");
    query.append("INNER JOIN ip_programs pro ON pel.program_id = pro.id ");
    query.append("WHERE pel.program_id = ");
    query.append(programID);


    LOG.debug("-- getIPElement() > Calling method executeQuery to get the results");
    return getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getIPElement(int programID, int elementTypeID) {
    LOG.debug(">> getIPElement( programID = {}, elementTypeID = {} )", programID, elementTypeID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT e.id, e.description,  pel.id as 'program_element_id', ");
    query.append("et.id as 'element_type_id', et.name as 'element_type_name', ");
    query.append("pro.id as 'program_id', pro.acronym as 'program_acronym' ");
    query.append("FROM ip_elements e ");
    query.append("INNER JOIN ip_element_types et ON e.element_type_id = et.id ");
    query.append("INNER JOIN ip_program_elements pel ON e.id = pel.element_id ");
    query.append("INNER JOIN ip_programs pro ON pel.program_id = pro.id ");
    query.append("WHERE pel.program_id = ");
    query.append(programID);
    query.append(" AND et.id = ");
    query.append(elementTypeID);
    query.append(" AND pel.relation_type_id = ");
    query.append(APConstants.PROGRAM_ELEMENT_RELATION_USE);

    LOG.debug("-- getIPElement() > Calling method executeQuery to get the results");
    return getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getIPElement(String[] elementIds) {
    LOG.debug(">> getIPElement( elementIds = {} )", Arrays.toString(elementIds));
    int elementsCount = elementIds.length;

    StringBuilder query = new StringBuilder();
    query.append("SELECT e.id, e.description, pel.id as 'program_element_id', ");
    query.append("et.id as 'element_type_id', et.name as 'element_type_name', ");
    query.append("pro.id as 'program_id', pro.acronym as 'program_acronym' ");
    query.append("FROM ip_elements e ");
    query.append("INNER JOIN ip_element_types et ON e.element_type_id = et.id ");
    query.append("INNER JOIN ip_program_elements pel ON e.id = pel.element_id ");
    query.append("INNER JOIN ip_programs pro ON pel.program_id = pro.id ");
    query.append("WHERE e.id IN ( ");

    for (int c = 0; c < elementsCount; c++) {
      query.append(elementIds[c]);
      if (c < elementsCount - 1) {
        query.append(", ");
      }
    }
    query.append("); ");

    LOG.debug("-- getIPElementList () > Calling method executeQuery to get the results");
    return getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getIPElementList() {
    LOG.debug(">> getIPElementList( )");

    StringBuilder query = new StringBuilder();
    query.append("SELECT e.id, e.description, pel.id as 'program_element_id', ");
    query.append("et.id as 'element_type_id', et.name as 'element_type_name', ");
    query.append("pro.id as 'program_id', pro.acronym as 'program_acronym' ");
    query.append("FROM ip_elements e ");
    query.append("INNER JOIN ip_element_types et ON e.element_type_id = et.id ");
    query.append("INNER JOIN ip_program_elements pel ON e.id = pel.element_id ");
    query.append("INNER JOIN ip_programs pro ON pel.program_id = pro.id ");

    LOG.debug("-- getIPElementList () > Calling method executeQuery to get the results");
    return getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getIPElementsByParent(int parentId, int relationTypeID) {
    LOG.debug(">> getIPElementsByParent( parentId = {} )", parentId);

    StringBuilder query = new StringBuilder();
    query.append("SELECT e.id, e.description,  ");
    query.append("et.id as 'element_type_id', et.name as 'element_type_name', ");
    query.append("pro.id as 'program_id', pro.acronym as 'program_acronym', ");
    query.append("pel.id as 'program_element_id' ");
    query.append("FROM ip_elements e ");
    query.append("INNER JOIN ip_relationships r ON e.id = r.child_id ");
    query.append("INNER JOIN ip_element_types et ON e.element_type_id = et.id ");
    query.append("INNER JOIN ip_program_elements pel ON e.id = pel.element_id ");
    query.append("INNER JOIN ip_programs pro ON pel.program_id = pro.id ");
    query.append("WHERE r.relation_type_id = ");
    query.append(relationTypeID);
    query.append(" AND r.parent_id = ");
    query.append(parentId);
    query.append(" GROUP BY e.id ");

    LOG.debug("-- getIPElementsRelated() > Calling method executeQuery to get the results");
    return getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getIPElementsRelated(int ipElementID, int relationTypeID) {
    LOG.debug(">> getIPElementsRelated( ipElementID = {}, relationTypeID = {} )", ipElementID, relationTypeID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT e.id, e.description,  ");
    query.append("et.id as 'element_type_id', et.name as 'element_type_name', ");
    query.append("pro.id as 'program_id', pro.acronym as 'program_acronym', ");
    query.append("pel.id as 'program_element_id' ");
    query.append("FROM ip_elements e ");
    query.append("INNER JOIN ip_relationships r ON e.id = r.parent_id AND r.child_id = ");
    query.append(ipElementID + " ");
    query.append("INNER JOIN ip_element_types et ON e.element_type_id = et.id ");
    query.append("INNER JOIN ip_program_elements pel ON e.id = pel.element_id ");
    query.append("INNER JOIN ip_programs pro ON pel.program_id = pro.id ");
    query.append("WHERE r.relation_type_id = ");
    query.append(relationTypeID);
    query.append(" GROUP BY e.id ");

    LOG.debug("-- getIPElementsRelated() > Calling method executeQuery to get the results");
    return getData(query.toString());
  }

  @Override
  public int getProgramElementID(int ipElementID, int ipProgramID) {
    LOG.debug(">> getProgramElementID(ipElementID={}, ipProgramID={})", ipElementID, ipProgramID);
    int programElementID = -1;

    StringBuilder query = new StringBuilder();
    query.append("SELECT id FROM ip_program_elements WHERE element_id = ");
    query.append(ipElementID);
    query.append(" AND program_id = ");
    query.append(ipProgramID);
    query.append(" AND relation_type_id = ");
    query.append(APConstants.PROGRAM_ELEMENT_RELATION_USE);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        programElementID = rs.getInt("id");
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- getProgramElementID() > Exception raised trying to get the id of the";
      exceptionMessage += "program element which relates the element " + ipElementID;
      exceptionMessage += " and the program " + ipProgramID;

      LOG.error(exceptionMessage, e);
    }

    return programElementID;
  }

  @Override
  public int relateIPElement(int elementID, int programID, int relationTypeID) {
    LOG.debug(">> relateIPElement(elementID={}, programID={}, relationTypeID={})", new int[] {elementID, programID,
      relationTypeID});

    StringBuilder query = new StringBuilder();
    query.append("INSERT INTO ip_program_elements (element_id, program_id, relation_type_id) ");
    query.append("VALUES (?, ?, ?) ");
    // ON DUPLICATE KEY -> do nothing
    query.append("ON DUPLICATE KEY UPDATE element_id = element_id");

    Object[] values = new Object[3];
    values[0] = elementID;
    values[1] = programID;
    values[2] = relationTypeID;

    int result = databaseManager.saveData(query.toString(), values);
    LOG.debug("<< relateIPElements():{}", result);
    return result;

  }
}
