package org.cgiar.ccafs.ap.action.reporting.activities;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.Activity;

import com.google.inject.Inject;


public class ActivitiesReportingAction extends BaseAction {

  private static final long serialVersionUID = 9001775749549472317L;
  private ActivityManager activityManager;

  private Activity[] currentActivities;

  @Inject
  public ActivitiesReportingAction(APConfig config, ActivityManager activityManager, LogframeManager logframeManager) {
    super(config, logframeManager);
    this.activityManager = activityManager;
  }

  public Activity[] getCurrentActivities() {
    return currentActivities;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    currentActivities = activityManager.getActivities(config.getCurrentYear(), this.getCurrentUser().getLeader());
  }

  public void setCurrentActivities(Activity[] currentActivities) {
    this.currentActivities = currentActivities;
  }


}
