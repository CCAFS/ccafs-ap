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

import org.cgiar.ccafs.ap.data.dao.DeliverableDAO;
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
 * @author Javier Andrés Gallego B.
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 * @author Hernán David Carvajal - CIAT/CCAFS
 */
public class MySQLDeliverableDAO implements DeliverableDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLDeliverableDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLDeliverableDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean deleteDeliverable(int deliverableId, int userID, String justification) {

    LOG.debug(">> deleteDeliverable(id={})", deliverableId);
    int result = -1;
    boolean saved = false;
    Object[] values;

    StringBuilder query = new StringBuilder();
    query.append("UPDATE deliverables d SET d.is_active = 0, modified_by = ?, modification_justification = ? ");
    query.append("WHERE d.id = ? ");
    values = new Object[3];
    values[0] = userID;
    values[1] = justification;
    values[2] = deliverableId;
    result = databaseManager.saveData(query.toString(), values);

    LOG.debug("<< deleteDeliverable():{}", result);
    if (result != -1) {
      saved = true;
    }

    return saved;
  }

  @Override
  public boolean deleteDeliverableOutput(int deliverableID) {
    LOG.debug(">> deleteDeliverableOutput(deliverableID={})", deliverableID);
    int result = -1;
    boolean saved = false;
    Object[] values;

    StringBuilder query = new StringBuilder();
    query.append("UPDATE ip_deliverable_contributions SET is_active = 0 ");
    query.append("WHERE deliverable_id = ? ");
    values = new Object[1];
    values[0] = deliverableID;
    result = databaseManager.saveData(query.toString(), values);

    LOG.debug("<< deleteDeliverableOutput():{}", result);
    if (result != -1) {
      saved = true;
    }

    return saved;
  }

  @Override
  public boolean deleteDeliverablesByProject(int projectID) {


    LOG.debug(">> deleteDeliverablesByProject(projectID={})", projectID);
    int result = -1;
    boolean saved = false;
    Object[] values;

    StringBuilder query = new StringBuilder();
    query.append("UPDATE deliverables d SET d.is_active = 0 ");
    query.append("WHERE d.project_id = ? ");
    values = new Object[1];
    values[0] = projectID;
    result = databaseManager.saveData(query.toString(), values);

    LOG.debug("<< deleteDeliverablesByProject():{}", result);
    if (result != -1) {
      saved = true;
    }

    return saved;
  }

  @Override
  public boolean existDeliverable(int deliverableID) {
    LOG.debug(">> existDeliverable deliverableID = {} )", deliverableID);
    StringBuilder query = new StringBuilder();
    query.append("SELECT COUNT(id) FROM deliverables WHERE id = ");
    query.append(deliverableID);
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
      LOG.error("Exception arised getting the deliverable id.", deliverableID, e.getMessage());
    }
    return exists;
  }

  private List<Map<String, String>> getData(String query) {
    LOG.debug(">> executeQuery(query='{}')", query);
    List<Map<String, String>> deliverablesList = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> deliverableData = new HashMap<String, String>();
        deliverableData.put("id", rs.getString("id"));
        deliverableData.put("title", rs.getString("title"));
        deliverableData.put("type_id", rs.getString("type_id"));
        deliverableData.put("type_other", rs.getString("type_other"));
        deliverableData.put("year", rs.getString("year"));
        deliverableData.put("active_since", String.valueOf(rs.getTimestamp("active_since").getTime()));

        deliverablesList.add(deliverableData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< executeQuery():deliverablesList.size={}", deliverablesList.size());
    return deliverablesList;
  }


  @Override
  public Map<String, String> getDeliverableById(int deliverableID) {
    Map<String, String> deliverableData = new HashMap<String, String>();
    LOG.debug(">> getDeliverableById( activityID = {} )", deliverableID);
    StringBuilder query = new StringBuilder();
    query.append("SELECT d.*   ");
    query.append("FROM deliverables as d ");
    query.append("WHERE d.id =  ");
    query.append(deliverableID);
    query.append(" AND d.is_active = 1");
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        deliverableData.put("id", rs.getString("id"));
        deliverableData.put("project_id", rs.getString("project_id"));
        deliverableData.put("title", rs.getString("title"));
        deliverableData.put("type_id", rs.getString("type_id"));
        deliverableData.put("type_other", rs.getString("type_other"));
        deliverableData.put("year", rs.getString("year"));
        deliverableData.put("active_since", String.valueOf(rs.getTimestamp("active_since").getTime()));
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the activity {}.", deliverableID, e);
    }
    LOG.debug("-- getDeliverableById() > Calling method executeQuery to get the results");
    return deliverableData;
  }

  @Override
  public Map<String, String> getDeliverableOutput(int deliverableID) {
    Map<String, String> deliverableContributionData = new HashMap<String, String>();
    LOG.debug(">> getDeliverableOutput deliverableID = {} )", deliverableID);

    StringBuilder query = new StringBuilder();

    query.append("SELECT ipe.id,ipe.description, ipe.ip_program_id, ipt.id as element_type_id, "
      + "ipt.name as element_type_name ");
    query.append("FROM ip_deliverable_contributions ipd ");
    query.append("INNER JOIN deliverables d ON ipd.deliverable_id=d.id ");
    query.append("INNER JOIN ip_project_contributions ipac ON ipd.project_contribution_id=ipac.id ");
    query.append("INNER JOIN ip_elements ipe ON ipac.mog_id=ipe.id ");
    query.append("INNER JOIN ip_element_types ipt ON ipe.element_type_id = ipt.id ");
    query.append("WHERE ipd.deliverable_id= ");
    query.append(deliverableID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        deliverableContributionData.put("id", rs.getString("id"));
        deliverableContributionData.put("description", rs.getString("description"));
        deliverableContributionData.put("ip_program_id", rs.getString("ip_program_id"));
        deliverableContributionData.put("element_type_id", rs.getString("element_type_id"));
        deliverableContributionData.put("element_type_name", rs.getString("element_type_name"));
      }

      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;
      LOG.error(exceptionMessage, e);
    }
    LOG.debug("<< getDeliverableOutput()");
    return deliverableContributionData;
  }

  @Override
  public List<Map<String, String>> getDeliverablesByProject(int projectID) {
    LOG.debug(">> getDeliverablesByProject projectID = {} )", projectID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT d.*   ");
    query.append("FROM deliverables as d ");
    query.append("INNER JOIN projects a ON d.project_id = a.id ");
    query.append("WHERE d.project_id =  ");
    query.append(projectID);
    query.append(" AND d.is_active = 1");

    LOG.debug("-- getDeliverablesByProject() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getDeliverablesByProjectPartnerID(int projectPartnerID) {
    LOG.debug(">> getDeliverablesByProjectPartnerID projectPartnerID = {} )", projectPartnerID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT d.* ");
    query.append("FROM deliverables d ");
    query.append("INNER JOIN deliverable_partnerships dp ON dp.deliverable_id = d.id ");
    query.append("WHERE dp.partner_id = ");
    query.append(projectPartnerID);
    query.append(" AND d.is_active = 1 AND dp.is_active = 1");

    LOG.debug("-- getDeliverablesByProjectPartnerID() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getProjectDeliverablesLedByUser(int projectID, int userID) {
    List<Map<String, String>> deliverables = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT d.* FROM deliverables d ");
    query.append("INNER JOIN deliverable_partnerships dp ON d.id = dp.deliverable_id ");
    query.append("INNER JOIN project_partner_persons pp ON dp.partner_person_id = pp.id ");
    query.append("WHERE d.project_id = ");
    query.append(projectID);
    query.append(" AND pp.user_id = ");
    query.append(userID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      Map<String, String> deliverableData;
      while (rs.next()) {
        deliverableData = new HashMap<>();
        deliverableData.put("id", rs.getString("id"));
        deliverableData.put("title", rs.getString("title"));

        deliverables.add(deliverableData);
      }

    } catch (SQLException e) {
      LOG.error("getProjectDeliverablesLedByUser() > Exception raised trying to get the deliverables led by user {} "
        + " whitin the project {}", new Object[] {userID, projectID, e});
    }

    return deliverables;
  }

  @Override
  public int saveDeliverable(Map<String, Object> deliverableData) {
    LOG.debug(">> saveDeliverable(deliverableData={})", deliverableData);
    StringBuilder query = new StringBuilder();
    int result = -1;
    Object[] values;
    if (deliverableData.get("id") == null) {
      // Insert new deliverable record
      query.append("INSERT INTO deliverables (id, project_id,  title, type_id, type_other, year, created_by, ");
      query.append("modified_by, modification_justification) ");
      query.append("VALUES (?,?,?,?,?,?,?,?,?) ");
      values = new Object[9];
      values[0] = deliverableData.get("id");
      values[1] = deliverableData.get("project_id");;
      values[2] = deliverableData.get("title");
      values[3] = deliverableData.get("type_id");
      values[4] = deliverableData.get("type_other");
      values[5] = deliverableData.get("year");
      // Logs
      values[6] = deliverableData.get("created_by");
      values[7] = deliverableData.get("modified_by");
      values[8] = deliverableData.get("modification_justification");
    } else {
      // Updating existing deliverable record
      query.append(
        "UPDATE deliverables SET title = ?, type_id = ?, type_other = ?, year = ?, modified_by = ?, modification_justification = ? ");
      query.append("WHERE id = ? ");
      values = new Object[7];
      values[0] = deliverableData.get("title");
      values[1] = deliverableData.get("type_id");
      values[2] = deliverableData.get("type_other");
      values[3] = deliverableData.get("year");
      values[4] = deliverableData.get("modified_by");
      values[5] = deliverableData.get("modification_justification");
      values[6] = deliverableData.get("id");
    }
    result = databaseManager.saveData(query.toString(), values);

    LOG.debug("<< saveDeliverable():{}", result);
    return result;
  }

  @Override
  public boolean saveDeliverableOutput(int deliverableID, int outputID, int userID, String justification) {
    LOG.debug(">> saveDeliverableOutput(deliverableData={})", new Object[] {deliverableID, outputID});
    StringBuilder query = new StringBuilder();
    int result = -1;
    int deliverableContributionID = -1;
    boolean saved = false;
    Object[] values;

    query.append("SELECT id FROM ip_deliverable_contributions where deliverable_id = ");
    query.append(deliverableID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        deliverableContributionID = rs.getInt(1);
      }
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;
      LOG.error(exceptionMessage, e);
    }


    /**
     * This query relates the deliverable with each project impact pathways which contains
     * the MOG selected
     */
    query.setLength(0);
    if (deliverableContributionID == -1) {
      query.append("INSERT INTO ip_deliverable_contributions (deliverable_id, project_contribution_id, created_by,");
      query.append(" modified_by, modification_justification ) ");
      query.append("SELECT ?, id, ?, ?, ? ");
      query.append("FROM ip_project_contributions ");
      query.append("WHERE project_id = (select d.project_id from deliverables d where d.id = " + deliverableID + ") ");
      query.append("AND mog_id = ? ");
      query.append("ON DUPLICATE KEY UPDATE project_contribution_id = VALUES(project_contribution_id), ");
      query.append("deliverable_id = VALUES(deliverable_id), modified_by = VALUES(modified_by), ");
      query.append("modification_justification = VALUES(modification_justification) ");
      values = new Object[5];
      values[0] = deliverableID;
      values[1] = userID;
      values[2] = userID;
      values[3] = justification;
      values[4] = outputID;
      result = databaseManager.saveData(query.toString(), values);
    } else {
      query
        .append("INSERT INTO ip_deliverable_contributions (id, deliverable_id, project_contribution_id, created_by,");
      query.append(" modified_by, modification_justification ) ");
      query.append("SELECT ?, ?, id, ?, ?, ? ");
      query.append("FROM ip_project_contributions ");
      query.append("WHERE project_id = (select d.project_id from deliverables d where d.id = " + deliverableID + ") ");
      query.append("AND mog_id = ? ");
      query.append("ON DUPLICATE KEY UPDATE project_contribution_id = VALUES(project_contribution_id), ");
      query.append("deliverable_id = VALUES(deliverable_id), modified_by = VALUES(modified_by), ");
      query.append("modification_justification = VALUES(modification_justification) ");
      values = new Object[6];
      values[0] = deliverableContributionID;
      values[1] = deliverableID;
      values[2] = userID;
      values[3] = userID;
      values[4] = justification;
      values[5] = outputID;
      result = databaseManager.saveData(query.toString(), values);
    }

    LOG.debug("<< saveDeliverableOutput():{}", result);
    if (result != -1) {
      saved = true;
    }
    return saved;

  }
}
