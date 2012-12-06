package org.cgiar.ccafs.ap.data.dao;

import java.util.List;
import java.util.Map;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLDeliverableTypeDAO;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLDeliverableTypeDAO.class)
public interface DeliverableTypeDAO {

  /**
   * Get a list whit all the deliverables types
   * 
   * @return a Map whit the types of deliverables.
   */
  public List<Map<String, String>> getDeliverableTypes();
}
