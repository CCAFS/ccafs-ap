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
 * This class contains the methods that used the DataBase Table IP_CROSS_CUTTING_THEME
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
  public List<Map<String, String>> getIPCrossCuttings() {
    LOG.debug(">> getIPCrossCutting( ");

    StringBuilder query = new StringBuilder();
    query.append("SELECT ipc.*  ");
    query.append("FROM ip_cross_cutting_themes ipc ");
    query.append("ORDER BY ipc.name ");


    LOG.debug("-- getIPCrossCutting() > Calling method executeQuery to get the results");
    return getData(query.toString());
  }


}
