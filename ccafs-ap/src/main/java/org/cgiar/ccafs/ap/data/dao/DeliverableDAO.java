package org.cgiar.ccafs.ap.data.dao;

import java.util.List;
import java.util.Map;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLDeliverableDAO;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLDeliverableDAO.class)
public interface DeliverableDAO {

  /**
   * Get a list of deliverables that belongs to the activity
   * identified whit activityID
   * 
   * @param activity identifier
   * @return a list whit Map of deliverables.
   */
  public List<Map<String, String>> getDeliverables(int activityID);
}
