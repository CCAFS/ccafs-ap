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
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andrés Gallego
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */
@ImplementedBy(NextUserManagerImpl.class)
public interface NextUserManager {


  /**
   * This method removes a set of next users that belongs to a specific Deliverable.
   * 
   * @param deliverableID is the Deliverable identifier.
   * @param user is the user who is making the change.
   * @param justification is the justification statement.
   * @return true if the set of next users were successfully deleted, false otherwise.
   */
  public boolean deleteNextUserByDeliverable(int deliverableID, User user, String justification);

  /**
   * This method removes a specific activity value from the database.
   * 
   * @param nextUserId is the activity identifier.
   * @param user is the user who is making the change.
   * @param justification is the justification statement.
   * @return true if the next user was successfully deleted, false otherwise.
   */
  public boolean deleteNextUserById(int nextUserId, User user, String justification);

  /**
   * This method gets all the next user information by a given identifier.
   * 
   * @param nextUserID is the next user identifier.
   * @return a next user objects.
   */
  public NextUser getNextUserById(int nextUserID);


  /**
   * This method gets all the next users information which belongs to a given Deliverable Id
   * 
   * @param deliverableID - is the Id of the Deliverable
   * @return a List of next users with the information related with the Deliverable
   */
  public List<NextUser> getNextUsersByDeliverableId(int deliverableID);

  /**
   * This method saves the information of the given next user that belong to a specific Deliverable into the database.
   * 
   * @param deliverableID - deliverable identifier where the next user belongs.
   * @param nextUser - is an object NextUser with the information to be added/updated.
   * @param user - is the user who is making the change.
   * @param justification - is the justification statement.
   * @return true if the nextUser was saved successfully, false otherwise.
   */
  public boolean saveNextUser(int deliverableID, NextUser nextUser, User user, String justification);

  /**
   * This method saves a set of next users that belong to a specific Deliverable.
   * 
   * @param deliverableID - deliverable identifier where the next user belongs.
   * @param nextUsers - is a set of NextUser objects with the information to be added/updated.
   * @param user - is the user who is making the change.
   * @param justification - is the justification statement.
   * @return true if the set of nextUsers were successfully saved, false otherwise.
   */
  public boolean saveNextUsers(int deliverableID, List<NextUser> nextUsers, User currentUser, String justification);


}
