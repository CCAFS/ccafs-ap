package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.CountryDAO;
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


public class MySQLCountryDAO implements CountryDAO {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(MySQLCountryDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLCountryDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getCountriesByRegion(String regionID) {
    LOG.debug(">> getCountriesByRegion(regionID={})", regionID);

    List<Map<String, String>> countryList = new ArrayList<>();
    String query =
      "SELECT co.iso2, co.name, re.id as 'region_id', re.name as 'region_name' FROM countries co "
        + "INNER JOIN regions re ON co.region_id = re.id " + "WHERE re.id = " + regionID + " ORDER BY co.name";
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> countryData = new HashMap<>();
        countryData.put("id", rs.getString("iso2"));
        countryData.put("name", rs.getString("name"));
        countryData.put("region_id", rs.getString("region_id"));
        countryData.put("region_name", rs.getString("region_name"));
        countryList.add(countryData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error(
        "-- getCountriesByRegion() > There was an error getting the list of countries that belongs to the region {}.",
        regionID, e);
    }

    if (countryList.isEmpty()) {
      LOG.debug("<< getCountriesByRegion():null");
      return null;
    }

    LOG.debug("<< getCountriesByRegion():countryList.size={}", countryList.size());
    return countryList;
  }

  @Override
  public List<Map<String, String>> getCountriesList() {
    LOG.debug(">> getCountriesList()");
    List<Map<String, String>> countryList = new ArrayList<>();
    String query =
      "SELECT co.iso2, co.name, re.id as 'region_id', re.name as 'region_name' FROM countries co "
        + "INNER JOIN regions re ON co.region_id = re.id " + "ORDER BY co.name";
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> countryData = new HashMap<>();
        countryData.put("id", rs.getString("iso2"));
        countryData.put("name", rs.getString("name"));
        countryData.put("region_id", rs.getString("region_id"));
        countryData.put("region_name", rs.getString("region_name"));
        countryList.add(countryData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getCountriesList() > There was an error getting the country list, \n{}", e);
    }

    if (countryList.isEmpty()) {
      LOG.debug(">> getCountriesList():null");
      return null;
    }
    LOG.debug(">> getCountriesList():countryList.size={}", countryList.size());
    return countryList;
  }

  @Override
  public Map<String, String> getCountryInformation(String id) {
    LOG.debug(">> getCountryInformation(id={})", id);
    Map<String, String> countryData = null;
    String query = "SELECT * FROM countries WHERE iso2='" + id + "';";
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      if (rs.next()) {
        countryData = new HashMap<>();
        countryData.put("id", rs.getString("iso2"));
        countryData.put("name", rs.getString("name"));
      }
      rs.close();
    } catch (Exception e) {
      LOG.error("-- getCountryInformation() > There was an error getting information for country {}.", id, e);
    }

    if (countryData == null) {
      return null;
    }

    LOG.debug("<< getCountryInformation():{}", countryData);
    return countryData;
  }

}
