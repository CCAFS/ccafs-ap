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
import org.cgiar.ccafs.ap.data.dao.DeliverableMetadataDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal
 */

public class MySQLDeliverableMetadataDAO implements DeliverableMetadataDAO {

  private DAOManager daoManager;
  public static Logger LOG = LoggerFactory.getLogger(MySQLDeliverableMetadataDAO.class);

  @Inject
  public MySQLDeliverableMetadataDAO(DAOManager daoManager) {
    this.daoManager = daoManager;
  }

  @Override
  public List<Map<String, String>> getDeliverableMetadata(int deliverableID) {
    LOG.debug(">> getDeliverableMetadata(deliverableID={})", deliverableID);
    List<Map<String, String>> metadataList = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT m.id as 'metadata_id', m.name as 'metadata_name', dm.value ");
    query.append("FROM deliverable_metadata dm ");
    query.append("RIGHT JOIN metadata_questions m ON dm.metadata_id = m.id ");
    query.append("AND dm.deliverable_id = ");
    query.append(deliverableID);

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      Map<String, String> deliverableMetadata;
      while (rs.next()) {
        deliverableMetadata = new HashMap<>();
        deliverableMetadata.put("metadata_id", rs.getString("metadata_id"));
        deliverableMetadata.put("metadata_name", rs.getString("metadata_name"));
        deliverableMetadata.put("value", rs.getString("value"));

        metadataList.add(deliverableMetadata);
      }

    } catch (SQLException e) {

    }

    LOG.debug("<< getDeliverableMetadata():metadataList.size={}", metadataList.size());
    return metadataList;
  }

  @Override
  public boolean saveDeliverableMetadata(Map<String, Object> deliverableMetadata) {
    LOG.debug(">> saveDeliverableMetadata(deliverableMetadata={})", deliverableMetadata);

    boolean saved = false;
    StringBuilder query = new StringBuilder();
    query.append("INSERT INTO deliverable_metadata (deliverable_id, metadata_id, value) ");
    query.append("VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE value = VALUES(value) ");

    Object[] values = new Object[deliverableMetadata.size()];
    values[0] = deliverableMetadata.get("deliverable_id");
    values[1] = deliverableMetadata.get("metadata_id");
    values[2] = deliverableMetadata.get("value");

    try (Connection con = daoManager.getConnection()) {
      int rowsAfected = daoManager.makeChangeSecure(con, query.toString(), values);
      if (rowsAfected < 0) {
        LOG.error("-- saveDeliverableMetadata(): There was a problem saving the deliverable metadata.");
        LOG.error("Query: {}", query);
        LOG.error("Values: {}", Arrays.toString(values));
      } else {
        saved = true;
      }
    } catch (SQLException e) {
      LOG.error("-- saveDeliverableMetadata(): There was a problem saving the deliverable metadata.", e);
    }

    LOG.debug("<< saveDeliverableMetadata():{}", saved);
    return saved;
  }

}
