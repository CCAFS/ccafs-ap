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
import org.cgiar.ccafs.ap.util.Capitalize;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StatusReportingAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(StatusReportingAction.class);
  private static final long serialVersionUID = 8943904574788764606L;

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
  private String genderIntegrationOption;
  private StringBuilder validationMessage;

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
    genderOptions.put("0", "No");

    validationMessage = new StringBuilder();
  }

  public Activity getActivity() {
    return activity;
  }

  public String getActivityRequestParameter() {
    return APConstants.ACTIVITY_REQUEST_ID;
  }


  public String getGenderIntegrationOption() {
    return genderIntegrationOption;
  }


  public Map<String, String> getGenderOptions() {
    return genderOptions;
  }

  public boolean getHasGender() {
    if (this.getActivity().getGenderIntegrationsDescription() != null) {
      if (this.getGenderIntegrationOption() == null || this.getGenderIntegrationOption().equals("1")) {
        return true;
      }
    }
    return false;
  }

  public String getMilestoneRequestParameter() {
    return APConstants.MILESTONE_REQUEST_ID;
  }

  public Status[] getStatusList() {
    return statusList;
  }

  @Override
  public String next() {
    save();
    return super.next();
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    this.statusList = statusManager.getStatusList();

    activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));
    LOG.info("The user {} loaded the status section for the activity {}", getCurrentUser().getEmail(), activityID);
    // get main activity information based on the status form.
    activity = activityManager.getActivityStatusInfo(activityID);
    // If the activity status is undefined, set it to imcomplete by default
    if (activity.getStatus().getId() == 0) {
      activity.setStatus(statusList[2]);
    }
    // get contact persons.
    List<ContactPerson> contactPersons = contactPersonManager.getContactPersons(activityID);
    activity.setContactPersons(contactPersons);
    // get activity budget.
    Budget budget = budgetManager.getBudget(activityID);
    activity.setBudget(budget);

  }

  @Override
  public String save() {
    if (activityManager.saveStatus(activity)) {
      if (validationMessage.toString().isEmpty()) {
        addActionMessage(getText("saving.success", new String[] {getText("reporting.activityStatus")}));
        LOG.info("The user {} saved the status of the activity {} successfully.", getCurrentUser().getEmail(),
          activityID);
      } else {
        String finalMessage = getText("saving.success", new String[] {getText("reporting.activityStatus")});
        finalMessage += getText("saving.keepInMind", new String[] {validationMessage.toString()});
        addActionWarning(Capitalize.capitalizeString(finalMessage));
      }

      return SUCCESS;
    } else {
      LOG.warn("The user {} had problems to save the status of the activity {} successfully.", getCurrentUser()
        .getEmail(), activityID);
      addActionError(getText("saving.problem"));
      return INPUT;
    }

  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public void setGenderIntegrationOption(String genderIntegrationOption) {
    this.genderIntegrationOption = genderIntegrationOption;
  }


  @Override
  public void validate() {
    super.validate();

    if (save) {
      if (activity.getStatusDescription().isEmpty()) {
        validationMessage.append(getText("reporting.activityStatus.statusDescription.invalid"));
      }

      if (this.getHasGender() && activity.getGenderIntegrationsDescription().isEmpty()) {
        validationMessage.append(getText("validation.field.required"));
      }

      if (activity.getStatus().getId() == 0) {
        validationMessage.append(getText("reporting.activityStatus.status.invalid"));
      }
    }

  }
}
