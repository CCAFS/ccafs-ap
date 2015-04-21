package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.ActivityObjectiveManagerImpl;
import org.cgiar.ccafs.ap.data.model.ActivityObjective;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ActivityObjectiveManagerImpl.class)
public interface ActivityObjectiveManager {

  /**
   * Delete the objectives related to the activity given.
   * 
   * @param activityId - Activity identifier
   * @return true if was successfully saved. False otherwise.
   */
  public boolean deleteActivityObjectives(int activityId);

  /**
   * Get the objectives related to the activity given
   * 
   * @param activityID - activity identifier
   * @return a List of activityObjective objects with the information
   */
  public List<ActivityObjective> getActivityObjectives(int activityID);

  /**
   * Save all the objectives that belongs to the activity given
   * 
   * @param objectives - The data to be saved
   * @param activityID - THe activity identifier
   * @return true if ALL the objective was successfully saved. False otherwise
   */
  public boolean saveActivityObjectives(List<ActivityObjective> objectives, int activityID);
}
