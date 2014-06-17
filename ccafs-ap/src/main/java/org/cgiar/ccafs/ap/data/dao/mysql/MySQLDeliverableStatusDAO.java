/*
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
 */

package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.DeliverableStatusDAO;

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


public class MySQLDeliverableStatusDAO implements DeliverableStatusDAO {

  private static final Logger LOG = LoggerFactory.getLogger(MySQLDeliverableStatusDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLDeliverableStatusDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getDeliverableStatus() {
    LOG.debug(">> getDeliverableStatus()");
    List<Map<String, String>> deliverableTypesList = new ArrayList<>();
    String query = "SELECT * from deliverable_status";
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> typesData = new HashMap<>();
        typesData.put("id", rs.getString("id"));
        typesData.put("name", rs.getString("name"));
        deliverableTypesList.add(typesData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getDeliverableStatus() > There was an error getting the deliverable status list.", e);
    }

    LOG.debug("<< getDeliverableStatus():deliverableTypesList.size={}", deliverableTypesList.size());
    return deliverableTypesList;
  }

  @Override
  public boolean setDeliverableStatus(int deliverableId, int statusId) {
    LOG.debug(">> setDeliverableStatus(deliverableId={}, statusId={})", deliverableId, statusId);
    String preparedUpdateQuery = "UPDATE deliverables SET deliverable_status_id = ? WHERE id = ?";
    try (Connection connection = databaseManager.getConnection()) {
      int rowsUpdated =
        databaseManager.makeChangeSecure(connection, preparedUpdateQuery, new Object[] {statusId, deliverableId});
      LOG.debug("<< getDeliverableStatus():", (rowsUpdated > 0));
      return (rowsUpdated > 0);

    } catch (SQLException e) {
      LOG.error("-- getDeliverableStatus() > There was an error updating the status of deliverable {}.", deliverableId,
        e);
    }
    LOG.debug("<< getDeliverableStatus():", false);
    return false;
  }
}
