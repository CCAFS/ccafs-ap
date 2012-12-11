package org.cgiar.ccafs.ap.data.dao;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;
import org.cgiar.ccafs.ap.data.dao.mysql.MySQLActivityPartnerDAO;

@ImplementedBy(MySQLActivityPartnerDAO.class)
public interface ActivityPartnerDAO {

  /**
   * Get all activity partners of the activity identified
   * whit activityID and the partner identified with partnerID
   * from the DAO.
   * 
   * @param activityID the activity identifier
   * @param partnerID the partner identifier
   * @return a List of Maps with the information or null
   *         if not exists .
   */
  public List<Map<String, String>> getActivityPartnersList(int activityID, int partnerID);

}
