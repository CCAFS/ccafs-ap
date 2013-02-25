package org.cgiar.ccafs.ap.action.reporting.tlrpl;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.RPLSynthesisReportManager;
import org.cgiar.ccafs.ap.data.model.RPLSynthesisReport;

import com.google.inject.Inject;


public class RPLSynthesisReportingAction extends BaseAction {

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
    synthesisReport =
      synthesisReportManager.getRPLSynthesisReport(this.getCurrentUser().getLeader(), this.getCurrentLogframe());
  }

  @Override
  public String save() {
    boolean saved =
      synthesisReportManager.saveRPLSynthesisReport(synthesisReport, this.getCurrentUser().getLeader(),
        this.getCurrentLogframe());
    if (saved) {
      addActionMessage(getText("saving.success", new String[] {getText("reporting.rplSynthesisreport")}));
      return SUCCESS;
    } else {
      addActionError(getText("saving.problem"));
      return INPUT;
    }
  }
}
