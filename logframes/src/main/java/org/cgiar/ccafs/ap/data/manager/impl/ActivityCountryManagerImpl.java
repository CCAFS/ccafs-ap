package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ActivityCountryDAO;
import org.cgiar.ccafs.ap.data.manager.ActivityCountryManager;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivityCountryManagerImpl implements ActivityCountryManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ActivityCountryManagerImpl.class);
  private ActivityCountryDAO activityCountryDAO;

  @Inject
  public ActivityCountryManagerImpl(ActivityCountryDAO activityCountryDAO) {
    this.activityCountryDAO = activityCountryDAO;
  }

  @Override
  public boolean deleteActivityCountries(int activityID) {
    return activityCountryDAO.deleteActivityCountries(activityID);
  }

  @Override
  public List<Country> getActvitiyCountries(int activityID) {
    List<Country> activityCountries = new ArrayList<>();
    List<Map<String, String>> countryDataList = activityCountryDAO.getActivityCountries(activityID);
    for (Map<String, String> CData : countryDataList) {
      Country countryTemp = new Country();
      countryTemp.setId(CData.get("iso2"));
      countryTemp.setName(CData.get("name"));

      // Temporal region
      Region regionTemp = new Region();
      regionTemp.setId(Integer.parseInt(CData.get("region_id")));
      regionTemp.setName(CData.get("region_name"));

      countryTemp.setRegion(regionTemp);

      activityCountries.add(countryTemp);
    }
    return activityCountries;
  }

  @Override
  public boolean saveActivityCountries(List<Country> countries, int activityID) {
    boolean saved = true;
    boolean countrySaved;
    for (Country country : countries) {
      countrySaved = activityCountryDAO.saveActivityCountry(activityID, country.getId());
      if (!countrySaved) {
        saved = false;
      }
    }
    return saved;
  }
}