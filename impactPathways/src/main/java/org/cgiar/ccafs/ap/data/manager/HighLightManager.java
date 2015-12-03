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

import org.cgiar.ccafs.ap.data.manager.impl.HighLightManagerImpl;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectHighlights;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.hibernate.model.ProjectHighligths;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Christian Garcia
 */
@ImplementedBy(HighLightManagerImpl.class)
public interface HighLightManager {


  /**
   * This method removes a specific highLight value from the database.
   * 
   * @param highLightId is the highLight identifier.
   * @param user - the user that is deleting the highLight.
   * @param justification - the justification statement.
   * @return true if the highLight was successfully deleted, false otherwise.
   */
  public boolean deleteHighLight(int highLightId, User user, String justification);


  /**
   * This method removes a set of highLights that belongs to a specific project.
   * 
   * @param projectID is the project identifier.
   * @return true if the set of highLights were successfully deleted, false otherwise.
   */
  public boolean deleteHighLightsByProject(int projectID);

  /**
   * This method validate if the highLight identify with the given id exists in the system.
   * 
   * @param highLightID is a highLight identifier.
   * @return true if the highLight exists, false otherwise.
   */
  public boolean existHighLight(int highLightID);

  /**
   * This method gets a highLight object by a given highLight identifier.
   * 
   * @param highLightID is the highLight identifier.
   * @return a HighLight object.
   */
  public ProjectHighligths getHighLightById(int highLightID);


  /**
   * This method gets all the highLights information by a given project identifier.
   * 
   * @param projectID - is the Id of the project
   * @return a List of highLights with the Information related with the project
   */
  public List<ProjectHighligths> getHighLightsByProject(int projectID);


  /**
   * This method returns the highLight identifier whether using composed codification (that is with the organization
   * IATI
   * standard id) or a simple id.
   * 
   * @param project , the project to get the standard identifier from.
   * @param highLight, the highLight related to get the standard identifier from.
   * @param useComposedCodification , true if you want to get the full IATI standard codification or false for simple
   *        form.
   * @return a String with the standard identifier.
   */
  public String getStandardIdentifier(Project project, ProjectHighlights highLight, boolean useComposedCodification);

  /**
   * This method saves the information of the given highLight that belong to a specific project into the database.
   * 
   * @param projectID is the project id where the highLight belongs to.
   * @param highLight - is the highLight object with the new information to be added/updated.
   * @param user - is the user that is making the change.
   * @param justification - is the justification statement.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the highLight was updated
   *         or -1 is some error occurred.
   */
  public int saveHighLight(int projectID, ProjectHighligths highLight, User user, String justification);

  /**
   * This method saves the HighLight Contribution relation
   * 
   * @param highLightID - is the Id of the highLight
   * @param outputID - is the Id of the output (MOG)
   * @param user - is the user that is making the change.
   * @param justification - is the justification statement.
   * @return true if the relation HighLight Contribution is successfully saved,
   *         false otherwise
   */

}
