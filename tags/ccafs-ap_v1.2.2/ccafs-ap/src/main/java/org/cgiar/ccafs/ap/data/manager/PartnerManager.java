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

import org.cgiar.ccafs.ap.data.manager.impl.PartnerManagerImpl;
import org.cgiar.ccafs.ap.data.model.Partner;

import com.google.inject.ImplementedBy;

@ImplementedBy(PartnerManagerImpl.class)
public interface PartnerManager {

  /**
   * Get all partners.
   * 
   * @return an array of Partner objects, or null if there are no partners.
   */
  public Partner[] getAllPartners();

  /**
   * Get the partner identified with the given id.
   * 
   * @param id - identifier.
   * @return a Partner object or null if no partner were found.
   */
  public Partner getPartner(int id);

  /**
   * Get all partners.
   * 
   * @return an array of Partner objects.
   */
  public Partner[] getPartnerForXML();

  /**
   * Get a list of partners that fill the conditions given or all partners
   * if there is no condition.
   * 
   * @param countryID - Country identifier or null if no filter by country is needed
   * @param partnerTypeID - Partner type identifier or null if no filter by type is needed
   * @return a list with partners who satisfy the conditions
   */
  public Partner[] getPartnersByFilter(String countryID, String partnerTypeID);
}
