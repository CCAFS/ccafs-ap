package org.cgiar.ccafs.ap.action.reporting.activities;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.MilestoneManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.model.Milestone;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MilestoneReportingAction extends BaseAction {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MilestoneReportingAction.class);
  private static final long serialVersionUID = -5490095465004832840L;

  // Managers
  protected MilestoneManager milestoneManager;
  private SubmissionManager submissionManager;

  // Model
  protected Milestone milestone;
  protected int milestoneID;

  @Inject
  public MilestoneReportingAction(APConfig config, LogframeManager logframeManager, MilestoneManager milestoneManager) {
    super(config, logframeManager);
    this.milestoneManager = milestoneManager;
  }

  @Override
  public String execute() throws Exception {
    return SUCCESS;
  }

  public Milestone getMilestone() {
    return milestone;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    // TODO - get the milestone id requested.
    // Also we need to create another interceptor in order to validate if the current milestone exists in the database
    // and validate if the current user has enough privileges to see it.
    try {
      milestoneID =
        Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.MILESTONE_REQUEST_ID)));
    } catch (NumberFormatException e) {
      LOG.error("There was an error trying to parse the mileston id {}",
        this.getRequest().getParameter(APConstants.MILESTONE_REQUEST_ID), e);
    }

    milestone = milestoneManager.getMilestone(milestoneID);
  }

  public void setActivity(Milestone milestone) {
    this.milestone = milestone;
  }
}
