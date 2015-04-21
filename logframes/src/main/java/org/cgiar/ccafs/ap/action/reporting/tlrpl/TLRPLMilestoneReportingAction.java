package org.cgiar.ccafs.ap.action.reporting.tlrpl;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.MilestoneReportManager;
import org.cgiar.ccafs.ap.data.manager.MilestoneStatusManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.model.MilestoneReport;
import org.cgiar.ccafs.ap.data.model.MilestoneStatus;
import org.cgiar.ccafs.ap.data.model.Submission;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TLRPLMilestoneReportingAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(TLOutputSummaryReportingAction.class);
  private static final long serialVersionUID = -4403719596243764261L;

  // Manager
  MilestoneReportManager milestoneReportManager;
  MilestoneStatusManager milestoneStatusManager;
  SubmissionManager submissionManager;

  // Models
  private MilestoneReport[] milestoneReports;
  private MilestoneStatus[] milestoneStatusList;
  private boolean canSubmit;

  @Inject
  public TLRPLMilestoneReportingAction(APConfig config, LogframeManager logframeManager,
    MilestoneReportManager milestoneReportManager, MilestoneStatusManager milestoneStatusManager,
    SubmissionManager submissionManager) {
    super(config, logframeManager);
    this.milestoneReportManager = milestoneReportManager;
    this.milestoneStatusManager = milestoneStatusManager;
    this.submissionManager = submissionManager;
  }

  public MilestoneReport[] getMilestoneReports() {
    return milestoneReports;
  }

  public MilestoneStatus[] getMilestoneStatusList() {
    return milestoneStatusList;
  }

  public boolean isCanSubmit() {
    return canSubmit;
  }


  @Override
  public void prepare() {
    LOG.info("The user {} loads the milestone reporting section for the leader {}.", getCurrentUser().getEmail(),
      getCurrentUser().getLeader().getId());
    // Get the milestone status
    milestoneStatusList = milestoneStatusManager.getMilestoneStatusList();

    // Get all the milestone reports
    milestoneReports =
      milestoneReportManager.getMilestoneReports(getCurrentUser().getLeader(), getCurrentReportingLogframe(),
        getCurrentUser().getRole());

    /* --------- Checking if the user can submit ------------- */
    Submission submission =
      submissionManager.getSubmission(getCurrentUser().getLeader(), getCurrentReportingLogframe(),
        APConstants.REPORTING_SECTION);

    canSubmit = (submission == null) ? true : false;
  }

  @Override
  public String save() {
    boolean problem = milestoneReportManager.saveMilestoneReports(milestoneReports);

    if (problem) {
      LOG.warn("The user {} had a problem saving the milestone reporting for the leader {}", getCurrentUser()
        .getEmail(), getCurrentUser().getLeader().getId());
      addActionError(getText("saving.problem"));
      return INPUT;
    } else {
      LOG.info("The user {} saved successfully the milestone reporting for the leader {}.",
        getCurrentUser().getEmail(), getCurrentUser().getLeader().getId());
      addActionMessage(getText("saving.success",
        new String[] {getText("reporting.tlRplMilestoneReport.milestoneReport")}));
      return SUCCESS;
    }
  }

  public void setMilestoneReports(MilestoneReport[] milestoneReports) {
    this.milestoneReports = milestoneReports;
  }


}
