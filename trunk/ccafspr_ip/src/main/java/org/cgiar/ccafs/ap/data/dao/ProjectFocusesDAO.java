package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLProjectDAO;

import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andrés Gallego
 */
@ImplementedBy(MySQLProjectDAO.class)
public interface ProjectFocusesDAO {


  /**
   * This method create into the database a new Project Focuses
   * 
   * @param ipElementData - Information to be saved
   * @return the last inserted id if any or 0 if some record was updated or -1 if any error recorded.
   */
  public int createProjectFocuses(Map<String, Object> ipElementData);

}
