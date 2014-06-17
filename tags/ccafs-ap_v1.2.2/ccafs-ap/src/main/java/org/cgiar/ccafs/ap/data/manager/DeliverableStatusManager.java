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

import org.cgiar.ccafs.ap.data.manager.impl.DeliverableStatusManagerImpl;
import org.cgiar.ccafs.ap.data.model.DeliverableStatus;

import com.google.inject.ImplementedBy;

@ImplementedBy(DeliverableStatusManagerImpl.class)
public interface DeliverableStatusManager {


  /**
   * Get all the deliverables status
   * 
   * @return a List whit all the Deliverable status.
   */
  public DeliverableStatus[] getDeliverableStatus();

  /**
   * Find the deliverable status object identified with the given id.
   * 
   * @param id - Deliverable status identifier.
   * @return a DeliverableStatus object identified with the given id or null if nothing found.
   */
  public DeliverableStatus getDeliverableStatus(String id);

  /**
   * Update the status of the deliverable identified with the given id.
   * 
   * @param id - deliverable id.
   * @param status - new status to be updated.
   * @return true if the update was successfully made, false if any problem appear.
   */
  public boolean setDeliverableStatus(int id, DeliverableStatus status);

}
