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

import org.cgiar.ccafs.ap.data.manager.impl.ActivityManagerImpl;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.ActivityLeader;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Héctor Fabio Tobón R.
 * @author Javier Andrés Gallego
 */
@ImplementedBy(ActivityManagerImpl.class)
public interface ActivityManager {


  /**
   * This method removes a set of activities that belongs to a specific project.
   * 
   * @param projectID is the project identifier.
   * @return true if the set of activities were successfully deleted, false otherwise.
   */
  public boolean deleteActivitiesByProject(int projectID);

  /**
   * This method removes a specific activity value from the database.
   * 
   * @param activityId is the activity identifier.
   * @return true if the activity was successfully deleted, false otherwise.
   */
  public boolean deleteActivity(int activityId);

  /**
   * This method gets all the activity information by a given Project Id
   * 
   * @param projectID - is the Id of the project
   * @return a List of activities with the activity Information related with the project
   */
  public List<Activity> getActivitiesByProject(int projectID);


  /**
   * This method gets all the activity information by a given activity ID.
   * 
   * @param activityID is the activity identifier.
   * @return a List of activities objects.
   */
  public Activity getActivityById(int activityID);


  /**
   * This method gets all the information from Activity Leader by a given activity ID
   * 
   * @param activityID - is the activity identifier
   * @return an activity leader object
   */
  public ActivityLeader getActivityLeader(int activityID);

  /**
   * This method saves the information of the given activity that belong to a specific project into the database.
   * 
   * @param projectID
   * @param activity
   * @return true if the activity was saved successfully, false otherwise.
   */
  public boolean saveActivity(int projectID, Activity activity);


}
