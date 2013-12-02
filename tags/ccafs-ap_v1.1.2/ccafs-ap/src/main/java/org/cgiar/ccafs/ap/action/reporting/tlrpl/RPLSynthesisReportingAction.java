package org.cgiar.ccafs.ap.action.reporting.tlrpl;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.RPLSynthesisReportManager;
import org.cgiar.ccafs.ap.data.model.RPLSynthesisReport;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RPLSynthesisReportingAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(RPLSynthesisReportingAction.class);
  private static final long serialVersionUID = 4734278462072395648L;

  // Managers
  RPLSynthesisReportManager synthesisReportManager;

  // Models
  private RPLSynthesisReport synthesisReport;

  @Inject
  public RPLSynthesisReportingAction(APConfig config, LogframeManager logframeManager,
    RPLSynthesisReportManager synthesisReportManager) {
    super(config, logframeManager);
    this.synthesisReportManager = synthesisReportManager;
  }

  public RPLSynthesisReport getSynthesisReport() {
    return synthesisReport;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    LOG.info("The user {} loads the synthesis section for the leader {}.", getCurrentUser().getEmail(),
      getCurrentUser().getLeader().getId());
    synthesisReport =
      synthesisReportManager.getRPLSynthesisReport(this.getCurrentUser().getLeader(), this.getCurrentReportingLogframe());
  }

  @Override
  public String save() {
    boolean saved =
      synthesisReportManager.saveRPLSynthesisReport(synthesisReport, this.getCurrentUser().getLeader(),
        this.getCurrentReportingLogframe());
    if (saved) {
      LOG.info("The user {} saved successfully the synthesis report for the leader {}.", getCurrentUser().getEmail(),
        getCurrentUser().getLeader().getId());
      addActionMessage(getText("saving.success", new String[] {getText("reporting.rplSynthesisreport")}));
      return SUCCESS;
    } else {
      LOG.error("The user {} had a problem saving the synthesis report for the leader {}.",
        getCurrentUser().getEmail(), getCurrentUser().getLeader().getId());
      addActionError(getText("saving.problem"));
      return INPUT;
    }
  }
}
