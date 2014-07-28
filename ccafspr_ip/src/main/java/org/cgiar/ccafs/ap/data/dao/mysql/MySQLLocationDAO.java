package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.LocationDAO;

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


public class MySQLLocationDAO implements LocationDAO {

  public static Logger LOG = LoggerFactory.getLogger(MySQLLocationDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLLocationDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getAllCountries() {
    LOG.debug(">> getLocationsByType( )");

    StringBuilder query = new StringBuilder();
    query.append("SELECT le.id, le.name, le.code, ");
    query.append("le.parent_id as region_id,let.name as region_name, let.code as region_code ");
    query.append("FROM loc_elements le ");
    query.append("INNER JOIN loc_elements let ON le.parent_id = let.id  ");
    query.append("INNER JOIN loc_element_types letd ON letd.id = le.element_type_id  ");
    query.append("WHERE letd.id =  ");
    query.append(APConstants.LOCATION_ELEMENT_TYPE_COUNTRY);
    query.append(" ORDER BY le.name ");

    List<Map<String, String>> countriesList = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> countryData = new HashMap<String, String>();
        countryData.put("id", rs.getString("id"));
        countryData.put("name", rs.getString("name"));
        countryData.put("code", rs.getString("code"));
        // Region Data
        countryData.put("region_id", rs.getString("region_id"));
        countryData.put("region_name", rs.getString("region_name"));
        countryData.put("region_code", rs.getString("region_code"));

        countriesList.add(countryData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;
      LOG.error(exceptionMessage, e);
      return null;
    }

    LOG.debug("<< executeQuery():getLocationsByType.size={}", countriesList.size());
    return countriesList;
  }

  @Override
  public List<Map<String, String>> getAllRegions() {
    LOG.debug(">> getAllRegions( )");

    StringBuilder query = new StringBuilder();
    query.append("SELECT le.id, le.name, le.code ");
    query.append("FROM loc_elements le ");
    query.append("INNER JOIN loc_element_types letd ON letd.id = le.element_type_id  ");
    query.append("WHERE letd.id =  ");
    query.append(APConstants.LOCATION_ELEMENT_TYPE_REGION);
    query.append(" ORDER BY le.name ");

    LOG.debug("-- getAllRegions() > Calling method executeQuery to get the results");
    return getData(query.toString());
  }


  @Override
  public Map<String, String> getCountry(int countryID) {
    LOG.debug(">> getLocationsByType( )");

    StringBuilder query = new StringBuilder();
    query.append("SELECT le.id, le.name, le.code, ");
    query.append("le.parent_id as region_id,let.name as region_name, let.code as region_code ");
    query.append("FROM loc_elements le ");
    query.append("INNER JOIN loc_elements let ON le.parent_id = let.id  ");
    query.append("INNER JOIN loc_element_types letd ON letd.id = le.element_type_id  ");
    query.append("WHERE letd.id =  ");
    query.append(APConstants.LOCATION_ELEMENT_TYPE_COUNTRY);
    query.append(" AND le.id= ");
    query.append(countryID);

    Map<String, String> countryData = new HashMap<String, String>();
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        // Country
        countryData.put("id", rs.getString("id"));
        countryData.put("name", rs.getString("name"));
        countryData.put("code", rs.getString("code"));
        // Region
        countryData.put("region_id", rs.getString("region_id"));
        countryData.put("region_name", rs.getString("region_name"));
        countryData.put("region_code", rs.getString("region_code"));
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;
      LOG.error(exceptionMessage, e);
      return countryData;
    }
    return countryData;
  }

