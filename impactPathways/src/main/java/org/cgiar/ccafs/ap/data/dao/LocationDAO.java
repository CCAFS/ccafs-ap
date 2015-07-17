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
package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLLocationDAO;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andrés Galelgo
 * @author Héctor Fabio Tobón R.
 */
@ImplementedBy(MySQLLocationDAO.class)
public interface LocationDAO {

  /**
   * This method returns all the information of the countries
   * 
   * @return a list of maps with the information of all countries.
   */
  public List<Map<String, String>> getAllCountries();

  /**
   * This method returns all the information of the regions
   * 
   * @return a list of maps with the information of all regions.
   */
  public List<Map<String, String>> getAllRegions();

  /**
   * This method returns the information of a Country by a given Country ID
   * 
   * @param countryID - is the ID of a Country
   * @return a map with the country information.
   */
  public Map<String, String> getCountry(int countryID);

  /**
   * This method returns the information of a specific country identified with the given code.
   * 
   * @param code of the country.
   * @return a Map with the information of the country, or an empty Map if nothing found.
   */
  public Map<String, String> getCountryByCode(String code);

  /**
   * This method returns all the countries in which at least
   * one institution is located
   * 
   * @return a list of maps with the information.
   */
  public List<Map<String, String>> getInstitutionCountries();


  /**
   * This method returns all the information of the location
   * identified by the value received as parameter
   * 
   * @param locationID - Location identifier
   * @return a list of maps with the information of all countries.
   */
  public Map<String, String> getLocation(int locationID);

  /**
   * This method returns the information from an specific location given by the type, and the location
   * 
   * @param typeID, identifier of the location element type
   * @param locationID, identifier of the location
   * @return a map with the information of the location returned.
   */

  public Map<String, String> getLocation(int typeID, int locationID);


  /**
   * This method gets the list of location elements
   * identified by the values received as parameter.
   * 
   * @param locationsIDs - List of locations identifiers
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getLocationsByIDs(String[] locationsIDs);


  /**
   * This method returns all the Locations given by a type
   * 
   * @param typeID, identifier of the location element type
   * @return a list of maps with the information of all locations returned.
   */

  public List<Map<String, String>> getLocationsByType(int typeID);

  /**
   * This method returns all the locations related with the project
   * identified by the value received as parameter.
   * 
   * @param projectID
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getProjectLocations(int projectID);

  /**
   * This method returns the information of a Region by a given Region ID
   * 
   * @param regionID - is the ID of a Region
   * @return a map with the region information.
   */
  public Map<String, String> getRegion(int regionID);

  /**
   * This method removes the location identified by the parameter given
   * 
   * @param LocationID - Location identifier
   * @return true if the location was successfully removed. False otherwise.
   */
  public boolean removeLocation(int locationID);

  /**
   * This method updates ALL the locations related with the project
   * received as parameter
   * 
   * @param projectID - project identifier
   * @return true if the relations were successfully updated. False otherwise.
   */
  public boolean removeProjectLocation(int projectID);

  /**
   * This method saves the information of a new location
   * 
   * @param locationData - Data to be saved
   * @return The last inserted id, 0 if some record was updated or
   *         -1 if any error occurred.
   */
  public int saveLocation(Map<String, String> locationData);

  /**
   * This method saves the geo-coordinates of a location
   * into the database.
   * 
   * @param geopositionData - data to be saved.
   * @return the last inserted id, 0 if some record was updated,
   *         -1 if some error occurred
   */
  public int saveLocationGeoPosition(Map<String, String> geopositionData);

  /**
   * This method relates the location and the project received into
   * the database
   * 
   * @param projectLocationData
   * @return the last inserted id or -1 if any error occurred.
   */
  public int saveProjectLocation(Map<String, String> projectLocationData);

  /**
   * This method updates the project received into the database
   * 
   * @param projectLocationData
   * @return the last inserted id or -1 if any error occurred.
   */
  public int updateProjectGlobal(int projectID, User user, String justification);

}
