package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLProjectDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLProjectDAO.class)
public interface ProjectDAO {


  /**
   * This method return a all the IP elements which belongs to the program
   * indicated by parameter.
   * 
   * @param programID, identifier of the program
   * @return a list of maps with the information of all IP elements returned.
   */

  public List<Map<String, String>> getProject(int projectLeaderID);

  public List<Map<String, String>> getProjectFlagship(int programId);

  public List<Map<String, String>> getProjectRegion(int programId);

  public int saveProject(Map<String, Object> projectData);

}
