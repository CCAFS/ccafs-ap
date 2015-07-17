/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform. CCAFS P&R is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or at your option) any later version. CCAFS P&R is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU
 * General Public License along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/
package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.DeliverablePartnerManagerImpl;
import org.cgiar.ccafs.ap.data.model.DeliverablePartner;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Héctor Fabio Tobón R.
 */
@ImplementedBy(DeliverablePartnerManagerImpl.class)
public interface DeliverablePartnerManager {

  /**
   * This method deletes a specific deliverable partner from the database
   *
   * @param id of the deliverable partner that will be deleted.
   * @param user the user that is deleting the record.
   * @param justification is the justification statement.
   * @return true if the deliverable partner was deleted or false otherwise.
   */
  public boolean deleteDeliverablePartner(int id, User user, String justifications);


  /**
   * @param deliverableID is the id of the deliverable
   * @param user is the user that is deleting the deliverable partners.
   * @param justification is the justification statement.
   * @return true if all the deliverable partners could be successfully deleted, false otherwise.
   */
  public boolean deleteDeliverablePartnerByDeliverable(int deliverableID, User user, String justification);

  /**
   * This method is used to get the list of Partners that belongs to a specific deliverable.
   *
   * @param deliverableID is the id of the deliverable.
   * @return a List of DeliverablePartner objects that belongs to the deliverable identified with the given id. If no
   *         deliverables
   *         are found, this method will return an empty list or null if some error occurred.
   */
  public List<DeliverablePartner> getDeliverablePartners(int deliverableID);

  /**
   * This method is used to get the list of Partners that belongs to a specific deliverable and type (Resp, Other).
   * 
   * @param deliverableID is the id of the deliverable.
   * @param deliverablePartnerType is the type of the Partner and can be found from constant variables within the
   *        class APConstants ("Type of Deliverable Partners")
   * @return List of DeliverablePartner objects that belongs to the deliverable identified with the given id and type.
   *         If no partners are found, this method will return an empty list or null if some error occurred.
   */
  public List<DeliverablePartner> getDeliverablePartners(int deliverableID, String deliverablePartnerType);

  /**
   * This method saves a Deliverable Partner individually.
   * 
   * @param deliverableID is the deliverable identifier
   * @param partner is the partner object that is going to be saved.
   * @param user is the user that is making the change.
   * @param justification is the justification for the change made.
   * @return the id of the deliverable partner inserted, 0 if the record was updated and -1 if some error occurred.
   */
  public int saveDeliverablePartner(int deliverableID, DeliverablePartner partner, User user, String justification);


  /**
   * This method save the deliverable partner of a specific deliverable
   *
   * @param deliverableID is the deliverable identifier in which these partners belong to.
   * @param partners is the information to be saved
   * @param user is the user that is making the change.
   * @param justification is the justification for the change made.
   * @return true if all partners were successfully saved; or false otherwise.
   */
  public boolean saveDeliverablePartners(int deliverableID, List<DeliverablePartner> partners, User user,
    String justification);


}
