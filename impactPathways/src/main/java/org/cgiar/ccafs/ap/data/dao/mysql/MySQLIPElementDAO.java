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
import org.cgiar.ccafs.ap.data.dao.IPElementDAO;
import org.cgiar.ccafs.utils.db.DAOManager;

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
 * @author Hernán David Carvajal B.
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
    Object[] values = new Object[6];

    if (ipElementData.get("id") == null) {
      query.append("INSERT INTO ip_elements (description, element_type_id, ip_program_id, created_by, ");
      query.append("modified_by, modification_justification ) ");
      query.append("VALUES (?, ?, ?, ?, ?, ?) ");

      values[0] = ipElementData.get("description");
      values[1] = ipElementData.get("element_type_id");
      values[2] = ipElementData.get("ip_program_id");
      values[3] = ipElementData.get("created_by");
      values[4] = ipElementData.get("modified_by");
      values[5] = ipElementData.get("modification_justification");

    } else {
      query.append("UPDATE ip_elements SET description = ?, element_type_id =?, ip_program_id=?, modified_by=?, ");
      query.append("modification_justification=? WHERE id=? ");

      values[0] = ipElementData.get("description");
      values[1] = ipElementData.get("element_type_id");
      values[2] = ipElementData.get("ip_program_id");
      values[3] = ipElementData.get("modified_by");
      values[4] = ipElementData.get("modification_justification");
      values[5] = ipElementData.get("id");
    }

    int result = databaseManager.saveData(query.toString(), values);
    LOG.debug("<< createIPElement():{}", result);
    return result;

  }

  @Override
  public boolean deleteChildIPElements(int parentElmentID) {
    // TODO - Check if this method need to be changed to manage the deletion with the column is_active instead of making
    // a phisical delete
    LOG.debug(">> deleteChildIPElements(parentElmentID={})", parentElmentID);

    StringBuilder query = new StringBuilder();
    query.append("DELETE ipe.* FROM ip_elements ipe ");
    query.append("INNER JOIN ip_relationships ipr ON ipe.id = ipr.child_id ");
    query.append("WHERE ipr.parent_id = ? ");

    int rowsDeleted = databaseManager.delete(query.toString(), new Object[] {parentElmentID});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteChildIPElements():{}", true);
      return true;
    }

    LOG.debug("<< deleteChildIPElements():{}", false);
    return false;
  }

  @Override
  public boolean deleteIPElement(int ipElementID) {
    LOG.debug(">> deleteIPElement(ipElementID={})", ipElementID);

    StringBuilder query = new StringBuilder();
    query.append("UPDATE ip_elements SET is_active = FALSE ");
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
  @Deprecated
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
  @Deprecated
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

  @Override
  public List<Map<String, String>> getAllIPElements() {
    LOG.debug(">> getIPElementList( )");

    StringBuilder query = new StringBuilder();
    query.append("SELECT e.id, e.description,  ");
    query.append("et.id as 'element_type_id', et.name as 'element_type_name', ");
    query.append("pro.id as 'program_id', pro.acronym as 'program_acronym' ");
    query.append("FROM ip_elements e ");
    query.append("INNER JOIN ip_element_types et ON e.element_type_id = et.id ");
    query.append("INNER JOIN ip_programs pro ON e.ip_program_id = pro.id ");
    query.append("GROUP BY e.id ");
    query.append("ORDER BY et.id, pro.type_id ");

    LOG.debug("-- getIPElementList () > Calling method executeQuery to get the results");
    return this.getData(query.toString());
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

        // TODO - delete this line once we are sure it doesn't generate errors
        ipElementData.put("program_element_id", null);

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
    query.append("SELECT ip.id, ip.name, ip.type_id as 'program_type_id' ");
    query.append("FROM ip_programs ip ");
    query.append("INNER JOIN ip_elements ie ON ip.id = ie.ip_program_id ");
    query.append("WHERE ie.id = ");
    query.append(ipElementID);

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
  public List<Map<String, String>> getIPElement(int programID, int elementTypeID) {
    LOG.debug(">> getIPElement( programID = {}, elementTypeID = {} )", programID, elementTypeID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT e.id, e.description,  ");
    query.append("et.id as 'element_type_id', et.name as 'element_type_name', ");
    query.append("pro.id as 'program_id', pro.acronym as 'program_acronym' ");
    query.append("FROM ip_elements e ");
    query.append("INNER JOIN ip_element_types et ON e.element_type_id = et.id ");
    query.append("INNER JOIN ip_programs pro ON e.ip_program_id = pro.id ");
    query.append("WHERE pro.id = ");
    query.append(programID);
    query.append(" AND et.id = ");
    query.append(elementTypeID);

    LOG.debug("-- getIPElement() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getIPElementByProgramID(int programID) {
    LOG.debug(">> getIPElement( programID = {} )", programID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT e.id, e.description,  ");
    query.append("et.id as 'element_type_id', et.name as 'element_type_name', ");
    query.append("pro.id as 'program_id', pro.acronym as 'program_acronym' ");
    query.append("FROM ip_elements e ");
    query.append("INNER JOIN ip_element_types et ON e.element_type_id = et.id ");
    query.append("INNER JOIN ip_programs pro ON e.ip_program_id = pro.id ");
    query.append("WHERE pro.id = ");
    query.append(programID);
    query.append(" GROUP BY e.id");
    query.append(" ORDER BY et.id, pro.type_id ");


    LOG.debug("-- getIPElement() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }


  @Override
  public List<Map<String, String>> getIPElementByProgramIDSynthesis(int programID) {
    LOG.debug(">> getIPElement( programID = {} )", programID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT e.id, e.description,  ");
    query.append("et.id as 'element_type_id', et.name as 'element_type_name', ");
    query.append("pro.id as 'program_id', pro.acronym as 'program_acronym' ");
    query.append("FROM ip_elements e ");
    query.append("INNER JOIN ip_element_types et ON e.element_type_id = et.id ");
    query.append("INNER JOIN ip_programs pro ON e.ip_program_id = pro.id ");
    query.append("WHERE pro.id = ");
    query.append(programID + " and et.id =4  ");
    query.append(" GROUP BY e.id");
    query.append(" ORDER BY et.id, pro.type_id ");


    LOG.debug("-- getIPElement() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }


  @Override
  public List<Map<String, String>> getIPElementListForSynthesisRegion(int programId) {

    List<Map<String, String>> ipElementList = new ArrayList<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT e.id, e.description, ");
    query.append("et.id as 'element_type_id', et.name as 'element_type_name', ");
    query.append("pro.id as 'program_id', pro.acronym as 'program_acronym', ");
    query.append("iep.id as 'parent_id', iep.description as 'parent_description' ");
    query.append("FROM ip_elements e ");
    query.append("INNER JOIN ip_element_types et ON e.element_type_id = et.id ");
    query.append("INNER JOIN ip_programs pro ON e.ip_program_id = pro.id ");
    query.append("INNER JOIN ip_project_contributions ipc ON e.id = ipc.mog_id ");
    query.append("INNER JOIN ip_elements iep ON iep.id = ipc.midOutcome_id ");
    query.append("WHERE project_id in (select project_id from project_focuses where program_id=" + programId + ")   ");

    query.append(" AND ipc.is_active = TRUE ");
    query.append("GROUP BY id order by  pro.acronym  ");

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
        ipElementData.put("parent_id", rs.getString("parent_id"));
        ipElementData.put("parent_description", rs.getString("parent_description"));

        ipElementList.add(ipElementData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
    }

    LOG.debug("<< getProjectOutputs():ipElementList.size={}", ipElementList.size());
    return ipElementList;
  }

  @Override
  public List<Map<String, String>> getIPElements(String[] elementIds) {
    LOG.debug(">> getIPElement( elementIds = {} )", Arrays.toString(elementIds));
    int elementsCount = elementIds.length;

    StringBuilder query = new StringBuilder();
    query.append("SELECT e.id, e.description, ");
    query.append("et.id as 'element_type_id', et.name as 'element_type_name', ");
    query.append("pro.id as 'program_id', pro.acronym as 'program_acronym' ");
    query.append("FROM ip_elements e ");
    query.append("INNER JOIN ip_element_types et ON e.element_type_id = et.id ");
    query.append("INNER JOIN ip_programs pro ON e.ip_program_id = pro.id ");
    query.append("WHERE e.id IN ( ");

    for (int c = 0; c < elementsCount; c++) {
      query.append(elementIds[c]);
      if (c < elementsCount - 1) {
        query.append(", ");
      }
    }
    query.append("); ");

    LOG.debug("-- getIPElementList () > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getIPElementsByParent(int parentId, int relationTypeID) {
    LOG.debug(">> getIPElementsByParent( parentId = {} )", parentId);

    StringBuilder query = new StringBuilder();
    query.append("SELECT e.id, e.description,  ");
    query.append("et.id as 'element_type_id', et.name as 'element_type_name', ");
    query.append("pro.id as 'program_id', pro.acronym as 'program_acronym' ");
    query.append("FROM ip_elements e ");
    query.append("INNER JOIN ip_relationships r ON e.id = r.child_id ");
    query.append("INNER JOIN ip_element_types et ON e.element_type_id = et.id ");
    query.append("INNER JOIN ip_programs pro ON e.ip_program_id = pro.id ");
    query.append("WHERE r.relation_type_id = ");
    query.append(relationTypeID);
    query.append(" AND r.parent_id = ");
    query.append(parentId);
    query.append(" GROUP BY e.id ");

    LOG.debug("-- getIPElementsRelated() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getIPElementsRelated(int ipElementID, int relationTypeID) {
    LOG.debug(">> getIPElementsRelated( ipElementID = {}, relationTypeID = {} )", ipElementID, relationTypeID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT e.id, e.description,  ");
    query.append("et.id as 'element_type_id', et.name as 'element_type_name', ");
    query.append("pro.id as 'program_id', pro.acronym as 'program_acronym' ");
    query.append("FROM ip_elements e ");
    query.append("INNER JOIN ip_relationships r ON e.id = r.parent_id AND r.child_id = ");
    query.append(ipElementID + " ");
    query.append("INNER JOIN ip_element_types et ON e.element_type_id = et.id ");
    query.append("INNER JOIN ip_programs pro ON e.ip_program_id = pro.id ");
    query.append("WHERE r.relation_type_id = ");
    query.append(relationTypeID);
    query.append(" GROUP BY e.id ");

    LOG.debug("-- getIPElementsRelated() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  @Deprecated
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
  public List<Map<String, String>> getProjectOutputs(int projectID) {
    LOG.debug(">> getProjectOutputs( projectID = {} )", projectID);
    List<Map<String, String>> ipElementList = new ArrayList<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT e.id, e.description, ");
    query.append("et.id as 'element_type_id', et.name as 'element_type_name', ");
    query.append("pro.id as 'program_id', pro.acronym as 'program_acronym', ");
    query.append("iep.id as 'parent_id', iep.description as 'parent_description' ");
    query.append("FROM ip_elements e ");
    query.append("INNER JOIN ip_element_types et ON e.element_type_id = et.id ");
    query.append("INNER JOIN ip_programs pro ON e.ip_program_id = pro.id ");
    query.append("INNER JOIN ip_project_contributions ipc ON e.id = ipc.mog_id ");
    query.append("INNER JOIN ip_elements iep ON iep.id = ipc.midOutcome_id ");
    query.append("WHERE project_id =  ");
    query.append(projectID);
    query.append(" AND ipc.is_active = TRUE ");
    query.append("GROUP BY id ");

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
        ipElementData.put("parent_id", rs.getString("parent_id"));
        ipElementData.put("parent_description", rs.getString("parent_description"));

        ipElementList.add(ipElementData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
    }

    LOG.debug("<< getProjectOutputs():ipElementList.size={}", ipElementList.size());
    return ipElementList;
  }

  @Override
  public List<Map<String, String>> getProjectOutputsCcafs(int projectID) {
    LOG.debug(">> getProjectOutputs( projectID = {} )", projectID);
    List<Map<String, String>> ipElementList = new ArrayList<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT e.id, e.description, ");
    query.append("et.id as 'element_type_id', et.name as 'element_type_name', ");
    query.append("pro.id as 'program_id', pro.acronym as 'program_acronym', ");
    query.append("iep.id as 'parent_id', iep.description as 'parent_description' ");
    query.append("FROM ip_elements e ");
    query.append("INNER JOIN ip_element_types et ON e.element_type_id = et.id ");
    query.append("INNER JOIN ip_programs pro ON e.ip_program_id = pro.id ");
    query.append("INNER JOIN ip_project_contributions ipc ON e.id = ipc.mog_id ");
    query.append("INNER JOIN ip_elements iep ON iep.id = ipc.midOutcome_id ");
    query.append("WHERE project_id =  ");
    query.append(projectID);
    query.append(" AND ipc.is_active = TRUE ");

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
        ipElementData.put("parent_id", rs.getString("parent_id"));
        ipElementData.put("parent_description", rs.getString("parent_description"));

        ipElementList.add(ipElementData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
    }

    LOG.debug("<< getProjectOutputs():ipElementList.size={}", ipElementList.size());
    return ipElementList;
  }

  @Override
  @Deprecated
  public int relateIPElement(int elementID, int programID, int relationTypeID) {
    LOG.debug(">> relateIPElement(elementID={}, programID={}, relationTypeID={})",
      new int[] {elementID, programID, relationTypeID});

    StringBuilder query = new StringBuilder();
    query.append("INSERT IGNORE INTO ip_program_elements (element_id, program_id, relation_type_id) ");
    query.append("VALUES (?, ?, ?) ");

    Object[] values = new Object[3];
    values[0] = elementID;
    values[1] = programID;
    values[2] = relationTypeID;

    int result = databaseManager.saveData(query.toString(), values);
    LOG.debug("<< relateIPElements():{}", result);
    return result;

  }
}
