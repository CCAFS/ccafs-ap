package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLDeliverableStatusDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLDeliverableStatusDAO.class)
public interface DeliverableStatusDAO {

  /**
   * Get a list whit all the deliverables status
   * 
   * @return a Map whit the status of deliverables.
   */
  public List<Map<String, String>> getDeliverableStatus();

  /**
   * Update the status of the deliverable identified with the given id.
   * 
   * @param deliverableId - deliverable identifier.
   * @param statusId - status id.
   * @return true if the status was successfully update, false if any problem occur.
   */
  public boolean setDeliverableStatus(int deliverableId, int statusId);
}
