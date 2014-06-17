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

import org.cgiar.ccafs.ap.data.manager.impl.ActivityObjectiveManagerImpl;
import org.cgiar.ccafs.ap.data.model.ActivityObjective;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ActivityObjectiveManagerImpl.class)
public interface ActivityObjectiveManager {

  /**
   * Delete the objectives related to the activity given.
   * 
   * @param activityId - Activity identifier
   * @return true if was successfully saved. False otherwise.
   */
  public boolean deleteActivityObjectives(int activityId);

  /**
   * Get the objectives related to the activity given
   * 
   * @param activityID - activity identifier
   * @return a List of activityObjective objects with the information
   */
  public List<ActivityObjective> getActivityObjectives(int activityID);

  /**
   * Save all the objectives that belongs to the activity given
   * 
   * @param objectives - The data to be saved
   * @param activityID - THe activity identifier
   * @return true if ALL the objective was successfully saved. False otherwise
   */
  public boolean saveActivityObjectives(List<ActivityObjective> objectives, int activityID);
}
