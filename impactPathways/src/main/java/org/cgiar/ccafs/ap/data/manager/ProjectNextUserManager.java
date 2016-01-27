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

import org.cgiar.ccafs.ap.data.manager.impl.ProjectNextUserManagerImpl;
import org.cgiar.ccafs.ap.data.model.ProjectNextUser;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Christian Garcia
 */
@ImplementedBy(ProjectNextUserManagerImpl.class)
public interface ProjectNextUserManager {


  /**
   * This method removes a specific projectNextUser value from the database.
   * 
   * @param projectNextUserId is the projectNextUser identifier.
   * @param user - the user that is deleting the projectNextUser.
   * @param justification - the justification statement.
   * @return true if the projectNextUser was successfully deleted, false otherwise.
   */
  public boolean deleteProjectNextUser(int projectNextUserId, User user, String justification);


  /**
   * This method removes a set of projectNextUsers that belongs to a specific project.
   * 
   * @param projectID is the project identifier.
   * @return true if the set of projectNextUsers were successfully deleted, false otherwise.
   */
  public boolean deleteProjectNextUsersByProject(int projectID);

  /**
   * This method validate if the projectNextUser identify with the given id exists in the system.
   * 
   * @param projectNextUserID is a projectNextUser identifier.
   * @return true if the projectNextUser exists, false otherwise.
   */
  public boolean existProjectNextUser(int projectNextUserID);

  /**
   * This method gets a projectNextUser object by a given projectNextUser identifier.
   * 
   * @param projectNextUserID is the projectNextUser identifier.
   * @return a ProjectNextUser object.
   */
  public ProjectNextUser getProjectNextUserById(int projectNextUserID);


  /**
   * This method gets all the projectNextUsers information by a given project identifier.
   * 
   * @param projectID - is the Id of the project
   * @return a List of projectNextUsers with the Information related with the project
   */
  public List<ProjectNextUser> getProjectNextUserProject(int projectID);


  /**
   * This method saves the information of the given projectNextUser that belong to a specific project into the database.
   * 
   * @param projectID is the project id where the projectNextUser belongs to.
   * @param projectNextUser - is the projectNextUser object with the new information to be added/updated.
   * @param user - is the user that is making the change.
   * @param justification - is the justification statement.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the projectNextUser was
   *         updated
   *         or -1 is some error occurred.
   */
  public int saveProjectNextUser(int projectID, ProjectNextUser projectNextUser, User user, String justification);

}
