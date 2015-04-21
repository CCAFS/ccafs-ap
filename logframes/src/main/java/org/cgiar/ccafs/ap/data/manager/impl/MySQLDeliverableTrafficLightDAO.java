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

package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.DeliverableTrafficLightDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal
 */

public class MySQLDeliverableTrafficLightDAO implements DeliverableTrafficLightDAO {

  private static Logger LOG = LoggerFactory.getLogger(MySQLDeliverableTrafficLightDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLDeliverableTrafficLightDAO(DAOManager daoManager) {
    this.databaseManager = daoManager;
  }

  @Override
  public Map<String, String> getTrafficLightData(int deliverableID) {
    LOG.debug(">> getTrafficLightData()");
    Map<String, String> trafficLightData = new HashMap<>();
    String query = "SELECT * FROM deliverable_traffic_light WHERE deliverable_id = " + deliverableID;

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        trafficLightData.put("is_metadata_documented", rs.getString("is_metadata_documented"));
        trafficLightData.put("have_collection_tools", rs.getString("have_collection_tools"));
        trafficLightData.put("is_quality_documented", rs.getString("is_quality_documented"));
        trafficLightData.put("is_supporting_dissemination", rs.getString("is_supporting_dissemination"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error(
        "--  getTrafficLightData() > There was a problem getting the traffic light information of the deliverable {}.",
        deliverableID, e);
    }

    LOG.debug("<< getTrafficLightData():metadataList={}", trafficLightData);
    return trafficLightData;

  }

  @Override
  public boolean saveDeliverableTrafficLight(Map<String, Object> trafficLightData) {
    LOG.debug(">> saveDeliverableTrafficLight(budgetData={})", trafficLightData);
    boolean saved = false;

    Object[] values = new Object[trafficLightData.size()];
    values[0] = trafficLightData.get("deliverable_id");
    values[1] = trafficLightData.get("is_metadata_documented");
    values[2] = trafficLightData.get("have_collection_tools");
    values[3] = trafficLightData.get("is_quality_documented");
    values[4] = trafficLightData.get("is_supporting_dissemination");

    StringBuilder query = new StringBuilder();
    query.append("INSERT INTO deliverable_traffic_light ");
    query.append("(deliverable_id, is_metadata_documented, have_collection_tools, is_quality_documented,  ");
    query.append("is_supporting_dissemination ) VALUES (?, ?, ?, ?, ?) ");
    query.append("ON DUPLICATE KEY UPDATE  ");
    query.append("is_metadata_documented = VALUES(is_metadata_documented), ");
    query.append("have_collection_tools = VALUES(have_collection_tools), ");
    query.append("is_quality_documented = VALUES(is_quality_documented),  ");
    query.append("is_supporting_dissemination = VALUES(is_supporting_dissemination) ");

    try (Connection con = databaseManager.getConnection()) {
      int rows = databaseManager.makeChangeSecure(con, query.toString(), values);
      if (rows < 0) {
        LOG.error("There was a problem saving the traffic light data.");
        LOG.error("Query: {}", query);
        LOG.error("Values: {}", Arrays.toString(values));
      } else {
        saved = true;
      }
    } catch (SQLException e) {
      LOG.error("-- saveDeliverableTrafficLight() > There was an error saving the traffic light data.");
      LOG.error("Query: {}", query);
      LOG.error("Values: {}", Arrays.toString(values));
      LOG.error("Error: ", e);
    }
    LOG.debug("<< saveDeliverableTrafficLight():{}", saved);
    return saved;
  }

}
