package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.CasesStudiesCountriesDAO;
import org.cgiar.ccafs.ap.data.manager.CasesStudiesCountriesManager;
import org.cgiar.ccafs.ap.data.model.CasesStudy;
import org.cgiar.ccafs.ap.data.model.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class CasesStudiesCountriesManagerImpl implements CasesStudiesCountriesManager {

  private CasesStudiesCountriesDAO casesStudiesCountriesDAO;

  @Inject
  public CasesStudiesCountriesManagerImpl(CasesStudiesCountriesDAO casesStudiesCountriesDAO) {
    this.casesStudiesCountriesDAO = casesStudiesCountriesDAO;
  }

  @Override
  public List<Country> getCasesStudiesCountriesList(CasesStudy caseStudy) {
    List<Map<String, String>> casesStudiesCountriesDataList =
      casesStudiesCountriesDAO.getCasesStudiesCountries(caseStudy.getId());
    Map<String, String> casesStudiesCountriesData;
    List<Country> casesStudiesCountries = new ArrayList<>();

    if (casesStudiesCountriesDataList.isEmpty()) {
      return null;
    }

    for (int c = 0; c < casesStudiesCountriesDataList.size(); c++) {
      casesStudiesCountriesData = casesStudiesCountriesDataList.get(c);

      Country temporalCountry = new Country(casesStudiesCountriesData.get("id"), casesStudiesCountriesData.get("name"));
      casesStudiesCountries.add(temporalCountry);
    }

    if (casesStudiesCountriesDataList.isEmpty()) {
      return null;
    }
    return casesStudiesCountries;
  }
}
