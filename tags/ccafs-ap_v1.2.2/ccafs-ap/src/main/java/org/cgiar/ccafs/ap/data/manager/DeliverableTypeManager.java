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

import org.cgiar.ccafs.ap.data.manager.impl.DeliverableTypeManagerImpl;
import org.cgiar.ccafs.ap.data.model.DeliverableType;

import com.google.inject.ImplementedBy;

@ImplementedBy(DeliverableTypeManagerImpl.class)
public interface DeliverableTypeManager {


  /**
   * Get the deliverable type identified with the given id.
   * 
   * @param id - Identifier.
   * @return a DeliverableType object or null if nothing was found.
   */
  public Object getDeliverableType(String id);

  /**
   * Get all the deliverables types
   * 
   * @return a List whit all the Deliverable types.
   */
  public DeliverableType[] getDeliverableTypes();
}
