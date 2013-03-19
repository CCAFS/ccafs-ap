package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.Activity;

import com.google.inject.Inject;


public class ActivitiesPlanningAction extends BaseAction {

  private static final long serialVersionUID = 610099339078512575L;

  // Managers
  protected ActivityManager activityManager;

  // Model
  private Activity[] currentActivities;
  private String[] activityStatuses;

  @Inject
  public ActivitiesPlanningAction(APConfig config, LogframeManager logframeManager, ActivityManager activityManager) {
    super(config, logframeManager);
    this.activityManager = activityManager;
  }

  /**
   * This method validates if a specific activity is completely filled or not.
   * It validates at first step if the activity status and the description has some content; at second step if the
   * activity has at least one deliverable without be reported; and finally it validates if the activity has at least
   * one partner associated without all the contact information filled.
   */
  private String calculateStatus(Activity activity) {
    // TODO
    return null;
  }

  public String getActivityRequestParameter() {
    return APConstants.ACTIVITY_REQUEST_ID;
  }

  public String[] getActivityStatuses() {
    return activityStatuses;
  }

  public Activity[] getCurrentActivities() {
    return currentActivities;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    currentActivities = activityManager.getActivities(config.getPlanningCurrentYear(), this.getCurrentUser());
    // activityStatuses = new String[currentActivities.length];
    // Calculate the status of each activity.
    /*
     * for (int c = 0; c < currentActivities.length; c++) {
     * activityStatuses[c] = calculateStatus(currentActivities[c]);
     * }
     */
  }

  public void setCurrentActivities(Activity[] currentActivities) {
    this.currentActivities = currentActivities;
  }


}
