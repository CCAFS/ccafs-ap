package org.cgiar.ccafs.ap.data.dao;

import java.util.List;
import java.util.Map;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLActivityDAO;

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
  public Map<String, String> getActivityDeliverablesInfo(int id);

  /**
   * Get an an activity identified with the given year.
   * 
   * @param id - identifier.
   * @return a Map with the activity information.
   */
  public Map<String, String> getActivityStatusInfo(int id);


}
