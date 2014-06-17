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
import org.cgiar.ccafs.ap.data.dao.IndicatorDAO;

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


public class MySQLIndicatorDAO implements IndicatorDAO {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MySQLIndicatorDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLIndicatorDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getIndicatorsList() {
    LOG.debug(">> getIndicatorsList()");
    List<Map<String, String>> indicatorsDataList = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT i.id, i.serial, i.name, i.description, i.is_active, ");
    query.append("it.id as 'indicator_type_id', it.name as 'indicator_type_name' ");
    query.append("FROM `indicators` i ");
    query.append("INNER JOIN `indicator_types` it ON i.indicator_type_id = it.id ");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> indicatorData = new HashMap<String, String>();
        indicatorData.put("id", rs.getString("id"));
        indicatorData.put("serial", rs.getString("serial"));
        indicatorData.put("name", rs.getString("name"));
        indicatorData.put("description", rs.getString("description"));
        indicatorData.put("is_active", rs.getString("is_active"));
        indicatorData.put("indicator_type_id", rs.getString("indicator_type_id"));
        indicatorData.put("indicator_type_name", rs.getString("indicator_type_name"));
        indicatorsDataList.add(indicatorData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getIndicatorsList() > There was an exception trying to load the indicators list.");
    }

    LOG.debug("<< getIndicatorsList():indicatorsDataList.size={}", indicatorsDataList.size());
    return indicatorsDataList;
  }

}