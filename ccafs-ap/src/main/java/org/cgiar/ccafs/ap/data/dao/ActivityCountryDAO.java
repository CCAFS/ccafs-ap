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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLActivityCountryDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLActivityCountryDAO.class)
public interface ActivityCountryDAO {

  /**
   * Delete all the countries related to the activity given
   * 
   * @param activityID - activity identifier
   * @return true if the countries was successfully deleted. False otherwise.
   */
  public boolean deleteActivityCountries(int activityID);

  /**
   * Get all the country locations related to the activity given
   * 
   * @param activityID - Activity identifier
   * @return a List of maps with the information
   */
  public List<Map<String, String>> getActivityCountries(int activityID);

  /**
   * Save the activity country given into the database.
   * 
   * @param activityID - The activity identifier.
   * @param countryID - The country identifier.
   * @return true if the data was successfully saved. False otherwise
   */
  public boolean saveActivityCountry(int activityID, String countryID);
}
