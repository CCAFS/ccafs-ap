package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLIndicatorReportDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLIndicatorReportDAO.class)
public interface IndicatorReportDAO {

  /**
   * This method return the list of indicator's reports made
   * by the activity leader corresponding to the given logframe.
   * 
   * @param activityLeaderId - Activity leader identifier
   * @param logframeId - Logframe identifier
   * @return A list of maps with the information
   */
  public List<Map<String, String>> getIndicatorReports(int activityLeaderId, int logframeId);

  /**
   * This method save the Indicator's report made by the leader
   * corresponding to the logframe.
   * 
   * @param indicatorsReport - Data with the report about the indicator
   * @param activityLeaderId - Activity leader identifier
   * @param logframeId - Logframe identifier
   * @return true if the information was successfully saved, false otherwise.
   */
  public boolean saveIndicatorReport(Map<String, String> indicatorReportData, int activityLeaderId, int logframeId);
}
