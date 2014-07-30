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
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ProjectManagerImpl.class)
public interface ProjectManager {

  /**
   * This method return the list of all CCAFS projects that belongs to a specific program.
   * 
   * @return a list with Project objects.
   */
  public List<Project> getAllProjects(int programId);

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
   * This method gets all the Programs related with a specific project determined by a given typeID
   * 
   * @param projectID - is the ID of the project
   * @param typeID - is the typeID of the program (FPL or RPL)
   * @return a List of Programs with information requested or if doesn't have records an empty list
   */
  public List<IPProgram> getProjectFocuses(int projectID, int typeID);

  /**
   * This method finds the Project Leader user from a specific Project.
   * 
   * @param projectId is the project id.
   * @return a User object who represents a Project Leader. Or NULL if no user was found.
   */
  public User getProjectLeader(int projectId);

  /**
   * This method saves or update an expected project leader possibly added in Pre-Planning step.
   * This expected project leader must belongs to a specific project.
   * 
   * @param projectId is the project identifier.
   * @param expectedLeader is the project leader to be added/updated.
   * @return true if the save process finalized successfully, false otherwise.
   */
  public boolean saveExpectedProjectLeader(int projectId, User expectedLeader);


}
