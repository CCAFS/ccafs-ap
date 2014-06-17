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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLPartnerDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLPartnerDAO.class)
public interface PartnerDAO {

  /**
   * Get a list of all Partners.
   * 
   * @return a List of Map of partner data.
   */
  public List<Map<String, String>> getAllPartners();

  /**
   * Get the information of a partner identified with the given id.
   * 
   * @param id - identifier.
   * @return a Map with the information of the partner, or null if there is is not a partner identified with
   *         the given id.
   */
  public Map<String, String> getPartner(int id);

  /**
   * Get a list of partners that fill the conditions given or all partners
   * if there is no condition.
   * 
   * @param countryID - Country identifier or null if no filter by country is needed
   * @param partnerTypeID - Partner type identifier or null if no filter by type is needed
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getPartnersByFilter(String countryID, String partnerTypeID);

  /**
   * Get a list of all Partners.
   * 
   * @return a List of Maps of partner data.
   */
  public List<Map<String, String>> getPartnersForXML();

  /**
   * Get all partners of the activity identified
   * whit activityID from the DAO.
   * 
   * @param activityID the activity identifier
   * @return a List of Maps with the information.
   */
  public List<Map<String, String>> getPartnersList(int activityID);
}
