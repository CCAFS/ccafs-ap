package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLMilestoneReportDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLMilestoneReportDAO.class)
public interface MilestoneReportDAO {

  /**
   * Get the list with all the milestones related to the activity leader and logframe
   * given
   * 
   * @param activityLeaderId
   * @param logframeId
   * @return
   */
  public List<Map<String, String>> getMilestoneReportList(int activityLeaderId, int logframeId);

  /**
   * Save the milestone report information into the database
   * 
   * @param milestoneReportDataList the list of maps with all the information
   * @return true if the data was successfully saved. False otherwise
   */
  public boolean saveMilestoneReportList(List<Map<String, Object>> milestoneReportDataList);
}
