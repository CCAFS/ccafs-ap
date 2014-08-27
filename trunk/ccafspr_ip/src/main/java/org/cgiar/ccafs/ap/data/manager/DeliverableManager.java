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

import org.cgiar.ccafs.ap.data.manager.impl.DeliverableManagerImpl;
import org.cgiar.ccafs.ap.data.model.Deliverable;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andrés Gallego B.
 */
@ImplementedBy(DeliverableManagerImpl.class)
public interface DeliverableManager {


  /**
   * This method removes a specific deliverable value from the database.
   *
   * @param deliverableId is the deliverable identifier.
   * @return true if the deliverable was successfully deleted, false otherwise.
   */
  public boolean deleteDeliverable(int deliverableId);

  /**
   * This method removes a set of deliverables that belongs to a specific activity.
   *
   * @param activityID is the activity identifier.
   * @return true if the set of activities were successfully deleted, false otherwise.
   */
  public boolean deleteDeliverablesByActivity(int activityID);

  /**
   * This method gets all the deliverable information by a given deliverable ID.
   *
   * @param deliverableID is the deliverable identifier.
   * @return a List of deliverables objects.
   */
  public Deliverable getDeliverableById(int deliverableID);


  /**
   * This method gets all the deliverables information by a given activity Id
   *
   * @param activityID - is the Id of the activity
   * @return a List of deliverables with the Information related with the activity
   */
  public List<Deliverable> getDeliverablesByActivity(int activityID);

  /**
   * This method saves the information of the given deliverable that belong to a specific activity into the database.
   *
   * @param activityID
   * @param deliverable
   * @return a number greater than 0 representing the new ID assigned by the databse, 0 if the deliverable was updated
   *         or -1 is some error occurred.
   */
  public int saveDeliverable(int activityID, Deliverable deliverable);


}
