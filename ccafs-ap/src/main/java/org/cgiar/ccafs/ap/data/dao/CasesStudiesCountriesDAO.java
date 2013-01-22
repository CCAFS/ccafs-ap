package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLCasesStudiesCountriesDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLCasesStudiesCountriesDAO.class)
public interface CasesStudiesCountriesDAO {

  /**
   * Get a list with all the countries related with the case studies
   * 
   * @param caseStudyIds
   * @return a list of maps with all the information
   */
  public List<Map<String, String>> getCasesStudiesCountries(int caseStudyId);

  /**
   * Save a set of countries related to a given case studies
   * 
   * @param caseStudiesId Case studies identifier
   * @param countriesIds
   * @return true if the information was successfully saved. False otherwise
   */
  public boolean saveCasesStudiesCountries(int caseStudiesId, ArrayList<String> countriesIds);
}
