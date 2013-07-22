package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.ActivityManagerImpl;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.User;

import com.google.inject.ImplementedBy;

@ImplementedBy(ActivityManagerImpl.class)
public interface ActivityManager {

  /**
   * Get a list of activities that belongs to the given user in the given year.
   * 
   * @param year
   * @param user
   * @return A list of activities. If the user is an administrator, the method will return all the activities of the
   *         given year.
   */
  public Activity[] getActivities(int year, User user);

  /**
   * Get a list of activities matching the parameters given in order to fill the Activities detail summary.
   * 
   * @param year - Year when activity was carried out or 0 to indicate no value.
   * @param activityID - Activity identifier or 0 to indicate no value.
   * @param activityLeader - Activity leader or 0 to indicate no value.
   * @return
   */
  public Activity[] getActivitiesForDetailedSummary(int year, int activityID, int activityLeader);

  /**
   * Get a list of activities that belong to a specific year.
   * 
   * @param year - (Integer) Year
   * @param limit - Number of activities that will be returned.
   * @return a list of activities ordered by date added.
   */
  public Activity[] getActivitiesForRSS(int year, int limit);

  /**
   * Get a list of activities matching the parameters given in order to fill the Activities status summary.
   * 
   * @param year - Year when activity was carried out or 0 to indicate no value.
   * @param activityID - Activity identifier or 0 to indicate no value.
   * @param activityLeader - Activity leader or 0 to indicate no value.
   * @return
   */
  public Activity[] getActivitiesForStatusSummary(int year, int activityID, int activityLeader);

  /**
   * Get a list of activities from the given year populated only with the id and the title.
   * 
   * @param year
   * @return a list of activity objects.
   */
  public Activity[] getActivitiesTitle(int year);

  /**
   * Get an activity identified with the given id.
   * 
   * @param id - Activity ID
   * @return an Activity object or null if nothing found.
   */
  public Activity getActivity(int id);

  /**
   * Get an activity identified with the given id populated only with the data showed in the status reporting interface.
   * 
   * @param id
   * @return an Activity object or null if no activity was found.
   */
  public Activity getActivityStatusInfo(int id);

  /**
   * Get a list of activities that is going to be used in the planning section.
   * 
   * @param year - An integer representing the year in which the activities belong.
   * @param user - An User object representing the leader in which the activities belong.
   * @return A List of Activity objects that belong to a specific year and user.
   */
  public Activity[] getPlanningActivityList(int year, User user);

  /**
   * Get an activity with its basic information (ID, Title, Leader)
   * 
   * @param id - identifier
   * @return an Activity object or null if no activity was found with the given id.
   */
  public Activity getSimpleActivity(int id);

  /**
   * Validate if the activity is active for current year
   * 
   * @param activityID - Activity identifier
   * @param year - Current year
   * @return true if activity is active, false otherwise.
   */
  public boolean isActiveActivity(int activityID, int year);

  /**
   * Validate if the given id actually exist in the current list of activities.
   * 
   * @param id - activity identifier.
   * @return true if the activity exists or false otherwise.
   */
  public boolean isValidId(int id);

  /**
   * Save the information from Planning section into the DAO.
   * 
   * @param activity - Activity object with the information of an activity.
   * @return true if the information was successfully saved, or false otherwise.
   */
  public boolean saveActivity(Activity activity);

  /**
   * Save the activity status information into the DAO.
   * 
   * @param activity - Activity object with the information populated on it.
   * @return true if the information was saved, or false otherwise.
   */
  public boolean saveStatus(Activity activity);

  /**
   * Set the value of attribute isGlobal into the DAO
   * 
   * @param activity - Activity object with the information on it
   * @return true if the information was updated successfully, false otherwise.
   */
  public boolean updateGlobalAttribute(Activity activity);

  /**
   * Update the main information (Title, description, milestone, budget, start and end date,
   * gender) into the DAO
   * 
   * @param activity - Activity object with the information populated on it
   * @return true if the information was updated successfully, false otherwise
   */
  public boolean updateMainInformation(Activity activity);
}