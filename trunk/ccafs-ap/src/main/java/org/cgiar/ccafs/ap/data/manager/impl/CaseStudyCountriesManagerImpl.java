package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.CaseStudyCountriesDAO;
import org.cgiar.ccafs.ap.data.manager.CaseStudyCountriesManager;
import org.cgiar.ccafs.ap.data.model.CaseStudy;
import org.cgiar.ccafs.ap.data.model.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class CaseStudyCountriesManagerImpl implements CaseStudyCountriesManager {

  private CaseStudyCountriesDAO caseStudyCountriesDAO;

  @Inject
  public CaseStudyCountriesManagerImpl(CaseStudyCountriesDAO caseStudyCountriesDAO) {
    this.caseStudyCountriesDAO = caseStudyCountriesDAO;
  }

  @Override
  public List<Country> getCaseStudyCountriesList(CaseStudy caseStudy) {
    List<Map<String, String>> caseStudyCountriesDataList =
      caseStudyCountriesDAO.getCaseStudyCountries(caseStudy.getId());
    Map<String, String> caseStudyCountriesData;
    List<Country> caseStudyCountries = new ArrayList<>();

    for (int c = 0; c < caseStudyCountriesDataList.size(); c++) {
      caseStudyCountriesData = caseStudyCountriesDataList.get(c);

      Country temporalCountry = new Country(caseStudyCountriesData.get("id"), caseStudyCountriesData.get("name"));
      caseStudyCountries.add(temporalCountry);
    }

    return caseStudyCountries;
  }
}
