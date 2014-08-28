/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 * ***************************************************************
 */
package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.LocationDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Gallego
 * @author Héctor Fabio Tobón R.
 */
public class MySQLLocationDAO implements LocationDAO {

  public static Logger LOG = LoggerFactory.getLogger(MySQLLocationDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLLocationDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getActivityLocations(int activityID) {
    LOG.debug(">> getActivityLocations( activityID={} )", activityID);
    List<Map<String, String>> activityLocationsData = new ArrayList<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT le.id, le.name, le.code, ");
    query.append("lp.id as 'location_parent_id',lp.name as 'location_parent_name', ");
    query.append("lp.code as 'location_parent_code', let.id as 'type_id', let.name as 'type_name', ");
    query.append("leg.id as 'loc_geo_id', leg.latitude as 'loc_geo_latitude', ");
    query.append("leg.longitude as 'loc_geo_longitude' ");
    query.append("FROM loc_elements le ");
    query.append("LEFT JOIN loc_elements lp ON le.parent_id = lp.id  ");
    query.append("INNER JOIN loc_element_types let ON let.id = le.element_type_id  ");
    query.append("LEFT JOIN loc_geopositions leg ON le.geoposition_id = leg.id ");
    query.append("INNER JOIN activity_locations al ON le.id = al.loc_element_id ");
    query.append("WHERE al.activity_id = ");
    query.append(activityID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      Map<String, String> locationData;
      while (rs.next()) {
        locationData = new HashMap<String, String>();
        locationData.put("id", rs.getString("id"));
        locationData.put("name", rs.getString("name"));
        locationData.put("code", rs.getString("code"));
        locationData.put("location_parent_id", rs.getString("location_parent_id"));
        locationData.put("location_parent_code", rs.getString("location_parent_code"));
        locationData.put("location_parent_name", rs.getString("location_parent_name"));
        locationData.put("type_id", rs.getString("type_id"));
        locationData.put("type_name", rs.getString("type_name"));
        locationData.put("loc_geo_id", rs.getString("loc_geo_id"));
        locationData.put("loc_geo_latitude", rs.getString("loc_geo_latitude"));
        locationData.put("loc_geo_longitude", rs.getString("loc_geo_longitude"));
        activityLocationsData.add(locationData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- getActivityLocations() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;
      LOG.error(exceptionMessage, e);
    }

    return activityLocationsData;
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
  public List<Map<String, String>> getInstitutionCountries() {
    LOG.debug(">> getLocationsByType( )");

    StringBuilder query = new StringBuilder();
    query.append("SELECT le.id, le.name, le.code, ");
    query.append("le.parent_id as region_id,let.name as region_name, let.code as region_code ");
    query.append("FROM loc_elements le ");
    query.append("INNER JOIN loc_elements let ON le.parent_id = let.id  ");
    query.append("INNER JOIN loc_element_types letd ON letd.id = le.element_type_id  ");
    query.append("INNER JOIN institutions i ON le.id = country_id ");
    query.append("WHERE letd.id =  ");
    query.append(APConstants.LOCATION_ELEMENT_TYPE_COUNTRY);
    query.append(" GROUP BY le.id ");
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
  public Map<String, String> getLocation(int locationID) {
    LOG.debug(">> getLocation( locationID={} )", locationID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT le.id, le.name, le.code, ");
    query.append("le.parent_id as parent_id, let.name as parent_name, let.code as parent_code ");
    query.append("FROM loc_elements le ");
    query.append("INNER JOIN loc_elements let ON le.parent_id = let.id  ");
    query.append("INNER JOIN loc_element_types letd ON letd.id = le.element_type_id  ");
    query.append("WHERE le.id =  ");
    query.append(locationID);

    Map<String, String> locationData = new HashMap<String, String>();
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        // Location
        locationData.put("id", rs.getString("id"));
        locationData.put("name", rs.getString("name"));
        locationData.put("code", rs.getString("code"));
        // Parent
        locationData.put("parent_id", rs.getString("parent_id"));
        locationData.put("parent_name", rs.getString("parent_name"));
        locationData.put("parent_code", rs.getString("parent_code"));
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;
      LOG.error(exceptionMessage, e);
      return locationData;
    }
    return locationData;
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
  public List<Map<String, String>> getLocationsByIDs(String[] locationsIDs) {
    LOG.debug(">> getLocationsByIDs( locationsIDs={} )", Arrays.toString(locationsIDs));

    StringBuilder query = new StringBuilder();
    query.append("SELECT lo.id, lo.name, lo.code, ");
    query.append("lt.id as 'type_id', lt.name as 'type_name', ");
    query.append("lp.id as 'parent_id', lp.name as 'parent_name' ");
    query.append("FROM loc_elements lo ");
    query.append("LEFT JOIN loc_elements lp ON lo.parent_id = lp.id   ");
    query.append("INNER JOIN loc_element_types lt ON lt.id = lo.element_type_id  ");
    query.append("WHERE lo.id IN ( ");

    for (int c = 0; c < locationsIDs.length; c++) {
      query.append(locationsIDs[c]);
      if (c < locationsIDs.length - 1) {
        query.append(", ");
      }
    }

    query.append(" ) ORDER BY lo.name ");

    List<Map<String, String>> locationsList = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> locationData = new HashMap<String, String>();
        locationData.put("id", rs.getString("id"));
        locationData.put("name", rs.getString("name"));
        locationData.put("code", rs.getString("code"));
        locationData.put("type_id", rs.getString("type_id"));
        locationData.put("type_name", rs.getString("type_name"));
        locationData.put("parent_id", rs.getString("parent_id"));
        locationData.put("parent_name", rs.getString("parent_name"));

        locationsList.add(locationData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- getLocationsByIDs() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;
      LOG.error(exceptionMessage, e);
      return null;
    }

    LOG.debug("<< getLocationsByIDs():locationsList.size={}", locationsList.size());
    return locationsList;
  }

  @Override
  public List<Map<String, String>> getLocationsByType(int typeID) {
    LOG.debug(">> getLocationsByType( )");

    StringBuilder query = new StringBuilder();
    query.append("SELECT lo.id, lo.name, lo.code, ");
    query.append("lt.id as 'type_id', lt.name as 'type_name', ");
    query.append("lp.id as 'parent_id', lp.name as 'parent_name' ");
    query.append("FROM loc_elements lo ");
    query.append("LEFT JOIN loc_elements lp ON lo.parent_id = lp.id   ");
    query.append("INNER JOIN loc_element_types lt ON lt.id = lo.element_type_id  ");
    query.append("WHERE lt.id =  ");
    query.append(typeID);
    query.append(" ORDER BY lo.name ");

    List<Map<String, String>> locationsList = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> locationData = new HashMap<String, String>();
        locationData.put("id", rs.getString("id"));
        locationData.put("name", rs.getString("name"));
        locationData.put("code", rs.getString("code"));
        locationData.put("type_id", rs.getString("type_id"));
        locationData.put("type_name", rs.getString("type_name"));
        locationData.put("parent_id", rs.getString("parent_id"));
        locationData.put("parent_name", rs.getString("parent_name"));

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

  @Override
  public boolean removeActivityLocation(int activityID) {
    LOG.debug(">> removeActivityLocation(activityID={})", activityID);

    StringBuilder query = new StringBuilder();
    query.append("DELETE FROM activity_locations ");
    query.append("WHERE activity_id = ?");

    int rowsDeleted = databaseManager.delete(query.toString(), new Object[] {activityID});
    if (rowsDeleted >= 0) {
      LOG.debug("<< removeActivityLocation():{}", true);
      return true;
    }

    LOG.debug("<< removeActivityLocation():{}", false);
    return false;
  }

  @Override
  public boolean removeLocation(int locationID) {
    LOG.debug(">> removeLocation(locationID={})", locationID);

    StringBuilder query = new StringBuilder();
    query.append("DELETE FROM loc_elements ");
    query.append("WHERE id = ?");

    int rowsDeleted = databaseManager.delete(query.toString(), new Object[] {locationID});
    if (rowsDeleted >= 0) {
      LOG.debug("<< removeLocation():{}", true);
      return true;
    }

    LOG.debug("<< removeLocation():{}", false);
    return false;
  }

  @Override
  public int saveActivityLocation(Map<String, String> activityLocationData) {
    LOG.debug(">> saveActivityLocation(activityLocationData={})", activityLocationData);
    StringBuilder query = new StringBuilder();
    int result = -1;

    query.append("INSERT IGNORE INTO activity_locations (activity_id, loc_element_id) ");
    query.append("VALUES (?, ?) ");

    Object[] values = new Object[2];
    values[0] = activityLocationData.get("activity_id");
    values[1] = activityLocationData.get("loc_element_id");

    result = databaseManager.saveData(query.toString(), values);
    LOG.debug("<< saveActivityLocation():{}", result);
    return result;
  }

  @Override
  public int saveLocation(Map<String, String> locationData) {
    LOG.debug(">> saveLocation(locationData={})", locationData);
    StringBuilder query = new StringBuilder();
    int result = -1;

    // Insert a new project record without id.
    query.append("INSERT INTO loc_elements (id, name, code, parent_id, element_type_id, geoposition_id) ");
    query.append("VALUES (?, ?, ?, ?, ?, ?) ");

    Object[] values = new Object[6];
    values[0] = locationData.get("id");
    values[1] = locationData.get("name");
    values[2] = locationData.get("code");
    values[3] = locationData.get("parent_id");
    values[4] = locationData.get("element_type_id");
    values[5] = locationData.get("geoposition_id");

    result = databaseManager.saveData(query.toString(), values);
    LOG.debug("<< saveLocation()", result);
    return result;
  }

  @Override
  public int saveLocationGeoPosition(Map<String, String> geopositionData) {
    LOG.debug(">> saveLocationGeoPosition(geopositionData={})", geopositionData);
    StringBuilder query = new StringBuilder();
    int result = -1;

    if (geopositionData.get("id") == null) {
      // Insert a new project record.
      query.append("INSERT INTO loc_geopositions (latitude, longitude) ");
      query.append("VALUES (?, ?) ");

      Object[] values = new Object[2];
      values[0] = geopositionData.get("latitude");
      values[1] = geopositionData.get("longitude");

      result = databaseManager.saveData(query.toString(), values);
      LOG.debug("<< saveLocationGeoPosition():{}", result);
    } else {
      // Update project.
      query.append("UPDATE loc_geopositions SET latitude = ?, longitude = ? ");
      query.append("WHERE id = ?");
      Object[] values = new Object[3];
      values[0] = geopositionData.get("latitude");
      values[1] = geopositionData.get("longitude");
      values[2] = geopositionData.get("id");
      result = databaseManager.saveData(query.toString(), values);
    }
    LOG.debug("<< saveLocationGeoPosition():result", result);
    return result;
  }
}
