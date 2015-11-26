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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLHighLightDAO;

/**
 * @author Christian Garcia
 */
import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLHighLightDAO.class)
public interface HighLightDAO {


  /**
   * Deletes the information of a Deliverable associated by a given id
   * 
   * @param highLightId - is the Id of an Deliverable
   * @param userID - is the user identifier who is deleting the highLight.
   * @param justification - is the justification statement.
   * @return true if the elements were deleted successfully. False otherwise
   */
  public boolean deleteHighLight(int highLightId, int userID, String justification);


  /**
   * Deletes the information of the HighLight related by a given Project id
   * 
   * @param projectID
   * @return true if the elements were deleted successfully. False otherwise
   */
  public boolean deleteHighLightsByProject(int projectID);

  /**
   * This method validates if the a given highLight exists in the database.
   * 
   * @param highLightID is a highLight identifier.
   * @return true if a record was found, false otherwise.
   */
  public boolean existHighLight(int highLightID);

  /**
   * This method gets a highLight information by a given Id
   * 
   * @param highLightID - is the ID of the highLight
   * @return a Map of the HighLight Information related by the ID
   */
  public Map<String, String> getHighLightById(int highLightID);


  /**
   * This method gets all the HighLight information by a given Project Id
   * 
   * @param projectID - is the Id of the Project
   * @return a List of Map of the HighLights Information related with the project
   */
  public List<Map<String, String>> getHighLightsByProject(int projectID);


  /**
   * This method return all the highLights that belongs to a given project and which are led by a given user.
   * The highLights will contain only the basic information: id, title
   * 
   * @param projectID - Project identifier
   * @param userID - User identifier
   * @return a list of maps with the information or an empty list if no highLight is found.
   */

  public int saveHighLight(Map<String, Object> highLightData);


}
