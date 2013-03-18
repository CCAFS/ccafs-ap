package org.cgiar.ccafs.ap.action.reporting.outcomes;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.OutcomeManager;
import org.cgiar.ccafs.ap.data.model.Outcome;

import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OutcomesReportingAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(OutcomesReportingAction.class);
  private static final long serialVersionUID = -1903471529414936807L;

  // Managers
  private OutcomeManager outcomeManager;

  // Models
  private List<Outcome> outcomes;

  @Inject
  public OutcomesReportingAction(APConfig config, LogframeManager logframeManager, OutcomeManager outcomeManager) {
    super(config, logframeManager);
    this.outcomeManager = outcomeManager;
  }

  public List<Outcome> getOutcomes() {
    return outcomes;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    LOG.info("The user {} loads the outcomes section.", getCurrentUser().getEmail());
    outcomes = outcomeManager.getOutcomes(this.getCurrentUser().getLeader(), this.getCurrentLogframe());

    // Remove all outcomes so they can be added again in the save method.
    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      LOG.debug("The outcomes for leader {} have been deleted from the model to save them later", getCurrentUser()
        .getLeader().getId());
      outcomes.clear();
    }
  }

  @Override
  public String save() {
    if (outcomes.size() > 0) {
      // Remove all outcomes from the database.
      boolean removed = outcomeManager.removeOutcomes(this.getCurrentUser().getLeader(), this.getCurrentLogframe());
      if (removed) {
        boolean added =
          outcomeManager.addOutcomes(outcomes, this.getCurrentUser().getLeader(), this.getCurrentLogframe());
        if (added) {
          LOG.info("The user {} saved the outcomes for the leader {}.", getCurrentUser().getEmail(), getCurrentUser()
            .getLeader().getId());
          addActionMessage(getText("saving.success", new String[] {getText("reporting.outcomes")}));
          return SUCCESS;
        }
      }
    }
    LOG.warn("There was an error saving the outcomes for the leader {}", getCurrentUser().getLeader());
    addActionError(getText("saving.problem"));
    return INPUT;
  }

  public void setOutcomes(List<Outcome> outcomes) {
    this.outcomes = outcomes;
  }

}
