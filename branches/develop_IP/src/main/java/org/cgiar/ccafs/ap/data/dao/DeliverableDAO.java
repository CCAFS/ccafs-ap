package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLDeliverableDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLDeliverableDAO.class)
public interface DeliverableDAO {

  /**
   * Add a new deliverable into the DAO.
   * 
   * @param deliverableData - a Map of objects with the information.
   * @return the identifier assigned to the new record.
   */
  public int addDeliverable(Map<String, Object> deliverableData);

  /**
   * Get a list of deliverables that belongs to the activity
   * identified whit activityID
   * 
   * @param activity identifier
   * @return a list whit Map of deliverables.
   */
  public List<Map<String, String>> getDeliverables(int activityID);

  /**
   * Get the number of deliverables that has a specific activity.
   * 
   * @param activityID - Activity identifier
   * @return an integer representing the number of deliverables that has the specified activity.
   */
  public int getDeliverablesCount(int activityID);

  /**
   * Remove all expected deliverables that belongs of a given activity.
   * 
   * @param activityID - activity identifier.
   * @return true if all deliverables were successfully deleted, false otherwise.
   */
  public boolean removeExpected(int activityID);

  /**
   * Remove all not expected deliverables that belongs of a given activity.
   * 
   * @param activityID - activity identifier.
   * @return true if all deliverables were successfully deleted, false otherwise.
   */
  public boolean removeNotExpected(int activityID);
}
