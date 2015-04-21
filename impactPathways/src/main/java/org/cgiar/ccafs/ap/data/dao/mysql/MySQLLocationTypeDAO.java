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
import org.cgiar.ccafs.ap.data.dao.LocationTypeDAO;

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

public class MySQLLocationTypeDAO implements LocationTypeDAO {

  private DAOManager daoManager;
  public static Logger LOG = LoggerFactory.getLogger(MySQLLocationTypeDAO.class);

  @Inject
  public MySQLLocationTypeDAO(DAOManager daoManager) {
    this.daoManager = daoManager;
  }

  @Override
  public List<Map<String, String>> getLocationTypes() {
    LOG.debug(">> getLocationTypes() ");
    List<Map<String, String>> locationTypesData = new ArrayList<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT * FROM loc_element_types");

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> ltData = new HashMap<String, String>();
        ltData.put("id", rs.getString("id"));
        ltData.put("name", rs.getString("name"));
        locationTypesData.add(ltData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("getLocationTypes() > Exception arised trying to get the list of location types.", e);
    }

    LOG.debug("<< getLocationTypes():locationTypesData.size={}", locationTypesData.size());
    return locationTypesData;
  }
}
