package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.ActivityOtherSiteDAO;
import org.cgiar.ccafs.ap.data.dao.DAOManager;

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


public class MySQLActivityOtherSiteDAO implements ActivityOtherSiteDAO {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MySQLActivityOtherSiteDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLActivityOtherSiteDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getActivityOtherSites(int activityID) {
    List<Map<String, String>> osDataList = new ArrayList<>();
    String query =
      "SELECT os.id, os.longitude, os.latitude, os.details, co.iso2 as 'country_iso2', "
        + "co.name as 'country_name' FROM other_sites os " + "INNER JOIN activities ac ON os.activity_id = ac.id "
        + "INNER JOIN countries co ON os.country_iso2 = co.iso2 " + "WHERE ac.id = " + activityID;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> osData = new HashMap<String, String>();
        osData.put("id", rs.getString("id"));
        osData.put("longitude", rs.getString("longitude"));
        osData.put("latitude", rs.getString("latitude"));
        osData.put("country_iso2", rs.getString("country_iso2"));
        osData.put("country_name", rs.getString("country_name"));
        osData.put("details", rs.getString("details"));
        osDataList.add(osData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("There was an error getting the data from 'other_sites' table. \n{}", query, e);
    }
    return osDataList;
  }

}
