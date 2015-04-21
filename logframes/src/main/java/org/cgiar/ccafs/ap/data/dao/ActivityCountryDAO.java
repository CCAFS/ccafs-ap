package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLActivityCountryDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLActivityCountryDAO.class)
public interface ActivityCountryDAO {

  /**
   * Delete all the countries related to the activity given
   * 
   * @param activityID - activity identifier
   * @return true if the countries was successfully deleted. False otherwise.
   */
  public boolean deleteActivityCountries(int activityID);

  /**
   * Get all the country locations related to the activity given
   * 
   * @param activityID - Activity identifier
   * @return a List of maps with the information
   */
  public List<Map<String, String>> getActivityCountries(int activityID);

  /**
   * Save the activity country given into the database.
   * 
   * @param activityID - The activity identifier.
   * @param countryID - The country identifier.
   * @return true if the data was successfully saved. False otherwise
   */
  public boolean saveActivityCountry(int activityID, String countryID);
}
