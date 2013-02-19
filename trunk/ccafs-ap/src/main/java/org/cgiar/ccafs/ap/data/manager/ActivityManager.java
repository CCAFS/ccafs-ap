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
   * Get a list of activities that belong to a specific year.
   * 
   * @param year - (Integer) Year
   * @param limit - Number of activities that will be returned.
   * @return a list of activities ordered by date added.
   */
  public Activity[] getActivitiesForRSS(int year, int limit);

  /**
   * Get an activity identified with the given id populated only with the data showed in the status reporting interface.
   * 
   * @param id
   * @return an Activity object or null if no activity was found.
   */
  public Activity getActivityStatusInfo(int id);

  /**
   * Get an activity with its basic information (ID, Title, Leader)
   * 
   * @param id - identifier
   * @return an Activity object or null if no activity was found with the given id.
   */
  public Activity getSimpleActivity(int id);

  /**
   * Validate if the given id actually exist in the current list of activities.
   * 
   * @param id - activity identifier.
   * @return true if the activity exists or false otherwise.
   */
  public boolean isValidId(int id);

  /**
   * Save the activity status information into the DAO.
   * 
   * @param activity - Activity object with the information populated on it.
   * @return true if the information was saved, or false otherwise.
   */
  public boolean saveStatus(Activity activity);
}
