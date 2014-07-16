package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLIPElementDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLIPElementDAO.class)
public interface IPElementDAO {

  /**
   * This method create into the database a new IP Element
   * 
   * @param ipElementData - Information to be saved
   * @return the last inserted id if any or 0 if some record was updated or -1 if any error recorded.
   */
  public int createIPElement(Map<String, Object> ipElementData);

  /**
   * This method deletes all the ip elements which belongs to the program given
   * and which are of the same type given
   * 
   * @param programId
   * @param typeId
   * @return true if the elements were deleted successfully. False otherwise
   */
  public boolean deleteIpElements(int programId, int typeId);

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

  /**
   * Get all the ipElements presents in the database
   * 
   * @return all list of maps with the information of all ipElements
   */
  public List<Map<String, String>> getIPElementList();

  /**
   * Get all IPElements which are parents of the ipElement given
   * 
   * @param ipElementID - ipElement identifier
   * @return a list of maps with the information of the element parents
   */
  public List<Map<String, String>> getParentsOfIPElement(int ipElementID);

  /**
   * This method relates an existent ip element with the program given.
   * 
   * @param elementID - IPElement identifier
   * @param programID - Program identifier
   * @return the last inserted id if any or 0 if some record was updated or -1 if any error occurred.
   */
  public int relateIPElement(int elementID, int programID);
}
