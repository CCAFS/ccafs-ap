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
   * This method returns the information of the deliverable identified
   * by the value received as parameter.
   * 
   * @param deliverableID - deliverable identifier
   * @return a map with the information of the deliverable.
   */
  public Map<String, String> getDeliverable(int deliverableID);

  /**
   * This method gets the leader of the activity that owns the deliverable
   * identified with the value received as parameter.
   * 
   * @param deliverableID - deliverable identifier
   * @return a map with the leader information.
   */
  public Map<String, String> getDeliverableLeader(int deliverableID);

  /**
   * This method gets all the metadata related to the deliverable
   * identified by the value received as parameter.
   * 
   * @param deliverableID - Product identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getDeliverableMetadata(int deliverableID);

  /**
   * Get a list of deliverables that belongs to the activity
   * identified whit activityID
   * 
   * @param activity identifier
   * @return a list whit Map of deliverables.
   */
  public List<Map<String, String>> getDeliverablesByActivityID(int activityID);

  /**
   * Get the number of deliverables that has a specific activity.
   * 
   * @param activityID - Activity identifier
   * @return an integer representing the number of deliverables that has the specified activity.
   */
  public int getDeliverablesCount(int activityID);

  /**
   * Get the description, year, status and type of the deliverables which
   * belong to activities carried out by the activity leader identified by
   * the value received as parameter.
   * 
   * @param activityLeaderID - activity leader identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getDeliverablesListByLeader(int activityLeaderID);

  /**
   * Return all the deliverables that belong to activities which contribute
   * to the theme received as parameter.
   * 
   * @param themeCode
   * @return a list of maps with the information to save.
   */
  public List<Map<String, String>> getDeliverablesListByTheme(int themeCode);

  /**
   * Returns the theme to which the deliverable contributes to.
   * 
   * @param deliverableID - Deliverable identifier
   * @return a Map with the information of the theme.
   */
  public Map<String, String> getDeliverableTheme(int deliverableID);

  /**
   * This method removes the deliverable identified by the value received as
   * parameter.
   * 
   * @param deliverableID - Deliverable identifier
   * @return true if the deliverable was removed successfully. False otherwise.
   */
  public boolean removeDeliverable(int deliverableID);

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
