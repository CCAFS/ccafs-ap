package org.cgiar.ccafs.ap.action.reporting.outcomes;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.OutcomeManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.model.Outcome;
import org.cgiar.ccafs.ap.data.model.Submission;
import org.cgiar.ccafs.ap.util.Capitalize;

import java.util.List;

import com.google.inject.Inject;
import org.apache.shiro.mgt.SecurityManager;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OutcomesReportingAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(OutcomesReportingAction.class);
  private static final long serialVersionUID = -1903471529414936807L;

  // Managers
  private OutcomeManager outcomeManager;
  private SubmissionManager submissionManager;

  // Models
  private List<Outcome> outcomes;
  private StringBuilder validationMessage;
  private boolean canSubmit;

  @Inject
  public OutcomesReportingAction(APConfig config, LogframeManager logframeManager, SecurityManager securityManager,
    OutcomeManager outcomeManager, SubmissionManager submissionManager) {
    super(config, logframeManager, securityManager);
    this.outcomeManager = outcomeManager;
    this.submissionManager = submissionManager;

    validationMessage = new StringBuilder();
  }

  public List<Outcome> getOutcomes() {
    return outcomes;
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
    LOG.info("The user {} loads the outcomes section.", getCurrentUser().getEmail());
    outcomes = outcomeManager.getOutcomes(this.getCurrentUser().getLeader(), this.getCurrentReportingLogframe());

    // Remove all outcomes so they can be added again in the save method.
    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      LOG.debug("The outcomes for leader {} have been deleted from the model to save them later", getCurrentUser()
        .getLeader().getId());
      outcomes.clear();
    }

    /* --------- Checking if the user can submit ------------- */
    Submission submission =
      submissionManager.getSubmission(getCurrentUser().getLeader(), getCurrentReportingLogframe(),
        APConstants.REPORTING_SECTION);

    canSubmit = (submission == null) ? true : false;
  }

  @Override
  public String save() {
    String finalMessage;
    boolean saved = false;

    if (outcomeManager.removeOutcomes(this.getCurrentUser().getLeader(), this.getCurrentReportingLogframe())) {
      saved =
        outcomeManager.addOutcomes(outcomes, this.getCurrentUser().getLeader(), this.getCurrentReportingLogframe());
    } else {
      Log.warn("There was an error removing the outcomes from the database.");
    }

    if (saved) {
      if (validationMessage.toString().isEmpty()) {
        addActionMessage(getText("saving.success", new String[] {getText("reporting.outcomes")}));
      } else {
        // If there were validation messages show them in a warning message.
        finalMessage = getText("saving.success", new String[] {getText("reporting.outcomes")});
        finalMessage += getText("saving.missingFields", new String[] {validationMessage.toString()});

        addActionWarning(Capitalize.capitalizeString(finalMessage));
      }

      LOG.info("The user {} saved the outcomes for the leader {}.", getCurrentUser().getEmail(), getCurrentUser()
        .getLeader().getId());
      return SUCCESS;
    } else {
      LOG.warn("There was an error saving the outcomes for the leader {}", getCurrentUser().getLeader());
      addActionError(getText("saving.problem"));
    }

    return INPUT;
  }

  public void setOutcomes(List<Outcome> outcomes) {
    this.outcomes = outcomes;
  }

  @Override
  public void validate() {
    boolean missingField = false;

    if (save) {
      for (Outcome o : outcomes) {
        if (o.getTitle() == null || o.getTitle().isEmpty()) {
          missingField = true;
        }
        if (o.getOutcome() == null || o.getOutcome().isEmpty()) {
          missingField = true;
        }
        if (o.getOutputs() == null || o.getOutputs().isEmpty()) {
          missingField = true;
        }
        if (o.getPartners() == null || o.getPartners().isEmpty()) {
          missingField = true;
        }
        if (o.getOutputUser() == null || o.getOutputUser().isEmpty()) {
          missingField = true;
        }
        if (o.getHowUsed() == null || o.getHowUsed().isEmpty()) {
          missingField = true;
        }
        if (o.getEvidence() == null || o.getEvidence().isEmpty()) {
          missingField = true;
        }
      }

      if (missingField) {
        validationMessage.append(getText("reporting.outcomes.validation"));
      }
    }
  }
}
