package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.ActivityCountryManagerImpl;
import org.cgiar.ccafs.ap.data.model.CountryLocation;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ActivityCountryManagerImpl.class)
public interface ActivityCountryManager {

  /**
   * Get all the countries related to the activity given
   * 
   * @param activityID - the activity identifier
   * @return a list of country objects with the information
   */
  public List<CountryLocation> getActvitiyCountries(int activityID);
}
