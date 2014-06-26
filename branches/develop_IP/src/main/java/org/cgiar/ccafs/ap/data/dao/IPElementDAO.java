package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLIPElementDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLIPElementDAO.class)
public interface IPElementDAO {

  /**
   * This method return a all the IP elements which belongs to the program
   * indicated by parameter.
   * 
   * @param programID, identifier of the program
   * @return a list of maps with the information of all IP elements returned.
   */

  public List<Map<String, String>> getIPElement(int programID);

  /**
   * This method return all the IP elements of the type given and that correspond
   * to the program given
   * 
   * @param programID - program identifier
   * @param elementTypeID - element type identifier
   * @return a list of maps with the information of all IP elements returned
   */
  public List<Map<String, String>> getIPElement(int programID, int elementTypeID);
}
