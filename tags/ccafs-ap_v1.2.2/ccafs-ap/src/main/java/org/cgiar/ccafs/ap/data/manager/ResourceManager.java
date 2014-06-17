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

import org.cgiar.ccafs.ap.data.manager.impl.ResourceManagerImpl;
import org.cgiar.ccafs.ap.data.model.Resource;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ResourceManagerImpl.class)
public interface ResourceManager {

  /**
   * Get the resources information related to the activity given
   * 
   * @param activityID - activity identifier
   * @return a list of Resources objects with the information
   */
  public List<Resource> getResources(int activityID);

  /**
   * Delete all the resources related to the activity given from the database.
   * 
   * @param activityID - Activity identifier
   * @return true if the records were successfully removed. False otherwise.
   */
  public boolean removeResources(int activityID);

  /**
   * Save the resources list into the database.
   * 
   * @param resources - The data to be saved.
   * @param activityID - activity identifier
   * @return true if the data was successfully saved. False otherwise
   */
  public boolean saveResources(List<Resource> resources, int activityID);
}
