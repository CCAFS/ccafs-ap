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

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.IPCrossCuttingDAO;

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
 * This class contains the methods for the database table project_cross_cutting_themes
 *
 * @author Javier Andrés Gallego B.
 */
public class MySQLIPCrossCuttingDAO implements IPCrossCuttingDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLIPCrossCuttingDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLIPCrossCuttingDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }


  @Override
  public boolean deleteCrossCutting(int projectID, int crossCuttingID) {
    LOG.debug(">> deleteCrossCutting(projectId={}, crossCuttingID={})", new String[] {projectID + "",
      crossCuttingID + ""});
    String query = "DELETE FROM project_cross_cutting_themes WHERE project_id = ? AND theme_id = ?";
    int rowsDeleted = databaseManager.delete(query, new Object[] {projectID, crossCuttingID});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteCrossCutting():{}", true);
      return true;
    }
    LOG.debug("<< deleteCrossCutting:{}", false);
    return false;
  }

  private List<Map<String, String>> getData(String query) {
    LOG.debug(">> executeQuery(query='{}')", query);
    List<Map<String, String>> ipCrossCuttingList = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> ipCrossCuttingData = new HashMap<String, String>();
        ipCrossCuttingData.put("id", rs.getString("id"));
        ipCrossCuttingData.put("name", rs.getString("name"));

        ipCrossCuttingList.add(ipCrossCuttingData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
    }

    LOG.debug("<< executeQuery():ipCrossCutingList.size={}", ipCrossCuttingList.size());
    return ipCrossCuttingList;
  }

  @Override
  public Map<String, String> getIPCrossCutting(int iD) {
    Map<String, String> ipCrossCuttingData = new HashMap<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT ipc.id, ipc.name ");
    query.append("FROM ip_cross_cutting_themes ipc ");
    query.append("WHERE ipc.id = ");
    query.append(iD);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        ipCrossCuttingData.put("id", rs.getString("id"));
        ipCrossCuttingData.put("name", rs.getString("name"));
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- getIPCrossCutting() > Exception raised trying to get the program ";
      exceptionMessage += "which created the ipCrossCutting " + iD;

      LOG.error(exceptionMessage, e);
    }
    return ipCrossCuttingData;
  }

  @Override
  public List<Map<String, String>> getIPCrossCuttingByProject(int projectID) {
    LOG.debug(">> getIPCrossCuttingByProject( projectID = {} )", projectID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT ipc.* ");
    query.append("FROM project_cross_cutting_themes pct ");
    query.append("INNER JOIN ip_cross_cutting_themes ipc ON pct.theme_id=ipc.id ");
    query.append("WHERE pct.project_id= ");
    query.append(projectID);
    query.append(" ORDER BY ipc.name ");

    LOG.debug("-- getIPCrossCuttingByProject() > Calling method executeQuery to get the results");
    return getData(query.toString());
  }


  @Override
  public List<Map<String, String>> getIPCrossCuttings() {
    LOG.debug(">> getIPCrossCutting( ");

    StringBuilder query = new StringBuilder();
    query.append("SELECT ipc.*  ");
    query.append("FROM ip_cross_cutting_themes ipc ");
    query.append("ORDER BY ipc.name ");

    LOG.debug("-- getIPCrossCutting() > Calling method executeQuery to get the results");
    return getData(query.toString());
  }


  @Override
  public boolean saveCrossCutting(Map<String, Object> elementData) {
    LOG.debug(">> saveCrossCutting(crossCuttingData={})", elementData);
    StringBuilder query = new StringBuilder();
    query.append("INSERT INTO project_cross_cutting_themes(project_id, theme_id) ");
    query.append("VALUES (?, ?) ");

    Object[] values = new Object[2];
    values[0] = elementData.get("project_id");
    values[1] = elementData.get("theme_id");
    int result = databaseManager.saveData(query.toString(), values);
    LOG.debug("<< saveCrossCutting():{}", result);
    return true;
  }


}
