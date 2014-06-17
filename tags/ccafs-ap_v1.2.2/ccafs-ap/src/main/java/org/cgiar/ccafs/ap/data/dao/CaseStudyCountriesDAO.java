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
