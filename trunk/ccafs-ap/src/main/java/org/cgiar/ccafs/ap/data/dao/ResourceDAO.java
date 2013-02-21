package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLResourceDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLResourceDAO.class)
public interface ResourceDAO {

  /**
   * Get the resources related to the activity given
   * 
   * @param activityID - Activity identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getResources(int activityID);
}
