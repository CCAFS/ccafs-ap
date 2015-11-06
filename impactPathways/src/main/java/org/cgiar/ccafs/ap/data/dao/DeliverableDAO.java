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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLDeliverableDAO;

/**
 * @author Javier Andrés Gallego
 */
import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLDeliverableDAO.class)
public interface DeliverableDAO {


  /**
   * Deletes the information of a Deliverable associated by a given id
   * 
   * @param deliverableId - is the Id of an Deliverable
   * @param userID - is the user identifier who is deleting the deliverable.
   * @param justification - is the justification statement.
   * @return true if the elements were deleted successfully. False otherwise
   */
  public boolean deleteDeliverable(int deliverableId, int userID, String justification);

  /**
   * Deletes the information of a Deliverable contribution by a given deliverable id
   * 
   * @param deliverableID - is the Id of an Deliverable
   * @return true if the elements were deleted successfully. False otherwisem
   */
  public boolean deleteDeliverableOutput(int deliverableID);

  /**
   * Deletes the information of the Deliverable related by a given Project id
   * 
   * @param projectID
   * @return true if the elements were deleted successfully. False otherwise
   */
  public boolean deleteDeliverablesByProject(int projectID);

  /**
   * This method validates if the a given deliverable exists in the database.
   * 
   * @param deliverableID is a deliverable identifier.
   * @return true if a record was found, false otherwise.
   */
  public boolean existDeliverable(int deliverableID);

  /**
   * This method gets a deliverable information by a given Id
   * 
   * @param deliverableID - is the ID of the deliverable
   * @return a Map of the Deliverable Information related by the ID
   */
  public Map<String, String> getDeliverableById(int deliverableID);

  /**
   * This method gets all the IP Element information related by a given Deliverable Id with an Activity Contribution
   * 
   * @param deliverableID - is the Id of the Deliverable
   * @return a list of Map of IP Element related with the deliverable as Deliverable Contributions
   */
  public Map<String, String> getDeliverableOutput(int deliverableID);

  /**
   * This method gets all the Deliverable information by a given Project Id
   * 
   * @param projectID - is the Id of the Project
   * @return a List of Map of the Deliverables Information related with the project
   */
  public List<Map<String, String>> getDeliverablesByProject(int projectID);

  /**
   * This method get a list of deliverables that are being contributed by the given project partner id.
   * 
   * @param projectPartnerID is the project partner identifier that is contributing to the deliverables.
   * @return a list of Maps with the information requested, an empty list if nothing found or null if some problem
   *         occurred.
   */
  public List<Map<String, String>> getDeliverablesByProjectPartnerID(int projectPartnerID);

  /**
   * This method return all the deliverables that belongs to a given project and which are led by a given user.
   * The deliverables will contain only the basic information: id, title
   * 
   * @param projectID - Project identifier
   * @param userID - User identifier
   * @return a list of maps with the information or an empty list if no deliverable is found.
   */
  public List<Map<String, String>> getProjectDeliverablesLedByUser(int projectID, int userID);

  /**
   * This method saves the Deliverable information
   * 
   * @param deliverableData - is a Map with the information of the deliverable to be saved
   * @return The last inserted id if there was a new record, 0 if the record was updated or -1 if any error happened.
   */
  public int saveDeliverable(Map<String, Object> deliverableData);

  /**
   * This method saves the Deliverable Contribution relation
   * 
   * @param deliverableID - is the Id of the deliverable
   * @param outputID - is the Id of the output (MOG).
   * @param userID - is the user who is making the change.
   * @param justification - is the justification statement.
   * @return true if the relation Deliverable Contribution is successfully saved, false otherwise
   */
  public boolean saveDeliverableOutput(int deliverableID, int outputID, int userID, String justification);

}
