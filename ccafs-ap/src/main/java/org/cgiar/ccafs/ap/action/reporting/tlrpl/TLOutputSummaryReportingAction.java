package org.cgiar.ccafs.ap.action.reporting.tlrpl;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.TLOutputSummaryManager;
import org.cgiar.ccafs.ap.data.model.TLOutputSummary;

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

  // Managers
  TLOutputSummaryManager tlOutputManager;

  @Inject
  public TLOutputSummaryReportingAction(APConfig config, LogframeManager logframeManager,
    TLOutputSummaryManager tlOutputManager) {
    super(config, logframeManager);
    this.tlOutputManager = tlOutputManager;
  }

  public List<TLOutputSummary> getTlOutputSummaries() {
    return tlOutputSummaries;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    LOG.info("The user {} loads the TL output summary for the leader {}.", getCurrentUser().getEmail(),
      getCurrentUser().getLeader().getId());
    tlOutputSummaries =
      tlOutputManager.getTLOutputSummaries(this.getCurrentUser().getLeader(), this.getCurrentLogframe());
  }

  @Override
  public String save() {
    boolean problem = false;
    problem = tlOutputManager.saveTLOutputSummaries(tlOutputSummaries, this.getCurrentUser().getLeader());
    if (problem) {
      LOG.warn("The user {} had a problem saving the TL output summary for the leader {}.",
        getCurrentUser().getEmail(), getCurrentUser().getLeader().getId());
      addActionError(getText("saving.problem"));
      return INPUT;
    } else {
      LOG.info("The user {} saved successfully the TL output summary for the leader {}.", getCurrentUser().getEmail(),
        getCurrentUser().getLeader().getId());
      addActionMessage(getText("saving.success", new String[] {getText("reporting.tlOutputSummaries")}));
      return SUCCESS;
    }
  }

  public void setTlOutputSummaries(List<TLOutputSummary> tlOutputSummaries) {
    this.tlOutputSummaries = tlOutputSummaries;
  }

}
