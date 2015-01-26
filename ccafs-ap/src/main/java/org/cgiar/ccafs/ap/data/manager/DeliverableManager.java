package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.DeliverableManagerImpl;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.Leader;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(DeliverableManagerImpl.class)
public interface DeliverableManager {


  /**
   * Add a deliverable to the database which its corresponding file formats.
   * 
   * @param deliverable - Product object.
   * @param activityID - Activity identifier in which the deliverable belongs to.
   * @return true y the deliverable was successfully added, false otherwise.
   */
  public boolean addDeliverable(Deliverable deliverable, int activityID);

  /**
   * Create a new deliverable in the database.
   * 
   * @param deliverable - Deliverable data
   * @param activityID - activity identifier
   * @return true if the deliverable was created. False otherwise.
   */
  public int createDeliverable(Deliverable deliverable, int activityID);

  /**
   * Get the information of the deliverable identified by the value
   * received as parameter.
   * 
   * @param deliverableID - deliverable identifier
   * @return a deliverable object with the information
   */
  public Deliverable getDeliverable(int deliverableID);

  /**
   * Get all the deliverables objects belongs to an activity identified
   * with the given id.
   * 
   * @param id
   * @return a Deliverables array or null if no deliverables was found.
   */
  public List<Deliverable> getDeliverableByActivityID(int activityId);

  /**
   * This method return the activity leader of the activity that owns
   * the deliverable identified by the value received as parameter.
   * 
   * @param deliverableID - Deliverable identifier
   * @return a Leader object with the information.
   */
  public Leader getDeliverableLeader(int deliverableID);

  /**
   * This method gets all the deliverables that belongs to activities
   * made by the leader identified with the value received as parameter.
   * 
   * @param activityLeaderID - Activity leader identifier
   * @return a List of Deliverable objects.
   */
  public List<Deliverable> getDeliverablesListByLeader(int activityLeaderID);

  /**
   * This method gets all the deliverables that belongs to activities
   * carried out in the year given and that were made by the leader
   * identified with the value received as parameter.
   * 
   * @param activityLeaderID - Activity leader identifier
   * @param year
   * @return a List of Deliverable objects.
   */
  public List<Deliverable> getDeliverablesListByLeader(int activityLeaderID, int year);

  /**
   * This method gets all the deliverables that belongs to activities
   * that contributes to the theme received as parameter.
   * 
   * @param themeID - theme identifier
   * @return a List of Deliverable objects.
   */
  public List<Deliverable> getDeliverablesListByTheme(int themeID);

  /**
   * This method gets all the deliverables that belongs to activities
   * that contributes to the theme received as parameter and that were
   * carried out in the year given.
   * 
   * @param themeID - theme identifier
   * @param year
   * @return a List of Deliverable objects.
   */
  public List<Deliverable> getDeliverablesListByTheme(int themeID, int year);

  /**
   * Returns the leader of the theme to which the deliverable contributes to.
   * 
   * @param deliverableID - Deliverable identifier
   * @return a theme object.
   */
  public Leader getDeliverableThemeLeader(int deliverableID);

  /**
   * This method removes the deliverable identified by the value received as parameter.
   * 
   * @param deliverableID - Deliverable identifier
   * @return true if the deliverable was removed successfully or if there was not deliverable identified
   *         with this value. False otherwise.
   */
  public boolean removeDeliverable(int deliverableID);

  /**
   * Remove all those expected deliverables that belongs to the specified activity id.
   * 
   * @param activityID - activity identifier.
   * @return true if all the not expected deliverables were removed, or false if any problem occur.
   */
  public boolean removeExpected(int activityID);

  /**
   * Remove all those not expected deliverables that belongs to the specified activity id.
   * 
   * @param activityID - activity identifier.
   * @return true if all the not expected deliverables were removed, or false if any problem occur.
   */
  public boolean removeNotExpected(int activityID);

  /**
   * Save into the DAO the list of the deliverables and its file formats associated with a specific activity.
   * 
   * @param deliverables - A list of Product objects representing the information that is going to be added into the
   *        DAO.
   * @param activityID - An integer representing the id of the activity that will contain those deliverable list.
   * @return true if the list of the deliverables was successfully added into the DAO, or false if any problem occur.
   */
  public boolean saveDeliverable(List<Deliverable> deliverables, int activityID);
}
