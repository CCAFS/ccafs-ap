package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.LeaderManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Leader;

import java.util.Map;
import java.util.TreeMap;

import com.google.inject.Inject;


public class AddActivityPlanningAction extends BaseAction {

  private static final long serialVersionUID = 9154840925469694494L;

  // Managers
  private ActivityManager activityManager;
  private LeaderManager leaderManager;

  // Model
  private Activity activity;

  private Map<Integer, String> continuousActivityList;

  private Leader[] leaders;

  @Inject
  public AddActivityPlanningAction(APConfig config, LogframeManager logframeManager, ActivityManager activityManager,
    LeaderManager leaderManager) {
    super(config, logframeManager);
    this.activityManager = activityManager;
    this.leaderManager = leaderManager;
    this.continuousActivityList = new TreeMap<>();
  }

  @Override
  public String execute() throws Exception {
    System.out.println(activity);
    return super.execute();
  }

  public Activity getActivity() {
    return activity;
  }

  public Map<Integer, String> getContinuousActivityList() {
    return continuousActivityList;
  }

  public Leader[] getLeaders() {
    return leaders;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    leaders = leaderManager.getAllLeaders();
    Activity[] oldActivities =
      activityManager.getActivities(this.getCurrentPlanningLogframe().getYear() - 1, this.getCurrentUser());
    String text;
    continuousActivityList.put(-1, "Select an activity.");
    for (int c = 0; c < oldActivities.length; c++) {
      text =
        oldActivities[c].getId()
          + " - "
          + (oldActivities[c].getTitle().length() > 40 ? oldActivities[c].getTitle().substring(0, 40)
            : oldActivities[c].getTitle()) + "...";
      continuousActivityList.put(oldActivities[c].getId(), text);
    }
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

}
