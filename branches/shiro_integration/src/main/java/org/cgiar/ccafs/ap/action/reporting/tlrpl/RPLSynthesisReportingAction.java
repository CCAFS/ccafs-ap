package org.cgiar.ccafs.ap.action.reporting.tlrpl;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.RPLSynthesisReportManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.model.RPLSynthesisReport;
import org.cgiar.ccafs.ap.data.model.Submission;
import org.cgiar.ccafs.ap.util.Capitalize;

import com.google.inject.Inject;
import org.apache.shiro.mgt.SecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RPLSynthesisReportingAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(RPLSynthesisReportingAction.class);
  private static final long serialVersionUID = 4734278462072395648L;

  // Managers
  private RPLSynthesisReportManager synthesisReportManager;
  private SubmissionManager submissionManager;

  // Models
  private RPLSynthesisReport synthesisReport;
  private StringBuilder validationMessage;
  private boolean canSubmit;

  @Inject
  public RPLSynthesisReportingAction(APConfig config, LogframeManager logframeManager, SecurityManager securityManager,
    RPLSynthesisReportManager synthesisReportManager, SubmissionManager submissionManager) {
    super(config, logframeManager, securityManager);
    this.synthesisReportManager = synthesisReportManager;
    this.submissionManager = submissionManager;

    validationMessage = new StringBuilder();
  }

  public RPLSynthesisReport getSynthesisReport() {
    return synthesisReport;
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
    super.prepare();
    LOG.info("The user {} loads the synthesis section for the leader {}.", getCurrentUser().getEmail(),
      getCurrentUser().getLeader().getId());
    synthesisReport =
      synthesisReportManager.getRPLSynthesisReport(this.getCurrentUser().getLeader(),
        this.getCurrentReportingLogframe());

    /* --------- Checking if the user can submit ------------- */
    Submission submission =
      submissionManager.getSubmission(getCurrentUser().getLeader(), getCurrentReportingLogframe(),
        APConstants.REPORTING_SECTION);

    canSubmit = (submission == null) ? true : false;
  }

  @Override
  public String save() {
    String finalMessage;
    boolean saved =
      synthesisReportManager.saveRPLSynthesisReport(synthesisReport, this.getCurrentUser().getLeader(),
        this.getCurrentReportingLogframe());

    if (saved) {

      if (validationMessage.toString().isEmpty()) {
        addActionMessage(getText("saving.success", new String[] {getText("reporting.rplSynthesisreport")}));
      } else {
        // If there were validation messages show them in a warning message.
        finalMessage = getText("saving.success", new String[] {getText("planning.mainInformation")});
        finalMessage += getText("saving.missingFields", new String[] {validationMessage.toString()});

        addActionWarning(Capitalize.capitalizeString(finalMessage));
      }

      LOG.info("The user {} saved successfully the synthesis report for the leader {}.", getCurrentUser().getEmail(),
        getCurrentUser().getLeader().getId());
      return SUCCESS;
    } else {
      LOG.error("The user {} had a problem saving the synthesis report for the leader {}.",
        getCurrentUser().getEmail(), getCurrentUser().getLeader().getId());
      addActionError(getText("saving.problem"));
      return INPUT;
    }
  }

  @Override
  public void validate() {
    boolean missingfield = false;

    if (save) {
      if (synthesisReport.getCcafsSites() == null || synthesisReport.getCcafsSites().isEmpty()) {
        missingfield = true;
      }

      if (synthesisReport.getCrossCenter() == null || synthesisReport.getCrossCenter().isEmpty()) {
        missingfield = true;
      }

      if (synthesisReport.getRegional() == null || synthesisReport.getRegional().isEmpty()) {
        missingfield = true;
      }

      if (synthesisReport.getDecisionSupport() == null || synthesisReport.getDecisionSupport().isEmpty()) {
        missingfield = true;
      }

      if (missingfield) {
        validationMessage.append(getText("reporting.synthesisReport.validation"));
      }
    }
  }
}
