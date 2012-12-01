package org.cgiar.ccafs.ap.action.reporting.activities;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.ContactPersonManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Budget;
import org.cgiar.ccafs.ap.data.model.ContactPerson;

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
  private ContactPersonManager contactPersonManager;
  private BudgetManager budgetManager;

  // Model
  protected Activity activity;
  protected int activityID;

  @Inject
  public StatusReportingAction(APConfig config, LogframeManager logframeManager, ActivityManager activityManager,
    ContactPersonManager contactPersonManager, BudgetManager budgetManager) {
    super(config, logframeManager);
    this.activityManager = activityManager;
    this.contactPersonManager = contactPersonManager;
    this.budgetManager = budgetManager;
  }

  @Override
  public String execute() throws Exception {
    return LOGIN;
  }

  public Activity getActivity() {
    return activity;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    // TODO - get the activity id requested.
    // Also we need to create another interceptor in order to validate if the current activity exists in the database
    // and validate if the current user has enough privileges to see it.
    activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));
    // get main activity information based on the status form.
    activity = activityManager.getActivityStatusInfo(activityID);
    // get contact persons.
    ContactPerson[] contactPersons = contactPersonManager.getContactPersons(activityID);
    activity.setContactPersons(contactPersons);
    // get activity budget.
    Budget budget = budgetManager.getBudget(activityID);
    activity.setBudget(budget);
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }


}
