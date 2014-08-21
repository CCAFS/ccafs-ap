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
   * This method gets a list of activities Id related with the program creator Id of the Project
   * 
   * @param programID
   * @return a list of activities Id
   */
  public List<Integer> getActivityIdsEditable(int programID);

  /**
   * This method gets the information of the Expected Activity Leader by a given Activity ID
   * 
   * @param activityID - is the id of the activity
   * @return a Map of the Expected Activity leader Information related with the Activity ID
   */
  public Map<String, String> getExpectedActivityLeaderByActivityId(int activityID);

  /**
   * This method saves the Activity information
   * 
   * @param activityData - is a Map with the information of the activity to be saved
   * @param projectID - is the Id of the project
   * @return The last inserted id if there was a new record, 0 if the record was updated or -1 if any error happened.
   */
  public int saveActivity(int projectID, Map<String, Object> activityData);

  /**
   * This method saves the Activity Leader information
   * 
   * @param activityLeaderData - is a Map with information of the Activity Leader to be saved
   * @return The last inserted id if there was a new record, 0 if the record was updated or -1 if any error happened.
   */
  public int saveExpectedActivityLeader(Map<String, Object> activityLeaderData);

}
