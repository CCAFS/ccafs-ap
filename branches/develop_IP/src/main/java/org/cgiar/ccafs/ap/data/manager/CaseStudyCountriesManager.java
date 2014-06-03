package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.CaseStudyCountriesManagerImpl;
import org.cgiar.ccafs.ap.data.model.CaseStudy;
import org.cgiar.ccafs.ap.data.model.Country;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(CaseStudyCountriesManagerImpl.class)
public interface CaseStudyCountriesManager {

  /**
   * Get a list with all the cases studies countries related with the
   * case study given
   * 
   * @param caseStudy case study object with the information
   * @return a list of countries objects
   */
  public List<Country> getCaseStudyCountriesList(CaseStudy caseStudy);
}
