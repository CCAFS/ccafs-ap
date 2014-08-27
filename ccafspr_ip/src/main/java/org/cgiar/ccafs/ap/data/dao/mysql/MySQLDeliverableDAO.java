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
import org.cgiar.ccafs.ap.data.dao.DeliverableDAO;

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
  public boolean deleteDeliverable(int deliverableId) {
    LOG.debug(">> deleteDeliverable(id={})", deliverableId);

    String query = "DELETE FROM deliverables WHERE id= ?";
    int rowsDeleted = databaseManager.delete(query, new Object[] {deliverableId});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteDeliverable():{}", true);
      return true;
    }
    LOG.debug("<< deleteDeliverable:{}", false);
    return false;
  }

  @Override
  public boolean deleteDeliverablesByActivity(int projectID) {
    LOG.debug(">> deleteDeliverablesByActivity(projectId={})", projectID);

    StringBuilder query = new StringBuilder();
    query.append("DELETE d FROM deliverables d ");
    query.append("WHERE d.activity_id = ? ");

    int rowsDeleted = databaseManager.delete(query.toString(), new Object[] {projectID});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteDeliverablesByActivity():{}", true);
      return true;
    }
    LOG.debug("<< deleteDeliverablesByActivity():{}", false);
    return false;
  }

  private List<Map<String, String>> getData(String query) {
    LOG.debug(">> executeQuery(query='{}')", query);
    List<Map<String, String>> deliverablesList = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> deliverableData = new HashMap<String, String>();
        deliverableData.put("id", rs.getString("id"));
        deliverableData.put("activity_id", rs.getString("activity_id"));
        deliverableData.put("title", rs.getString("title"));
        deliverableData.put("type_id", rs.getString("type_id"));
        deliverableData.put("year", rs.getString("year"));

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
    query.append("WHERE d.id=  ");
    query.append(deliverableID);
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        deliverableData.put("id", rs.getString("id"));
        deliverableData.put("activity_id", rs.getString("activity_id"));
        deliverableData.put("title", rs.getString("title"));
        deliverableData.put("type_id", rs.getString("type_id"));
        deliverableData.put("year", rs.getString("year"));

      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the activity {}.", deliverableID, e);
    }
    LOG.debug("-- getDeliverableById() > Calling method executeQuery to get the results");
    return deliverableData;
  }


  @Override
  public List<Map<String, String>> getDeliverableContributions(int deliverableID) {
    List<Map<String, String>> deliverableContributionList = new ArrayList<>();
    LOG.debug(">> getDeliverableContributions deliverableID = {} )", deliverableID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT ipe.id,ipe.description ");
    query.append("FROM ip_deliverable_contributions ipd ");
    query.append("INNER JOIN deliverables d ON ipd.deliverable_id=d.id ");
    query.append("INNER JOIN ip_activity_contributions ipac ON ipd.activity_contribution_id=ipac.id ");
    query.append("INNER JOIN ip_elements ipe ON ipac.ip_element_id=ipe.id ");
    query.append("WHERE ipd.deliverable_id= ");
    query.append(deliverableID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> activityContributionData = new HashMap<String, String>();
        activityContributionData.put("id", rs.getString("id"));
        activityContributionData.put("description", rs.getString("description"));

        deliverableContributionList.add(activityContributionData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
    }

    LOG.debug("<< getDeliverableContributions():deliverableContributionList.size={}",
      deliverableContributionList.size());
    return deliverableContributionList;
  }

  @Override
  public List<Map<String, String>> getDeliverablesByActivity(int activityID) {
    LOG.debug(">> getDeliverablesByActivity activityID = {} )", activityID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT d.*   ");
    query.append("FROM deliverables as d ");
    query.append("INNER JOIN activities a ON d.activity_id = a.id ");
    query.append("WHERE d.activity_id=  ");
    query.append(activityID);

    LOG.debug("-- getDeliverablesByActivity() > Calling method executeQuery to get the results");
    return getData(query.toString());
  }

  @Override
  public int saveDeliverable(int activityID, Map<String, Object> deliverableData) {
    LOG.debug(">> saveDeliverable(deliverableData={})", deliverableData);
    StringBuilder query = new StringBuilder();
    int result = -1;
    Object[] values;
    if (deliverableData.get("id") == null) {
      // Insert new deliverable record
      query.append("INSERT INTO deliverables (activity_id, title, type_id, year) ");
      query.append("VALUES (?,?,?,?) ");
      values = new Object[4];
      values[0] = activityID;
      values[1] = deliverableData.get("title");
      values[2] = deliverableData.get("type_id");
      values[3] = deliverableData.get("year");
      result = databaseManager.saveData(query.toString(), values);

    } else {
      // update deliverable record
      query.append("UPDATE deliverables SET activity_id = ?, title = ?, type_id = ?, year = ? ");
      query.append("WHERE id = ? ");
      values = new Object[5];
      values[0] = activityID;
      values[1] = deliverableData.get("title");
      values[2] = deliverableData.get("type_id");
      values[3] = deliverableData.get("year");
      values[4] = deliverableData.get("id");
      result = databaseManager.saveData(query.toString(), values);
      if (result == -1) {
        LOG.error("A problem happened trying to update the deliverable identified with the id = {}",
          deliverableData.get("id"));
        return -1;
      }
    }
    LOG.debug("<< saveDeliverable():{}", result);
    return result;
  }
}
