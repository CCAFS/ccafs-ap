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
  public boolean deleteActivityOtherSites(int activityID) {
    LOG.debug(">> deleteActivityOtherSites(activityID={})", activityID);
    boolean deleted = false;
    String query = "DELETE FROM other_sites WHERE activity_id = ?";
    Object[] values = new String[] {String.valueOf(activityID)};

    try (Connection con = databaseManager.getConnection()) {
      int rows = databaseManager.makeChangeSecure(con, query, values);
      if (rows <= -1) {
        LOG.warn(
          "-- deleteActivityOtherSites() > There was a problem deleting the other sites related to the activity {}",
          activityID);
      } else {
        deleted = true;
      }
    } catch (SQLException e) {
      LOG.error(
        "-- deleteActivityOtherSites() > There was an error deleting the other sites related to the activity {}",
        activityID, e);
    }

    LOG.debug("<< deleteActivityOtherSites():{}", deleted);
    return deleted;
  }

  @Override
  public List<Map<String, String>> getActivityOtherSites(int activityID) {
    LOG.debug(">> getActivityOtherSites(activityID={})", activityID);

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
      LOG.error("-- getActivityOtherSites() > There was an error getting activity other sites for activity {}",
        activityID, e);
    }

    LOG.debug("<< getActivityOtherSites():osDataList.size={}", osDataList.size());
    return osDataList;
  }

  @Override
  public boolean saveActivityOtherSites(Map<String, String> otherSite, int activityID) {
    LOG.debug(">> saveActivityOtherSites(otherSite={}, activityID={})", otherSite, activityID);
    boolean saved = false;
    String query =
      "INSERT INTO other_sites (id, latitude, longitude, details, country_iso2, activity_id) VALUES (?, ?, ?, ?, ?, "
        + activityID + ")";
    Object[] values = new Object[5];
    values[0] = otherSite.get("id");
    values[1] = otherSite.get("latitude");
    values[2] = otherSite.get("longitude");
    values[3] = otherSite.get("details");
    values[4] = otherSite.get("country_iso2");

    try (Connection con = databaseManager.getConnection()) {
      int rows = databaseManager.makeChangeSecure(con, query, values);
      if (rows <= -1) {
        LOG.warn(
          "-- saveActivityOtherSites() > There was a problem saving an otherSite location. \nQuery: {} \nValues: {}",
          query, values);
      } else {
        saved = true;
      }
    } catch (SQLException e) {
      LOG.error("-- saveActivityOtherSites() > There was an error saving an otherSite location for activity {}",
        activityID, e);
    }
    LOG.debug("<< saveActivityOtherSites():{}", saved);
    return saved;
  }
}
