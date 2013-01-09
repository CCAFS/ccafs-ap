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
   * Get the information of a partner identified with the given id.
   * 
   * @param id - identifier.
   * @return a Map with the information of the partner, or null if there is is not a partner identified with
   *         the given id.
   */
  public Map<String, String> getPartner(int id);

  /**
   * Get all partners of the activity identified
   * whit activityID from the DAO.
   * 
   * @param activityID the activity identifier
   * @return a List of Maps with the information.
   */
  public List<Map<String, String>> getPartnersList(int activityID);


}
