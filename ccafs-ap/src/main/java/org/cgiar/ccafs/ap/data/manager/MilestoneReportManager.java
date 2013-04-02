package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.MilestoneReportManagerImpl;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.MilestoneReport;
import org.cgiar.ccafs.ap.data.model.User.UserRole;

import com.google.inject.ImplementedBy;

@ImplementedBy(MilestoneReportManagerImpl.class)
public interface MilestoneReportManager {

  /**
   * Get a list of milestone reports that belongs to the activity leader and are
   * related to the logframe
   * 
   * @param activityLeaderId activity leader identifier
   * @param logframeId logframe identifier
   * @return A list of milestonReport objects
   */
  public MilestoneReport[] getMilestoneReports(Leader activityLeader, Logframe logframe, UserRole role);

  /**
   * Save into the DAO the milestone report information
   * 
   * @param milestoneReports list of milestoneReports objects
   * @return true if the process was successful. False otherwise
   */
  public boolean saveMilestoneReports(MilestoneReport[] milestoneReports);
}
