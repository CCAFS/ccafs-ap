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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLActivityPartnerDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLActivityPartnerDAO.class)
public interface ActivityPartnerDAO {

  /**
   * Get all activity partners of the activity identified
   * whit activityID and the partner identified with partnerID
   * from the DAO.
   * 
   * @param activityID the activity identifier
   * @return a List of Maps with the information or null
   *         if not exists .
   */
  public List<Map<String, String>> getActivityPartnersList(int activityID);

  /**
   * Get the number of activity partners that has a specific activity.
   * 
   * @param activityID - Activity identifier
   * @return an integer representing the number of partners that has the specified activity.
   */
  public int getPartnersCount(int activityID);

  /**
   * Remove all the activity parters that belongs to a given activity.
   * 
   * @param activityID - activity identifier.
   * @return true if all the activity partners were successfully removed. False otherwise.
   */
  public boolean removeActivityPartners(int activityID);

  /**
   * Save all the activity partners into de database.
   * 
   * @param activityPartnersData - List of Maps with the information of each activity partner to be added.
   * @return true if all the information was successfully saved. False otherwise.
   */
  public boolean saveActivityPartnerList(List<Map<String, Object>> activityPartnersData);

}
