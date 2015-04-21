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
import org.cgiar.ccafs.ap.data.dao.mysql.MySQLActivityPartnerDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLActivityPartnerDAO.class)
public interface ActivityPartnerDAO {


  /**
   * Deletes the information of a Activity Partner associated by a given id
   * 
   * @param activityPartnerId - is the Id of an Activity Partner
   * @return true if the elements were deleted successfully. False otherwise
   */
  public boolean deleteActivityPartner(int activityPartnerId);


  /**
   * Deletes the information of the Activity Partner related by a given activity id
   * 
   * @param activityID
   * @return true if the elements were deleted successfully. False otherwise
   */
  public boolean deleteActivityPartnerByActivityId(int activityID);

  /**
   * This method gets all the Activity Partners information by a given activity Id
   * 
   * @param activityID - is the Id of the project
   * @return a List of Map of the Activity Partner Information related with the project
   */
  public List<Map<String, String>> getActivityPartnersByActivity(int activityID);

  /**
   * This method gets all the Activity Partner information by a given Id
   * 
   * @param activityPartnerID - is the ID of the activity
   * @return a Map of the Activity Information related by the ID
   */
  public Map<String, String> getActivityPartnerById(int activityPartnerID);

  /**
   * This method saves the Activity information
   * 
   * @param activityPartnerData - is a Map with the information of the activity partner to be saved
   * @param activityID - is the Id of the activity
   * @return The last inserted id if there was a new record, 0 if the record was updated or -1 if any error happened.
   */
  public int saveActivityPartner(int activityID, Map<String, Object> activityPartnerData);

}
