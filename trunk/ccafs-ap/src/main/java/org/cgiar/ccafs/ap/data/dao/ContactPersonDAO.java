package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLContactPersonDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLContactPersonDAO.class)
public interface ContactPersonDAO {

  /**
   * Get a all the information of all the contact persons of a given activity.
   * 
   * @param activityID - activity identifier.
   * @return a List of Maps of contact persons.
   */
  public List<Map<String, String>> getContactPersons(int activityID);

}
