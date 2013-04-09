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
   * Get a list of activities to be showed in the RSS system.
   * 
   * @param year - Year in which the activities were added.
   * @param limit - Number of activities to be showed. If limit is -1 all the activities will be showed.
   * @return a List of Maps with the information of each activity took from the DAO.
   */
  public List<Map<String, String>> getActivitiesForRSS(int year, int limit);

  /**
   * Get an an activity identified with the given year.
   * 
   * @param id - identifier.
   * @return a Map with the activity information.
   */
  public Map<String, String> getActivityStatusInfo(int id);

  /**
   * Get a basic information for each activity that is going to be used in the home planning section.
   * 
   * @param year - The year in which the activities belong.
   * @param leaderId - the identification of the leader in which the activities belong.
   * @return a List of Map objects with the information of each activity (id, title and milestone).
   */
  public List<Map<String, String>> getPlanningActivityList(int year, int leaderId);

  /**
   * Get the basic main information of an activity identified with the given integer.
   * 
   * @param id - Activity identifier.
   * @return a Map with the activity information.
   */
  public Map<String, String> getSimpleActivity(int id);

  /**
   * Get a list of activities populated only with the id and title.
   * 
   * @param year - the logframe year when the activities were added.
   * @param leaderTypeCode - the leader id where the activities belong to.
   * @return a list of maps with the id, and title for each activity.
   */
  public List<Map<String, String>> getTitles(int year, int leaderTypeCode);

  /**
   * Validate if the given id actually exist in the current list of activities.
   * 
   * @param id - activity identifier.
   * @return true if the activity exists or false otherwise.
   */
  public boolean isValidId(int id);

  /**
   * Add a new activity to the DAO.
   * 
   * @param activityData - a Map with the main activity information.
   * @return the id that represents the added activity, or -1 if some error happened.
   */
  public int saveSimpleActivity(Map<String, Object> activityData);


  /**
   * Save the status reporting information of the given activity.
   * 
   * @param activityData - Map with the data to be saved.
   * @return true if the data was saved successfully, or false otherwise.
   */
  public boolean saveStatus(Map<String, String> activityData);

  /**
   * Set the value of attribute isGlobal into the DAO
   * 
   * @param activityID - Activity identifier
   * @param isGlobal - value of global attribute
   * @return true if the information was successfully saved. False otherwise
   */
  public boolean updateGlobalAttribute(int activityID, boolean isGlobal);

  /**
   * Update the main information of the given activity
   * 
   * @param activityData - Map with the data to be saved.
   * @return true if the data was saved successfully, or false otherwise.
   */
  public boolean updateMainInformation(Map<String, String> activityData);

}
