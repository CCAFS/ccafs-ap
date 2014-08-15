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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLProjectDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andrés Gallego
 * @author Héctor Fabio Tobón R.
 */
@ImplementedBy(MySQLProjectDAO.class)
public interface ProjectDAO {


  /**
   * This method validate if the project exists in the database.
   * 
   * @param projectId is the project identifier.
   * @return true if the project exists or false otherwise.
   */
  public boolean existProject(int projectId);

  /**
   * This method return all the Projects
   * 
   * @return a list of maps with the information of all Projects found.
   */

  public List<Map<String, String>> getAllProjects();

  /**
   * This method returns the information of an expected project leader.
   * 
   * @param projectId is the project identifier.
   * @return a Map with the main data of the expected project leader.
   */
  public Map<String, String> getExpectedProjectLeader(int projectId);

  /**
   * This method return the project information of the selected list
   * indicated by parameter.
   * 
   * @param projectID, identifier of the project selected
   * @return a list of maps with the information of the Project returned.
   */
  public Map<String, String> getProject(int projectID);

  /**
   * This method returns a list of project identifiers that belongs to a specific program and/or a specific owner.
   * 
   * @param programId is the program identifier.
   * @param ownerId is the owner identifier.
   * @return a list of Integers which represent the project identifiers.
   */
  public List<Integer> getProjectIdsEditables(int programId, int ownerId);

  /**
   * Get a Project Leader information with a given Project Id
   * 
   * @param ProjectId is the id of a project
   * @return a Map with the project leader information or an empty map if no user found. If an error occurs, a NULL will
   *         be returned.
   */
  public Map<String, String> getProjectLeader(int projectID);

  /**
   * This method return a list with the employees that belongs to a program
   * indicated by parameter.
   * 
   * @param programID, identifier of the program
   * @return a list of maps with the information of Employees returned.
   */
  public List<Map<String, String>> getProjectOwnerContact(int programId);

  /**
   * This method return the Program which belongs to the logged user
   * indicated by parameter.
   * 
   * @param userID, identifier of the program
   * @return a list of maps with the information of the program returned.
   */
  public List<Map<String, String>> getProjectOwnerId(int programId);

  /**
   * This method return all the Projects which belongs to the program
   * indicated by the parameter.
   * 
   * @param programID is identifier of the program
   * @return a list of maps with the information of all Projects found.
   */

  public List<Map<String, String>> getProjectsByProgram(int programId);

  /**
   * This method gets all the projects that belongs to a given user related with an institution
   * 
   * @param institutionId - is the id of the institution
   * @param userId - is the id of the user
   * @return a list of map of projects related with the user and institution.
   */
  public List<Map<String, String>> getProjectsOwning(int institutionId, int userId);

  /**
   * this method add or update an expected project leader that belongs to a specific project.
   * 
   * @param projectId is the id of the project where the expected project leader belongs to.
   * @param expectedProjectLeaderData is the expected project leader data.
   * @return the id of the new expected project leader, 0 if there was an update of the data and -1 if some error
   *         happened.
   */
  public int saveExpectedProjectLeader(int projectId, Map<String, Object> expectedProjectLeaderData);

  /**
   * This method saves the Project information given by the user
   * indicated by parameter.
   * 
   * @param programID, identifier of the program
   * @return if the operation succeed or not.
   */
  public int saveProject(Map<String, Object> projectData);

}
