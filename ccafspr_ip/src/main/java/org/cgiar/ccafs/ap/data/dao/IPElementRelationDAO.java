package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLIPElementRelationDAO;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLIPElementRelationDAO.class)
public interface IPElementRelationDAO {

  /**
   * This method removes from the database the relation between the parent
   * element and the child element
   * 
   * @param parentElementID - parent element identifier
   * @param childElementID - child element identifier
   * @return true if the relation was successfully removed. False otherwise.
   */
  public boolean deleteIpElementsRelation(int parentElementID, int childElementID);

  /**
   * This method removes from the database all records when the childElementID
   * is the value received as parameter.
   * 
   * @param childElementID - element identifier
   * @return true if the relations were removed. False otherwise.
   */
  public boolean deleteRelationsByChildElement(int childElementID);
}
