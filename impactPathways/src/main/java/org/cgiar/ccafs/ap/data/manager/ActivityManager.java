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
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andrés Gallego
 */
@ImplementedBy(ActivityManagerImpl.class)
public interface ActivityManager {

  /**
   * This method removes a set of activities that belongs to a specific project.
   * 
   * @param projectID is the project identifier.
   * @param user is the user who is making the deletion.
   * @param justification is the justification statement.
   * @return true if the set of activities were successfully deleted, false otherwise.
   */
  public boolean deleteActivitiesByProject(int projectID, User user, String justification);

  /**
   * This method removes a specific activity value from the database.
   * 
   * @param activityId is the activity identifier.
   * @param user is the person who is making the deletion.
   * @param justification is the justification statement.
   * @return true if the activity was successfully deleted, false otherwise.
   */
  public boolean deleteActivity(int activityId, User user, String justification);

  /**
   * This method validate if the activity identify with the given id exists in the system.
   * 
   * @param activityID is an activity identifier.
   * @return true if the activity exists, false otherwise.
   */
  public boolean existActivity(int activityID);

  /**
   * This method gets all the activity information by a given Project Id
   * 
   * @param projectID - is the Id of the project
   * @return a List of activities with the activity Information related with the project
   */
  public List<Activity> getActivitiesByProject(int projectID);

  /**
   * This method gets all the activities information by a given Project Partner Id
   * 
   * @param projectPartnerID - is the Id of the project partner related to the activities
   * @return a List of activities related to the project partner id given as parameter
   */
  public List<Activity> getActivitiesByProjectPartner(int projectPartnerID);

  /**
   * This method gets the activity information by a given activity ID.
   * The activity has to be active.
   * 
   * @param activityID is the activity identifier.
   * @return an Activity object with the information requested, or null if the activity was not found or is not active.
   */
  public Activity getActivityById(int activityID);

  /**
   * This method returns all the activities that are entered in the system and that are active.
   * 
   * @return an Array of Activity objects.
   */
  public List<Activity> getAllActivities();

  /**
   * This method saves the information of the given activity that belongs to a specific project into the database.
   * 
   * @param projectID - project identifier
   * @param activity - the activity to be saved
   * @param user - the user that makes changes to the information
   * @param justification - the justification for the changes made
   * @return A number greater than zero representing the new ID assigned by the database for the activity, 0 if the
   *         activity was updated or -1 if some error occurred.
   */
  public int saveActivity(int projectID, Activity activity, User user, String justification);

  /**
   * This method saves a set of Activities related to the project identified by the
   * value received as parameter
   * 
   * @param projectID - the project identifier
   * @param activityArray - the list of activities to be saved
   * @param user - the user that makes changes to the information
   * @param justification - the justification for the changes made
   * @return true if the information was saved successfully, false otherwise.
   */
  public boolean saveActivityList(int projectID, List<Activity> activityArray, User user, String justification);
}