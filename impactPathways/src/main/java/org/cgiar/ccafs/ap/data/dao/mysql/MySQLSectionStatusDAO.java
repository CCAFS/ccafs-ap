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
 *****************************************************************/

package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.SectionStatusDAO;
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
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class MySQLSectionStatusDAO implements SectionStatusDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLSectionStatusDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLSectionStatusDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public Map<String, String> getDeliverableSectionStatus(int deliverableID, String cycle, String section) {
    LOG.debug(">> getDeliverableSectionStatus deliverableID = {}, cycle = {} and section = {})",
      new Object[] {deliverableID, cycle, section});

    StringBuilder query = new StringBuilder();
    query.append("SELECT * ");
    query.append("FROM section_statuses ");
    query.append("WHERE deliverable_id = ");
    query.append(deliverableID);
    query.append(" AND cycle = '");
    query.append(cycle);
    query.append("' AND section_name = '");
    query.append(section);
    query.append("'");

    LOG.debug(">> getDeliverableSectionStatus() > Calling method executeQuery to get the results");

    Map<String, String> statusData = new HashMap<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        statusData.put("id", rs.getString("id"));
        statusData.put("project_id", rs.getString("project_id"));
        statusData.put("deliverable_id", rs.getString("deliverable_id"));
        statusData.put("cycle", rs.getString("cycle"));
        statusData.put("section_name", rs.getString("section_name"));
        statusData.put("missing_fields", rs.getString("missing_fields"));
      }
      rs.close();
      rs = null; // For the garbage collector to find it easily.
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;
      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< getDeliverableSectionStatus() > Calling method executeQuery to get the results");
    return statusData;
  }

  @Override
  public Map<String, String> getProjectSectionStatus(int projectID, String cycle, String section) {
    LOG.debug(">> getProjectSectionStatus projectID = {}, cycle = {} and section = {})",
      new Object[] {projectID, cycle, section});

    StringBuilder query = new StringBuilder();
    query.append("SELECT * ");
    query.append("FROM section_statuses ");
    query.append("WHERE project_id = ");
    query.append(projectID);
    query.append(" AND cycle = '");
    query.append(cycle);
    query.append("' AND section_name = '");
    query.append(section);
    query.append("'");

    LOG.debug(">> getProjectSectionStatus() > Calling method executeQuery to get the results");

    Map<String, String> statusData = new HashMap<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        statusData.put("id", rs.getString("id"));
        statusData.put("project_id", rs.getString("project_id"));
        statusData.put("cycle", rs.getString("cycle"));
        statusData.put("section_name", rs.getString("section_name"));
        statusData.put("missing_fields", rs.getString("missing_fields"));
      }
      rs.close();
      rs = null; // For the garbage collector to find it easily.
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;
      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< getProjectSectionStatus() > Calling method executeQuery to get the results");
    return statusData;
  }

  @Override
  public List<Map<String, String>> getProjectSectionStatuses(int projectID, String cycle) {
    LOG.debug(">> getProjectSectionStatuses projectID = {} and cycle = {} )", new Object[] {projectID, cycle});

    StringBuilder query = new StringBuilder();
    query.append("SELECT ss.* ");
    query.append("FROM section_statuses ss ");
    query.append("LEFT JOIN deliverables d ON d.id = ss.deliverable_id ");
    query.append("WHERE ss.project_id = ");
    query.append(projectID);
    query.append(" AND cycle = '");
    query.append(cycle);
    query.append("' AND (d.is_active IS NULL OR d.is_active = 1)");

    LOG.debug(">> getProjectSectionStatuses() > Calling method executeQuery to get the results");
    List<Map<String, String>> statusDataList = new ArrayList<>();
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> statusData = new HashMap<>();
        statusData.put("id", rs.getString("id"));
        statusData.put("project_id", rs.getString("project_id"));
        statusData.put("cycle", rs.getString("cycle"));
        statusData.put("section_name", rs.getString("section_name"));
        statusData.put("missing_fields", rs.getString("missing_fields"));
        statusDataList.add(statusData);
      }
      rs.close();
      rs = null; // For the garbage collector to find it easily.
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;
      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< getProjectSectionStatuses() > Calling method executeQuery to get the results");
    return statusDataList;
  }

  @Override
  public int saveDeliverableSectionStatus(Map<String, Object> statusData) {
    LOG.debug(">> saveDeliverableSectionStatus(statusData={})", new Object[] {statusData});
    StringBuilder query = new StringBuilder();
    Object[] values;
    int result = -1;
    if (statusData.get("id") == null) {
      query.append("INSERT INTO section_statuses (project_id, deliverable_id, cycle, section_name, missing_fields) ");
      query.append("VALUES (?, ?, ?, ?, ?) ");
      values = new Object[5];
      values[0] = statusData.get("project_id");
      values[1] = statusData.get("deliverable_id");
      values[2] = statusData.get("cycle");
      values[3] = statusData.get("section_name");
      values[4] = statusData.get("missing_fields");
      result = databaseManager.saveData(query.toString(), values);
      if (result <= 0) {
        LOG.error("A problem happened trying to add a new section_status for the project_id={}",
          statusData.get("project_id"));
      }
    } else {
      // Updating submission record.
      query.append(
        "UPDATE section_statuses SET project_id = ?, deliverable_id = ?, cycle = ?, section_name = ?, missing_fields = ? ");
      query.append("WHERE id = ? ");
      values = new Object[6];
      values[0] = statusData.get("project_id");
      values[1] = statusData.get("delivearable_id");
      values[2] = statusData.get("cycle");
      values[3] = statusData.get("section_name");
      values[4] = statusData.get("missing_fields");
      values[5] = statusData.get("id");
      result = databaseManager.saveData(query.toString(), values);
      if (result == -1) {
        LOG.error("A problem happened trying to update the section_status identified with the id = {}",
          statusData.get("id"));
      }
    }
    return result;
  }

  @Override
  public int saveProjectSectionStatus(Map<String, Object> statusData) {
    LOG.debug(">> saveSectionStatus(statusData={})", new Object[] {statusData});
    StringBuilder query = new StringBuilder();
    Object[] values;
    int result = -1;
    if (statusData.get("id") == null) {
      query.append("INSERT INTO section_statuses (project_id, cycle, section_name, missing_fields) ");
      query.append("VALUES (?, ?, ?, ?) ");
      values = new Object[4];
      values[0] = statusData.get("project_id");;
      values[1] = statusData.get("cycle");
      values[2] = statusData.get("section_name");
      values[3] = statusData.get("missing_fields");
      result = databaseManager.saveData(query.toString(), values);
      if (result <= 0) {
        LOG.error("A problem happened trying to add a new section_status for the project_id={}",
          statusData.get("project_id"));
      }
    } else {
      // Updating submission record.
      query.append("UPDATE section_statuses SET project_id = ?, cycle = ?, section_name = ?, missing_fields = ? ");
      query.append("WHERE id = ? ");
      values = new Object[5];
      values[0] = statusData.get("project_id");
      values[1] = statusData.get("cycle");
      values[2] = statusData.get("section_name");
      values[3] = statusData.get("missing_fields");
      values[4] = statusData.get("id");
      result = databaseManager.saveData(query.toString(), values);
      if (result == -1) {
        LOG.error("A problem happened trying to update the section_status identified with the id = {}",
          statusData.get("id"));
      }
    }
    return result;
  }

}
