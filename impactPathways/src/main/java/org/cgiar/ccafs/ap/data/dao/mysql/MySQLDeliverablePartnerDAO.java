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

import org.cgiar.ccafs.ap.data.dao.DeliverablePartnerDAO;
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
 * @author Héctor Fabio Tobón R.
 */
public class MySQLDeliverablePartnerDAO implements DeliverablePartnerDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLDeliverablePartnerDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLDeliverablePartnerDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean deleteDeliverablePartner(int id, int userID, String justification) {
    StringBuilder query = new StringBuilder();
    // updating record is_active to false.
    query.append("UPDATE deliverable_partnerships SET is_active = 0, modified_by = ?, modification_justification = ? ");
    query.append("WHERE id = ? ");
    Object[] values = new Object[3];
    values[0] = userID;
    values[1] = justification;
    values[2] = id;

    int result = databaseManager.saveData(query.toString(), values);
    if (result == 0) {
      LOG.debug("<< deleteDeliverablePartner():{}", true);
      return true;
    }
    LOG.debug("<< deleteDeliverablePartner:{}", false);
    return false;
  }


  private List<Map<String, String>> getData(String query) {
    LOG.debug(">> executeQuery(query='{}')", query);
    List<Map<String, String>> deliverablePartnerList = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> deliverablePartnerData = new HashMap<String, String>();
        deliverablePartnerData.put("id", rs.getString("id"));
        deliverablePartnerData.put("deliverable_id", rs.getString("deliverable_id"));
        deliverablePartnerData.put("partner_type", rs.getString("partner_type"));

        deliverablePartnerList.add(deliverablePartnerData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< executeQuery():deliverablePartnerList.size={}", deliverablePartnerList.size());
    return deliverablePartnerList;
  }

  @Override
  public List<Map<String, String>> getDeliverablePartners(int deliverableID) {
    LOG.debug(">> getDeliverablePartners deliverableID = {} )", deliverableID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT dp.*   ");
    query.append("FROM deliverable_partnerships as dp ");
    query.append("WHERE dp.deliverable_id = ");
    query.append(deliverableID);
    query.append(" AND dp.is_active = 1 ");
    query.append("ORDER BY dp.partner_type");

    LOG.debug("-- getDeliverablePartners() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getDeliverablePartners(int deliverableID, String deliverablePartnerType) {
    LOG.debug(">> getDeliverablePartners deliverableID = {},  deliverablePartnerType = {})", new Object[] {
      deliverableID, deliverablePartnerType});

    StringBuilder query = new StringBuilder();
    query.append("SELECT *   ");
    query.append("FROM deliverable_partnerships ");
    query.append("WHERE deliverable_id = ");
    query.append(deliverableID);
    query.append(" AND partner_type = '");
    query.append(deliverablePartnerType);
    query.append("'");
    query.append(" AND is_active = 1 ");
    query.append("ORDER BY partner_type");

    LOG.debug("-- getDeliverablePartners() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }


  @Override
  public int saveDeliverablePartner(Map<String, Object> deliverablePartnerData) {
    LOG.debug(">> saveDeliverablePartner(deliverablePartnerData)", deliverablePartnerData);
    StringBuilder query = new StringBuilder();
    Object[] values;
    if (deliverablePartnerData.get("id") == null) {
      // Insert new record
      query
      .append("INSERT INTO deliverable_partnerships (id, deliverable_id, partner_id, partner_type, created_by, modified_by, modification_justification) ");
      query.append("VALUES (?, ?, ?, ?, ?, ?, ?) ");
      values = new Object[7];
      values[0] = deliverablePartnerData.get("id");
      values[1] = deliverablePartnerData.get("deliverable_id");
      values[2] = deliverablePartnerData.get("partner_id");
      values[3] = deliverablePartnerData.get("partner_type");
      values[4] = deliverablePartnerData.get("created_by");
      values[5] = deliverablePartnerData.get("modified_by");
      values[6] = deliverablePartnerData.get("modification_justification");
    } else {
      // update record
      query
      .append("UPDATE deliverable_partnerships SET deliverable_id = ?, partner_id = ?, partner_type = ?, modified_by = ?, modification_justification = ? ");
      query.append("WHERE id = ? ");
      values = new Object[6];
      values[0] = deliverablePartnerData.get("deliverable_id");
      values[1] = deliverablePartnerData.get("partner_id");
      values[2] = deliverablePartnerData.get("partner_type");
      values[3] = deliverablePartnerData.get("modified_by");
      values[4] = deliverablePartnerData.get("modification_justification");
      values[5] = deliverablePartnerData.get("id");
    }

    int result = databaseManager.saveData(query.toString(), values);
    LOG.debug("<< saveDeliverablePartner():{}", result);
    return result;
  }
}
