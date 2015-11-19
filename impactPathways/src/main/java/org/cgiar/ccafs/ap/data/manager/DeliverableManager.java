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
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andrés Gallego B.
 * @author Hernán David Carvajal.
 */
@ImplementedBy(DeliverableManagerImpl.class)
public interface DeliverableManager {

  /**
   * This method removes a specific deliverable value from the database.
   * 
   * @param deliverableId is the deliverable identifier.
   * @param user - the user that is deleting the deliverable.
   * @param justification - the justification statement.
   * @return true if the deliverable was successfully deleted, false otherwise.
   */
  public boolean deleteDeliverable(int deliverableId, User user, String justification);

  /**
   * This method removes a specific deliverable contribution value from the database.
   * 
   * @param deliverableID is the deliverable identifier.
   * @return true if the deliverable was successfully deleted, false otherwise.
   */
  public boolean deleteDeliverableOutput(int deliverableID);

  /**
   * This method removes a set of deliverables that belongs to a specific project.
   * 
   * @param projectID is the project identifier.
   * @return true if the set of deliverables were successfully deleted, false otherwise.
   */
  public boolean deleteDeliverablesByProject(int projectID);

  /**
   * This method validate if the deliverable identify with the given id exists in the system.
   * 
   * @param deliverableID is a deliverable identifier.
   * @return true if the deliverable exists, false otherwise.
   */
  public boolean existDeliverable(int deliverableID);

  /**
   * This method gets a deliverable object by a given deliverable identifier.
   * 
   * @param deliverableID is the deliverable identifier.
   * @return a Deliverable object.
   */
  public Deliverable getDeliverableById(int deliverableID);

  /**
   * TODO this method should be moved to IPElementManager.
   * This method gets the information of IP Element related with the MOG in which this deliverable contributes to.
   * 
   * @param deliverableID - is the id of the Deliverable
   * @return an IPElement with the information related to a Deliverable as contributions.
   */
  public IPElement getDeliverableOutput(int deliverableID);

  /**
   * This method gets all the deliverables information by a given project identifier.
   * 
   * @param projectID - is the Id of the project
   * @return a List of deliverables with the Information related with the project
   */
  public List<Deliverable> getDeliverablesByProject(int projectID);

  /**
   * This method gets the list of deliverables that are being contributed by the given project partner.
   * 
   * @param projectPartnerID is the project partner identifier that is contributing to the deliverables.
   * @return a list of Deliverable objects with the information requested, an empty list if nothing found or null
   *         if some problem occurred.
   */
  public List<Deliverable> getDeliverablesByProjectPartnerID(int projectPartnerID);

  /**
   * This method gets the list of Deliverable by type
   * 
   * @return a List of Map of the account of Deliverable Types
   */
  public List<Map<String, String>> getDeliverablesCountByType();

  /**
   * This method gets the list of Expected Deliverables by Year
   * 
   * @return a List of Map of the account of the Expected Deliverables per Year
   */
  public List<Map<String, String>> getExpectedDeliverablesCountByYear();

  /**
   * This method return all the deliverables that belongs to a given project and which are led by a given user.
   * The deliverables will contain only the basic information: id, title
   * 
   * @param projectID - Project identifier
   * @param userID - User identifier
   * @return a list of Deliverable objects or an empty list if no deliverable is found.
   */
  public List<Deliverable> getProjectDeliverablesLedByUser(int projectID, int userID);

  /**
   * This method returns the deliverable identifier whether using composed codification (that is with the organization
   * IATI
   * standard id) or a simple id.
   * 
   * @param project , the project to get the standard identifier from.
   * @param deliverable, the deliverable related to get the standard identifier from.
   * @param useComposedCodification , true if you want to get the full IATI standard codification or false for simple
   *        form.
   * @return a String with the standard identifier.
   */
  public String getStandardIdentifier(Project project, Deliverable deliverable, boolean useComposedCodification);

  /**
   * This method saves the information of the given deliverable that belong to a specific project into the database.
   * 
   * @param projectID is the project id where the deliverable belongs to.
   * @param deliverable - is the deliverable object with the new information to be added/updated.
   * @param user - is the user that is making the change.
   * @param justification - is the justification statement.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the deliverable was updated
   *         or -1 is some error occurred.
   */
  public int saveDeliverable(int projectID, Deliverable deliverable, User user, String justification);

  /**
   * This method saves the Deliverable Contribution relation
   * 
   * @param deliverableID - is the Id of the deliverable
   * @param outputID - is the Id of the output (MOG)
   * @param user - is the user that is making the change.
   * @param justification - is the justification statement.
   * @return true if the relation Deliverable Contribution is successfully saved,
   *         false otherwise
   */
  public boolean saveDeliverableOutput(int deliverableID, int outputID, User user, String justification);

}
