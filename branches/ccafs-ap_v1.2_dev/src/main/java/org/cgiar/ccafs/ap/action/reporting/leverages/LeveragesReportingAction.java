package org.cgiar.ccafs.ap.action.reporting.leverages;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.LeverageManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.Leverage;

import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LeveragesReportingAction extends BaseAction {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(LeveragesReportingAction.class);
  private static final long serialVersionUID = 5600071396191142596L;

  // Manager
  private LeverageManager leverageManager;

  // Model
  List<Leverage> leverages;

  @Inject
  public LeveragesReportingAction(APConfig config, LogframeManager logframeManager, LeverageManager leverageManager) {
    super(config, logframeManager);
    this.leverageManager = leverageManager;
  }

  public List<Leverage> getLeverages() {
    return leverages;
  }

  @Override
  public void prepare() throws Exception {
    leverages = leverageManager.getLeverages(getCurrentUser().getLeader(), getCurrentReportingLogframe());
  }

  @Override
  public String save() {
    boolean saved = leverageManager.saveLeverages(leverages, getCurrentUser().getLeader());

    if (saved) {
      addActionMessage("");
      return SUCCESS;
    } else {
      addActionError("");
      return INPUT;
    }
  }

  public void setLeverages(List<Leverage> leverages) {
    this.leverages = leverages;
  }
}