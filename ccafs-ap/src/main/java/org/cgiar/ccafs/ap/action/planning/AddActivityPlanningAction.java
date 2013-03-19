package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.LeaderManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Leader;

import com.google.inject.Inject;


public class AddActivityPlanningAction extends BaseAction {

  private static final long serialVersionUID = 9154840925469694494L;

  // Managers
  private ActivityManager activityManager;
  private LeaderManager leaderManager;

  // Model
  private Activity activity;
  private Activity[] oldActivities;
  private Leader[] leaders;

  @Inject
  public AddActivityPlanningAction(APConfig config, LogframeManager logframeManager, ActivityManager activityManager,
    LeaderManager leaderManager) {
    super(config, logframeManager);
    this.activityManager = activityManager;
    this.leaderManager = leaderManager;
  }

  @Override
  public String execute() throws Exception {
    // TODO Auto-generated method stub
    return super.execute();
  }

  public Activity getActivity() {
    return activity;
  }

  public Leader[] getLeaders() {
    return leaders;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    leaders = leaderManager.getAllLeaders();
  }


}
