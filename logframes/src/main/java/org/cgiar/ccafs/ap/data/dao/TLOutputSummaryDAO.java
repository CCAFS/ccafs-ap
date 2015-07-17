package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLTLOutputSummaryDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLTLOutputSummaryDAO.class)
public interface TLOutputSummaryDAO {

  /**
   * Get a list of Summaries by Output that belongs to the given theme and a specific logframe.
   * 
   * @param theme_code
   * @param logframe_id - Logframe identifier.
   * @return a List of Maps with all the summary by outputs information.
   */
  public List<Map<String, Object>> getTLOutputSummaries(int theme_code, int logframe_id);

  /**
   * Save a list of Summaries by Output.
   * 
   * @param outputs - List of Maps with the summaries by output information.
   * @return true if all the outputs were successfully saved. False if any problem appear.
   */
  public boolean saveTLOutputSummaries(List<Map<String, Object>> outputs);
}
