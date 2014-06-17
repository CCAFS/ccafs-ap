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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLContactPersonDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLContactPersonDAO.class)
public interface ContactPersonDAO {

  /**
   * Delete all the contact persons related to the given activity from the DAO
   * 
   * @param activityID - activity identifier
   * @return true if was successfully deleted. False otherwise.
   */
  public boolean deleteContactPersons(int activityID);

  /**
   * Get a all the information of all the contact persons of a given activity.
   * 
   * @param activityID - activity identifier.
   * @return a List of Maps of contact persons.
   */
  public List<Map<String, String>> getContactPersons(int activityID);

  /**
   * Save a contact persons into the DAO
   * 
   * @param contactPerson - The information to save
   * @param activityID - The activity identifier
   * @return true if the contact person was successfully saved. False otherwise
   */
  public boolean saveContactPersons(Map<String, String> contactPerson, int activityID);

}
