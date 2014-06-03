package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.BudgetPercentageManager;
import org.cgiar.ccafs.ap.data.manager.ContactPersonManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.MilestoneManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.BudgetPercentage;
import org.cgiar.ccafs.ap.data.model.Milestone;
import org.cgiar.ccafs.ap.data.model.Submission;
import org.cgiar.ccafs.ap.validation.planning.MainInformationValidation;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainInformationPlanningAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(MainInformationPlanningAction.class);
  private static final long serialVersionUID = -20327742862730172L;

  // Managers
  ActivityManager activityManager;
  BudgetPercentageManager budgetPercentageManager;
  BudgetManager budgetManager;
  ContactPersonManager contactPersonManager;
  MilestoneManager milestoneManager;
  SubmissionManager submissionManager;

  // Validator
  MainInformationValidation validator;

  // Model
  private int activityID;
  private StringBuilder validationMessage;
  private List<BudgetPercentage> budgetPercentages;
  private Activity activity;
  private Milestone[] milestones;
  private Map<String, String> genderOptions;
  private String genderIntegrationOption;
  private boolean canSubmit;


  @Inject
  public MainInformationPlanningAction(APConfig config, LogframeManager logframeManager,
    ActivityManager activityManager, BudgetPercentageManager budgetPercentageManager,
    ContactPersonManager contactPersonManager, BudgetManager budgetManager, MilestoneManager milestoneManager,
    SubmissionManager submissionManager, MainInformationValidation validator) {
    super(config, logframeManager);
    this.activityManager = activityManager;
    this.budgetPercentageManager = budgetPercentageManager;
    this.contactPersonManager = contactPersonManager;
    this.budgetManager = budgetManager;
    this.milestoneManager = milestoneManager;
    this.submissionManager = submissionManager;
    this.validator = validator;

    this.genderOptions = new LinkedHashMap<>();
    genderOptions.put("1", getText("form.options.yes"));
    genderOptions.put("0", getText("form.options.no"));
  }

  public Activity getActivity() {
    return activity;
  }

  public String getActivityRequestParameter() {
    return APConstants.ACTIVITY_REQUEST_ID;
  }

  public List<BudgetPercentage> getBudgetPercentages() {
    return budgetPercentages;
  }

  public int getCurrentYear() {
    return config.getPlanningCurrentYear();
  }

  public int getEndYear() {
    return config.getEndYear();
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

  public Milestone[] getMilestones() {
    return milestones;
  }

  public String getPublicActivtyRequestParameter() {
    return APConstants.PUBLIC_ACTIVITY_ID;
  }

  public int getStartYear() {
    return config.getStartYear();
  }

  public boolean isCanSubmit() {
    return canSubmit;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    validationMessage = new StringBuilder();

    String activityStringID = StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID));
    try {
      activityID = Integer.parseInt(activityStringID);
    } catch (NumberFormatException e) {
      LOG.error("-- prepare() > There was an error parsing the activity identifier '{}'.", activityStringID, e);
    }

    LOG.info("-- prepare() > User {} load the main information for activity {} in planing section", getCurrentUser()
      .getEmail(), activityID);

    budgetPercentages = budgetPercentageManager.getBudgetPercentageList();
    milestones = milestoneManager.getMilestoneList(getCurrentPlanningLogframe());
    // Get the basic information about the activity
    activity = activityManager.getActivityStatusInfo(activityID);

    // Set contact persons
    activity.setContactPersons(contactPersonManager.getContactPersons(activityID));
    // Set the budget
    activity.setBudget(budgetManager.getBudget(activityID));

    // If the workplan was submitted before the user can't save new information
    Submission submission =
      submissionManager.getSubmission(getCurrentUser().getLeader(), getCurrentPlanningLogframe(),
        APConstants.PLANNING_SECTION);
    canSubmit = (submission == null) ? true : false;
  }

  @Override
  public String save() {
    boolean success = true;

    if (activityManager.updateMainInformation(activity)) {

      if (!budgetManager.saveBudget(activity.getBudget(), activity.getId())) {
        LOG.warn("-- save() > There was a problem saving the budget for activity {}", activity.getId());
        success = false;
      }

      if (!contactPersonManager.saveContactPersons(activity.getContactPersons(), activityID)) {
        LOG.warn("-- save() > There was a problem saving the contact persons for activity {}", activity.getId());
        success = false;
      }

      // As there were changes in the activity we should mark the validation as false
      activity.setValidated(false);
      activityManager.validateActivity(activity);

    } else {
      success = false;
    }


    setDataSaved(success);
    return SAVED_STATUS;
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public String validateForm() {
    String messages = validator.validateForm(activity);

    if (messages.isEmpty()) {
      addActionMessage(getText("validation.success"));
    } else {
      String validationResult = getText("validation.fail") + messages;
      addActionWarning(validationResult);
    }
    return SUCCESS;
  }
}