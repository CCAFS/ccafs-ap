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

import org.cgiar.ccafs.ap.data.dao.HighLightDAO;
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
 * @author Christian Garcia
 */
public class MySQLHighLightDAO implements HighLightDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLHighLightDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLHighLightDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean deleteHighLight(int highLightId, int userID, String justification) {

    LOG.debug(">> deleteHighLight(id={})", highLightId);
    int result = -1;
    boolean saved = false;
    Object[] values;

    StringBuilder query = new StringBuilder();
    query.append("UPDATE project_highligths d SET d.is_active = 0, modified_by = ?, modification_justification = ? ");
    query.append("WHERE d.id = ? ");
    values = new Object[3];
    values[0] = userID;
    values[1] = justification;
    values[2] = highLightId;
    result = databaseManager.saveData(query.toString(), values);

    LOG.debug("<< deleteHighLight():{}", result);
    if (result != -1) {
      saved = true;
    }

    return saved;
  }


  @Override
  public boolean deleteHighLightsByProject(int projectID) {


    LOG.debug(">> deleteHighLightsByProject(projectID={})", projectID);
    int result = -1;
    boolean saved = false;
    Object[] values;

    StringBuilder query = new StringBuilder();
    query.append("UPDATE project_highligths d SET d.is_active = 0 ");
    query.append("WHERE d.project_id = ? ");
    values = new Object[1];
    values[0] = projectID;
    result = databaseManager.saveData(query.toString(), values);

    LOG.debug("<< deleteHighLightsByProject():{}", result);
    if (result != -1) {
      saved = true;
    }

    return saved;
  }

  @Override
  public boolean existHighLight(int highLightID) {
    LOG.debug(">> existHighLight highLightID = {} )", highLightID);
    StringBuilder query = new StringBuilder();
    query.append("SELECT COUNT(id) FROM project_highligths WHERE id = ");
    query.append(highLightID);
    boolean exists = false;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        if (rs.getInt(1) > 0) {
          exists = true;
        }
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the highLight id.", highLightID, e.getMessage());
    }
    return exists;
  }

  private List<Map<String, String>> getData(String query) {
    LOG.debug(">> executeQuery(query='{}')", query);
    List<Map<String, String>> highLightsList = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> highLightData = new HashMap<String, String>();

        highLightData.put("id", rs.getString("id"));
        highLightData.put("title", rs.getString("title"));
        highLightData.put("author", rs.getString("author"));
        highLightData.put("start_date", String.valueOf(rs.getTimestamp("start_date").getTime()));
        highLightData.put("end_date", String.valueOf(rs.getTimestamp("end_date").getTime()));
        highLightData.put("photo", rs.getString("photo"));
        highLightData.put("objectives", rs.getString("objectives"));
        highLightData.put("description", rs.getString("description"));
        highLightData.put("results", rs.getString("results"));
        highLightData.put("partners", rs.getString("partners"));
        highLightData.put("links", rs.getString("links"));
        highLightData.put("keywords", rs.getString("keywords"));
        highLightData.put("subject", rs.getString("subject"));
        highLightData.put("contributor", rs.getString("contributor"));
        highLightData.put("publisher", rs.getString("publisher"));
        highLightData.put("relation", rs.getString("relation"));
        highLightData.put("coverage", rs.getString("coverage"));
        highLightData.put("rights", rs.getString("rights"));
        highLightData.put("is_global", String.valueOf(rs.getBoolean("is_global")));
        highLightData.put("leader", rs.getString("leader"));
        highLightData.put("type", rs.getString("type"));
        highLightData.put("project_id", rs.getString("project_id"));
        highLightData.put("year", rs.getString("year"));
        highLightData.put("status", rs.getString("status"));

        highLightsList.add(highLightData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< executeQuery():highLightsList.size={}", highLightsList.size());
    return highLightsList;
  }


  @Override
  public Map<String, String> getHighLightById(int highLightID) {
    Map<String, String> highLightData = new HashMap<String, String>();
    LOG.debug(">> getHighLightById( activityID = {} )", highLightID);
    StringBuilder query = new StringBuilder();
    query.append("SELECT d.*   ");
    query.append("FROM project_highligths as d ");
    query.append("WHERE d.id =  ");
    query.append(highLightID);
    query.append(" AND d.is_active = 1");
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        highLightData.put("id", rs.getString("id"));
        highLightData.put("title", rs.getString("title"));
        highLightData.put("author", rs.getString("author"));
        highLightData.put("start_date", String.valueOf(rs.getTimestamp("start_date").getTime()));
        highLightData.put("end_date", String.valueOf(rs.getTimestamp("end_date").getTime()));
        highLightData.put("photo", rs.getString("photo"));
        highLightData.put("objectives", rs.getString("objectives"));
        highLightData.put("description", rs.getString("description"));
        highLightData.put("results", rs.getString("results"));
        highLightData.put("partners", rs.getString("partners"));
        highLightData.put("links", rs.getString("links"));
        highLightData.put("keywords", rs.getString("keywords"));
        highLightData.put("subject", rs.getString("subject"));
        highLightData.put("contributor", rs.getString("contributor"));
        highLightData.put("publisher", rs.getString("publisher"));
        highLightData.put("relation", rs.getString("relation"));
        highLightData.put("coverage", rs.getString("coverage"));
        highLightData.put("rights", rs.getString("rights"));
        highLightData.put("is_global", String.valueOf(rs.getBoolean("is_global")));
        highLightData.put("leader", rs.getString("leader"));
        highLightData.put("type", rs.getString("type"));
        highLightData.put("project_id", rs.getString("project_id"));
        highLightData.put("year", rs.getString("year"));
        highLightData.put("status", rs.getString("status"));
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the activity {}.", highLightID, e);
    }
    LOG.debug("-- getHighLightById() > Calling method executeQuery to get the results");
    return highLightData;
  }


  @Override
  public List<Map<String, String>> getHighLightsByProject(int projectID) {
    LOG.debug(">> getHighLightsByProject projectID = {} )", projectID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT d.*   ");
    query.append("FROM project_highligths as d ");
    query.append("INNER JOIN projects a ON d.project_id = a.id ");
    query.append("WHERE d.project_id =  ");
    query.append(projectID);
    query.append(" AND d.is_active = 1");

    LOG.debug("-- getHighLightsByProject() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }


  @Override
  public int saveHighLight(Map<String, Object> highLightData) {
    LOG.debug(">> saveHighLight(highLightData={})", highLightData);
    StringBuilder query = new StringBuilder();
    int result = -1;
    Object[] values;
    if (highLightData.get("id") == null) {
      // Insert new highLight record
      query.append(
        "INSERT INTO project_highligths (title,author,start_date,end_date,photo,objectives,description,results,partners,links,keywords,subject,contributor,publisher,relation,coverage,");

      query.append(
        "rights,is_global,leader,type,project_id,created_by,modified_by, modification_justification,year,status) ");
      query.append("VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
      values = new Object[26];
      values[0] = highLightData.get("title");
      values[1] = highLightData.get("author");
      values[2] = highLightData.get("start_date");
      values[3] = highLightData.get("end_date");
      values[4] = highLightData.get("photo");
      values[5] = highLightData.get("objectives");
      values[6] = highLightData.get("description");
      values[7] = highLightData.get("results");
      values[8] = highLightData.get("partners");
      values[9] = highLightData.get("links");
      values[10] = highLightData.get("keywords");
      values[11] = highLightData.get("subject");
      values[12] = highLightData.get("contributor");
      values[13] = highLightData.get("publisher");
      values[14] = highLightData.get("relation");
      values[15] = highLightData.get("coverage");
      values[16] = highLightData.get("rights");
      values[17] = highLightData.get("is_global");
      values[18] = highLightData.get("leader");
      values[19] = highLightData.get("type");
      values[20] = highLightData.get("project_id");
      values[21] = highLightData.get("created_by");
      values[22] = highLightData.get("modified_by");
      values[23] = highLightData.get("modification_justification");
      values[24] = highLightData.get("year");
      values[25] = highLightData.get("status");

    } else {
      // Updating existing highLight record
      query.append(
        "UPDATE project_highligths SET title=?,author=?,start_date=?,end_date=?,photo=?,objectives=?,description=?,results=?,partners=?,links=?,keywords=?,subject=?,contributor=?,publisher=?,relation=?,coverage=?,rights=?,is_global=?,leader=?,type=?,project_id=?,modified_by=?, modification_justification=?,year=?,status=? ");
      query.append("WHERE id = ? ");
      values = new Object[26];
      values[0] = highLightData.get("title");
      values[1] = highLightData.get("author");
      values[2] = highLightData.get("start_date");
      values[3] = highLightData.get("end_date");
      values[4] = highLightData.get("photo");
      values[5] = highLightData.get("objectives");
      values[6] = highLightData.get("description");
      values[7] = highLightData.get("results");
      values[8] = highLightData.get("partners");
      values[9] = highLightData.get("links");
      values[10] = highLightData.get("keywords");
      values[11] = highLightData.get("subject");
      values[12] = highLightData.get("contributor");
      values[13] = highLightData.get("publisher");
      values[14] = highLightData.get("relation");
      values[15] = highLightData.get("coverage");
      values[16] = highLightData.get("rights");
      values[17] = highLightData.get("is_global");
      values[18] = highLightData.get("leader");
      values[19] = highLightData.get("type");
      values[20] = highLightData.get("project_id");

      values[21] = highLightData.get("modified_by");
      values[22] = highLightData.get("modification_justification");
      values[23] = highLightData.get("year");
      values[24] = highLightData.get("status");
      values[25] = highLightData.get("id");
    }
    result = databaseManager.saveData(query.toString(), values);

    LOG.debug("<< saveHighLight():{}", result);
    return result;
  }


}
