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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLCommunicationDAO;

import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLCommunicationDAO.class)
public interface CommunicationDAO {

  /**
   * Get all the communications that belongs to the given leader and corresponds
   * to the given logframe
   * 
   * @param leader_id
   * @param logframe_id
   * @return a list of maps with the information
   */
  public Map<String, String> getCommunicationReport(int leader_id, int logframe_id);

  /**
   * Save the data communication.
   * 
   * @param communicationData
   * @return true if the information was saved successfully. False otherwise.
   */
  public boolean saveCommunicationReport(Map<String, String> communicationData);
}
