package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLPublicationDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLPublicationDAO.class)
public interface PublicationDAO {

  /**
   * Get the publication that belongs to the deliverable identified by the value
   * received as parameter.
   * 
   * @param deliverableID - Deliverable identifier
   * @return a Publication object
   */
  public Map<String, String> getPublication(int deliverableID);

  /**
   * get a list of publications depending on a given leader and logframe identifiers.
   * 
   * @param leaderId - leader identifier
   * @param logframeId - logframe identifier
   * @return a List of Maps with the information of each publications.
   */
  public List<Map<String, String>> getPublications(int leaderId, int logframeId);

  /**
   * Remove all the publications that belong to a specific leader in a certain logframe.
   * 
   * @param leaderId - leader identifier.
   * @param logframeId - logframe identifier.
   * @return true if the remove was successfully made, false if any problem occur.
   */
  public boolean removeAllPublications(int leaderId, int logframeId);

  /**
   * This method removes the publications linked with the deliverable
   * identified by the value received as parameter.
   * 
   * @param deliverableID - Deliverable identifier
   * @return true if the publication was removed or if any publication
   *         was linked to the deliverable. False if any error occurred.
   */
  public boolean removePublicationByDeliverable(int deliverableID);

  /**
   * Save a list of publications into the database.
   * 
   * @param publications - List of Maps with all the information of publications.
   * @return the identifier assigned to the new record.
   */
  public int savePublication(Map<String, String> publications);

}
