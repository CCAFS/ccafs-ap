package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLOutputSummaryDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLOutputSummaryDAO.class)
public interface OutputSummaryDAO {

  /**
   * Get the summary of the output identified with outputId made
   * by the activity leader identified by activityLeaderId
   * 
   * @param outputId The output identifier
   * @param activityLeaderId the activity leader identifier
   * @return A map with the information
   */
  public Map<String, String> getOutputSummary(int outputId, int activityLeaderId);

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
