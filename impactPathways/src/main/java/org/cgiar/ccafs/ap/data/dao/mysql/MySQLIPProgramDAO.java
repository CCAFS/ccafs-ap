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

import org.cgiar.ccafs.ap.data.dao.IPProgramDAO;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.utils.db.DAOManager;

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
 * @author Javier Andrés Gallego
 * @author Héctor Fabio Tobón R.
 */
public class MySQLIPProgramDAO implements IPProgramDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLIPProgramDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLIPProgramDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean deleteProjectFocus(int projectID, int ipProgramID) {
    LOG.debug(">> deleteProjectFocus(projectId={}, ipProgramID={})", new String[] {projectID + "", ipProgramID + ""});
    String query = "DELETE FROM project_focuses WHERE project_id = ? AND program_id = ?";
    int rowsDeleted = databaseManager.delete(query, new Object[] {projectID, ipProgramID});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteProjectFocus():{}", true);
      return true;
    }
    LOG.debug("<< deleteProjectFocus:{}", false);
    return false;
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
        ProgramData.put("type_name", rs.getString("type_name"));
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
  public Map<String, String> getIPProgramById(int ipProgramID) {
    Map<String, String> ipProgramData = new HashMap<>();
    LOG.debug(">> getIPProgramById( programID = {} )", ipProgramID);
    StringBuilder query = new StringBuilder();
    query.append("SELECT ipr.*, type.name as 'type_name' ");
    query.append("FROM ip_programs as ipr  ");
    query.append("INNER JOIN ip_program_types type ON type.id = ipr.type_id ");
    query.append("WHERE ipr.id =  ");
    query.append(ipProgramID);
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        ipProgramData.put("id", rs.getString("id"));
        ipProgramData.put("name", rs.getString("name"));
        ipProgramData.put("acronym", rs.getString("acronym"));
        ipProgramData.put("region_id", rs.getString("region_id"));
        ipProgramData.put("type_id", rs.getString("type_id"));
        ipProgramData.put("type_name", rs.getString("type_name"));
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- getIPProgramById() > Exception raised trying to get the program ";
      exceptionMessage += "which created the getIPProgramById " + ipProgramID;

      LOG.error(exceptionMessage, e);
    }
    return ipProgramData;
  }


  @Override
  public Map<String, String> getIPProgramByProjectId(int projectID) {
    Map<String, String> ipProgramData = new HashMap<>();
    LOG.debug(">> getIPProgramByProjectId( projectID = {} )", projectID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT ipr.*, type.name as 'type_name' ");
    query.append("FROM ip_programs as ipr ");
    query.append("INNER JOIN projects p ON ipr.id=p.liaison_institution_id ");
    query.append("INNER JOIN ip_program_types type ON type.id = ipr.type_id ");
    query.append("WHERE p.id= ");
    query.append(projectID);
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        ipProgramData.put("id", rs.getString("id"));
        ipProgramData.put("name", rs.getString("name"));
        ipProgramData.put("acronym", rs.getString("acronym"));
        ipProgramData.put("region_id", rs.getString("region_id"));
        ipProgramData.put("type_id", rs.getString("type_id"));
        ipProgramData.put("type_name", rs.getString("type_name"));
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- getIPProgramByProjectId() > Exception raised trying to get the program ";
      exceptionMessage += "which created the getIPProgramByProjectId " + projectID;

      LOG.error(exceptionMessage, e);
    }
    return ipProgramData;
  }


  @Override
  public List<Map<String, String>> getProgramsByType(int typeId) {
    LOG.debug(">> getProgramsByType( typeId = {} )", typeId);

    StringBuilder query = new StringBuilder();
    query.append("SELECT ipr.*, type.name as 'type_name' ");
    query.append("FROM ip_programs as ipr ");
    query.append("INNER JOIN ip_program_types type ON type.id = ipr.type_id ");
    query.append("WHERE ipr.type_id= ");
    query.append(typeId);
    query.append(" ORDER BY ipr.acronym ");


    LOG.debug("-- getProgramsByType() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }


  @Override
  public Map<String, Object> getProgramsByTypeMap(int ipProgramTypeID) {
    LOG.debug(">> getProgramsByTypeMap( ipProgramTypeID = {} )", ipProgramTypeID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT ipr.*, type.name as 'type_name' ");
    query.append("FROM ip_programs as ipr ");
    query.append("INNER JOIN ip_program_types type ON type.id = ipr.type_id ");
    query.append("WHERE ipr.type_id= ");
    query.append(ipProgramTypeID);
    query.append(" ORDER BY ipr.acronym ");

    Map<String, Object> programData;
    programData = new HashMap<String, Object>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      IPProgram ipProgram;
      while (rs.next()) {
        ipProgram = new IPProgram();
        ipProgram.setId(Integer.parseInt(rs.getString("id")));
        ipProgram.setAcronym(rs.getString("acronym"));
        ipProgram.setName(rs.getString("name"));
        programData.put(rs.getString("id"), ipProgram);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
    }
    return programData;
  }

  @Override
  public List<Map<String, String>> getProjectFocuses(int projectID, int typeID) {
    LOG.debug(">> getProjectFocuses projectID = {}, typeID ={} )", projectID, typeID);
    List<Map<String, String>> projectFocusesDataList = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT ipr.id as program_id, ipr.name as program_name, ipr.acronym as program_acronym, ");
    query.append("le.id as region_id, le.name as region_name, le.code as region_code, pf.active_since ");
    query.append("FROM project_focuses pf ");
    query.append("INNER JOIN ip_programs ipr ON ipr.id = pf.program_id ");
    query.append("LEFT JOIN loc_elements le   ON le.id = ipr.region_id ");
    query.append("WHERE pf.project_id = ");
    query.append(projectID);
    query.append(" AND ipr.type_id= ");
    query.append(typeID);
    query.append(" AND pf.is_active = 1 ");
    query.append(" ORDER BY ipr.name");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> projectFocusesData = new HashMap<String, String>();
        projectFocusesData.put("program_id", rs.getString("program_id"));
        projectFocusesData.put("program_name", rs.getString("program_name"));
        projectFocusesData.put("program_acronym", rs.getString("program_acronym"));
        projectFocusesData.put("region_id", rs.getString("region_id"));
        projectFocusesData.put("region_name", rs.getString("region_name"));
        projectFocusesData.put("region_code", rs.getString("region_code"));
        projectFocusesData.put("active_since", rs.getTimestamp("active_since").getTime() + "");

        projectFocusesDataList.add(projectFocusesData);
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the project focuses {} ", projectID, e);
    }

    return projectFocusesDataList;
  }

  @Override
  public boolean saveProjectFocuses(Map<String, Object> ipElementData) {
    LOG.debug(">> saveProjectFocuses(ipElementData={})", ipElementData);
    StringBuilder query = new StringBuilder();
    query.append("INSERT INTO project_focuses (project_id, program_id, ");
    query.append("created_by, modified_by, modification_justification)  ");
    query.append("VALUES (?, ?, ?, ?, ?) ");

    Object[] values = new Object[5];
    values[0] = ipElementData.get("project_id");
    values[1] = ipElementData.get("program_id");
    values[2] = ipElementData.get("user_id");
    values[3] = ipElementData.get("user_id");
    values[4] = ipElementData.get("justification");
    int result = databaseManager.saveData(query.toString(), values);
    LOG.debug("<< saveProjectFocuses():{}", result);
    return true;
  }


}
