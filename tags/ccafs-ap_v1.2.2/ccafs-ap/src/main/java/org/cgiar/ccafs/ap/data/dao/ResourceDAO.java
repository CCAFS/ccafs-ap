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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLResourceDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLResourceDAO.class)
public interface ResourceDAO {

  /**
   * Get the resources related to the activity given
   * 
   * @param activityID - Activity identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getResources(int activityID);

  /**
   * Remove all the resources related to the given activity
   * 
   * @param activityID - activity identifier
   * @return true if the records were successfully deleted. False otherwise.
   */
  public boolean removeResources(int activityID);

  /**
   * Save the resource information into the database.
   * 
   * @param resourceData - the data to be saved
   * @return true if the information was successfully saved. False otherwise
   */
  public boolean saveResource(Map<String, String> resourceData);
}
