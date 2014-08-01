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
  public Map<String, String> getIPProgramById(int ipProgramID) {
    Map<String, String> ipProgramData = new HashMap<>();
    LOG.debug(">> getIPProgramById( programID = {} )", ipProgramID);
    StringBuilder query = new StringBuilder();
    query.append("SELECT ipr.*  ");
    query.append("FROM ip_programs as ipr  ");
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
    query.append("SELECT ipr.* ");
    query.append("FROM ip_programs as ipr ");
    query.append("INNER JOIN projects p ON ipr.id=p.program_creator_id ");
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
    query.append("SELECT ipr.* ");
    query.append("FROM ip_programs as ipr ");
    query.append(" WHERE ipr.type_id= ");
    query.append(typeId);
    query.append(" ORDER BY ipr.acronym ");


    LOG.debug("-- getProgramsByType() > Calling method executeQuery to get the results");
    return getData(query.toString());
  }


  @Override
  public List<Map<String, String>> getProgramType(int projectId, int typeProgramId) {
    LOG.debug(">> getProgramType( programID = {} )", projectId, typeProgramId);
    StringBuilder query = new StringBuilder();
    query.append("SELECT ipr.type_id, ipr.acronym   ");
    query.append("FROM ip_programs as ipr  ");
    query.append("INNER JOIN project_focuses pf  ON pf.program_id=ipr.id  ");
    query.append("WHERE pf.project_id='1' ");
    query.append("ORDER BY ipr.type_id, ipr.acronym ");

    LOG.debug("-- getProgramType() > Calling method executeQuery to get the results");
    return getData(query.toString());
  }

}
