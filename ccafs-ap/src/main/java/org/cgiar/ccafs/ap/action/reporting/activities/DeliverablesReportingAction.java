package org.cgiar.ccafs.ap.action.reporting.activities;

import org.apache.commons.lang3.StringUtils;
import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;


public class DeliverablesReportingAction extends BaseAction {

  private static final long serialVersionUID = -5156289630539056822L;

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(DeliverablesReportingAction.class);

  // Managers
  private DeliverableManager deliverableManager;
  private ActivityManager activityManager;

  // Model
  private Deliverable[] deliverables;
  private Activity activity;

  private int activityID;


  @Inject
  public DeliverablesReportingAction(APConfig config, LogframeManager logframeManager,
    DeliverableManager deliverableManager, ActivityManager activityManager) {
    super(config, logframeManager);
    this.deliverableManager = deliverableManager;
    this.activityManager = activityManager;
  }

  @Override
  public String execute() throws Exception {
    System.out.println("--------EXCECUTE----------");
    return SUCCESS;
  }


  public Activity getActivity() {
    return activity;
  }


  public String getActivityRequestParameter() {
    return APConstants.ACTIVITY_REQUEST_ID;
  }


  public Deliverable[] getDeliverables() {
    return deliverables;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    // TODO - we need to create another interceptor in order to validate if the current activity exists in the database
    // and validate if the current user has enough privileges to see it.
    activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));
    // get information of deliverables that belong to the activity whit activityID
    deliverables = deliverableManager.getDeliverables(activityID);

    activity = activityManager.getActivityDeliverableInfo(activityID);
  }

  public String save() {
    // TODO Auto-generated method stub
    System.out.println("-------------SAVING-----------");
    return SUCCESS;
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public void setDeliverables(Deliverable[] deliverables) {
    this.deliverables = deliverables;
  }


}
