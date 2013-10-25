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
import org.cgiar.ccafs.ap.util.Capitalize;
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
  SubmissionManager submissionManager;

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
    SubmissionManager submissionManager) {
    super(config, logframeManager);
    this.activityManager = activityManager;
    this.budgetPercentageManager = budgetPercentageManager;
    this.contactPersonManager = contactPersonManager;
    this.budgetManager = budgetManager;
    this.milestoneManager = milestoneManager;
    this.submissionManager = submissionManager;

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
  public String next() {
    save();
    return super.next();
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
    String finalMessage;

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

    if (success) {

      // Check if there is a validation message.
      if (validationMessage.toString().isEmpty()) {
        addActionMessage(getText("saving.success", new String[] {getText("planning.mainInformation")}));
        LOG.info("-- save() > The user {} saved the main information of the activity {} successfully.",
          getCurrentUser().getEmail(), activityID);
      } else {
        // If there were validation messages show them in a warning message.
        finalMessage = getText("saving.success", new String[] {getText("planning.mainInformation")});
        finalMessage += getText("saving.missingFields", new String[] {validationMessage.toString()});

        addActionWarning(Capitalize.capitalizeString(finalMessage));
        LOG.info("-- save() > The user {} saved the main information of the activity {} with empty fields.",
          getCurrentUser().getEmail(), activityID);
      }

      return SUCCESS;
    } else {
      LOG.warn("-- save() > The user {} had problems to save the main information of the activity {}.",
        getCurrentUser().getEmail(), activityID);
      finalMessage = getText("saving.problem");
      addActionError(finalMessage);
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

      if (activity.getTitle().isEmpty()) {
        validationMessage.append(getText("planning.mainInformation.title") + ", ");
        problem = true;
      }

      if (activity.getDescription().isEmpty()) {
        validationMessage.append(getText("planning.mainInformation.descripition") + ", ");
        problem = true;
      }

      // Validate if there is at least one contact person, if there is a contact person
      // without name nor email remove it from the list.
      if (activity.getContactPersons() != null) {
        if (!activity.getContactPersons().isEmpty()) {
          boolean invalidEmail = false;
          for (int c = 0; c < activity.getContactPersons().size(); c++) {
            // If there is a contact email, check if it is valid
            if (!activity.getContactPersons().get(c).getEmail().isEmpty()) {
              if (!EmailValidator.isValidEmail(activity.getContactPersons().get(c).getEmail())) {
                invalidEmail = true;
                problem = true;
              }
            } else {
              if (activity.getContactPersons().get(c).getName().isEmpty()) {
                activity.getContactPersons().remove(c);
                c--;
              }
            }
          }

          if (invalidEmail) {
            validationMessage.append(getText("planning.mainInformation.contactEmail") + ", ");
          }
        }
      }

      // The list could be empty after the last validation
      if (activity.getContactPersons().isEmpty()) {
        validationMessage.append(getText("planning.mainInformation.validation.contactPerson") + ", ");
        problem = true;
      }

      if (activity.getStartDate() == null) {
        validationMessage.append(getText("planning.mainInformation.startDate") + ", ");
        problem = true;
      }

      if (activity.getEndDate() == null) {
        validationMessage.append(getText("planning.mainInformation.endDate") + ", ");
        problem = true;
      }

      // Change the last colon by a period
      if (problem == true) {
        validationMessage.setCharAt(validationMessage.lastIndexOf(","), '.');
      }
    }

  }
}