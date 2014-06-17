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

import org.cgiar.ccafs.ap.data.manager.impl.ActivityCountryManagerImpl;
import org.cgiar.ccafs.ap.data.model.Country;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ActivityCountryManagerImpl.class)
public interface ActivityCountryManager {

  /**
   * Delete all the countries related to the activity given
   * 
   * @param activityID - activity identifier
   * @return true if the countries was successfully deleted. False otherwise.
   */
  public boolean deleteActivityCountries(int activityID);

  /**
   * Get all the countries related to the activity given
   * 
   * @param activityID - the activity identifier
   * @return a list of country objects with the information
   */
  public List<Country> getActvitiyCountries(int activityID);

  /**
   * Save the activity countries given.
   * 
   * @param activityID - The activity identifier.
   * @param countries - The country list with the data.
   * @return true if ALL the countries were successfully saved. False otherwise
   */
  public boolean saveActivityCountries(List<Country> countries, int activityID);
}