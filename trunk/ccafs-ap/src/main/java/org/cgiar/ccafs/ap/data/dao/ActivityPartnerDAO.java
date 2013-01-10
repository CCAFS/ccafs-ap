package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLActivityPartnerDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLActivityPartnerDAO.class)
public interface ActivityPartnerDAO {

  /**
   * Get all activity partners of the activity identified
   * whit activityID and the partner identified with partnerID
   * from the DAO.
   * 
   * @param activityID the activity identifier
   * @return a List of Maps with the information or null
   *         if not exists .
   */
  public List<Map<String, String>> getActivityPartnersList(int activityID);

  /**
   * Remove all the activity parters that belongs to a given activity.
   * 
   * @param activityID - activity identifier.
   * @return true if all the activity partners were successfully removed. False otherwise.
   */
  public boolean removeActivityPartners(int activityID);

  /**
   * Save all the activity partners into de database.
   * 
   * @param activityPartnersData - List of Maps with the information of each activity partner to be added.
   * @return true if all the information was successfully saved. False otherwise.
   */
  public boolean saveActivityPartnerList(List<Map<String, Object>> activityPartnersData);


}
