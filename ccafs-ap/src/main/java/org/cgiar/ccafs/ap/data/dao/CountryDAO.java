package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLCountryDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLCountryDAO.class)
public interface CountryDAO {

  /**
   * Get all the countries from the DAO
   * 
   * @return a List of Maps with the information
   */
  public List<Map<String, String>> getCountriesList();

  public Map<String, String> getCountryInformation(String id);

}
