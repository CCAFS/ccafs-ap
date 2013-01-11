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


public class MySQLCountryDAO implements CountryDAO {

  private DAOManager databaseManager;

  @Inject
  public MySQLCountryDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getCountriesList() {
    List<Map<String, String>> countryList = new ArrayList<>();
    try (Connection con = databaseManager.getConnection()) {
      String query = "SELECT * FROM countries ORDER BY name";
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> countryData = new HashMap<>();
        countryData.put("id", rs.getString("iso2"));
        countryData.put("name", rs.getString("name"));
        countryList.add(countryData);
      }
      rs.close();
    } catch (SQLException e) {
      // TODO Auto generated catch block
      e.printStackTrace();
    }

    if (countryList.isEmpty()) {
      return null;
    }
    return countryList;
  }

  @Override
  public Map<String, String> getCountryInformation(String id) {
    Map<String, String> countryData = null;
    try (Connection con = databaseManager.getConnection()) {
      String query = "SELECT * FROM countries WHERE iso2='" + id + "';";
      ResultSet rs = databaseManager.makeQuery(query, con);
      if (rs.next()) {
        countryData = new HashMap<>();
        countryData.put("id", rs.getString("iso2"));
        countryData.put("name", rs.getString("name"));
      }
      rs.close();
    } catch (Exception e) {
      // TODO Auto generated catch block
      e.printStackTrace();
    }

    if (countryData == null) {
      return null;
    }

    return countryData;
  }

}
