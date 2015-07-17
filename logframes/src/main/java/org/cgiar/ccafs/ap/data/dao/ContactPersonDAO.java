package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLContactPersonDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLContactPersonDAO.class)
public interface ContactPersonDAO {

  /**
   * Delete all the contact persons related to the given activity from the DAO
   * 
   * @param activityID - activity identifier
   * @return true if was successfully deleted. False otherwise.
   */
  public boolean deleteContactPersons(int activityID);

  /**
   * Get a all the information of all the contact persons of a given activity.
   * 
   * @param activityID - activity identifier.
   * @return a List of Maps of contact persons.
   */
  public List<Map<String, String>> getContactPersons(int activityID);

  /**
   * Save a contact persons into the DAO
   * 
   * @param contactPerson - The information to save
   * @param activityID - The activity identifier
   * @return true if the contact person was successfully saved. False otherwise
   */
  public boolean saveContactPersons(Map<String, String> contactPerson, int activityID);

}
