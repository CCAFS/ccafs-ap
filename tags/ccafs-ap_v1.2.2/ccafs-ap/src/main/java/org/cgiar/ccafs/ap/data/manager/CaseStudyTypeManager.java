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

package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.CaseStudyTypeManagerImpl;
import org.cgiar.ccafs.ap.data.model.CaseStudy;
import org.cgiar.ccafs.ap.data.model.CaseStudyType;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(CaseStudyTypeManagerImpl.class)
public interface CaseStudyTypeManager {


  /**
   * Get a list of case study type object corresponding to the given array of ids
   * 
   * @param ids - Array of case study type identifiers
   * @return a list of case study type objects
   */
  public List<CaseStudyType> getCaseStudyTypeList(String[] ids);

  /**
   * Get a list with all the case study types
   * 
   * @return a list of case study type objects
   */
  public CaseStudyType[] getCaseStudyTypes();

  /**
   * Return an array of case study type objects related to
   * the case study given
   * 
   * @param caseStudy - The case study object
   * @return an array of Case study objects
   */
  public List<CaseStudyType> getCaseStudyTypes(CaseStudy caseStudy);

  /**
   * Save into the database a case study information
   * 
   * @param caseStudy - The object to save
   * @return true if was successfully save, false otherwise
   */
  public boolean saveCaseStudyTypes(CaseStudy caseStudy);
}
