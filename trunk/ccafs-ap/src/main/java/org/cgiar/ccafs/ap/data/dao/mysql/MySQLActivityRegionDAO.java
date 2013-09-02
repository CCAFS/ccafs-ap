package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.ActivityRegionDAO;
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


public class MySQLActivityRegionDAO implements ActivityRegionDAO {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MySQLActivityRegionDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLActivityRegionDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean deleteActivityRegions(int activityID) {
    LOG.debug(">> deleteActivityRegions(activityID={})", activityID);
    boolean deleted = false;
    String query = "DELETE FROM region_locations WHERE activity_id = ?";
    try (Connection con = databaseManager.getConnection()) {
      int rows = databaseManager.makeChangeSecure(con, query, new String[] {String.valueOf(activityID)});
      if (rows < 0) {
        LOG.warn("-- deleteActivityRegions() > There was a problem deleting the regions related with the activity {}.",
          activityID);
      } else {
        LOG.info("-- deleteActivityRegions() > Region locations related to the activity {} were deleted", activityID);
        deleted = true;
      }
    } catch (SQLException e) {
      LOG.error("-- deleteActivityRegions() > There was an error deleting the regions related with the activity {}.",
        activityID, e);
    }

    LOG.debug("<< deleteActivityRegions():{}", deleted);
    return deleted;
  }

  @Override
  public List<Map<String, String>> getActivityRegions(int activityID) {
    LOG.debug(">> getActivityRegions(activityID={})", activityID);

    List<Map<String, String>> regionsDataList = new ArrayList<>();
    String query =
      "SELECT re.id, re.name, rl.details FROM region_locations rl " + "INNER JOIN regions re ON rl.region_id = re.id "
        + "WHERE rl.activity_id = " + activityID;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> countryData = new HashMap<String, String>();
        countryData.put("id", rs.getString("id"));
        countryData.put("name", rs.getString("name"));
        countryData.put("details", rs.getString("details"));
        regionsDataList.add(countryData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getActivityRegions() > There was an error getting the data from 'region_locations' table. \n{}",
        query, e);
    }

    LOG.debug("<< getActivityRegions():List.size={}", regionsDataList.size());
    return regionsDataList;
  }

  @Override
  public boolean saveActivityRegion(int activityID, String regionID) {
    LOG.debug(">> saveActivityRegion(activityID={}, regionID={})", activityID, regionID);

    boolean saved = false;
    String query = "INSERT INTO region_locations (activity_id, region_id) VALUES (?, ?)";
    Object[] values = new Object[2];
    values[0] = activityID;
    values[1] = regionID;

    try (Connection con = databaseManager.getConnection()) {
      int rows = databaseManager.makeChangeSecure(con, query, values);
      if (rows < 0) {
        LOG.error("-- saveActivityRegion > There was a problem saving the activity region. \n Query: {} \n Values: {}",
          query, values);
      } else {
        LOG.info("-- saveActivityRegion > Region {} was saved as a location for activity {}", regionID, activityID);
        saved = true;
      }
    } catch (SQLException e) {
      LOG.error("-- saveActivityRegion > There was an error saving the activity region.", e);
    }

    LOG.debug("<< saveActivityRegion():{}", saved);
    return saved;
  }
}
