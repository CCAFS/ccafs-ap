package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ActivityCountryDAO;
import org.cgiar.ccafs.ap.data.manager.ActivityCountryManager;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.CountryLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class ActivityCountryManagerImpl implements ActivityCountryManager {

  private ActivityCountryDAO activityCountryDAO;

  @Inject
  public ActivityCountryManagerImpl(ActivityCountryDAO activityCountryDAO) {
    this.activityCountryDAO = activityCountryDAO;
  }

  @Override
  public List<CountryLocation> getActvitiyCountries(int activityID) {
    List<CountryLocation> activityCountries = new ArrayList<>();
    List<Map<String, String>> countryDataList = activityCountryDAO.getActivityCountries(activityID);
    for (Map<String, String> CData : countryDataList) {
      Country countryTemp = new Country();
      countryTemp.setId(CData.get("iso2"));
      countryTemp.setName(CData.get("name"));

      CountryLocation clTemp = new CountryLocation();
      clTemp.setCountry(countryTemp);
      clTemp.setDetails(CData.get("details"));
      activityCountries.add(clTemp);
    }
    return activityCountries;
  }
}
