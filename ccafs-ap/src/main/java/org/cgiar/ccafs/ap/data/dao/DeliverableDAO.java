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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLDeliverableDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLDeliverableDAO.class)
public interface DeliverableDAO {

  /**
   * Add a new deliverable into the DAO.
   * 
   * @param deliverableData - a Map of objects with the information.
   * @return the identifier assigned to the new record.
   */
  public int addDeliverable(Map<String, Object> deliverableData);

  /**
   * Get a list of deliverables that belongs to the activity
   * identified whit activityID
   * 
   * @param activity identifier
   * @return a list whit Map of deliverables.
   */
  public List<Map<String, String>> getDeliverables(int activityID);

  /**
   * Get the number of deliverables that has a specific activity.
   * 
   * @param activityID - Activity identifier
   * @return an integer representing the number of deliverables that has the specified activity.
   */
  public int getDeliverablesCount(int activityID);

  /**
   * Remove all expected deliverables that belongs of a given activity.
   * 
   * @param activityID - activity identifier.
   * @return true if all deliverables were successfully deleted, false otherwise.
   */
  public boolean removeExpected(int activityID);

  /**
   * Remove all not expected deliverables that belongs of a given activity.
   * 
   * @param activityID - activity identifier.
   * @return true if all deliverables were successfully deleted, false otherwise.
   */
  public boolean removeNotExpected(int activityID);
}
