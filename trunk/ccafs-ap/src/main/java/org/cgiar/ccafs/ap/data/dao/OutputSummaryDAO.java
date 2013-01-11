package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLOutputSummaryDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLOutputSummaryDAO.class)
public interface OutputSummaryDAO {

  /**
   * Get all the summaries by outputs from the DAO.
   * 
   * @param activityLeaderId - The activity leader identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getOutputSummariesList(int activityLeaderId);

}
