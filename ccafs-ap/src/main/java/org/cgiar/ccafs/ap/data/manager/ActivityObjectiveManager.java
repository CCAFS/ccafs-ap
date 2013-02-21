package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.ActivityObjectiveManagerImpl;
import org.cgiar.ccafs.ap.data.model.ActivityObjective;

import com.google.inject.ImplementedBy;

@ImplementedBy(ActivityObjectiveManagerImpl.class)
public interface ActivityObjectiveManager {

  /**
   * Get the objectives related to the activity given
   * 
   * @param activityID - activity identifier
   * @return a List of activityObjective objects with the information
   */
  public ActivityObjective[] getActivityObjectives(int activityID);
}
