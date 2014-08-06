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
import org.cgiar.ccafs.ap.data.dao.mysql.MySQLDeliverableTypeDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLDeliverableTypeDAO.class)
public interface DeliverableTypeDAO {


  /**
   * Deletes the information of a Deliverable associated by a given id
   * 
   * @param deliverableTypeId - is the Id of an Deliverable
   * @return true if the elements were deleted successfully. False otherwise
   */
  public boolean deleteDeliverableType(int deliverableTypeId);

  /**
   * This method gets all the Deliverable information by a given Id
   * 
   * @param deliverableTypeID - is the ID of the deliverable type
   * @return a Map of the Deliverable Information related by the ID
   */
  public Map<String, String> getDeliverableTypeById(int deliverableTypeID);

  /**
   * This method gets all the Deliverable information by a given Activity Id
   * 
   * @param activityID - is the Id of the Activity
   * @return a List of Map of the Deliverables Information related with the activity
   */
  public List<Map<String, String>> getDeliverableTypes();


  /**
   * This method saves the Deliverable information
   * 
   * @param deliverableTypeData - is a Map with the information of the deliverable to be saved
   * @param activityID - is the Id of the project
   * @return The last inserted id if there was a new record, 0 if the record was updated or -1 if any error happened.
   */
  public int saveDeliverableType(Map<String, Object> deliverableTypeData);


}
