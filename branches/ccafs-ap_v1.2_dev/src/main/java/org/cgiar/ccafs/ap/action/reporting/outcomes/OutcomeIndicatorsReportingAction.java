package org.cgiar.ccafs.ap.action.reporting.outcomes;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.OutcomeIndicatorReportManager;
import org.cgiar.ccafs.ap.data.manager.ThemeManager;
import org.cgiar.ccafs.ap.data.model.OutcomeIndicatorReport;
import org.cgiar.ccafs.ap.data.model.Theme;

import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OutcomeIndicatorsReportingAction extends BaseAction {

  private static final long serialVersionUID = 8463505732461732629L;
  private static Logger LOG = LoggerFactory.getLogger(OutcomeIndicatorsReportingAction.class);

  // Managers
  private OutcomeIndicatorReportManager outcomeIndicatorReportManager;
  private ThemeManager themeManager;

  // Model
  private List<OutcomeIndicatorReport> outcomeIndicatorReports;
  private Theme[] themes;
  private int currentIndicatorsTheme;

  @Inject
  public OutcomeIndicatorsReportingAction(APConfig config, LogframeManager logframeManager,
    OutcomeIndicatorReportManager outcomeIndicatorReportManager, ThemeManager themeManager) {
    super(config, logframeManager);
    this.outcomeIndicatorReportManager = outcomeIndicatorReportManager;
    this.themeManager = themeManager;
  }

  public int getCurrentIndicatorsTheme() {
    return currentIndicatorsTheme;
  }

  public List<OutcomeIndicatorReport> getOutcomeIndicatorReports() {
    return outcomeIndicatorReports;
  }

  public Theme[] getThemes() {
    return themes;
  }

  @Override
  public void prepare() throws Exception {
    outcomeIndicatorReports = outcomeIndicatorReportManager.getOutcomeIndicatorReports(getCurrentReportingLogframe());
    themes = themeManager.getThemes(getCurrentReportingLogframe());
  }

  @Override
  public String save() {
    outcomeIndicatorReportManager.saveOutcomeIndicatorReports(outcomeIndicatorReports, getCurrentUser().getLeader(),
      getCurrentReportingLogframe());
    return super.save();
  }

  public void setCurrentIndicatorsTheme(int currentIndicatorsTheme) {
    this.currentIndicatorsTheme = currentIndicatorsTheme;
  }

  public void setOutcomeIndicatorReports(List<OutcomeIndicatorReport> outcomeIndicatorReports) {
    this.outcomeIndicatorReports = outcomeIndicatorReports;
  }
}