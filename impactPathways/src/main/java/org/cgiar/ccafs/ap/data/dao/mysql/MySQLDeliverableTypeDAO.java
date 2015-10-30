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

import org.cgiar.ccafs.ap.data.dao.DeliverableTypeDAO;
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
 * @author Carlos Alberto Martínez M.
 */
public class MySQLDeliverableTypeDAO implements DeliverableTypeDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLDeliverableTypeDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLDeliverableTypeDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean deleteDeliverableType(int deliverableTypeId) {
    LOG.debug(">> deleteDeliverableType(id={})", deliverableTypeId);

    String query = "DELETE FROM deliverable_types WHERE id= ?";
    int rowsDeleted = databaseManager.delete(query, new Object[] {deliverableTypeId});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteDeliverableType():{}", true);
      return true;
    }
    LOG.debug("<< deleteDeliverableType:{}", false);
    return false;
  }

  private List<Map<String, String>> getData(String query) {
    LOG.debug(">> executeQuery(query='{}')", query);
    List<Map<String, String>> deliverableTypesList = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> deliverableTypeData = new HashMap<String, String>();
        deliverableTypeData.put("id", rs.getString("id"));
        deliverableTypeData.put("name", rs.getString("name"));
        deliverableTypeData.put("parent_id", rs.getString("parent_id"));
        deliverableTypeData.put("description", rs.getString("description"));
        deliverableTypeData.put("timeline", rs.getString("timeline"));

        deliverableTypesList.add(deliverableTypeData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< executeQuery():deliverableTypesList.size={}", deliverableTypesList.size());
    return deliverableTypesList;
  }

  @Override
  public List<Map<String, String>> getDeliverableSubTypes() {
    LOG.debug(">> getDeliverableSubTypes )");

    StringBuilder query = new StringBuilder();
    query.append("SELECT dt.*   ");
    query.append("FROM deliverable_types as dt ");
    query.append("WHERE dt.parent_id IS NOT NULL AND dt.timeline IS NOT NULL ");

    LOG.debug("-- getDeliverableSubTypes() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }


  @Override
  public Map<String, String> getDeliverableTypeById(int deliverableTypeID) {
    Map<String, String> deliverableTypeData = new HashMap<String, String>();
    LOG.debug(">> getDeliverableTypeById( activityID = {} )", deliverableTypeID);
    StringBuilder query = new StringBuilder();
    query.append("SELECT d.*   ");
    query.append("FROM deliverable_types as d ");
    query.append("WHERE d.id=  ");
    query.append(deliverableTypeID);
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        deliverableTypeData.put("id", rs.getString("id"));
        deliverableTypeData.put("name", rs.getString("name"));
        deliverableTypeData.put("parent_id", rs.getString("parent_id"));
        deliverableTypeData.put("description", rs.getString("description"));
        deliverableTypeData.put("timeline", rs.getString("timeline"));
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the activity {}.", deliverableTypeID, e);
    }
    LOG.debug("-- getDeliverableTypeById() > Calling method executeQuery to get the results");
    return deliverableTypeData;
  }

  @Override
  public List<Map<String, String>> getDeliverableTypes() {
    LOG.debug(">> getDeliverableTypes activityID = {} )");

    StringBuilder query = new StringBuilder();
    query.append("SELECT dt.*   ");
    query.append("FROM deliverable_types as dt ");
    query.append("WHERE dt.parent_id IS NULL");

    LOG.debug("-- getDeliverableTypes() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getDeliverableTypes(int typeID) {
    LOG.debug(">> getDeliverableTypes typeID = {})", typeID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT dt.*   ");
    query.append("FROM deliverable_types as dt ");
    query.append("WHERE dt.parent_id= ");
    query.append(typeID);

    LOG.debug("-- getDeliverableTypes() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public int saveDeliverableType(Map<String, Object> deliverableTypeData) {
    LOG.debug(">> saveDeliverableType(deliverableData={})", deliverableTypeData);
    StringBuilder query = new StringBuilder();
    int result = -1;
    Object[] values;
    if (deliverableTypeData.get("id") == null) {
      // Insert new deliverable type record
      query.append("INSERT INTO deliverable_types (name, parent_id, description, timeline) ");
      query.append("VALUES (?,?,?,?) ");
      values = new Object[4];
      values[0] = deliverableTypeData.get("name");
      values[1] = deliverableTypeData.get("parent_id");
      values[2] = deliverableTypeData.get("description");
      values[3] = deliverableTypeData.get("timeline");
      result = databaseManager.saveData(query.toString(), values);

    } else {
      // update deliverable type record
      query.append("UPDATE deliverable_types SET name = ?, parent_id = ?, description = ?, timeline = ? ");
      query.append("WHERE id = ? ");
      values = new Object[5];
      values[0] = deliverableTypeData.get("name");
      values[1] = deliverableTypeData.get("parent_id");
      values[2] = deliverableTypeData.get("description");
      values[3] = deliverableTypeData.get("timeline");
      values[4] = deliverableTypeData.get("id");
      result = databaseManager.saveData(query.toString(), values);
      if (result == -1) {
        LOG.error("A problem happened trying to update the deliverable type identified with the id = {}",
          deliverableTypeData.get("id"));
        return -1;
      }
    }
    LOG.debug("<< saveDeliverableType():{}", result);
    return result;
  }
}
