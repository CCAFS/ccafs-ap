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
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLActivityDAO.class)
public interface ActivityDAO {

  /**
   * Deletes the information of the Activities related by a given project id
   * 
   * @param projectID is the project identifier.
   * @param userID is the identifier of the user who is making the deletion.
   * @param justification is the justification statement.
   * @return true if the elements were deleted successfully. False otherwise
   */
  public boolean deleteActivitiesByProject(int projectID, int userID, String justification);

  /**
   * Deletes the information of a Activity associated by a given id
   * 
   * @param activityID - is the Id of an Activity
   * @param userID is the identifier of the user who is making the deletion.
   * @param justification is the justification statement.
   * @return true if the elements were deleted successfully. False otherwise
   */
  public boolean deleteActivity(int activityID, int userID, String justification);

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
   * @return a Map of the Activity Information related by the ID, or an empty Map if the activity was not found os is
   *         not active.
   */
  public Map<String, String> getActivityById(int activityID);

  /**
   * This methods returns all the activities entered into the system and that are active.
   * 
   * @return a list of Map with the information requested.
   */
  public List<Map<String, String>> getAllActivities();

  /**
   * This method saves the Activity information
   * 
   * @param activityData - is a Map with the information of the activity to be saved
   * @param projectID - is the Id of the project
   * @param user - the user that makes changes to the information
   * @param justification - the justification for the changes made
   * @return The last inserted id if there was a new record, 0 if the record was updated or -1 if any error happened.
   */
  public int saveActivity(int projectID, Map<String, Object> activityData, User user, String justification);

  /**
   * This method updates the activity, with the activity Leader by the given employee ID
   * 
   * @param activityID
   * @param employeeID
   * @return 0 if the record was updated or -1 if any error happened.
   */
  public int saveActivityLeader(int activityID, int employeeID);

  /**
   * This method saves the Activity list related to the project identified by the
   * value received as parameter
   * 
   * @param activityArrayMap - information of the activities list to be saved
   * @param projectID - the project identifier
   * @param user - the user that makes changes to the information
   * @param justification - the justification for the changes made
   * @return true if the information was saved successfully, false otherwise.
   */
  public boolean saveActivityList(int projectID, List<Map<String, Object>> activityArrayMap, User user,
    String justification);

}
