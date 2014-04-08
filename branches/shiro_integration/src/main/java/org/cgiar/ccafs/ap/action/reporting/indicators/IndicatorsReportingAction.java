package org.cgiar.ccafs.ap.action.reporting.indicators;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.IndicatorReportManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.model.IndicatorReport;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.Submission;
import org.cgiar.ccafs.ap.util.Capitalize;

import java.util.List;

import com.google.inject.Inject;
import org.apache.shiro.mgt.SecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IndicatorsReportingAction extends BaseAction {

  // Loger
  private static Logger LOG = LoggerFactory.getLogger(IndicatorsReportingAction.class);
  private static final long serialVersionUID = -5180271642115069487L;

  // Manager
  private IndicatorReportManager indicatorReportManager;
  private SubmissionManager submissionManager;

  // Model
  private List<IndicatorReport> indicatorReports;
  private StringBuilder validationMessage;
  private boolean canSubmit;

  @Inject
  public IndicatorsReportingAction(APConfig config, LogframeManager logframeManager, SecurityManager securityManager,
    IndicatorReportManager indicatorReportManager, SubmissionManager submissionManager) {
    super(config, logframeManager, securityManager);
    this.indicatorReportManager = indicatorReportManager;
    this.submissionManager = submissionManager;

    validationMessage = new StringBuilder();
  }

  public int getCurrentReportingYear() {
    return config.getReportingCurrentYear();
  }

  public List<IndicatorReport> getIndicatorReports() {
    return indicatorReports;
  }

  public boolean isCanSubmit() {
    return canSubmit;
  }

  @Override
  public String next() {
    save();
    return super.next();
  }

  @Override
  public void prepare() throws Exception {
    Leader leader = getCurrentUser().getLeader();
    Logframe logframe = getCurrentReportingLogframe();
    indicatorReports = indicatorReportManager.getIndicatorReportsList(leader, logframe);

    /* --------- Checking if the user can submit ------------- */
    Submission submission =
      submissionManager.getSubmission(getCurrentUser().getLeader(), getCurrentReportingLogframe(),
        APConstants.REPORTING_SECTION);

    canSubmit = (submission == null) ? true : false;
  }

  @Override
  public String save() {
    String finalMessage;
    boolean saved = indicatorReportManager.saveIndicatorReportsList(indicatorReports, getCurrentUser().getLeader());

    if (saved) {
      if (validationMessage.toString().isEmpty()) {
        addActionMessage(getText("saving.success", new String[] {getText("reporting.indicators")}));
      } else {
        // If there were validation messages show them in a warning message.
        finalMessage = getText("saving.success", new String[] {getText("planning.mainInformation")});
        finalMessage += getText("saving.missingFields", new String[] {validationMessage.toString()});

        addActionWarning(Capitalize.capitalizeString(finalMessage));
      }
      LOG.info("The user {} saved the indicators for the leader {}.", getCurrentUser().getEmail(), getCurrentUser()
        .getLeader().getId());
      return SUCCESS;
    } else {
      LOG.warn("There was an error saving the indicators for the leader {}", getCurrentUser().getLeader());
      addActionError(getText("saving.problem"));
      return INPUT;
    }
  }


  public void setIndicatorReports(List<IndicatorReport> indicatorReports) {
    this.indicatorReports = indicatorReports;
  }

  @Override
  public void validate() {
    /*
     * for (int c = 0; c < indicatorReports.size(); c++) {
     * IndicatorReport ir = indicatorReports.get(c);
     * if (ir.getActual() == null || ir.getActual().isEmpty()) {
     * validationMessage.append(getText("reporting.indicators.validation.actual", new String[] {String.valueOf(c)}));
     * }
     * if (ir.getNextYearTarget() == null || ir.getNextYearTarget().isEmpty()) {
     * validationMessage
     * .append(getText("reporting.indicators.validation.nextTarget", new String[] {String.valueOf(c)}));
     * }
     * }
     */
  }

}
