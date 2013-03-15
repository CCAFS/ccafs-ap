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
  public List<Map<String, String>> getActivityCountries(int activityID) {
    List<Map<String, String>> countriesDataList = new ArrayList<>();
    String query =
      "SELECT co.iso2, co.name, cl.details FROM country_locations cl "
        + "INNER JOIN countries co ON cl.country_iso2 = co.iso2 " + "WHERE cl.activity_id = " + activityID;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> countryData = new HashMap<String, String>();
        countryData.put("iso2", rs.getString("iso2"));
        countryData.put("name", rs.getString("name"));
        countryData.put("details", rs.getString("details"));
        countriesDataList.add(countryData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("There was an error getting the data from 'country_locations' table. \n{}", query, e);
    }
    return countriesDataList;
  }

}
