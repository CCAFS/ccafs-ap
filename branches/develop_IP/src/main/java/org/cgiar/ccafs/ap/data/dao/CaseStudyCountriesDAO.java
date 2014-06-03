package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLCaseStudyCountriesDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLCaseStudyCountriesDAO.class)
public interface CaseStudyCountriesDAO {

  /**
   * Get a list with all the countries related with the case study
   * 
   * @param caseStudyId the case study identifier
   * @return a list of maps with all the information
   */
  public List<Map<String, String>> getCaseStudyCountries(int caseStudyId);

  /**
   * Save a set of countries related to a given case study
   * 
   * @param caseStudyId Case study identifier
   * @param countriesIds
   * @return true if the information was successfully saved. False otherwise
   */
  public boolean saveCaseStudyCountries(int caseStudyId, ArrayList<String> countriesIds);
}
