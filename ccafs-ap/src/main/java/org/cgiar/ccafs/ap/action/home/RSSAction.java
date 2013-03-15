package org.cgiar.ccafs.ap.action.home;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.Activity;

import com.google.inject.Inject;


public class RSSAction extends BaseAction {

  private static final long serialVersionUID = 4983286741588568418L;

  // Managers
  private ActivityManager activityManager;

  // Models
  private Activity[] activities;
  private int year;

  @Inject
  public RSSAction(APConfig config, LogframeManager logframeManager, ActivityManager activityManager) {
    super(config, logframeManager);
    this.activityManager = activityManager;
  }

  @Override
  public String execute() throws Exception {
    return super.execute();
  }

  public Activity[] getActivities() {
    return activities;
  }

  public String getLimitRequestParameter() {
    return APConstants.ACTIVITY_LIMIT_REQUEST;
  }

  public int getYear() {
    return year;
  }

  public String getYearRequestParameter() {
    return APConstants.ACTIVITY_YEAR_REQUEST;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    String yearRequested = this.getRequest().getParameter(this.getYearRequestParameter());
    String limitRequested = this.getRequest().getParameter(this.getLimitRequestParameter());
    if (yearRequested != null) {
      try {
        year = Integer.parseInt(yearRequested);
      } catch (NumberFormatException e) {
        year = 0;
      }
    } else {
      year = 0;
    }
    int limit = -1;
    if (limitRequested != null) {
      try {
        limit = Integer.parseInt(limitRequested);
      } catch (NumberFormatException e) {
        // Nothing here
      }
    }
    activities = activityManager.getActivitiesForRSS(year, limit);
    if (activities == null) {
      activities = new Activity[0];
    }
  }


}
