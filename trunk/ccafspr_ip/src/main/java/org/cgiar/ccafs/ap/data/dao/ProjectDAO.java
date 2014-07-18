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


  public List<Map<String, String>> getProject(int projectID);

  public List<Map<String, String>> getProjectOwnerContact(int programId);

  public List<Map<String, String>> getProjectOwnerId(int programId);

  /**
   * This method return a all the Projects which belongs to the program
   * indicated by parameter.
   * 
   * @param programID, identifier of the program
   * @return a list of maps with the information of all IP elements returned.
   */

  public List<Map<String, String>> getProjects(int programId);

  public int saveProject(Map<String, Object> projectData);

}
