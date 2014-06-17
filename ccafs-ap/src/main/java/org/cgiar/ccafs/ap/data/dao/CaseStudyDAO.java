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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLCaseStudyDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLCaseStudyDAO.class)
public interface CaseStudyDAO {

  /**
   * Get all the cases studies that belongs to a given activity leader
   * and are related to the given logframe
   * 
   * @param activityLeaderId - The activity leader identifier
   * @param logframeId - The logframe identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getCaseStudyList(int activityLeaderId, int logframeId);

  /**
   * Get all the case studies that belongs to the given leader and were
   * carried out the given year.
   * 
   * @param activityLeaderId - Activity leader identifier
   * @param year
   * @param countriesIds - Country identifiers
   * @param typesIds - Case study types identifiers
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getCaseStudyListForSummary(int activityLeaderId, int year, String countriesIds,
    String typesIds);

  /**
   * Remove all the case studies related to the activity leader and logframe given
   * 
   * @param activityLeaderId activity leader identifier
   * @param logframeId logframe identifier
   * @return true if it was successfully removed. False otherwise
   */
  public boolean removeAllCaseStudies(int activityLeaderId, int logframeId);

  /**
   * Save a caseStudy into the database
   * 
   * @param caseStudyData - A map of objects with the information
   * @return the identifier assigned to the new record
   */
  public int saveCaseStudy(Map<String, Object> caseStudyData);
}
