package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLDeliverableTypeDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLDeliverableTypeDAO.class)
public interface DeliverableTypeDAO {

  /**
   * Get a list with all the deliverable types and subtypes
   * 
   * @return
   */
  public List<Map<String, String>> getDeliverableTypesAndSubTypes();

  /**
   * Get a list with all the deliverables sub types
   * 
   * @return a Map with the sub types of deliverables.
   */
  public List<Map<String, String>> getDeliverableSubTypes();

  /**
   * Get a list with all the top deliverables types
   * 
   * @return a Map with the types of deliverables.
   */
  public List<Map<String, String>> getDeliverableTypes();
}
