package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.CountryDAO;
import org.cgiar.ccafs.ap.data.manager.CountryManager;
import org.cgiar.ccafs.ap.data.model.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class CountryManagerImpl implements CountryManager {

  private CountryDAO countryDAO;

  @Inject
  public CountryManagerImpl(CountryDAO countryDAO) {
    this.countryDAO = countryDAO;
  }

  @Override
  public Country[] getCountriesList() {
    List<Map<String, String>> countryDataList = countryDAO.getCountriesList();
    Map<String, String> countryData;

    if (countryDataList == null) {
      return null;
    }

    Country[] countriesList = new Country[countryDataList.size()];
    for (int c = 0; c < countryDataList.size(); c++) {
      countryData = countryDataList.get(c);
      countriesList[c] = new Country(countryData.get("id"), countryData.get("name"));
    }

    if (countryDataList.size() > 0) {
      return countriesList;
    }
    return null;
  }

  @Override
  public List<Country> getCountriesList(String[] ids) {
    List<Country> countries = new ArrayList<>();
    for (Country country : getCountriesList()) {
      for (String id : ids) {
        if (country.getId().equals(id)) {
          countries.add(country);
        }
      }
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
}
