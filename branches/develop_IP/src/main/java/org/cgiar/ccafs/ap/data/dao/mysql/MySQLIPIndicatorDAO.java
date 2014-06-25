package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.IPIndicatorDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLIPIndicatorDAO implements IPIndicatorDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLIPIndicatorDAO.class);

  private DAOManager databaseManager;

  public MySQLIPIndicatorDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getIndicatorsByIpElementID(int ipElementID) {
    LOG.debug(">> getIndicatorsByIpElementID( ipElementID = {} )", ipElementID);
    List<Map<String, String>> ipIndicatorList = new ArrayList<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT * FROM ip_indicators WHERE element_id = " + ipElementID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> indicatorData = new HashMap<String, String>();
        indicatorData.put("id", rs.getString("id"));
        indicatorData.put("description", rs.getString("description"));
        indicatorData.put("target", rs.getString("target"));
        ipIndicatorList.add(indicatorData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- getIndicatorsByIpElementID() > Exception raised trying ";
      exceptionMessage += "to get the ip indicators corresponding to the ip element " + ipElementID;

      LOG.error(exceptionMessage);
    }

    LOG.debug("<< getIndicatorsByIpElementID():ipIndicatorList.size={}", ipIndicatorList.size());
    return ipIndicatorList;
  }
}
