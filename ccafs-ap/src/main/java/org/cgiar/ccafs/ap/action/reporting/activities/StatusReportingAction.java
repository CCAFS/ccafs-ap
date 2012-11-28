package org.cgiar.ccafs.ap.action.reporting.activities;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.Activity;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StatusReportingAction extends BaseAction {

  private static final long serialVersionUID = 8943904574788764606L;

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(StatusReportingAction.class);

  // Managers
  private ActivityManager activityManager;

  // Model
  protected Activity activity;
  protected int activityID;

  @Inject
  public StatusReportingAction(APConfig config, LogframeManager logframeManager, ActivityManager activityManager) {
    super(config, logframeManager);
    this.activityManager = activityManager;
  }

  @Override
  public String execute() throws Exception {
    System.out.println("EXECUTING STATUS");
    System.out.println(this.getRequest().toString());
    return LOGIN;
  }

  public Activity getActivity() {
    return activity;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    // TODO - get the request activity id. Also create another interceptor to validate if the requested id actually
    // exists in the database.
    activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));
    activity = activityManager.getActivityStatusInfo(activityID);
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }


}
