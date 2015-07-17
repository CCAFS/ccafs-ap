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

  /**
   * Remove all activity keywords related to the activity given.
   * 
   * @param activityID - Activity identifier
   * @return true if the records were successfully saved. False otherwise.
   */
  public boolean removeActivityKeywords(int activityID);

  /**
   * Save the keyword information into the database
   * 
   * @param activityID - Activity identifier
   * @param keywordID - Keyword identifier
   * @return true if the information was successfully saved. False otherwise
   */
  public boolean saveKeyword(Map<String, String> keywordData);
}
