package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLActivityObjectiveDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLActivityObjectiveDAO.class)
public interface ActivityObjectiveDAO {

  /**
   * Delete all the objectives related to the activity given.
   * 
   * @param activityID - Activity identifier
   * @return true if was successfully deleted. False otherwise
   */
  public boolean deleteActivityObjectives(int activityID);

  /**
   * Get the objectives related to the activity given
   * 
   * @param activityID - the activity identifier
   * @return a list of activityObjective objects with the information
   */
  public List<Map<String, String>> getActivityObjectives(int activityID);

  /**
   * Save the objectives corresponding to the activity given
   * 
   * @param objectives - The data to save into the DAO
   * @param activityID - Activity identifier
   * @return true if was successfully saved. False otherwise
   */
  public boolean saveActivityObjectives(Map<String, String> objectives, int activityID);
}
