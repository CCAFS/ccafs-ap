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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLLeverageDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLLeverageDAO.class)
public interface LeverageDAO {

  /**
   * Get all the leverages that belongs to the given leader and
   * corresponding to the given logframe.
   * 
   * @param leader_id
   * @param logframe_id
   * @return a list of maps with the information.
   */
  public List<Map<String, String>> getLeverages(int leader_id, int logframe_id);

  /**
   * Remove the leverages that belongs to the given leader for the corresponding
   * logframe from the database.
   * 
   * @param leader_id
   * @param logframe_id
   * @return true if the leverages were successfully removed. False otherwise.
   */
  public boolean removeLeverages(int leader_id, int logframe_id);

  /**
   * Save the leverages of the leader for the given logframe.
   * 
   * @param leverages - List of leverages
   * @param leader
   * @param logframe
   * @return true if the information was successfully saved. False otherwise.
   */
  public boolean saveLeverages(List<Map<String, String>> leverages, int activity_leader_id);
}
