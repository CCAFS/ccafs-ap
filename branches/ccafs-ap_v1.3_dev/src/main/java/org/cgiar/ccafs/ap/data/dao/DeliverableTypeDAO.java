package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLDeliverableTypeDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLDeliverableTypeDAO.class)
public interface DeliverableTypeDAO {

  /**
   * Get a list with all the deliverables types that are active.
   * 
   * @return a Map with the types of deliverables.
   */
  public List<Map<String, String>> getActiveDeliverableTypes();

  /**
   * Get a list with all the deliverables types
   * 
   * @return a Map with the types of deliverables.
   */
  public List<Map<String, String>> getAllDeliverableTypes();
}
