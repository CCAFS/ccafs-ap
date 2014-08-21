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
import org.cgiar.ccafs.ap.data.dao.mysql.MySQLIPOtherContributionDAO;

import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLIPOtherContributionDAO.class)
public interface IPOtherContributionDAO {


  /**
   * Deletes the information of a IP Other Contribution associated by a given id
   * 
   * @param ipOtherContributionId - is the Id of an IP Other Contribution
   * @return true if the element were deleted successfully. False otherwise
   */
  public boolean deleteIPOtherContribution(int ipOtherContributionId);


  /**
   * Deletes the information of the IP Other Contribution related by a given activity id
   * 
   * @param activityID- is the Id of an activity
   * @return true if the elements were deleted successfully. False otherwise
   */
  public boolean deleteIPOtherContributionsByActivityId(int activityID);

  /**
   * This method gets all the IP Other Contributions information by a given activity Id
   * 
   * @param activityID - is the Id of the activity
   * @return a List of Map of the IP Other Contributions Information related with the activity
   */
  public Map<String, String> getIPOtherContributionByActivityId(int activityID);


  /**
   * This method gets all the IP Other Contribution information by a given Id
   * 
   * @param ipOtherContributionId - is the ID of the IP Other Contribution
   * @return a Map of the IP Other Contribution Information related by the ID
   */
  public Map<String, String> getIPOtherContributionById(int ipOtherContributionId);

  /**
   * This method saves or update the IP Other Contribution information
   * 
   * @param ipOtherContributionData - is a Map with the information of the IP Other Contribution to be saved
   * @param activityID - is the Id of the activity
   * @return The last inserted id if there was a new record, 0 if the record was updated or -1 if any error happened.
   */
  public int saveIPOtherContribution(int activityID, Map<String, Object> ipOtherContributionData);


}
