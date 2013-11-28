package org.cgiar.ccafs.ap.action.reporting.communications;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.CommunicationManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.Communication;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CommunicationsReportingAction extends BaseAction {

  private static Logger LOG = LoggerFactory.getLogger(CommunicationsReportingAction.class);

  // Manager
  CommunicationManager communicationManager;

  // Model
  private Communication communicationReport;

  @Inject
  public CommunicationsReportingAction(APConfig config, LogframeManager logframeManager,
    CommunicationManager communicationManager) {
    super(config, logframeManager);
    this.communicationManager = communicationManager;
  }

  public Communication getCommunicationReport() {
    return communicationReport;
  }

  @Override
  public void prepare() throws Exception {
    communicationReport =
      communicationManager.getCommunicationReport(getCurrentUser().getLeader(), getCurrentReportingLogframe());
  }

  @Override
  public String save() {
    boolean saved;

    saved =
      communicationManager.saveCommunicationReport(communicationReport, getCurrentUser().getLeader(),
        getCurrentReportingLogframe());

    return (saved) ? SUCCESS : INPUT;
  }

  public void setCommunicationReport(Communication communicationReport) {
    this.communicationReport = communicationReport;
  }
}
