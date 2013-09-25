package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityBenchmarkSiteManager;
import org.cgiar.ccafs.ap.data.manager.ActivityCountryManager;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.ActivityObjectiveManager;
import org.cgiar.ccafs.ap.data.manager.ActivityOtherSiteManager;
import org.cgiar.ccafs.ap.data.manager.ActivityPartnerManager;
import org.cgiar.ccafs.ap.data.manager.ActivityRegionManager;
import org.cgiar.ccafs.ap.data.manager.ContactPersonManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.LeaderManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Submission;
import org.cgiar.ccafs.ap.util.ActivityValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ActivitiesPlanningAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ActivitiesPlanningAction.class);
  private static final long serialVersionUID = 610099339078512575L;

  // Managers
  private ActivityManager activityManager;
  private LeaderManager leaderManager;
  private ContactPersonManager contactPersonManager;
  private DeliverableManager deliverableManager;
  private ActivityPartnerManager activityPartnerManager;
  private ActivityObjectiveManager activityObjectiveManager;
  private ActivityCountryManager activityCountryManager;
  private ActivityRegionManager activityRegionManager;
  private ActivityBenchmarkSiteManager activityBenchmarkSiteManager;
  private ActivityOtherSiteManager activityOtherSiteManager;
  private SubmissionManager submissionManager;

  // Model
  private Submission submission;
  private Activity activity;
  private List<Activity> ownActivities, othersActivities, pastActivities;
  private String[] activityStatuses;
  private boolean workplanSubmitted, canSubmit;
  private int activityIndex, activityID, activitiesFilled;

  @Inject
  public ActivitiesPlanningAction(APConfig config, LogframeManager logframeManager, ActivityManager activityManager,
    LeaderManager leaderManager, ContactPersonManager contactPersonManager, DeliverableManager deliverableManager,
    ActivityPartnerManager activityPartnerManager, ActivityObjectiveManager activityObjectiveManager,
    ActivityCountryManager activityCountryManager, ActivityRegionManager activityRegionManager,
    ActivityBenchmarkSiteManager activityBenchmarkSiteManager, ActivityOtherSiteManager activityOtherSiteManager,
    SubmissionManager submissionManager) {
    super(config, logframeManager);
    this.activityManager = activityManager;
    this.leaderManager = leaderManager;
    this.contactPersonManager = contactPersonManager;
    this.deliverableManager = deliverableManager;
    this.activityPartnerManager = activityPartnerManager;
    this.activityObjectiveManager = activityObjectiveManager;
    this.activityCountryManager = activityCountryManager;
    this.activityRegionManager = activityRegionManager;
    this.activityBenchmarkSiteManager = activityBenchmarkSiteManager;
    this.activityOtherSiteManager = activityOtherSiteManager;
    this.submissionManager = submissionManager;
  }

  public int getActivityID() {
    return activityID;
  }

  public String getActivityRequestParameter() {
    return APConstants.ACTIVITY_REQUEST_ID;
  }

  public String[] getActivityStatuses() {
    return activityStatuses;
  }

  public List<Activity> getOthersActivities() {
    return othersActivities;
  }

  public List<Activity> getOwnActivities() {
    return ownActivities;
  }

  public List<Activity> getPastActivities() {
    return pastActivities;
  }

  public String getPublicActivityRequestParameter() {
    return APConstants.PUBLIC_ACTIVITY_ID;
  }

  public boolean isCanSubmit() {
    return canSubmit;
  }


  /**
   * This method checks if the activity to submit is valid, checking
   * its owner, if it is from current year and if the activity isn't
   * submitted yet.
   */
  private boolean isValidActivity() {
    boolean isValidActivity = false;

    // Check if the activity is valid
    if (activityManager.isValidId(activityID)) {
      // Check if the activity is from current year
      if (activityManager.isActiveActivity(activityID, config.getPlanningCurrentYear())) {
        // Check if the current user owns the activity to submit
        if (leaderManager.getActivityLeader(activityID).getId() == getCurrentUser().getLeader().getId()) {
          // Check if the activity hasn't been submitted yet
          if (!activityManager.isValidatedActivity(activityID)) {
            isValidActivity = true;
          }
        }
      }
    }
    return isValidActivity;
  }

  public boolean isWorkplanSubmitted() {
    return workplanSubmitted;
  }

  /**
   * This method is called only after press the validate button
   * if the activity is valid.
   */
  private void loadActivityInformation() {
    // Load the activity data to validate if it is complete.
    activity = new Activity();

    // Get the basic information about the activity
    activity = activityManager.getActivityStatusInfo(activityID);
    // Set contact persons
    activity.setContactPersons(contactPersonManager.getContactPersons(activityID));
    // Set the deliverables
    activity.setDeliverables(deliverableManager.getDeliverables(activityID));
    // Set activity partners
    activity.setActivityPartners(activityPartnerManager.getActivityPartners(activityID));
    // Set the activity objectives
    activity.setObjectives(activityObjectiveManager.getActivityObjectives(activityID));
    // Set activity countries
    activity.setCountries(activityCountryManager.getActvitiyCountries(activityID));
    // Set activity regions
    activity.setRegions(activityRegionManager.getActvitiyRegions(activityID));
    // Set activity benchmark sites
    activity.setBsLocations(activityBenchmarkSiteManager.getActivityBenchmarkSites(activityID));
    // Set activity other sites
    activity.setOtherLocations(activityOtherSiteManager.getActivityOtherSites(activityID));
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    LOG.info("User {} load the list of activities for leader {} in planing section", getCurrentUser().getEmail(),
      getCurrentUser().getLeader().getId());

    /* --------- Getting all the activities ------------- */

    // For TL and RPL this list will contain the own activities and the activities related to their programmes.
    Activity[] currentActivities =
      activityManager.getPlanningActivityList(config.getPlanningCurrentYear(), this.getCurrentUser());

    ownActivities = new ArrayList<>();
    othersActivities = new ArrayList<>();
    activitiesFilled = 0;
    for (Activity activity : currentActivities) {
      if (activity.getLeader().getId() == getCurrentUser().getLeader().getId()) {
        // Check if the activity is filled
        activitiesFilled += (activity.isValidated()) ? 1 : 0;
        ownActivities.add(activity);
      } else {
        othersActivities.add(activity);
      }
    }

    // This array contains activities from previous years.
    pastActivities = new ArrayList<>();
    for (int year = config.getStartYear(); year <= config.getPlanningCurrentYear() - 1; year++) {
      pastActivities.addAll(Arrays.asList(activityManager.getActivityListByYear(year)));
    }

    /* --------- Checking if the user can submit ------------- */
    // First, check if the workplan is already submitted.
    submission =
      submissionManager.getSubmission(getCurrentUser().getLeader(), getCurrentPlanningLogframe(),
        APConstants.PLANNING_SECTION);

    if (submission == null) {
      workplanSubmitted = false;

      // If the workplan wasn't submitted yet, the user can do it if
      // all activities are completely filled.
      canSubmit = (ownActivities.size() == activitiesFilled);
    } else {
      workplanSubmitted = true;
      // If the user already did the submission thus can't do it again.
      canSubmit = false;
    }
  }

  @Override
  public String save() {
    boolean validated = false;

    // After validate that all information is complete, set the activity as submitted
    ownActivities.get(activityIndex).setValidated(true);
    validated = activityManager.validateActivity(ownActivities.get(activityIndex));

    if (validated) {
      activitiesFilled++;
      // After make the activity validation we must
      // check if now the user can submit
      if (submission == null) {
        canSubmit = (ownActivities.size() == activitiesFilled);
      }
      addActionMessage(getText("planning.activityList.validation.success", new String[] {String.valueOf(activityID)}));
    } else {
      addActionError(getText("saving.problem"));
    }

    return INPUT;
  }

  public void setActivityIndex(int activityIndex) {
    this.activityIndex = activityIndex;
  }

  public String submit() {
    boolean submitted;

    if (submission != null) {
      AddActionWarning("Workplan already submitted");
      return INPUT;
    }

    submission = new Submission();
    submission.setLeader(getCurrentUser().getLeader());
    submission.setLogframe(getCurrentPlanningLogframe());
    submission.setSection(APConstants.PLANNING_SECTION);

    submitted = submissionManager.submit(submission);
    if (submitted) {
      // Now the user can't submit again.
      workplanSubmitted = true;
      canSubmit = false;
      addActionMessage(getText("planning.activityList.submission.success"));
    } else {
      addActionError(getText("planning.activityList.submission.error"));
    }
    return INPUT;
  }

  @Override
  public void validate() {
    if (save) {
      String result;
      activityID = ownActivities.get(activityIndex).getId();
      // Check if the user can submit the activity
      if (!isValidActivity()) {
        addActionError(getText("planning.activityList.validation.noValidActivity",
          new String[] {String.valueOf(activityID)}));
        return;
      }

      // Load the information to validate
      loadActivityInformation();

      // Validate process
      ActivityValidator av = new ActivityValidator();
      result = av.validateActivityPlanning(activity);

      if (!result.isEmpty()) {
        addActionError(result);
      }
    }
  }
}