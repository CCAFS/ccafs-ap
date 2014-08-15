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
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ProjectManagerImpl.class)
public interface ProjectManager {

  /**
   * This method validate if the system has a project identified with the given parameter.
   * 
   * @param projectId is the project identifier to be verified.
   * @return true if the project exists in the system and false otherwise.
   */
  public boolean existProject(int projectId);

  /**
   * This method returns the list of all CCAFS projects.
   * 
   * @return a list with Project objects.
   */
  public List<Project> getAllProjects();

  /**
   * This method finds the Expected Project Leader user from a specific Project.
   * 
   * @param projectId is the project id.
   * @return a User object who represents an expected Project Leader. Or NULL if no user was found.
   */
  public User getExpectedProjectLeader(int projectId);

  /**
   * This method gets all the Project information given by a previous project selected
   * 
   * @param projectID
   * @return an Project Object.
   */
  public Project getProject(int projectId);

  /**
   * This method returns the list of projects that the given user is able to edit.
   * 
   * @param user is the user object.
   * @return a List of project identifiers (Integer numbers).
   */
  public List<Integer> getProjectIdsEditables(User user);

  /**
   * This method finds the Project Leader user from a specific Project.
   * 
   * @param projectId is the project id.
   * @return a User object who represents a Project Leader. Or NULL if no user was found.
   */
  public User getProjectLeader(int projectId);

  /**
   * This method returns the list of all CCAFS projects that belongs to a specific program.
   * 
   * @return a list with Project objects.
   */
  public List<Project> getProjectsByProgram(int programId);

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
   */
  public boolean saveExpectedProjectLeader(int projectId, User expectedLeader);

  /**
   * This method saves or create a project into the database.
   * 
   * @param project is the Project object to be saved.
   * @return A number representing the new Id assigned to the new project, 0 if the project was updated or -1 if some
   *         error happened.
   */
  public int saveProjectDescription(Project project);


}
