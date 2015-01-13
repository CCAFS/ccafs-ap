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
import org.cgiar.ccafs.ap.data.dao.MetadataDAO;

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
 * @author Hernán David Carvajal
 */

public class MySQLMetadataDAO implements MetadataDAO {

  private static Logger LOG = LoggerFactory.getLogger(MySQLMetadataDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLMetadataDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getMetadataList() {
    LOG.debug(">> getMetadataList()");
    List<Map<String, String>> metadataList = new ArrayList<>();
    String query = "SELECT * FROM metadata_questions ";

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("id", rs.getString("id"));
        metadata.put("name", rs.getString("name"));
        metadata.put("description", rs.getString("description"));
        metadataList.add(metadata);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("--  getMetadataList() > There was a problem getting the list of metadata.", e);
    }

    LOG.debug("<< getMetadataList():metadataList.size={}", metadataList.size());
    return metadataList;
  }

}
