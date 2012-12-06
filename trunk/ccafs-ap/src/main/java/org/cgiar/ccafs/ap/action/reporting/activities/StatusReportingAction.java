package org.cgiar.ccafs.ap.action.reporting.activities;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.ContactPersonManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.StatusManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Budget;
import org.cgiar.ccafs.ap.data.model.ContactPerson;
import org.cgiar.ccafs.ap.data.model.Status;

import java.util.LinkedHashMap;
import java.util.Map;

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
  private StatusManager statusManager;

  // Model
  private Activity activity;
  private int activityID;
  private Status[] statusList;
  private Map<String, String> genderOptions;


  @Inject
  public StatusReportingAction(APConfig config, LogframeManager logframeManager, ActivityManager activityManager,
    ContactPersonManager contactPersonManager, BudgetManager budgetManager, StatusManager statusManager) {
    super(config, logframeManager);
    this.activityManager = activityManager;
    this.contactPersonManager = contactPersonManager;
    this.budgetManager = budgetManager;
    this.statusManager = statusManager;

    this.genderOptions = new LinkedHashMap<>();
    genderOptions.put("1", "Yes");
    genderOptions.put("2", "No");
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


  public String getActivityRequestParameter() {
    return APConstants.ACTIVITY_REQUEST_ID;
  }

  public Map<String, String> getGenderOptions() {
    return genderOptions;
  }

  public String getHasGender() {
    if (this.getActivity().getGenderIntegrationsDescription() != null) {
      return "1";
    } else {
      return "2";
    }
  }

  public String getMilestoneRequestParameter() {
    return APConstants.MILESTONE_REQUEST_ID;
  }

  public Status[] getStatusList() {
    return statusList;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    this.statusList = statusManager.getStatusList();

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
    System.out.println(activity.getStatus());
    System.out.println("------------------------------");
    return SUCCESS;
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }
}
