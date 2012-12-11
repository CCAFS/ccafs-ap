package org.cgiar.ccafs.ap.data.manager;

import com.google.inject.ImplementedBy;
import org.cgiar.ccafs.ap.data.manager.impl.ActivityManagerImpl;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.User;

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
   * Get an activity identified with the given id.
   * 
   * @param id
   * @return an Activity object or null if no activity was found.
   */
  public Activity getActivityDeliverableInfo(int id);

  /**
   * Get an activity identified with the given id.
   * 
   * @param id
   * @return an Activity object or null if no activity was found.
   */
  public Activity getActivityPartnersInfo(int id);

  /**
   * Get an activity identified with the given id.
   * 
   * @param id
   * @return an Activity object or null if no activity was found.
   */
  public Activity getActivityStatusInfo(int id);

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
