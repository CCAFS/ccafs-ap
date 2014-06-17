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

import org.cgiar.ccafs.ap.data.manager.impl.ActivityRegionManagerImpl;
import org.cgiar.ccafs.ap.data.model.Region;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ActivityRegionManagerImpl.class)
public interface ActivityRegionManager {

  /**
   * Delete all the regions related to the activity given
   * 
   * @param activityID - activity identifier
   * @return true if the regions was successfully deleted. False otherwise.
   */
  public boolean deleteActivityRegions(int activityID);

  /**
   * Get all the regions related to the activity given
   * 
   * @param activityID - the activity identifier
   * @return a list of region objects with the information
   */
  public List<Region> getActvitiyRegions(int activityID);

  /**
   * Save the activity regions given.
   * 
   * @param activityID - The activity identifier.
   * @param regions - The region list with the data.
   * @return true if ALL the regions were successfully saved. False otherwise
   */
  public boolean saveActivityRegions(List<Region> regions, int activityID);
}