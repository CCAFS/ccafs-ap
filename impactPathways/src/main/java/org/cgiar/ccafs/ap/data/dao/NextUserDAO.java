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
 * ***************************************************************
 */
package org.cgiar.ccafs.ap.data.dao;

/**
 * @author Javier Andrés Gallego
 */
import org.cgiar.ccafs.ap.data.dao.mysql.MySQLNextUserDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLNextUserDAO.class)
public interface NextUserDAO {


  /**
   * Deletes the information of a Next User associated by a given id
   * 
   * @param nextUserId - is the Id of a next user.
   * @param userID is the user identifier who is making the change.
   * @param justification is the justification statement.
   * @return true if the next user was deleted successfully. False otherwise
   */
  public boolean deleteNextUserById(int nextUserId, int userID, String justification);


  /**
   * Deletes the information of the Next Users related by a given activity id
   * 
   * @param deliverableID - is the Id of the deliverable
   * @param userID is the user identifier who is making the change.
   * @param justification is the justification statement.
   * @return true if the elements were deleted successfully. False otherwise
   */
  public boolean deleteNextUsersByDeliverableId(int deliverableID, int userID, String justification);

  /**
   * This method gets all the Next User information by a given Id
   * 
   * @param nextUserID - is the ID of a Next User
   * @return a Map of the Activity Information related by the ID
   */
  public Map<String, String> getNextUserById(int nextUserID);

  /**
   * This method gets all the Next users information by a given Deliverable Id
   * 
   * @param deliverableID - is the Id of the deliverable
   * @return a List of Map of the Next users Information related with the deliverable
   */
  public List<Map<String, String>> getNextUsersByDeliverable(int deliverableID);


  /**
   * This method saves the Next users information
   * 
   * @param nextUserData - is a Map with the information of the Next users to be saved
   * @param deliverableID - is the Id of the deliverable
   * @return The last inserted id if there was a new record, 0 if the record was updated or -1 if any error happened.
   */
  public int saveNextUser(int deliverableID, Map<String, Object> nextUserData);


}
