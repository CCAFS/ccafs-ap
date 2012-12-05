package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.ActivityManagerImpl;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Leader;

import com.google.inject.ImplementedBy;

@ImplementedBy(ActivityManagerImpl.class)
public interface ActivityManager {


  /**
   * Get a list of activities.
   * 
   * @param year
   * @param leader
   * @return
   */
  public Activity[] getActivities(int year, Leader leader);

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
  public Activity getActivityStatusInfo(int id);
}
