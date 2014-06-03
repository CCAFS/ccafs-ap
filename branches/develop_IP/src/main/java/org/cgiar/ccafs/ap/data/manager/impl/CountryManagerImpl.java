package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.CountryDAO;
import org.cgiar.ccafs.ap.data.manager.CountryManager;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CountryManagerImpl implements CountryManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(CountryManagerImpl.class);
  private CountryDAO countryDAO;

  @Inject
  public CountryManagerImpl(CountryDAO countryDAO) {
    this.countryDAO = countryDAO;
  }

  @Override
  public Country[] getCountriesByRegion(String regionID) {
    List<Map<String, String>> countryDataList = countryDAO.getCountriesByRegion(regionID);
    Country[] countries = new Country[countryDataList.size()];

    int c = 0;
    for (Map<String, String> countryData : countryDataList) {
      Country country = new Country();
      country.setId(countryData.get("id"));
      country.setName(countryData.get("name"));

      Region regionTemp = new Region(Integer.parseInt(countryData.get("region_id")), countryData.get("region_name"));
      country.setRegion(regionTemp);

      countries[c] = country;
      c++;
    }
    return countries;
  }

  @Override
  public Country getCountry(String id) {
    Map<String, String> countryData = countryDAO.getCountryInformation(id);

    if (countryData == null) {
      return null;
    }
    return new Country(countryData.get("id"), countryData.get("name"));
  }

  @Override
  public Country[] getCountryList() {
    List<Map<String, String>> countryDataList = countryDAO.getCountriesList();
    Map<String, String> countryData;

    if (countryDataList == null) {
      return null;
    }

    Country[] countryList = new Country[countryDataList.size()];
    for (int c = 0; c < countryDataList.size(); c++) {
      countryData = countryDataList.get(c);
      Country countryTemp = new Country(countryData.get("id"), countryData.get("name"));

      // Temporal region
      Region regionTemp = new Region(Integer.parseInt(countryData.get("region_id")), countryData.get("region_name"));
      countryTemp.setRegion(regionTemp);

      countryList[c] = countryTemp;
    }

    if (countryDataList.size() > 0) {
      return countryList;
    }
    LOG.warn("Country list loaded is empty.");
    return null;
  }

  @Override
  public List<Country> getCountryList(String[] ids) {
    List<Country> countries = new ArrayList<>();
    for (Country country : getCountryList()) {
      for (String id : ids) {
        if (country.getId().equals(id)) {
          countries.add(country);
        }
      }
    }
    return countries;
  }
}
