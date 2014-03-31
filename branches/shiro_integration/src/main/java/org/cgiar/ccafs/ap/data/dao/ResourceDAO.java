package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLResourceDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLResourceDAO.class)
public interface ResourceDAO {

  /**
   * Get the resources related to the activity given
   * 
   * @param activityID - Activity identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getResources(int activityID);

  /**
   * Remove all the resources related to the given activity
   * 
   * @param activityID - activity identifier
   * @return true if the records were successfully deleted. False otherwise.
   */
  public boolean removeResources(int activityID);

  /**
   * Save the resource information into the database.
   * 
   * @param resourceData - the data to be saved
   * @return true if the information was successfully saved. False otherwise
   */
  public boolean saveResource(Map<String, String> resourceData);
}
