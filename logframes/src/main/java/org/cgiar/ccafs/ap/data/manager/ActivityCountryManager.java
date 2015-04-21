package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.ActivityCountryManagerImpl;
import org.cgiar.ccafs.ap.data.model.Country;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ActivityCountryManagerImpl.class)
public interface ActivityCountryManager {

  /**
   * Delete all the countries related to the activity given
   * 
   * @param activityID - activity identifier
   * @return true if the countries was successfully deleted. False otherwise.
   */
  public boolean deleteActivityCountries(int activityID);

  /**
   * Get all the countries related to the activity given
   * 
   * @param activityID - the activity identifier
   * @return a list of country objects with the information
   */
  public List<Country> getActvitiyCountries(int activityID);

  /**
   * Save the activity countries given.
   * 
   * @param activityID - The activity identifier.
   * @param countries - The country list with the data.
   * @return true if ALL the countries were successfully saved. False otherwise
   */
  public boolean saveActivityCountries(List<Country> countries, int activityID);
}