package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLCountryDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLCountryDAO.class)
public interface CountryDAO {

  /**
   * Get all the countries that belongs to the given region.
   * 
   * @param regionID - Region identifier
   * @return a list of maps with the information.
   */
  public List<Map<String, String>> getCountriesByRegion(String regionID);

  /**
   * Get all the countries from the DAO
   * 
   * @return a List of Maps with the information
   */
  public List<Map<String, String>> getCountriesList();

  /**
   * Get the country information of the given country identifier.
   * 
   * @param id - identifier
   * @return
   */
  public Map<String, String> getCountryInformation(String id);
}
