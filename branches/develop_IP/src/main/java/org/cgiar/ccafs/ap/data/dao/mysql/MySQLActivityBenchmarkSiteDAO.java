package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.ActivityBenchmarkSiteDAO;
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


public class MySQLActivityBenchmarkSiteDAO implements ActivityBenchmarkSiteDAO {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MySQLActivityBenchmarkSiteDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLActivityBenchmarkSiteDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean deleteActivityBenchmarkSites(int activityID) {
    LOG.debug(">> deleteActivityBenchmarkSites(activityID={} )", activityID);

    boolean deleted = false;
    String query = "DELETE FROM bs_locations WHERE activity_id = ?";
    try (Connection con = databaseManager.getConnection()) {
      int rows = databaseManager.makeChangeSecure(con, query, new String[] {String.valueOf(activityID)});
      if (rows < 0) {
        LOG
          .warn(
            "-- deleteActivityBenchmarkSites() > There was an problem deleting the benchmark sites locations for activity {}.",
            activityID);
      } else {
        LOG
          .info("-- deleteActivityBenchmarkSites() > Benchmark sites related to activity {} were deleted.", activityID);
        deleted = true;
      }
    } catch (SQLException e) {
      LOG.error("-- deleteActivityBenchmarkSites() > There was an error deleting the benchmark sites locations.", e);
    }

    LOG.debug("<< deleteActivityBenchmarkSites():{}", deleted);
    return deleted;
  }

  @Override
  public List<Map<String, String>> getActivityBenchmarkSites(int activityID) {
    LOG.debug(">> getActivityBenchmarkSites(activityID={})", activityID);

    List<Map<String, String>> bsDataList = new ArrayList<>();
    String query =
      "SELECT bs.id, bs.bs_id, bs.name, bs.longitude, bs.latitude, bsl.details, "
        + "co.iso2 as 'country_iso2', co.name as 'country_name' FROM benchmark_sites bs "
        + "INNER JOIN bs_locations bsl ON bs.id = bsl.bs_id " + "INNER JOIN countries co ON bs.country_iso2 = co.iso2 "
        + "INNER JOIN activities ac ON bsl.activity_id = ac.id " + "WHERE ac.id = " + activityID;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> bsData = new HashMap<String, String>();
        bsData.put("id", rs.getString("id"));
        bsData.put("bs_id", rs.getString("bs_id"));
        bsData.put("name", rs.getString("name"));
        bsData.put("longitude", rs.getString("longitude"));
        bsData.put("latitude", rs.getString("latitude"));
        bsData.put("country_iso2", rs.getString("country_iso2"));
        bsData.put("country_name", rs.getString("country_name"));
        bsData.put("details", rs.getString("details"));
        bsDataList.add(bsData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error(
        "-- getActivityBenchmarkSites() > There was an error getting the data from 'benchmark_sites' table. \n{}",
        query, e);
    }

    LOG.debug("<< getActivityBenchmarkSites():List.size()={}", bsDataList.size());
    return bsDataList;
  }

  @Override
  public boolean saveActivityBenchmarkSite(String benchmarkSiteID, int activityID) {
    LOG.debug(">> saveActivityBenchmarkSite(benchmarkSiteID={}, activityID={})", benchmarkSiteID, activityID);

    boolean saved = false;
    String query = "INSERT INTO bs_locations (bs_id, activity_id) VALUES (?, ?)";
    Object[] values = new Object[2];
    values[0] = benchmarkSiteID;
    values[1] = activityID;

    try (Connection con = databaseManager.getConnection()) {
      int rows = databaseManager.makeChangeSecure(con, query, values);
      if (rows < 0) {
        LOG
          .error(
            "-- saveActivityBenchmarkSite() > There was a problem saving a benchmark site location into the DAO. \n Query: {} |n Values: ",
            query, values);
      } else {
        saved = true;
        LOG.info("-- saveActivityBenchmarkSite() > Benchmark site {} was saved in the DAO related to the activity {}",
          benchmarkSiteID, activityID);
      }
    } catch (SQLException e) {
      LOG
        .error("-- saveActivityBenchmarkSite() > There was an error saving a benchmark site location into the DAO.", e);
    }

    LOG.debug("<< saveActivityBenchmarkSite(): {}", saved);
    return saved;
  }

}
