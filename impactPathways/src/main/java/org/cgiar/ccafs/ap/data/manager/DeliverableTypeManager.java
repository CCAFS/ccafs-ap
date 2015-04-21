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

import org.cgiar.ccafs.ap.data.manager.impl.DeliverableTypeManagerImpl;
import org.cgiar.ccafs.ap.data.model.DeliverableType;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andrés Gallego B.
 */
@ImplementedBy(DeliverableTypeManagerImpl.class)
public interface DeliverableTypeManager {


  /**
   * This method removes a specific deliverable type value from the database.
   *
   * @param deliverableTypeId is the deliverable type identifier.
   * @return true if the deliverable type was successfully deleted, false otherwise.
   */
  public boolean deleteDeliverableType(int deliverableTypeId);

  /**
   * This method gets all the information of the Deliverable Type that belongs to another Deliverable Type
   *
   * @return a List of Deliverable Type related to a Parent Deliverable Type
   */
  public List<DeliverableType> getDeliverableSubTypes();

  /**
   * This method gets all the deliverable information by a given deliverable type ID.
   *
   * @param deliverableID is the deliverable type identifier.
   * @return a List of deliverables type objects.
   */
  public DeliverableType getDeliverableTypeById(int deliverableTypeID);

  /**
   * This method gets all the deliverables type that are main categories.
   *
   * @return a List of deliverables type with the Information requested.
   */
  public List<DeliverableType> getDeliverableTypes();

  /**
   * This method gets all the Deliverables type information by a given deliverable type ID
   *
   * @param typeID is the deliverable main type or parent id.
   * @return a List of the Deliverables Type Information related to a specific Deliverable Type
   */
  public List<DeliverableType> getDeliverableTypes(int typeID);

  /**
   * This method saves the information of the given deliverable type into the database.
   *
   * @param deliverableTypeData
   * @return true if the activity was saved successfully, false otherwise.
   */
  public boolean saveDeliverableType(DeliverableType deliverableTypeData);


}
