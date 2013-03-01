package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLActivityOtherSiteDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLActivityOtherSiteDAO.class)
public interface ActivityOtherSiteDAO {

  /**
   * Get all the other sites related to the activity given
   * 
   * @param activityID - the activity identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getActivityOtherSites(int activityID);
}
