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

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLIPIndicatorDAO implements IPIndicatorDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLIPIndicatorDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLIPIndicatorDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getIndicatorsByIpElementID(int ipElementID) {
    LOG.debug(">> getIndicatorsByIpElementID( ipElementID = {} )", ipElementID);
    List<Map<String, String>> ipIndicatorList = new ArrayList<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT * FROM ip_indicators WHERE program_element_id = " + ipElementID);

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

  /**
   * This method is in charge of execute the insert querys to the databases
   * related to the IPElements.
   * 
   * @param query
   * @param data
   * @return the last inserted id or -1 if the row was not inserted
   */
  private int saveData(String query, Object[] data) {
    int generatedId = -1;

    try (Connection con = databaseManager.getConnection()) {
      int ipIndicatorAdded = databaseManager.makeChangeSecure(con, query, data);
      if (ipIndicatorAdded > 0) {
        // get the id assigned to this new record.
        ResultSet rs = databaseManager.makeQuery("SELECT LAST_INSERT_ID()", con);
        if (rs.next()) {
          generatedId = rs.getInt(1);
        }
        rs.close();
      }
    } catch (SQLException e) {
      LOG.error("-- saveData() > There was a problem saving information into the database. \n{}", e);
    }
    return generatedId;
  }

  @Override
  public int saveIndicator(Map<String, Object> indicatorData) {
    LOG.debug(">> saveIndicator(indicatorData={})", indicatorData);

    StringBuilder query = new StringBuilder();
    query.append("INSERT INTO ip_indicators (id, description, target, program_element_id) ");
    query.append("VALUES (?, ?, ?, ?) ");
    query.append("ON DUPLICATE KEY UPDATE description = VALUES(description), target = VALUES(target)");

    Object[] values = new Object[4];
    values[0] = indicatorData.get("id");
    values[1] = indicatorData.get("description");
    values[2] = indicatorData.get("target");
    values[3] = indicatorData.get("program_element_id");

    int result = saveData(query.toString(), values);
    LOG.debug("<< saveIndicator():{}", result);
    return result;
  }
}
