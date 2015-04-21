package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLActivityOtherSiteDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLActivityOtherSiteDAO.class)
public interface ActivityOtherSiteDAO {

  /**
   * Delete all the other locations related with the activity given.
   * 
   * @param activityID - The activity identifier
   * @return true if it was successfully saved. False otherwise.
   */
  public boolean deleteActivityOtherSites(int activityID);

  /**
   * Get all the other sites related to the activity given
   * 
   * @param activityID - the activity identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getActivityOtherSites(int activityID);

  /**
   * Save the other location into the DAO
   * 
   * @param otherSite - Data to be saved
   * @param activityID - The activity identifier
   * @return true if it was successfully saved. False otherwise
   */
  public boolean saveActivityOtherSites(Map<String, String> otherSite, int activityID);
}
