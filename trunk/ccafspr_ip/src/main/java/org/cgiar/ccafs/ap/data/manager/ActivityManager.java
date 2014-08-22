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
import org.cgiar.ccafs.ap.data.model.ExpectedActivityLeader;
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
   * This method gets all the activity information by a given activity ID.
   *
   * @param activityID is the activity identifier.
   * @return a List of activities objects.
   */
  public Activity getActivityById(int activityID);

  /**
   * This method gets a list of activities identifiers related with the program of the given user.
   *
   * @param user is the user who belongs to a specific CCAFS program.
   * @return a list of activities identifiers.
   */
  public List<Integer> getActivityIdsEditable(User user);

  /**
   * this method gets the activity leader assigned to a specific activity.
   *
   * @param activityID is the activity identifier.
   * @return a User object representing the activity leader, or null if the activity leader was not found.
   */
  public User getActivityLeader(int activityID);

  /**
   * This method gets all the information from Expected Activity Leader by a given activity ID
   *
   * @param activityID - is the activity identifier
   * @return an expected activity leader object, or null if no information were found.
   */
  public ExpectedActivityLeader getExpectedActivityLeader(int activityID);


  /**
   * This method saves the information of the given activity that belong to a specific activity into the database.
   *
   * @param projectID
   * @param activity
   * @return A number greater than zero representing the new ID assigned by the database for the activity, 0 if the
   *         activity was updated or -1 if some error occurred.
   */
  public int saveActivity(int projectID, Activity activity);

  /**
   * This method updates the activity, with the give activity Leader.
   *
   * @param user is the activity leader.
   * @param projectID is the activity identifier.
   * @return true if the activity leader was successfully saved, false otherwise.
   */
  public boolean saveActivityLeader(int projectID, User user);

  /**
   * This method saves the information of a given expected activity leader
   *
   * @param expectedActivityLeader
   * @param activityID
   * @return true if the expected activity leader was saved successfully, false otherwise
   */
  public boolean saveExpectedActivityLeader(int activityID, ExpectedActivityLeader expectedActivityLeader);


}
