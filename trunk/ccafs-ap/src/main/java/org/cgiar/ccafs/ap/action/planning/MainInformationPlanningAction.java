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
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.BudgetPercentage;
import org.cgiar.ccafs.ap.data.model.Milestone;
import org.cgiar.ccafs.ap.util.EmailValidator;

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

  // Model
  private int activityID;
  private List<BudgetPercentage> budgetPercentages;
  private Activity activity;
  private Milestone[] milestones;
  private Map<String, String> genderOptions;
  private String genderIntegrationOption;

  @Inject
  public MainInformationPlanningAction(APConfig config, LogframeManager logframeManager,
    ActivityManager activityManager, BudgetPercentageManager budgetPercentageManager,
    ContactPersonManager contactPersonManager, BudgetManager budgetManager, MilestoneManager milestoneManager) {
    super(config, logframeManager);
    this.activityManager = activityManager;
    this.budgetPercentageManager = budgetPercentageManager;
    this.contactPersonManager = contactPersonManager;
    this.budgetManager = budgetManager;
    this.milestoneManager = milestoneManager;

    this.genderOptions = new LinkedHashMap<>();
    genderOptions.put("1", "Yes");
    genderOptions.put("0", "No");
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

  @Override
  public void prepare() throws Exception {
    super.prepare();

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

    // Remove all contact persons in case user clicked on submit button
    if (activity.getContactPersons() != null) {
      if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
        activity.getContactPersons().clear();
        LOG.debug("-- prepare() > All the case studies related to the leader {} was deleted from model",
          getCurrentUser().getLeader().getId());
      }
    }
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

    } else {
      success = false;
    }

    if (success) {
      addActionMessage(getText("saving.success", new String[] {getText("planning.mainInformation")}));
      LOG.info("-- save() > The user {} saved the main information of the activity {} successfully.", getCurrentUser()
        .getEmail(), activityID);
      if (save) {
        return SUCCESS;
      } else {
        return SAVE_NEXT;
      }
    } else {
      LOG.warn("-- save() > The user {} had problems to save the main information of the activity {}.",
        getCurrentUser().getEmail(), activityID);
      addActionError(getText("saving.problem"));
      return INPUT;
    }

  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  @Override
  public void validate() {
    boolean problem = false;

    if (save) {
      // Validate if the contact person is filled
      if (activity.getContactPersons() == null || activity.getContactPersons().isEmpty()) {
        problem = true;
        addFieldError("activity.contactPersons[0].name", getText("validation.field.required"));
      } else {
        for (int c = 0; c < activity.getContactPersons().size(); c++) {
          // Check if at least there is a contact name
          if (activity.getContactPersons().get(c).getName().isEmpty()) {
            problem = true;
            addFieldError("activity.contactPersons[" + c + "].name", getText("validation.field.required"));
          }

          // If there is a contact email, check if it is valid
          if (!activity.getContactPersons().get(c).getEmail().isEmpty()) {
            if (!EmailValidator.isValidEmail(activity.getContactPersons().get(c).getEmail())) {
              problem = true;
              addFieldError("activity.contactPersons[" + c + "].email",
                getText("validation.invalid", new String[] {getText("planning.mainInformation.contactEmail")}));
            }
          }
        }
      }
    }

    if (problem) {
      LOG.info(
        "-- validate() > User {} try to save the main information for activity {} but don't fill all required fields.",
        this.getCurrentUser().getEmail(), activityID);
      addActionError(getText("saving.fields.required"));
    }
  }
}