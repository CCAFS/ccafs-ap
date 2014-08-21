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

import org.cgiar.ccafs.ap.data.manager.impl.NextUserManagerImpl;
import org.cgiar.ccafs.ap.data.model.NextUser;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andrés Gallego
 */
@ImplementedBy(NextUserManagerImpl.class)
public interface NextUserManager {


  /**
   * This method removes a set of activities that belongs to a specific Deliverable.
   * 
   * @param deliverableID is the Deliverable identifier.
   * @return true if the set of activities were successfully deleted, false otherwise.
   */
  public boolean deleteNextUserByDeliverable(int deliverableID);

  /**
   * This method removes a specific activity value from the database.
   * 
   * @param nextUserId is the activity identifier.
   * @return true if the activity was successfully deleted, false otherwise.
   */
  public boolean deleteNextUserById(int nextUserId);

  /**
   * This method gets all the activity information by a given activity ID.
   * 
   * @param nextUserID is the activity identifier.
   * @return a List of activities objects.
   */
  public NextUser getNextUserById(int nextUserID);


  /**
   * This method gets all the activity information by a given Deliverable Id
   * 
   * @param deliverableID - is the Id of the Deliverable
   * @return a List of activities with the activity Information related with the Deliverable
   */
  public List<NextUser> getNextUsersByDeliverableId(int deliverableID);

  /**
   * This method saves the information of the given activity that belong to a specific Deliverable into the database.
   * 
   * @param deliverableID
   * @param nextUser
   * @return true if the activity was saved successfully, false otherwise.
   */
  public boolean saveNextUser(int deliverableID, NextUser nextUser);


}
