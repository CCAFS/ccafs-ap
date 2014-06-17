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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLLeaderDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLLeaderDAO.class)
public interface LeaderDAO {

  /**
   * Find the Leader of a given activity id.
   * 
   * @param activityID - activity identifier.
   * @return a Map with the leader data or null if no leader was found.
   */
  public Map<String, String> getActivityLeader(int activityID);

  /**
   * Return a list with the information of all leaders.
   * 
   * @return a list of maps with all the leaders.
   */
  public List<Map<String, String>> getAllLeaders();

  /**
   * Find the Leader of a given user id.
   * 
   * @param activityID - user identifier.
   * @return a Map with the leader data or null if no leader was found.
   */
  public Map<String, String> getUserLeader(int userID);

}
