package org.cgiar.ccafs.ap.data.dao;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;
import org.cgiar.ccafs.ap.data.dao.mysql.MySQLPartnerDAO;

@ImplementedBy(MySQLPartnerDAO.class)
public interface PartnerDAO {

  /**
   * Get all partners of the activity identified
   * whit activityID from the DAO.
   * 
   * @param activityID the activity identifier
   * @return a List of Maps with the information.
   */
  public List<Map<String, String>> getPartnersList(int activityID);

}
