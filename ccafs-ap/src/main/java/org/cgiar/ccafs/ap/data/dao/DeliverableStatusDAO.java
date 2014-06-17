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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLDeliverableStatusDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLDeliverableStatusDAO.class)
public interface DeliverableStatusDAO {

  /**
   * Get a list whit all the deliverables status
   * 
   * @return a Map whit the status of deliverables.
   */
  public List<Map<String, String>> getDeliverableStatus();

  /**
   * Update the status of the deliverable identified with the given id.
   * 
   * @param deliverableId - deliverable identifier.
   * @param statusId - status id.
   * @return true if the status was successfully update, false if any problem occur.
   */
  public boolean setDeliverableStatus(int deliverableId, int statusId);
}
