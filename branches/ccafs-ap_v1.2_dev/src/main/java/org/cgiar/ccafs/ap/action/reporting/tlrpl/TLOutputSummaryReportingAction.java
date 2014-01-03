package org.cgiar.ccafs.ap.action.reporting.tlrpl;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.manager.TLOutputSummaryManager;
import org.cgiar.ccafs.ap.data.model.Submission;
import org.cgiar.ccafs.ap.data.model.TLOutputSummary;
import org.cgiar.ccafs.ap.util.Capitalize;

import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TLOutputSummaryReportingAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(TLOutputSummaryReportingAction.class);
  private static final long serialVersionUID = 2184360140983552059L;

  // Models
  private List<TLOutputSummary> tlOutputSummaries;
  private StringBuilder validationMessage;
  private boolean canSubmit;

  // Managers
  private TLOutputSummaryManager tlOutputManager;
  private SubmissionManager submissionManager;


  @Inject
  public TLOutputSummaryReportingAction(APConfig config, LogframeManager logframeManager,
    TLOutputSummaryManager tlOutputManager, SubmissionManager submissionManager) {
    super(config, logframeManager);
    this.tlOutputManager = tlOutputManager;
    this.submissionManager = submissionManager;
    validationMessage = new StringBuilder();
  }

  public List<TLOutputSummary> getTlOutputSummaries() {
    return tlOutputSummaries;
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
    LOG.info("The user {} loads the TL output summary for the leader {}.", getCurrentUser().getEmail(),
      getCurrentUser().getLeader().getId());
    tlOutputSummaries =
      tlOutputManager.getTLOutputSummaries(this.getCurrentUser().getLeader(), this.getCurrentReportingLogframe());

    /* --------- Checking if the user can submit ------------- */
    Submission submission =
      submissionManager.getSubmission(getCurrentUser().getLeader(), getCurrentReportingLogframe(),
        APConstants.REPORTING_SECTION);

    canSubmit = (submission == null) ? true : false;
  }

  @Override
  public String save() {
    String finalMessage;
    boolean problem = false;

    problem = tlOutputManager.saveTLOutputSummaries(tlOutputSummaries, this.getCurrentUser().getLeader());
    if (problem) {
      LOG.warn("The user {} had a problem saving the TL output summary for the leader {}.",
        getCurrentUser().getEmail(), getCurrentUser().getLeader().getId());
      addActionError(getText("saving.problem"));
      return INPUT;
    } else {
      if (validationMessage.toString().isEmpty()) {
        addActionMessage(getText("saving.success", new String[] {getText("reporting.tlOutputSummaries")}));
      } else {
        // If there were validation messages show them in a warning message.
        finalMessage = getText("saving.success", new String[] {getText("reporting.tlOutputSummaries")});
        finalMessage += getText("saving.missingFields", new String[] {validationMessage.toString()});

        addActionWarning(Capitalize.capitalizeString(finalMessage));
      }
      LOG.info("The user {} saved successfully the TL output summary for the leader {}.", getCurrentUser().getEmail(),
        getCurrentUser().getLeader().getId());
      return SUCCESS;
    }
  }

  public void setTlOutputSummaries(List<TLOutputSummary> tlOutputSummaries) {
    this.tlOutputSummaries = tlOutputSummaries;
  }

  @Override
  public void validate() {
    boolean missing = false;

    if (save) {
      for (TLOutputSummary tlo : tlOutputSummaries) {
        if (tlo.getDescription() == null || tlo.getDescription().isEmpty()) {
          missing = true;
        }
      }

      if (missing) {
        validationMessage.append(getText("reporting.tlOutputSummaries.validation"));
      }
    }
  }
}
