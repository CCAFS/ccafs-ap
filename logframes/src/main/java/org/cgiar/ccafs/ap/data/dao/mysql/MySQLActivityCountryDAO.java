package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.ActivityCountryDAO;
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


public class MySQLActivityCountryDAO implements ActivityCountryDAO {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MySQLActivityCountryDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLActivityCountryDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean deleteActivityCountries(int activityID) {
    LOG.debug(">> deleteActivityCountries(activityID={})", activityID);

    boolean deleted = false;
    String query = "DELETE FROM country_locations WHERE activity_id = ?";
    try (Connection con = databaseManager.getConnection()) {
      int rows = databaseManager.makeChangeSecure(con, query, new String[] {String.valueOf(activityID)});
      if (rows < 0) {
        LOG.warn(
          "-- deleteActivityCountries() > There was a problem deleting the countries related with the activity {}.",
          activityID);
      } else {
        LOG
          .info("-- deleteActivityCountries() > Country locations related to the activity {} were deleted", activityID);
        deleted = true;
      }
    } catch (SQLException e) {
      LOG.error(
        "-- deleteActivityCountries() > There was an error deleting the countries related with the activity {}.",
        activityID, e);
    }

    LOG.debug("<< deleteActivityCountries():{}", deleted);
    return deleted;
  }

  @Override
  public List<Map<String, String>> getActivityCountries(int activityID) {
    LOG.debug(">> getActivityCountries(activityID={})", activityID);

    List<Map<String, String>> countriesDataList = new ArrayList<>();
    String query =
      "SELECT co.iso2, co.name, cl.details, r.name as 'region_name', r.id as 'region_id' FROM country_locations cl "
        + "INNER JOIN countries co ON cl.country_iso2 = co.iso2 " + "INNER JOIN regions r ON co.region_id = r.id "
        + "WHERE cl.activity_id = " + activityID;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> countryData = new HashMap<String, String>();
        countryData.put("iso2", rs.getString("iso2"));
        countryData.put("name", rs.getString("name"));
        countryData.put("region_id", rs.getString("region_id"));
        countryData.put("region_name", rs.getString("region_name"));
        countryData.put("details", rs.getString("details"));
        countriesDataList.add(countryData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getActivityCountries > There was an error getting the data from 'country_locations' table. \n{}",
        query, e);
    }

    LOG.debug("<< getActivityCountries():List.size={}", countriesDataList.size());
    return countriesDataList;
  }

  @Override
  public boolean saveActivityCountry(int activityID, String countryID) {
    LOG.debug(">> saveActivityCountry(activityID={}, countryID={})", activityID, countryID);

    boolean saved = false;
    String query = "INSERT INTO country_locations (activity_id, country_iso2) VALUES (?, ?)";
    Object[] values = new Object[2];
    values[0] = activityID;
    values[1] = countryID;

    try (Connection con = databaseManager.getConnection()) {
      int rows = databaseManager.makeChangeSecure(con, query, values);
      if (rows < 0) {
        LOG.error(
          "-- saveActivityCountry > There was a problem saving the activity country. \n Query: {} \n Values: {}",
          query, values);
      } else {
        LOG.info("-- saveActivityCountry > Country {} was saved as a location for activity {}", countryID, activityID);
        saved = true;
      }
    } catch (SQLException e) {
      LOG.error("-- saveActivityCountry > There was an error saving the activity country.", e);
    }

    LOG.debug("<< saveActivityCountry():{}", saved);
    return saved;
  }
}