  @Override
  public Map<String, String> getCountryByCode(String code) {
    LOG.debug(">> getCountryByCode( )");

    StringBuilder query = new StringBuilder();
    query.append("SELECT leo.id, leo.name, leo.code, leo.element_type_id, leo.parent_id as region_id, ");
    query.append("let.name as region_name, let.code as region_code, let.element_type_id as region_element_type ");
    query.append("FROM loc_elements leo ");
    query.append("LEFT JOIN loc_elements let ON leo.parent_id = let.id ");
    query.append("INNER JOIN loc_element_types letd ON letd.id = leo.element_type_id ");
    query.append("WHERE letd.id =  ");
    query.append(APConstants.LOCATION_ELEMENT_TYPE_COUNTRY);
    query.append(" AND leo.code = '");
    query.append(code);
    query.append("'");

    Map<String, String> countryData = new HashMap<String, String>();
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        // Country
        countryData.put("id", rs.getString("id"));
        countryData.put("name", rs.getString("name"));
        countryData.put("code", rs.getString("code"));
        // Region
        countryData.put("region_id", rs.getString("region_id"));
        countryData.put("region_name", rs.getString("region_name"));
        countryData.put("region_code", rs.getString("region_code"));
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;
      LOG.error(exceptionMessage, e);
      return countryData;
    }
    return countryData;
  }

  private List<Map<String, String>> getData(String query) {
    LOG.debug(">> executeQuery(query='{}')", query);
    List<Map<String, String>> locationsList = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> locationData = new HashMap<String, String>();
        locationData.put("id", rs.getString("id"));
        locationData.put("name", rs.getString("name"));
        locationData.put("code", rs.getString("code"));

        locationsList.add(locationData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
      return null;
    }

    LOG.debug("<< executeQuery():getLocations.size={}", locationsList.size());
    return locationsList;
  }

  @Override
  public Map<String, String> getLocation(int typeID, int locationID) {
    Map<String, String> locationData = new HashMap<String, String>();
    LOG.debug(">> getLocation( typeID = {} )", typeID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT le.id, le.name, le.code ");
    query.append("FROM loc_elements le ");
    query.append("INNER JOIN loc_element_types let ON let.id=le.element_type_id  ");
    query.append("WHERE let.id =  ");
    query.append(typeID);
    query.append(" AND le.id =  ");
    query.append(locationID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        locationData.put("id", rs.getString("id"));
        locationData.put("name", rs.getString("name"));
        locationData.put("code", rs.getString("code"));
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the Country for the user {}.", locationID, e);
    }

    LOG.debug("-- getLocation() > Calling method executeQuery to get the results");
    return locationData;
  }

  @Override
  public List<Map<String, String>> getLocationsByType(int typeID) {
    LOG.debug(">> getLocationsByType( )");

    StringBuilder query = new StringBuilder();
    query.append("SELECT leo.id, leo.name, leo.code, leo.element_type_id, leo.parent_id as region_id, ");
    query.append("let.name as region_name, let.code as region_code, let.element_type_id as region_element_type ");
    query.append("FROM loc_elements leo ");
    query.append("LEFT JOIN loc_elements let ON leo.parent_id = let.id   ");
    query.append("INNER JOIN loc_element_types letd ON letd.id = leo.element_type_id  ");
    query.append("WHERE letd.id =  ");
    query.append(typeID);
    query.append(" ORDER BY leo.name ");

    List<Map<String, String>> locationsList = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> locationData = new HashMap<String, String>();
        locationData.put("id", rs.getString("id"));
        locationData.put("name", rs.getString("name"));
        locationData.put("code", rs.getString("code"));
        // Region
        // locationData.put("region_id", rs.getString("region_id"));
        // locationData.put("region_name", rs.getString("region_name"));
        // locationData.put("region_code", rs.getString("region_code"));

        locationsList.add(locationData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;
      LOG.error(exceptionMessage, e);
      return null;
    }

    LOG.debug("<< executeQuery():getLocationsByType.size={}", locationsList.size());
    return locationsList;

  }

  @Override
  public Map<String, String> getRegion(int regionID) {
    LOG.debug(">> getLocationsByType( )");

    StringBuilder query = new StringBuilder();
    query.append("SELECT le.id as region_id, le.name as region_name, le.code as region_code ");
    query.append("FROM loc_elements le ");
    query.append("INNER JOIN loc_element_types letd ON letd.id = le.element_type_id  ");
    query.append("WHERE letd.id =  ");
    query.append(APConstants.LOCATION_ELEMENT_TYPE_REGION);
    query.append(" AND le.id= ");
    query.append(regionID);

    LOG.debug("-- getIPElement() > Calling method executeQuery to get the results");
    Map<String, String> regionData = new HashMap<String, String>();
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        // Region
        regionData.put("region_id", rs.getString("region_id"));
        regionData.put("region_name", rs.getString("region_name"));
        regionData.put("region_code", rs.getString("region_code"));
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;
      LOG.error(exceptionMessage, e);
      return regionData;
    }
    return regionData;
  }

}
