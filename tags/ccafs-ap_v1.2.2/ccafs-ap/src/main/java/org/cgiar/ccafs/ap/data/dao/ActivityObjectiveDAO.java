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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLActivityObjectiveDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLActivityObjectiveDAO.class)
public interface ActivityObjectiveDAO {

  /**
   * Delete all the objectives related to the activity given.
   * 
   * @param activityID - Activity identifier
   * @return true if was successfully deleted. False otherwise
   */
  public boolean deleteActivityObjectives(int activityID);

  /**
   * Get the objectives related to the activity given
   * 
   * @param activityID - the activity identifier
   * @return a list of activityObjective objects with the information
   */
  public List<Map<String, String>> getActivityObjectives(int activityID);

  /**
   * Save the objectives corresponding to the activity given
   * 
   * @param objectives - The data to save into the DAO
   * @param activityID - Activity identifier
   * @return true if was successfully saved. False otherwise
   */
  public boolean saveActivityObjectives(Map<String, String> objectives, int activityID);
}
