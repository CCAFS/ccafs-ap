package org.cgiar.ccafs.ap.action.reporting.activities;

import org.apache.commons.lang3.StringUtils;
import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.MilestoneManager;
import org.cgiar.ccafs.ap.data.model.Milestone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;


public class MilestoneReportingAction extends BaseAction {

  /**
   * 
   */
  private static final long serialVersionUID = -5490095465004832840L;

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MilestoneReportingAction.class);

  // Managers
  protected MilestoneManager milestoneManager;

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
    milestoneID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.MILESTONE_REQUEST_ID)));
    // get main activity information based on the status form.
    milestone = milestoneManager.getMilestone(milestoneID);

  }

  public void setActivity(Milestone milestone) {
    this.milestone = milestone;
  }


}
