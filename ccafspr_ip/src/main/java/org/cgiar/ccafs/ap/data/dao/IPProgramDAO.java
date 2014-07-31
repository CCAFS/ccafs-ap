package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLIPProgramDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andrés Gallego
 */
@ImplementedBy(MySQLIPProgramDAO.class)
public interface IPProgramDAO {

  /**
   * This method gets the information of a IP Program by a given ID
   * 
   * @param ipProgramID - is the ID of the program
   * @return a Map with the IP Program information.
   */
  public Map<String, String> getIPProgramById(int ipProgramID);


  public Map<String, String> getIPProgramByProjectId(int projectID);

  /**
   * This method return the Programs Type identified as Flagship
   * indicated by parameter.
   * 
   * @param none
   * @return a list of maps with the information of all IP elements returned.
   */
  public List<Map<String, String>> getProgramsByType(int typeId);

  /**
   * This method return all the Program Type from an specified Project which belongs to the program
   * indicated by parameter.
   * 
   * @param programID, identifier of the program
   * @return a list of maps with the information of all IP elements returned.
   */
  public List<Map<String, String>> getProgramType(int programID, int typeProgramId);


}
