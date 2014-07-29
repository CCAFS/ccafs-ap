package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLProjectFocusesDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andrés Gallego
 */
@ImplementedBy(MySQLProjectFocusesDAO.class)
public interface ProjectFocusesDAO {


  /**
   * This method create into the database a new Project Focuses
   * 
   * @param ipElementData - Information to be saved
   * @return the last inserted id if any or 0 if some record was updated or -1 if any error recorded.
   */
  public int createProjectFocuses(Map<String, Object> ipElementData);

  /**
   * This method gets all the information of Project Focuses by a give project Id and a type Id
   * 
   * @param projectID - is the id of the project
   * @param typeID - is the id of a program type
   * @return a List of Map with the information of project focuses
   */
  public List<Map<String, String>> getProjectFocuses(int projectID, int typeID);

}
