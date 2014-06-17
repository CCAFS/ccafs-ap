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
