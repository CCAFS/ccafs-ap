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

import org.cgiar.ccafs.ap.data.manager.impl.ProjectLeverageManagerImpl;
import org.cgiar.ccafs.ap.data.model.ProjectLeverage;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Christian Garcia
 */
@ImplementedBy(ProjectLeverageManagerImpl.class)
public interface ProjectLeverageManager {


  /**
   * This method removes a specific projectLeverage value from the database.
   * 
   * @param projectLeverageId is the projectLeverage identifier.
   * @param user - the user that is deleting the projectLeverage.
   * @param justification - the justification statement.
   * @return true if the projectLeverage was successfully deleted, false otherwise.
   */
  public boolean deleteProjectLeverage(int projectLeverageId, User user, String justification);


  /**
   * This method removes a set of projectLeverages that belongs to a specific project.
   * 
   * @param projectID is the project identifier.
   * @return true if the set of projectLeverages were successfully deleted, false otherwise.
   */
  public boolean deleteProjectLeveragesByProject(int projectID);

  /**
   * This method validate if the projectLeverage identify with the given id exists in the system.
   * 
   * @param projectLeverageID is a projectLeverage identifier.
   * @return true if the projectLeverage exists, false otherwise.
   */
  public boolean existProjectLeverage(int projectLeverageID);

  /**
   * This method gets a projectLeverage object by a given projectLeverage identifier.
   * 
   * @param projectLeverageID is the projectLeverage identifier.
   * @return a ProjectLeverage object.
   */
  public ProjectLeverage getProjectLeverageById(int projectLeverageID);


  /**
   * This method gets all the projectLeverages information by a given project identifier.
   * 
   * @param projectID - is the Id of the project
   * @return a List of projectLeverages with the Information related with the project
   */
  public List<ProjectLeverage> getProjectLeverageProject(int projectID);


  /**
   * This method saves the information of the given projectLeverage that belong to a specific project into the database.
   * 
   * @param projectID is the project id where the projectLeverage belongs to.
   * @param projectLeverage - is the projectLeverage object with the new information to be added/updated.
   * @param user - is the user that is making the change.
   * @param justification - is the justification statement.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the projectLeverage was
   *         updated
   *         or -1 is some error occurred.
   */
  public int saveProjectLeverage(int projectID, ProjectLeverage projectLeverage, User user, String justification);

}
