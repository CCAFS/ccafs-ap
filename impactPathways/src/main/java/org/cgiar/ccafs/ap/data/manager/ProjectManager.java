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

import org.cgiar.ccafs.ap.data.manager.impl.ProjectManagerImpl;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ProjectManagerImpl.class)
public interface ProjectManager {

  /**
   * This method delete the relation between the project and the indicator
   * received.
   * 
   * @param projectID - project identifier
   * @param indicatorID - indicator identifier
   * @return true if the relation was successfully removed. False otherwise.
   */
  public boolean deleteIndicator(int projectID, int indicatorID);

  /**
   * This method deletes the project identified by the value received by
   * parameter.
   * 
   * @param projectID
   * @return True if the project was deleted, false otherwise.
   */
  public boolean deleteProject(int projectID);

  /**
   * This method delete the relation between the project and the output
   * received.
   * 
   * @param projectID - project identifier
   * @param outputID - output identifier
   * @param outcomeID - identifier of the outcome to which the output belongs to.
   * @param userID - identifier of the user who is deleting the output
   * @param justification
   * @return true if the relation was successfully removed. False otherwise.
   */
  public boolean deleteProjectOutput(int projectID, int outputID, int outcomeID, int userID, String justification);

  /**
   * This method validate if the system has a project identified with the given parameter.
   * 
   * @param projectId is the project identifier to be verified.
   * @return true if the project exists in the system and false otherwise.
   */
  public boolean existProject(int projectId);

  /**
   * This method returns the list of all CCAFS projects
   * but only with the basic information:
   * ID, title, the regions and flagships to which the project contributes
   * and the total budget
   * 
   * @return a list with Project objects.
   */
  public List<Project> getAllProjectsBasicInfo();

  /**
   * This method returns the core projects that contributes with the flagship and the regions received by parameter.
   * If the parameters are '-1' they are not used to filter the list.
   * 
   * @return a list of projects that only contains the id and title.
   */
  public List<Project> getCoreProjects(int flagshipID, int regionID);

  /**
   * This method finds the Expected Project Leader user from a specific Project.
   * 
   * @param projectId is the project id.
   * @return a User object who represents an expected Project Leader. Or NULL if no user was found.
   * @deprecated This method is deprecated as we do not have expected Project Leaders any more. Instead, all the PLs
   *             will be created in the users table but some of them will be active or not.
   */
  @Deprecated
  public User getExpectedProjectLeader(int projectId);

  /**
   * This method returns the list of project identifiers where the given user is assigned as Project Leader.
   * 
   * @param user is the user object.
   * @return a List of project identifiers (Integer numbers).
   */
  public List<Integer> getPLProjectIds(User user);

  /**
   * This method gets all the Project information given by a previous project selected
   * 
   * @param projectID
   * @return an Project Object.
   */
  public Project getProject(int projectId);

  /**
   * This method returns the basic information of the project identified
   * by the value received as parameter
   * ID, title, the regions and flagships to which the project contributes
   * and the total budget
   * 
   * @return a list with Project objects.
   */
  public Project getProjectBasicInfo(int projectID);

  /**
   * This method returns the Project in which the activity belongs to.
   * 
   * @param activityID is the Activity identifier.
   * @return a Project object.
   */
  public Project getProjectFromActivityId(int activityID);

  /**
   * This method returns the list of project identifiers that the given user is able to edit.
   * 
   * @param user is the user object.
   * @return a List of project identifiers (Integer numbers).
   */
  public List<Integer> getProjectIdsEditables(User user);

  /**
   * This method gets all the indicators related to the project passed as parameter
   * 
   * @param projectID - project identifier
   * @return a list of IPIndicator objects
   */
  public List<IPIndicator> getProjectIndicators(int projectID);

  /**
   * This method finds the Project Leader user from a specific Project.
   * 
   * @param projectId is the project id.
   * @return a User object who represents a Project Leader. Or NULL if no user was found.
   * @deprecated Deprecated as of P&R v2.1, replaced by ProjectPartnerManager.getProjectPartners(int projectId, String
   *             projectPartnerType).
   */
  @Deprecated
  public User getProjectLeader(int projectId);

  /**
   * This method gets all the outputs related with the project identified by the value
   * received as parameter.
   * 
   * @param projectID - project identifer
   * @return a list of IPElement objects
   */
  public List<IPElement> getProjectOutputs(int projectID);

  /**
   * This method returns the list of all CCAFS projects that belongs to a specific program.
   * 
   * @return a list with Project objects.
   */
  public List<Project> getProjectsByProgram(int programId);

  /**
   * This method returns a list of projects identified by the values received by parameter.
   * 
   * @param values
   * @return a list of Project objects.
   */
  public List<Project> getProjectsList(String[] values);

  /**
   * This method gets all the projects in which the given user is assigned as Project Owner
   * 
   * @param user is the user object.
   * @return a List of projects.
   */
  public List<Project> getProjectsOwning(User user);

  /**
   * This method saves or update an expected project leader possibly added in Pre-Planning step.
   * This expected project leader must belongs to a specific project.
   * 
   * @param projectId is the project identifier.
   * @param expectedLeader is the project leader to be added/updated.
   * @return true if the save process finalized successfully, false otherwise.
   * @deprecated Please use ProjectPartnerManager.saveProjectPartner(...).
   */
  @Deprecated
  public boolean saveExpectedProjectLeader(int projectId, User expectedLeader);

  /**
   * This method create or updates a project into the database.
   * 
   * @param project is the Project object to be saved.
   * @param user - The user who is creating/updating the project
   * @param justification - Why the user made the change
   * @return A number representing the new Id assigned to the new project, 0 if the project was updated or -1 if some
   *         error happened.
   */
  public int saveProjectDescription(Project project, User user, String justification);

  /**
   * This method save into the database the relation between a project and
   * some midOutcomes indicators
   * 
   * @param indicators - List of indicators objects
   * @param projectID - project identifier
   * @return true if ALL the indicators were saved successfully. False otherwise
   */
  public boolean saveProjectIndicators(List<IPIndicator> indicators, int projectID);

  /**
   * This method save into the database the relation between a project and
   * the outputs
   * 
   * @param outputs - A list of ipElmenet objects
   * @param projectID - project identifier
   * @return true if ALL the relations were saved successfully. False otherwise.
   */
  public boolean saveProjectOutputs(List<IPElement> outputs, int projectID);
}
