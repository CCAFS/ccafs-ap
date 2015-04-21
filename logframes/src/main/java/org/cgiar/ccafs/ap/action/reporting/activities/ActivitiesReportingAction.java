package org.cgiar.ccafs.ap.action.reporting.activities;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.PublicationManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.ActivityPartner;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.Publication;
import org.cgiar.ccafs.ap.data.model.Submission;
import org.cgiar.ccafs.ap.validation.reporting.activities.deliverables.DeliverableInformationReportingActionValidation;

import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ActivitiesReportingAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ActivitiesReportingAction.class);
  private static final long serialVersionUID = 9001775749549472317L;

  // Managers
  protected ActivityManager activityManager;
  private SubmissionManager submissionManager;
  private DeliverableInformationReportingActionValidation validator;

  // Model
  private Activity[] currentActivities;
  private String[] activityStatuses;
  private boolean canSubmit;
  private PublicationManager publicationManager;

  @Inject
  public ActivitiesReportingAction(APConfig config, LogframeManager logframeManager, ActivityManager activityManager,
    SubmissionManager submissionManager, DeliverableInformationReportingActionValidation validator,
    PublicationManager publicationManager) {
    super(config, logframeManager);
    this.activityManager = activityManager;
    this.submissionManager = submissionManager;
    this.validator = validator;
    this.publicationManager = publicationManager;
  }

  /**
   * This method validates if a specific activity is completely filled or not.
   * It validates at first step if the activity status and the description has some content; at second step if the
   * activity has at least one deliverable without be reported; and finally it validates if the activity has at least
   * one partner associated without all the contact information filled.
   */
  private String calculateStatus(Activity activity) {
    String problemDescription = "";

    if (activity.getStatus() != null && activity.getStatus().getName().equals("Cancelled")) {
      return null;
    }

    /* Activity Status */
    boolean problem = false;
    if (activity.getStatusDescription() == null || activity.getStatusDescription().isEmpty()) {
      problemDescription += getText("reporting.activityList.missingStatus");
    }

    /* Deliverables */
    List<Deliverable> deliverables = activity.getDeliverables();
    if (activity.getDeliverables() != null) {
      Deliverable deliverable;
      for (int c = 0; c < deliverables.size(); c++) {
        deliverable = deliverables.get(c);
        Publication publication =
          (deliverable.isPublication()) ? publicationManager.getPublicationByDeliverableID(deliverable.getId())
            : new Publication();
        validator.validate(deliverable, publication);
        if (!validator.isValid()) {
          problemDescription += "<br /> - The information of the deliverable #";
          problemDescription += (c + 1);
          problemDescription += " ( ";
          problemDescription += validator.getValidationMessage();
          problemDescription += " ); ";
          problem = true;
        }
      }
    }

    /* Partners */
    problem = false;
    List<ActivityPartner> activityPartners = activity.getActivityPartners();
    if (activityPartners != null) {
      ActivityPartner activityPartner;
      for (int c = 0; c < activityPartners.size(); c++) {
        activityPartner = activityPartners.get(c);
        if (activityPartner.getContactName() == null || activityPartner.getContactName().isEmpty()
          || activityPartner.getContactEmail() == null || activityPartner.getContactEmail().isEmpty()) {
          problem = true;
          problemDescription += "<br />-The contact information for partner #" + (c + 1) + " is incomplete";
        }
      }
    }
    return problemDescription.isEmpty() ? null : problemDescription;
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

  public boolean isCanSubmit() {
    return canSubmit;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    currentActivities = activityManager.getActivities(config.getReportingCurrentYear(), this.getCurrentUser());
    LOG.info("User {} charge the activity list for the year {}", this.getCurrentUser().getEmail(),
      String.valueOf(config.getReportingCurrentYear()));
    activityStatuses = new String[currentActivities.length];
    // Calculate the status of each activity.
    for (int c = 0; c < currentActivities.length; c++) {
      activityStatuses[c] = calculateStatus(currentActivities[c]);
    }

    /* --------- Checking if the user can submit ------------- */
    Submission submission =
      submissionManager.getSubmission(getCurrentUser().getLeader(), getCurrentReportingLogframe(),
        APConstants.REPORTING_SECTION);
    canSubmit = (submission == null) ? true : false;
  }

  public void setCurrentActivities(Activity[] currentActivities) {
    this.currentActivities = currentActivities;
  }

}