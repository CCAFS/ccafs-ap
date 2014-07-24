package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLIPRelationshipDAO;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLIPRelationshipDAO.class)
public interface IPRelationshipDAO {

  /**
   * This method save the relationship existent between two ip elements
   * 
   * @param parentElementID - element identifier
   * @param childElementID - element identifier
   * @param relationTypeID - relation type identifier
   * @return true if all the information was saved successfully. False otherwise
   */
  public boolean saveIPRelation(int parentElementID, int childElementID, int relationTypeID);
}
