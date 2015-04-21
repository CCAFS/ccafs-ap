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

import org.cgiar.ccafs.ap.data.manager.impl.ActivityPartnerManagerImpl;
import org.cgiar.ccafs.ap.data.model.ActivityPartner;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Héctor Fabio Tobón R.
 * @author Javier Andrés Gallego
 */
@ImplementedBy(ActivityPartnerManagerImpl.class)
public interface ActivityPartnerManager {


  /**
   * This method removes a specific activity value from the database.
   * 
   * @param activityId is the activity identifier.
   * @return true if the activity was successfully deleted, false otherwise.
   */
  public boolean deleteActivityPartner(int activityPartnerId);

  /**
   * This method removes a set of activity partners that belongs to a specific activity.
   * 
   * @param activityID is the activity identifier.
   * @return true if the set of activities were successfully deleted, false otherwise.
   */
  public boolean deleteActivityPartnersByActivityId(int activityID);

  /**
   * This method gets all the activity information by a given activity ID.
   * 
   * @param activityID is the activity identifier.
   * @return a List of activities objects.
   */
  public ActivityPartner getActivityPartnerById(int activityPartnerID);


  /**
   * This method gets all the activity information by a given Project Id
   * 
   * @param projectID - is the Id of the project
   * @return a List of activities with the activity Information related with the project
   */
  public List<ActivityPartner> getActivityPartnersByActivity(int activityID);

  /**
   * This method saves the information of the given activity Partner that belong to a specific activity into the
   * database.
   * 
   * @param activityID
   * @param activityPartnerData
   * @return true if the activity was saved successfully, false otherwise.
   */
  public boolean saveActivityPartner(int activityID, ActivityPartner activityPartnerData);


}
