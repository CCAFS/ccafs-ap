package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLMilestoneReportDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLMilestoneReportDAO.class)
public interface MilestoneReportDAO {

  /**
   * Get the list of milestone reports according to the given parameters.
   * 
   * @param logframeId - Logframe identifier
   * @param themeId - Theme identifier
   * @param milestoneId - Milestone identifier
   * @return
   */
  public List<Map<String, String>> getMilestoneReportListForSummary(int logframeId, int themeId, int milestoneId);

  /**
   * Get the list with all the milestones related to the activity leader and logframe
   * given made by RPL's
   * 
   * @param activityLeaderId
   * @param logframeId
   * @return
   */
  public List<Map<String, String>> getRPLMilestoneReportList(int activityLeaderId, int logframeId, int currentYear);

  /**
   * Get the list with all the milestones related to the activity leader and logframe
   * given made by TL's
   * 
   * @param activityLeaderId
   * @param logframeId
   * @return
   */
  public List<Map<String, String>> getTLMilestoneReportList(int activityLeaderId, int logframeId, int currentYear);

  /**
   * As there is no way to know which theme is in charge of TL in the database,
   * this function use a query to get the first activity made by the theme leader
   * for the current logframe and take the theme code.
   * 
   * @param themeLeaderID - Theme leader identifier
   * @param currentPlanningLogframeID - current Planning Logframe Identifier
   * @return theme code or -1 if wasn't found any theme related.
   */
  public String getTLrelatedTheme(int themeLeaderID, int currentPlanningLogframeID);

  /**
   * Save the milestone report information into the database
   * 
   * @param milestoneReportDataList the list of maps with all the information
   * @return true if the data was successfully saved. False otherwise
   */
  public boolean saveMilestoneReportList(List<Map<String, Object>> milestoneReportDataList);
}
