/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform. CCAFS P&R is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or at your option) any later version. CCAFS P&R is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU
 * General Public License along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 * ***************************************************************
 */
package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLDeliverablePartnerDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Héctor Fabio Tobón R.
 */
@ImplementedBy(MySQLDeliverablePartnerDAO.class)
public interface DeliverablePartnerDAO {

  /**
   * This method deletes a deliverable partner from the database.
   *
   * @param id of the deliverable partner to be deleted.
   * @param userID is the user identifier that is deleting it.
   * @param justification is the justification statement.
   * @return true if the record could be successfully deleted, false otherwise.
   */
  public boolean deleteDeliverablePartner(int id, int userID, String justification);

  /**
   * This method gets the deliverable partners information given the deliverable Id
   *
   * @param deliverableID - is the id of a deliverable
   * @return a list of deliverable partners that belong to the given deliverable; an empty list if nothing was found or
   *         NULL if an error occurred.
   */
  public List<Map<String, String>> getDeliverablePartners(int deliverableID);

  /**
   * This method gets the deliverable partners information given the deliverable Id and type of deliverable partner
   * 
   * @param deliverableID is the id of a deliverable
   * @param deliverablePartnerType is the type of a deliverable partner (Resp, Other).
   * @return a list of Map with the information of the partners that belong to the given deliverable and deliverable
   *         partner type; an empty list if nothing was found or NULL if an error occurred.
   */
  public List<Map<String, String>> getDeliverablePartners(int deliverableID, String deliverablePartnerType);


  /**
   * This method saves into the database a new Deliverable Partner
   *
   * @param deliverablePartnerData - Information to be saved
   * @return The last inserted id if there was a new record, 0 if the record was updated or -1 if any error happened.
   */
  public int saveDeliverablePartner(Map<String, Object> deliverablePartnerData);


}
