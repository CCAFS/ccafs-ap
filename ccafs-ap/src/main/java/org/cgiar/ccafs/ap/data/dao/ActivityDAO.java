package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLActivityDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLActivityDAO.class)
public interface ActivityDAO {

  /**
   * Get all the activities in a given year. This method is generally used to return the activities of a user who has
   * administration privileges so he can view all activities added in the database.
   * 
   * @param year
   * @return a List with Map of activities.
   */
  public List<Map<String, String>> getActivities(int year);

  /**
   * Get all the activities in a given year added by the leader type supplied. This method is generally used to return
   * the activities of a user who has CP, TL or RPL privileges so he can view all activities added by him.
   * 
   * @param year
   * @return a List with Map of activities.
   */
  public List<Map<String, String>> getActivities(int year, int leaderTypeCode);

  /**
   * Get an an activity identified with the given year.
   * 
   * @param id - identifier.
   * @return a Map with the activity information.
   */
  public Map<String, String> getActivityStatusInfo(int id);

  /**
   * Get the basic main information of an activity identified with the given integer.
   * 
   * @param id - Activity identifier.
   * @return a Map with the activity information.
   */
  public Map<String, String> getSimpleActivity(int id);

  /**
   * Validate if the given id actually exist in the current list of activities.
   * 
   * @param id - activity identifier.
   * @return true if the activity exists or false otherwise.
   */
  public boolean isValidId(int id);

  /**
   * Save the status reporting information of the given activity.
   * 
   * @param activityData - Map with the data to be saved.
   * @return true if the data was saved successfully, or false otherwise.
   */
  public boolean saveStatus(Map<String, String> activityData);

}
