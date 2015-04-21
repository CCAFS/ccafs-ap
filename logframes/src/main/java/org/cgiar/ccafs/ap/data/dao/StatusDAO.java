package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLStatusDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLStatusDAO.class)
public interface StatusDAO {

  /**
   * Get all the status from the DAO.
   * 
   * @return a List of Maps with the information.
   */
  public List<Map<String, String>> getStatusList();

}
