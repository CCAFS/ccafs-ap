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
import org.cgiar.ccafs.ap.data.model.Status;

import java.util.ArrayList;
import java.util.List;

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
  private Activity activity;
  private int activityID;
  private List<Status> statusList;


  @Inject
  public StatusReportingAction(APConfig config, LogframeManager logframeManager, ActivityManager activityManager,
    ContactPersonManager contactPersonManager, BudgetManager budgetManager) {
    super(config, logframeManager);
    this.activityManager = activityManager;
    this.contactPersonManager = contactPersonManager;
    this.budgetManager = budgetManager;

    // TODO - populate the statusList variable directly from the database.
    statusList = new ArrayList<>();
    statusList.add(new Status(1, "Completed"));
    statusList.add(new Status(2, "Partially completed"));
    statusList.add(new Status(3, "Uncompleted"));
    // --------------------------------------------------------------------

  }


  @Override
  public String execute() throws Exception {
    System.out.println("--------EXCECUTE----------");
    System.out.println(activity);
    System.out.println("-------------------------");
    return SUCCESS;
  }


  public Activity getActivity() {
    return activity;
  }

  public String getMilestoneRequestParameter() {
    return APConstants.MILESTONE_REQUEST_ID;
  }

  public List<Status> getStatusList() {
    return statusList;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    // TODO - we need to create another interceptor in order to validate if the current activity exists in the database
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

  public String save() {
    // TODO Auto-generated method stub
    System.out.println("-------------SAVING-----------");
    System.out.println("------------------------------");
    return SUCCESS;
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }


}
