package org.cgiar.ccafs.ap.action.reporting.outcomes;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.OutcomeIndicatorManager;
import org.cgiar.ccafs.ap.data.model.OutcomeIndicator;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OutcomeIndicatorsReportingAction extends BaseAction {

  private static final long serialVersionUID = 8463505732461732629L;
  private static Logger LOG = LoggerFactory.getLogger(OutcomeIndicatorsReportingAction.class);

  // Managers
  private OutcomeIndicatorManager outcomeIndicatorManager;

  // Model
  OutcomeIndicator[] outcomeIndicators;

  @Inject
  public OutcomeIndicatorsReportingAction(APConfig config, LogframeManager logframeManager,
    OutcomeIndicatorManager outcomeIndicatorManager) {
    super(config, logframeManager);
    this.outcomeIndicatorManager = outcomeIndicatorManager;
  }

  public OutcomeIndicator[] getOutcomeIndicators() {
    return outcomeIndicators;
  }

  @Override
  public void prepare() throws Exception {
    outcomeIndicators = outcomeIndicatorManager.getOutcomeIndicators(getCurrentReportingLogframe());
  }

}