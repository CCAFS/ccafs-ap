package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.BenchmarkSiteDAO;
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


public class MySQLBenchmarkSiteDAO implements BenchmarkSiteDAO {

  private static final Logger LOG = LoggerFactory.getLogger(MySQLBenchmarkSiteDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLBenchmarkSiteDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getActiveBenchmarkSiteList() {
    List<Map<String, String>> bsDataList = new ArrayList<>();
    String query =
      "SELECT bs.id, bs.bs_id, bs.name, bs.longitude, bs.latitude, co.iso2 as 'country_iso2', co.name as 'country_name' "
        + "FROM benchmark_sites bs " + "INNER JOIN countries co ON bs.country_iso2 = co.iso2 "
        + "WHERE is_active = true";
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
        bsDataList.add(bsData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("There was an error getting the benchmark sites list.", e);
    }
    return bsDataList;
  }

  @Override
  public List<Map<String, String>> getActiveBenchmarkSitesByCountry(String countryID) {
    List<Map<String, String>> bsDataList = new ArrayList<>();
    String query =
      "SELECT bs.id, bs.bs_id, bs.name, bs.longitude, bs.latitude, co.iso2 as 'country_iso2', co.name as 'country_name' "
        + "FROM benchmark_sites bs " + "INNER JOIN countries co ON bs.country_iso2 = co.iso2 "
        + "WHERE is_active = true AND co.iso2 = '" + countryID + "';";
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
        bsDataList.add(bsData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("There was an error getting the benchmark sites list.", e);
    }
    return bsDataList;
  }
}
