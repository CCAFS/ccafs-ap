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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLCaseStudyTypeDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLCaseStudyTypeDAO.class)
public interface CaseStudyTypeDAO {

  /**
   * Get a list with all the case study types
   * 
   * @return a Map with the types of case studies
   */
  public List<Map<String, String>> getCaseStudyTypes();

  /**
   * Get a list with all the case study types related to a case study given
   * 
   * @param caseStudyId - The case study identifier
   * @return a Map with the types of case studies
   */
  public List<Map<String, String>> getCaseStudyTypes(int caseStudyId);

  /**
   * Save into the database a list of types related to the case study given
   * 
   * @param caseStudyId - the case study identifier
   * @param typeList - The list of case study type identifier
   * @return True if the elements was successfully saved, false otherwise
   */
  public boolean saveCaseStudyTypes(int caseStudyId, int[] caseStudyTypeIds);
}
