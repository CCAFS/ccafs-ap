package org.cgiar.ccafs.ap.data.dao;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;
import org.cgiar.ccafs.ap.data.dao.mysql.MySQLDeliverableStatusDAO;

@ImplementedBy(MySQLDeliverableStatusDAO.class)
public interface DeliverableStatusDAO {

  /**
   * Get a list whit all the deliverables status
   * 
   * @return a Map whit the status of deliverables.
   */
  public List<Map<String, String>> getDeliverableStatus();
}
