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
 * @author Hernán David Carvajal
 */
import org.cgiar.ccafs.ap.data.dao.mysql.MySQLActivityDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLActivityDAO.class)
public interface ActivityDAO {


  /**
   * Deletes the information of the Activities related by a given project id
   *
   * @param projectID
   * @return true if the elements were deleted successfully. False otherwise
   */
  public boolean deleteActivitiesByProject(int projectID);


  /**
   * Deletes the information of a Activity associated by a given id
   *
   * @param activityId - is the Id of an Activity
   * @return true if the elements were deleted successfully. False otherwise
   */
  public boolean deleteActivity(int activityId);

  /**
   * This method deletes from the database the relation between the activity and the indicator
   * received.
   *
   * @param activityID - activity identifier
   * @param indicatorID - indicator identifier
   * @return true if the relation was successfully removed. False otherwise.
   */
  public boolean deleteActivityIndicator(int activityID, int indicatorID);

  /**
   * This method deletes from the database the relation between the activity and the output
   * received.
   *
   * @param activityID - activity identifier
   * @param outputID - output identifier
   * @return true if the relation was successfully removed. False otherwise.s
   */
  public boolean deleteActivityOutput(int activityID, int outputID);

  /**
   * This method validates if the a given activity exists in the database.
   *
   * @param activityID is an activity identifier.
   * @return true if a record was found, false otherwise.
   */
  public boolean existActivity(int activityID);

  /**
   * This method gets all the Activities information by a given Project Id
   *
   * @param projectID - is the Id of the project
   * @return a List of Map of the Activities Information related with the project
   */
  public List<Map<String, String>> getActivitiesByProject(int projectID);

  /**
   * This method gets all the Activity information by a given Id
   *
   * @param activityID - is the ID of the activity
   * @return a Map of the Activity Information related by the ID
   */
  public Map<String, String> getActivityById(int activityID);

  /**
   * This method gets a list of activities Id related with the program creator Id of the Project that they belongs to.
   *
   * @param programID is the CCAFS program id from the ip_programs table.
   * @return a list of activities identifiers, or an empty list if nothing found.
   */
  public List<Integer> getActivityIdsEditable(int programID);

  /**
   * This method returns all the indicators related with the activity
   * identified by the value received as parameter.
   *
   * @param activityID - activity identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getActivityIndicators(int activityID);


  /**
   * This method returns the id from the employees table that belong to the activity leader.
   *
   * @param activityID is the activity identifier.
   * @return an integer representing the identifier of the employee user that is leadering the activity, or -1 if
   *         nothing was found.
   */
  public int getActivityLeaderId(int activityID);

  /**
   * This method returns all the outputs related to the activity identified
   * by the value received as parameter.
   *
   * @param activityID - Activity identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getActivityOutputs(int activityID);

  /**
   * This methods returns all the activities entered into the system.
   *
   * @return a list of Map with the information requested.
   */
  public List<Map<String, String>> getAllActivities();


  /**
   * This method gets the information of the Expected Activity Leader by a given Activity ID
   *
   * @param activityID - is the id of the activity
   * @return a Map of the Expected Activity leader Information related with the Activity ID
   */
  public Map<String, String> getExpectedActivityLeader(int activityID);


  /**
   * This methods gets the list of activity identifiers that correspond to the activities where the user was assigned as
   * Activity Leader.
   * 
   * @param employeeId is the id of the user in the employees table.
   * @return a list of Integers representing the ids.
   */
  public List<Integer> getLedActivities(int employeeId);


  /**
   * This method lets you know if the Project Leader wants to create or not an account for the specified Activity
   * Leader. The information is saved in the column is_official from the expected_activity_leaders table.
   *
   * @param activityID - is the activity identifier
   * @return true if is_official is true, or false otherwise.
   */
  public boolean isOfficialExpectedLeader(int activityID);


  /**
   * This method saves the Activity information
   *
   * @param activityData - is a Map with the information of the activity to be saved
   * @param projectID - is the Id of the project
   * @return The last inserted id if there was a new record, 0 if the record was updated or -1 if any error happened.
   */
  public int saveActivity(int projectID, Map<String, Object> activityData);


  /**
   * This method save into the database the relation between an activity and
   * some midOutcomes indicators
   *
   * @param indicatorData - map with the information to be saved
   * @return true if the relation was successfully added.s
   */
  public boolean saveActivityIndicators(Map<String, String> indicatorData);


  /**
   * This method updates the activity, with the activity Leader by the given employee ID
   *
   * @param activityID
   * @param employeeID
   * @return 0 if the record was updated or -1 if any error happened.
   */
  public int saveActivityLeader(int activityID, int employeeID);


  /**
   * This method save into the database the relation between an activity and
   * one output
   *
   * @param outputData - information to be saved
   * @return The last inserted id if there was a new record, 0 if the record was updated or -1 if any error happened.
   */
  public int saveActivityOutput(Map<String, String> outputData);

  /**
   * This method saves the Expected Activity Leader information
   *
   * @param activityID
   * @param activityLeaderData - is a Map with information of the Activity Leader to be saved
   * @param isOfficialLeader - is true when the user wants to create a profile of this leader into the system.
   * @return The last inserted id if there was a new record, 0 if the record was updated or -1 if any error happened.
   */
  public int
    saveExpectedActivityLeader(int activityID, Map<String, Object> activityLeaderData, boolean isOfficialLeader);

}
