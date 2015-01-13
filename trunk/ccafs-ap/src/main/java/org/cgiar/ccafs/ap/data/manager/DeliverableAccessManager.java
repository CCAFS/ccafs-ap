/*****************************************************************
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
 *****************************************************************/

package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.DeliverableAccessManagerImpl;
import org.cgiar.ccafs.ap.data.model.DeliverableAccess;

import com.google.inject.ImplementedBy;


/**
 * @author Hernán David Carvajal
 */

@ImplementedBy(DeliverableAccessManagerImpl.class)
public interface DeliverableAccessManager {

  /**
   * This method gets the deliverable data access information
   * of the deliverable identified by the value received as
   * parameter.
   * 
   * @param deliverableID - Deliverable identifier
   * @return a deliverableAccess object with the information
   */
  public DeliverableAccess getDeliverableAccessData(int deliverableID);

  /**
   * This method saves all the deliverable data access information
   * 
   * @param deliverableAccessData object with the information to be saved
   * @return true if the information was saved successfully, false otherwise.
   */
  public boolean saveDeliverableAccessData(DeliverableAccess deliverableAccess, int deliverableID);
}
