package org.cgiar.ccafs.ap.action.reporting.tlrpl;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.MilestoneReportManager;
import org.cgiar.ccafs.ap.data.manager.MilestoneStatusManager;
import org.cgiar.ccafs.ap.data.model.MilestoneReport;
import org.cgiar.ccafs.ap.data.model.MilestoneStatus;

import com.google.inject.Inject;


public class TLRPLMilestoneReportingAction extends BaseAction {

  private static final long serialVersionUID = -4403719596243764261L;
  // Manager
  MilestoneReportManager milestoneReportManager;
  MilestoneStatusManager milestoneStatusManager;

  // Models
  private MilestoneReport[] milestoneReports;
  private MilestoneStatus[] milestoneStatusList;

  @Inject
  public TLRPLMilestoneReportingAction(APConfig config, LogframeManager logframeManager,
    MilestoneReportManager milestoneReportManager, MilestoneStatusManager milestoneStatusManager) {
    super(config, logframeManager);
    this.milestoneReportManager = milestoneReportManager;
    this.milestoneStatusManager = milestoneStatusManager;
  }

  public MilestoneReport[] getMilestoneReports() {
    return milestoneReports;
  }

  public MilestoneStatus[] getMilestoneStatusList() {
    return milestoneStatusList;
  }

  @Override
  public void prepare() {
    // Get the milestone status
    milestoneStatusList = milestoneStatusManager.getMilestoneStatusList();

    // Get all the milestone reports
    milestoneReports =
      milestoneReportManager.getMilestoneReports(getCurrentUser().getLeader().getId(), getCurrentLogframe().getId());
  }


  @Override
  public String save() {
    boolean problem = milestoneReportManager.saveMilestoneReports(milestoneReports);

    if (problem) {
      addActionError(getText("saving.problem"));
      return INPUT;
    } else {
      addActionMessage(getText("saving.success",
        new String[] {getText("reporting.tlRplMilestoneReport.milestoneReport")}));
      return SUCCESS;
    }
  }

  public void setMilestoneReports(MilestoneReport[] milestoneReports) {
    this.milestoneReports = milestoneReports;
  }


}
