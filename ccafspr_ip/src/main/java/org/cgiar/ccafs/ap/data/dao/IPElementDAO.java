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
   * This method remove from the database the IPElement identified with the
   * parameter received
   * 
   * @param ipElementID - IPElement identifier
   * @return true if the record was successfully deleted. False otherwise
   */
  public boolean deleteIPElement(int ipElementID);

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
   * This method remove of the database the relation between the program and
   * an IPElement identified with the value given as parameter.
   * 
   * @param elementID
   * @return true if the element was successfully removed. False otherwise.
   */
  public boolean deleteProgramElement(int programElementID);

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
   * Get all IPElements which have relation with the element identified by the
   * value passed as parameter.
   * 
   * @param ipElementID - ipElement identifier
   * @param relationTypeID - relation type identifier
   * @return a list of maps with the information of the related parents
   */
  public List<Map<String, String>> getIPElementsRelated(int ipElementID, int relationTypeID);

  /**
   * This method returns the identifier of the record which relates
   * the ipElement and the ipProgram inside the table ip_element_programs
   * 
   * @param ipElementID
   * @param ipProgramID
   * @return
   */
  public int getProgramElementID(int ipElementID, int ipProgramID);

  /**
   * This method relates an existent ip element with the program given.
   * 
   * @param elementID - IPElement identifier
   * @param programID - Program identifier
   * @param relationTypeID - Identifies the type of relation between
   *        the element and the program (createdBy or usedBy)
   * @return the last inserted id if any or 0 if some record was updated or -1 if any error occurred.
   */
  public int relateIPElement(int elementID, int programID, int relationTypeID);
}
