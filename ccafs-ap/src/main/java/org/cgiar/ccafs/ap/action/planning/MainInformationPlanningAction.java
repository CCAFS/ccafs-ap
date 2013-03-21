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

  @Override
  public void prepare() throws Exception {
    super.prepare();
    LOG.info("User {} load the activity main information planning for leader {} in planing section", getCurrentUser()
      .getEmail(), getCurrentUser().getLeader().getId());

    String activityStringID = StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID));
    try {
      activityID = Integer.parseInt(activityStringID);
    } catch (NumberFormatException e) {
      LOG.error("There was an error parsing the activity identifier '{}'.", activityStringID, e);
    }

    budgetPercentages = budgetPercentageManager.getBudgetPercentageList();
    milestones = milestoneManager.getMilestoneList(getCurrentPlanningLogframe().getId() + "");
    // Get the basic information about the activity
    activity = activityManager.getActivityStatusInfo(activityID);
    // Set contact persons
    activity.setContactPersons(contactPersonManager.getContactPersons(activityID));
    // Set the budget
    activity.setBudget(budgetManager.getBudget(activityID));
  }

  @Override
  public String save() {
    System.out.println(activity.getMilestone());
    System.out.println("------ SAVE -------");
    return super.save();
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }
}