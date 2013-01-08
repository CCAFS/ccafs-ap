package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLPartnerDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLPartnerDAO.class)
public interface PartnerDAO {

  /**
   * Get a list of all Partners.
   * 
   * @return a List of Map of partner data.
   */
  public List<Map<String, String>> getAllPartners();

  /**
   * Get all partners of the activity identified
   * whit activityID from the DAO.
   * 
   * @param activityID the activity identifier
   * @return a List of Maps with the information.
   */
  public List<Map<String, String>> getPartnersList(int activityID);

}
