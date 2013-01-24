package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLOutputSummaryDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLOutputSummaryDAO.class)
public interface OutputSummaryDAO {

  /**
   * Get a list with all the output summaries that belongs to the activity
   * leader which are related to the logframeId
   * 
   * @param activityLeaderId the activity leader identifier
   * @param logframeId the logframe identifier
   * @return a list of maps with all the information
   */
  public List<Map<String, String>> getOutputSummariesList(int activityLeaderId, int logframeId);

  /**
   * Save all the outputs summary into the database
   * 
   * @param outputsSummaryData - List of maps with the information of each summary by output to be added
   * @return true if all the information was successfully saved. False otherwise
   */
  public boolean saveOutputsSummaryList(List<Map<String, Object>> outputsSummaryData);

  /**
   * Update all the outputs summary into the database
   * 
   * @param outputsSummaryData - List of maps with the information of each summary by output to be updated
   * @return true if all the information was successfully saved. False otherwise
   */
  public boolean updateOutputsSummaryList(List<Map<String, Object>> outputsSummaryData);
}
