package org.cgiar.ccafs.ap.action.reporting.indicators;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.IndicatorReportManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.IndicatorReport;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;

import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IndicatorsReportingAction extends BaseAction {

  // Loger
  private static Logger LOG = LoggerFactory.getLogger(IndicatorsReportingAction.class);
  private static final long serialVersionUID = -5180271642115069487L;

  // Manager
  private IndicatorReportManager indicatorReportManager;

  // Model
  List<IndicatorReport> indicatorReports;

  @Inject
  public IndicatorsReportingAction(APConfig config, LogframeManager logframeManager,
    IndicatorReportManager indicatorReportManager) {
    super(config, logframeManager);
    this.indicatorReportManager = indicatorReportManager;
  }

  public int getCurrentReportingYear() {
    return config.getReportingCurrentYear();
  }

  public List<IndicatorReport> getIndicatorReports() {
    return indicatorReports;
  }

  @Override
  public void prepare() throws Exception {
    Leader leader = getCurrentUser().getLeader();
    Logframe logframe = getCurrentReportingLogframe();
    indicatorReports = indicatorReportManager.getIndicatorReportsList(leader, logframe);
  }

  @Override
  public String save() {
    boolean saved =
      indicatorReportManager.saveIndicatorReportsList(indicatorReports, getCurrentUser().getLeader(),
        getCurrentReportingLogframe());

    if (saved) {
      LOG.info("The user {} saved the indicators for the leader {}.", getCurrentUser().getEmail(), getCurrentUser()
        .getLeader().getId());
      addActionMessage(getText("saving.success", new String[] {getText("reporting.indicators")}));
      return SUCCESS;
    } else {
      LOG.warn("There was an error saving the indicators for the leader {}", getCurrentUser().getLeader());
      addActionError(getText("saving.problem"));
      return INPUT;
    }
  }


  public void setIndicatorReports(List<IndicatorReport> indicatorReports) {
    this.indicatorReports = indicatorReports;
  }


}
