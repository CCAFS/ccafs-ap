package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLProjectDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andrés Gallego
 */
@ImplementedBy(MySQLProjectDAO.class)
public interface IPProgramDAO {

  /**
   * This method return a all the Program Type from an specified Project which belongs to the program
   * indicated by parameter.
   * 
   * @param programID, identifier of the program
   * @return a list of maps with the information of all IP elements returned.
   */
  public List<Map<String, String>> getProgramType(int programID, int typeProgramId);

  /**
   * This method save the information of the selected Flagships for the project
   * indicated by parameter.
   * 
   * @param projectID, identifier of the program
   * @return a list of maps with the information of all IP elements returned.
   */
  public int saveProjectFlagships(Map<String, Object> projectData);

  /**
   * This method save the information of the selected Regions for the project
   * indicated by parameter.
   * 
   * @param projectID, identifier of the program
   * @return a list of maps with the information of all IP elements returned.
   */
  public int saveProjectRegions(Map<String, Object> projectData);

}
