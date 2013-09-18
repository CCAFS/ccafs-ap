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
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.util.EmailValidator;

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

  // Model
  private StringBuilder validationMessages;
  private Activity activity;
  private Activity[] currentActivities;
  private String[] activityStatuses;
  private int activityIndex;
  private int activityID;

  @Inject
  public ActivitiesPlanningAction(APConfig config, LogframeManager logframeManager, ActivityManager activityManager,
    LeaderManager leaderManager, ContactPersonManager contactPersonManager, DeliverableManager deliverableManager,
    ActivityPartnerManager activityPartnerManager, ActivityObjectiveManager activityObjectiveManager,
    ActivityCountryManager activityCountryManager, ActivityRegionManager activityRegionManager,
    ActivityBenchmarkSiteManager activityBenchmarkSiteManager, ActivityOtherSiteManager activityOtherSiteManager) {
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

  public Activity[] getCurrentActivities() {
    return currentActivities;
  }

  /**
   * This method checks if the activity to submit is valid, checking
   * its owner, if it is from current year and if the activity isn't
   * submitted yet.
   */
  private boolean isValidActivity() {
    boolean problem = false;

    // Check if the activity is valid
    if (activityManager.isValidId(activityID)) {
      // Check if the activity is from current year
      if (activityManager.isActiveActivity(activityID, config.getPlanningCurrentYear())) {
        // Check if the current user owns the activity to submit
        if (leaderManager.getActivityLeader(activityID).getId() == getCurrentUser().getLeader().getId()) {
          // Check if the activity hasn't been submitted yet
          if (activityManager.isValidatedActivity(activityID)) {
            problem = true;
          }
        } else {
          problem = true;
        }
      } else {
        problem = true;
      }
    } else {
      problem = true;
    }

    return !problem;
  }

  /**
   * This method is called only after press the submit button
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
    validationMessages = new StringBuilder();
    LOG.info("User {} load the list of activities for leader {} in planing section", getCurrentUser().getEmail(),
      getCurrentUser().getLeader().getId());
    currentActivities = activityManager.getPlanningActivityList(config.getPlanningCurrentYear(), this.getCurrentUser());
  }


  @Override
  public String save() {
    boolean validated = false;

    // After validate that all information is complete, set the activity as submitted
    currentActivities[activityIndex].setValidated(true);
    validated = activityManager.validateActivity(currentActivities[activityIndex]);

    if (validated) {
      addActionMessage(getText("planning.activityList.validation.success", new String[] {String.valueOf(activityID)}));
    } else {
      addActionError(getText("saving.problem"));
    }

    return INPUT;
  }

  public void setActivityIndex(int activityIndex) {
    this.activityIndex = activityIndex;
  }

  public void setCurrentActivities(Activity[] currentActivities) {
    this.currentActivities = currentActivities;
  }

  @Override
  public void validate() {
    boolean problem = false;

    if (save) {

      // It is needed take the activity identifier before continue
      activityID = currentActivities[activityIndex].getId();

      // Check if the user can submit the activity
      if (!isValidActivity()) {
        addActionError(getText("planning.activityList.validation.noValidActivity",
          new String[] {String.valueOf(activityID)}));
        return;
      }

      // Load the information to validate
      loadActivityInformation();

      // Validate process
      // Check the exists an start date and end date
      if (activity.getStartDate() == null) {
        validationMessages.append(getText("planning.mainInformation.validation.startDate"));
        validationMessages.append(", ");
        problem = true;
      }

      if (activity.getEndDate() == null) {
        validationMessages.append(getText("planning.mainInformation.validation.endDate"));
        validationMessages.append(", ");
        problem = true;
      }

      // Check if the activity have at least one contact person
      if (activity.getContactPersons() == null || activity.getContactPersons().isEmpty()) {
        validationMessages.append(getText("planning.mainInformation.validation.contactPerson"));
        validationMessages.append(", ");
        problem = true;
      } else {
        for (int c = 0; c < activity.getContactPersons().size(); c++) {
          // Check if at least there is a contact name
          if (activity.getContactPersons().get(c).getName().isEmpty()) {
            validationMessages.append(getText("planning.mainInformation.validation.contactPerson.name"));
            validationMessages.append(", ");
            problem = true;
          }

          // If there is a contact email, check if it is valid
          if (!activity.getContactPersons().get(c).getEmail().isEmpty()) {
            if (!EmailValidator.isValidEmail(activity.getContactPersons().get(c).getEmail())) {
              validationMessages.append(getText("planning.mainInformation.validation.contactPerson.email"));
              validationMessages.append(", ");
              problem = true;
            }
          }
        }
      }

      // Validate objectives
      if (activity.getObjectives().isEmpty()) {
        validationMessages.append(getText("planning.objectives.validation.atLeastOne"));
        validationMessages.append(", ");
        problem = true;
      }

      // Validate locations
      // Activity should be global or have at least one location
      if (!activity.isGlobal() && activity.getCountries().isEmpty() && activity.getRegions().isEmpty()
        && activity.getOtherLocations().isEmpty()) {
        validationMessages.append(getText("planning.locations.validation.atLeastOneLocation"));
        validationMessages.append(", ");
      }

      validationMessages.setCharAt(validationMessages.lastIndexOf(","), '.');
      if (problem) {
        String message = getText("planning.activityList.validation.error", new String[] {String.valueOf(activityID)});
        message += validationMessages.toString();
        addActionError(message);
      }

    }
  }
}