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

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.DeliverableAccessDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal
 */

public class MySQLDeliverableAccessDAO implements DeliverableAccessDAO {

  private DAOManager daoManager;
  public static Logger LOG = LoggerFactory.getLogger(MySQLDeliverableAccessDAO.class);

  @Inject
  public MySQLDeliverableAccessDAO(DAOManager daoManager) {
    this.daoManager = daoManager;
  }

  @Override
  public Map<String, String> getDeliverableAccessData(int deliverableID) {
    LOG.debug(">> getDeliverableAccessData(deliverableID={})", deliverableID);
    Map<String, String> deliverableAccessData = new HashMap<String, String>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT * FROM deliverable_access ");
    query.append("WHERE deliverable_id = ");
    query.append(deliverableID);

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        deliverableAccessData.put("description", rs.getString("description"));
        deliverableAccessData.put("data_dictionary", rs.getString("data_dictionary"));
        deliverableAccessData.put("quality_procedures", rs.getString("quality_procedures"));
        deliverableAccessData.put("access_restrictions", rs.getString("access_restrictions"));
        deliverableAccessData.put("access_limits", rs.getString("access_limits"));
        deliverableAccessData.put("access_limit_start_date", rs.getString("access_limit_start_date"));
        deliverableAccessData.put("access_limit_end_date", rs.getString("access_limit_end_date"));
        deliverableAccessData.put("harvesting_protocols", rs.getString("harvesting_protocols"));
        deliverableAccessData.put("harvesting_protocol_details", rs.getString("harvesting_protocol_details"));
      }
    } catch (SQLException e) {
      LOG.error("-- getDeliverableAccessData(): There was an error ");
    }

    LOG.debug("<< getDeliverableAccessData():deliverableAccessData.size={}", deliverableAccessData.size());
    return deliverableAccessData;
  }

  @Override
  public boolean saveDeliverableAccessData(Map<String, Object> deliverableAccessData) {
    LOG.debug(">> saveDeliverableAccessData(deliverableAccessData={})", deliverableAccessData);
    boolean saved = false;
    StringBuilder query = new StringBuilder();
    query.append("INSERT INTO deliverable_access (data_dictionary, quality_procedures, ");
    query.append("access_restrictions, access_limits, access_limit_start_date, access_limit_end_date, ");
    query.append("harvesting_protocols, harvesting_protocol_details, deliverable_id) ");
    query.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ");
    query.append("ON DUPLICATE KEY UPDATE ");
    query.append("data_dictionary = VALUES(data_dictionary), quality_procedures = VALUES(quality_procedures), ");
    query.append("access_restrictions = VALUES(access_restrictions), access_limits = VALUES(access_limits), ");
    query.append("access_limit_start_date = VALUES(access_limit_start_date), ");
    query.append("access_limit_end_date = VALUES(access_limit_end_date), ");
    query.append("harvesting_protocols = VALUES(harvesting_protocols), ");
    query.append("harvesting_protocol_details = VALUES(harvesting_protocol_details) ");

    Object[] values = new Object[deliverableAccessData.size()];
    values[0] = deliverableAccessData.get("data_dictionary");
    values[1] = deliverableAccessData.get("quality_procedures");
    values[2] = deliverableAccessData.get("access_restrictions");
    values[3] = deliverableAccessData.get("access_limits");
    values[4] = deliverableAccessData.get("access_limit_start_date");
    values[5] = deliverableAccessData.get("access_limit_end_date");
    values[6] = deliverableAccessData.get("harvesting_protocols");
    values[7] = deliverableAccessData.get("harvesting_protocol_details");
    values[8] = deliverableAccessData.get("deliverable_id");

    try (Connection con = daoManager.getConnection()) {
      int affectedRows = daoManager.makeChangeSecure(con, query.toString(), values);
      if (affectedRows < 0) {
        LOG.error("-- saveDeliverableAccessData(): there was a problem getting the deliverable access data");
        LOG.error("Query: {}", query);
        LOG.error("Values: {}", values);
      }
    } catch (SQLException e) {
      LOG.error("-- saveDeliverableAccessData(): there was an exception getting the delvierable access data", e);
    }

    LOG.debug("<< saveDeliverableAccessData():{}", saved);
    return saved;
  }

}
