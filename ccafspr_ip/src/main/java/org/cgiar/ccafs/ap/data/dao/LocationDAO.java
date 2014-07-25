package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLLocationDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLLocationDAO.class)
public interface LocationDAO {

  /**
   * This method return all the information of the countries
   * 
   * @return a list of maps with the information of all countries.
   */
  public List<Map<String, String>> getAllCountries();

  /**
   * This method return all the information of the regions
   * 
   * @return a list of maps with the information of all regions.
   */
  public List<Map<String, String>> getAllRegions();

  /**
   * This method return the information of a Country by a given Country ID
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
   * This method return the information from an specific location given by the type, and the location
   * 
   * @param typeID, identifier of the location element type
   * @param locationID, identifier of the location
   * @return a map with the information of the location returned.
   */

  public Map<String, String> getLocation(int typeID, int locationID);


  /**
   * This method return all the Locations given by a type
   * 
   * @param typeID, identifier of the location element type
   * @return a list of maps with the information of all locations returned.
   */

  public List<Map<String, String>> getLocationsByType(int typeID);


  /**
   * This method return the information of a Region by a given Region ID
   * 
   * @param regionID - is the ID of a Region
   * @return a map with the region information.
   */
  public Map<String, String> getRegion(int regionID);


}
