package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLProjectDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andrés Gallego
 */
@ImplementedBy(MySQLProjectDAO.class)
public interface ProjectDAO {


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
   * indicated by parameter.
   *
   * @param programID, identifier of the program
   * @return a list of maps with the information of all Projects returned.
   */

  public List<Map<String, String>> getProjects(int programId);

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
