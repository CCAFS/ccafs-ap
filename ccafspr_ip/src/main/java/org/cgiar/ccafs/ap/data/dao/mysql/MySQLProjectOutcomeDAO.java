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

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.ProjectOutcomeDAO;

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
 * @author Hernán David Carvajal.
 */
public class MySQLProjectOutcomeDAO implements ProjectOutcomeDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLProjectOutcomeDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLProjectOutcomeDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean deleteProjectOutcomeById(int projectOutcomeID) {
    LOG.debug(">> deleteProjectOutcomeById(id={})", projectOutcomeID);

    String query = "DELETE po FROM project_outcomes as po WHERE po.id= ?";

    int rowsDeleted = databaseManager.delete(query, new Object[] {projectOutcomeID});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteProjectOutcomeById():{}", true);
      return true;
    }

    LOG.debug("<< deleteProjectOutcomeById:{}", false);
    return false;
  }

  @Override
  public boolean deleteProjectOutcomesByProject(int projectID) {
    LOG.debug(">> deleteProjectOutcomesByProject(projectId={})", projectID);

    StringBuilder query = new StringBuilder();
    query.append("DELETE po FROM project_outcomes as po  ");
    query.append("WHERE po.project_id = ? ");

    int rowsDeleted = databaseManager.delete(query.toString(), new Object[] {projectID});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteProjectOutcomesByProject():{}", true);
      return true;
    }
    LOG.debug("<< deleteProjectOutcomesByProject():{}", false);
    return false;
  }

  private List<Map<String, String>> getData(String query) {
    LOG.debug(">> executeQuery(query='{}')", query);
    List<Map<String, String>> projectOutcomeList = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> projectOutcomeData = new HashMap<String, String>();
        projectOutcomeData.put("id", rs.getString("id"));
        projectOutcomeData.put("year", rs.getString("year"));
        projectOutcomeData.put("statement", rs.getString("statement"));
        projectOutcomeData.put("stories", rs.getString("stories"));
        projectOutcomeData.put("project_id", rs.getString("project_id"));

        projectOutcomeList.add(projectOutcomeData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< executeQuery():projectPartnerList.size={}", projectOutcomeList.size());
    return projectOutcomeList;
  }

  @Override
  public List<Map<String, String>> getProjectOutcomesByProject(int projectID) {
    LOG.debug(">> getProjectOutcomesByProject projectID = {} )", projectID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT po.*  ");
    query.append("FROM project_outcomes po ");
    query.append("WHERE po.project_id= ");
    query.append(projectID);
    query.append(" ORDER BY po.year ");


    LOG.debug("-- getProjectOutcomesByProject() > Calling method executeQuery to get the results");
    return getData(query.toString());
  }


  @Override
  public Map<String, String> getProjectOutcomesByYear(int projectID, int year) {
    LOG.debug(">> getProjectOutcomesByYear projectID = {}, year={} )", new Object[] {projectID, year});
    Map<String, String> projectOutcomeData = new HashMap<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT po.*   ");
    query.append("FROM project_outcomes po  ");
    query.append("WHERE po.project_id=  ");
    query.append(projectID);
    query.append(" AND po.year=  ");
    query.append(year);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        projectOutcomeData.put("id", rs.getString("id"));
        projectOutcomeData.put("year", rs.getString("year"));
        projectOutcomeData.put("statement", rs.getString("statement"));
        projectOutcomeData.put("stories", rs.getString("stories"));
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the Project Outcomes for project {}.", projectID, e);
    }
    LOG.debug("-- getProjectOutcomesByYear() > Calling method executeQuery to get the results");
    return projectOutcomeData;
  }


  @Override
  public int saveProjectOutcome(int projectID, Map<String, Object> projectOutcomeData) {
    LOG.debug(">> saveProjectOutcome(projectOutcomeData={})", projectOutcomeData);
    StringBuilder query = new StringBuilder();
    int result = -1;
    Object[] values;
    if (projectOutcomeData.get("id") == null) {
      // Insert new projectOutcome record
      query.append("INSERT INTO project_outcomes (year, statement, stories, project_id) ");
      query.append("VALUES (?,?,?,?) ");
      values = new Object[4];
      values[0] = projectOutcomeData.get("year");
      values[1] = projectOutcomeData.get("statement");
      values[2] = projectOutcomeData.get("stories");
      values[3] = projectID;
      result = databaseManager.saveData(query.toString(), values);
    } else {
      // update projectOutcome record
      query.append("UPDATE project_outcomes SET year = ?, statement = ?, stories = ?, project_id = ? ");
      query.append("WHERE id = ? ");
      values = new Object[5];
      values[0] = projectOutcomeData.get("year");
      values[1] = projectOutcomeData.get("statement");
      values[2] = projectOutcomeData.get("stories");
      values[3] = projectID;
      values[4] = projectOutcomeData.get("id");
      result = databaseManager.saveData(query.toString(), values);
      if (result == -1) {
        LOG.error("A problem happened trying to update a project Outcome identified with the id = {}",
          projectOutcomeData.get("id"));
        return -1;
      }
    }
    LOG.debug("<< saveProjectOutcome():{}", result);
    return result;
  }
}
