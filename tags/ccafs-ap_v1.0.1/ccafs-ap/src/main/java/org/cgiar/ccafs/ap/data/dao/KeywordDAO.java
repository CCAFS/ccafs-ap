package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLKeywordDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLKeywordDAO.class)
public interface KeywordDAO {

  /**
   * Get the keyword information of the given keyword identifier.
   * 
   * @param id - identifier
   * @return
   */
  public Map<String, String> getKeywordInformation(String id);

  /**
   * Get the list with all the keywords from the DAO
   * 
   * @return a list of maps with the data
   */
  public List<Map<String, String>> getKeywordList();
}
