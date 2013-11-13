package org.cgiar.ccafs.ap.action.reporting.leverages;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.LeverageManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.ThemeManager;
import org.cgiar.ccafs.ap.data.model.Leverage;
import org.cgiar.ccafs.ap.data.model.Theme;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LeveragesReportingAction extends BaseAction {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(LeveragesReportingAction.class);
  private static final long serialVersionUID = 5600071396191142596L;

  // Manager
  private LeverageManager leverageManager;
  private ThemeManager themeManager;

  // Model
  private List<Leverage> leverages;
  private Map<Integer, String> themeList;

  @Inject
  public LeveragesReportingAction(APConfig config, LogframeManager logframeManager, LeverageManager leverageManager,
    ThemeManager themeManager) {
    super(config, logframeManager);
    this.leverageManager = leverageManager;
    this.themeManager = themeManager;
  }

  public List<Leverage> getLeverages() {
    return leverages;
  }

  public Map<Integer, String> getThemeList() {
    return themeList;
  }

  @Override
  public void prepare() throws Exception {
    leverages = leverageManager.getLeverages(getCurrentUser().getLeader(), getCurrentReportingLogframe());

    Theme[] themes = themeManager.getThemes(this.getCurrentReportingLogframe());
    themeList = new HashMap<>();
    for (Theme theme : themes) {
      themeList.put(theme.getId(), getText("reporting.publications.Theme") + " " + theme.getCode());
    }

    // Remove all indicators so they can be added again in the save method.
    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      LOG.debug("The leverages for leader {} have been deleted from the model to save them later", getCurrentUser()
        .getLeader().getId());
      leverages.clear();
    }
  }

  @Override
  public String save() {
    boolean saved = false;

    // First, remove all the leverages
    if (leverageManager.removeLeverages(getCurrentUser().getLeader(), getCurrentReportingLogframe())) {
      saved = leverageManager.saveLeverages(leverages, getCurrentUser().getLeader());
    } else {
      Log.warn("There was an error removing the leverages from the database.");
    }

    if (saved) {
      LOG.info("The user {} saved the leverages for the leader {}.", getCurrentUser().getEmail(), getCurrentUser()
        .getLeader().getId());
      addActionMessage(getText("saving.success", new String[] {getText("reporting.leverages")}));
      return SUCCESS;
    } else {
      LOG.warn("There was an error saving the leverages for the leader {}", getCurrentUser().getLeader());
      addActionError(getText("saving.problem"));
      return INPUT;
    }
  }

  public void setLeverages(List<Leverage> leverages) {
    this.leverages = leverages;
  }
}