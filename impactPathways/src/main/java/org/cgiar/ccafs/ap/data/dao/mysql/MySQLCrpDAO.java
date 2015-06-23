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

import org.cgiar.ccafs.ap.data.dao.CrpDAO;
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
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class MySQLCrpDAO implements CrpDAO {

  private static Logger LOG = LoggerFactory.getLogger(MySQLCrpDAO.class);
  private DAOManager daoManager;

  @Inject
  public MySQLCrpDAO(DAOManager daoManager) {
    this.daoManager = daoManager;
  }

  @Override
  public List<Map<String, String>> getCrpContributions(int projectID) {
    List<Map<String, String>> crps = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT * FROM crps c ");
    query.append("INNER JOIN project_crp_contributions pcc ON c.id = pcc.crp_id ");
    query.append("WHERE ");
    query.append("project_id = ");
    query.append(projectID);
    query.append(" AND pcc.is_active = TRUE ");
    query.append(" AND c.is_active = TRUE ");

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> crp = new HashMap<>();
        crp.put("id", rs.getString("id"));
        crp.put("name", rs.getString("name"));
        crp.put("acronym", rs.getString("acronym"));
        crps.add(crp);
      }
    } catch (SQLException e) {
      LOG.error("getCrpsList() > Exception raised trying to get the list of CRPs.", e);
    }

    return crps;
  }

  @Override
  public List<Map<String, String>> getCRPsList() {
    List<Map<String, String>> crps = new ArrayList<>();
    String query = "SELECT * FROM crps WHERE is_active = TRUE";

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> crp = new HashMap<>();
        crp.put("id", rs.getString("id"));
        crp.put("name", rs.getString("name"));
        crp.put("acronym", rs.getString("acronym"));
        crps.add(crp);
      }
    } catch (SQLException e) {
      LOG.error("getCrpsList() > Exception raised trying to get the list of CRPs.", e);
    }

    return crps;
  }

}
