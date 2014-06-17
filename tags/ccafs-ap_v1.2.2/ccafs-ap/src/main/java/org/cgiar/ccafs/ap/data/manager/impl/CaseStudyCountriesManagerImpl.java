/*
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 */

package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.CaseStudyCountriesDAO;
import org.cgiar.ccafs.ap.data.manager.CaseStudyCountriesManager;
import org.cgiar.ccafs.ap.data.model.CaseStudy;
import org.cgiar.ccafs.ap.data.model.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CaseStudyCountriesManagerImpl implements CaseStudyCountriesManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(CaseStudyCountriesManagerImpl.class);
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
