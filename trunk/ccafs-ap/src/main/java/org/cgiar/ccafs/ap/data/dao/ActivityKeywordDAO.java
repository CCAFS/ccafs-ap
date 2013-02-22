package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLActivityKeywordDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLActivityKeywordDAO.class)
public interface ActivityKeywordDAO {

  /**
   * Get the list of keywords related to the activity given
   * 
   * @param activityID - the activity identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getKeywordList(int activityID);
}
