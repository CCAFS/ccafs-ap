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
   * This method return the project information of the selected list
   * indicated by parameter.
   * 
   * @param projectID, identifier of the project selected
   * @return a list of maps with the information of the Project returned.
   */
  public Map<String, String> getProject(int projectID);

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
   * This method saves the Project information given by the user
   * indicated by parameter.
   * 
   * @param programID, identifier of the program
   * @return if the operation succeed or not.
   */
  public int saveProject(Map<String, Object> projectData);

}
