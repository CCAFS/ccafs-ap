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
import org.cgiar.ccafs.ap.data.dao.PublicationTypeDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLPublicationTypeDAO implements PublicationTypeDAO {

  private static final Logger LOG = LoggerFactory.getLogger(MySQLPublicationTypeDAO.class);
  private DAOManager dbManager;

  @Inject
  public MySQLPublicationTypeDAO(DAOManager dbManager) {
    this.dbManager = dbManager;
  }

  @Override
  public Map<String, String> getPublicationType(String id) {
    LOG.debug(">> getPublicationType(id={})", id);
    Map<String, String> typeData = new HashMap<>();
    String query = "SELECT * FROM publication_types WHERE id = " + id;
    try (Connection connection = dbManager.getConnection()) {
      ResultSet rs = dbManager.makeQuery(query, connection);
      if (rs.next()) {
        typeData.put("id", rs.getString("id"));
        typeData.put("name", rs.getString("name"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getPublicationType() > There was an error getting publication type with id {}.", id, e);
    }

    LOG.debug("<< getPublicationType():{}", typeData);
    return typeData;
  }

  @Override
  public Map<String, String> getPublicationTypes() {
    LOG.debug(">> getPublicationTypes()");
    Map<String, String> typesData = new LinkedHashMap<>();
    String query = "SELECT * FROM publication_types";
    try (Connection connection = dbManager.getConnection()) {
      ResultSet rs = dbManager.makeQuery(query, connection);
      while (rs.next()) {
        typesData.put(rs.getString("id"), rs.getString("name"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getPublicationTypes() > There was an error getting the list of publication types", e);
    }

    LOG.debug("<< getPublicationTypes():{}", typesData);
    return typesData;
  }

}
