package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLOpenAccessDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLOpenAccessDAO.class)
public interface OpenAccessDAO {

  /**
   * Get the access information related with the id given
   * 
   * @param id - The open access identifier
   * @return a map with the information
   */
  public Map<String, String> getOpenAccess(String id);

  /**
   * Get all the access options
   * 
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getOpenAccessOptions();
}
