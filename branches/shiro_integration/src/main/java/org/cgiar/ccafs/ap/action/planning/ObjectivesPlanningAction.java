package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.ActivityObjectiveManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Submission;
import org.cgiar.ccafs.ap.util.Capitalize;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectivesPlanningAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ObjectivesPlanningAction.class);
  private static final long serialVersionUID = -8764463530270764535L;

  // Managers
  private ActivityManager activityManager;
  private ActivityObjectiveManager activityObjectiveManager;
  private SubmissionManager submissionManager;

  // Model
  private int activityID;
  private Activity activity;
  private StringBuilder validationMessage;
  private boolean canSubmit;

  @Inject
  public ObjectivesPlanningAction(APConfig config, LogframeManager logframeManager, SecurityManager securityManager,
    ActivityManager activityManager, ActivityObjectiveManager activityObjectiveManager,
    SubmissionManager submissionManager) {
    super(config, logframeManager, securityManager);
    this.activityManager = activityManager;
    this.activityObjectiveManager = activityObjectiveManager;
    this.submissionManager = submissionManager;
  }

  public Activity getActivity() {
    return activity;
  }

  public String getActivityRequestParameter() {
    return APConstants.ACTIVITY_REQUEST_ID;
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

    LOG.info("-- prepare() > User {} load the objectives for activity {} in planing section", getCurrentUser()
      .getEmail(), activityID);

    // Get the basic information about the activity
    activity = activityManager.getSimpleActivity(activityID);

    // Get the activity objectives
    activity.setObjectives(activityObjectiveManager.getActivityObjectives(activityID));

    // If the workplan was submitted before the user can't save new information
    Submission submission =
      submissionManager.getSubmission(getCurrentUser().getLeader(), getCurrentPlanningLogframe(),
        APConstants.PLANNING_SECTION);
    canSubmit = (submission == null) ? true : false;

    if (getRequest().getMethod().equalsIgnoreCase("post")) {
      activity.getObjectives().clear();
    }
  }

  @Override
  public String save() {
    boolean saved = false;
    // First delete all the objectives from the DAO
    activityObjectiveManager.deleteActivityObjectives(activityID);

    // Save the new objectives
    saved = activityObjectiveManager.saveActivityObjectives(activity.getObjectives(), activityID);
    if (saved) {
      if (validationMessage.toString().isEmpty()) {
        addActionMessage(getText("saving.success", new String[] {getText("planning.objectives")}));
      } else {
        String finalMessage = getText("saving.success", new String[] {getText("planning.objectives")});
        finalMessage += getText("saving.keepInMind", new String[] {validationMessage.toString()});
        addActionWarning(Capitalize.capitalizeString(finalMessage));
      }

      // As there were changes in the activity we should mark the validation as false
      activity.setValidated(false);
      activityManager.validateActivity(activity);

      LOG.info("-- save() > User {} save successfully the objectives for activity {}",
        this.getCurrentUser().getEmail(), activityID);
      return SUCCESS;
    } else {
      LOG.info("-- save() > User {} had problems to save the objectives for activity {}", this.getCurrentUser()
        .getEmail(), activityID);
      addActionError(getText("saving.problem"));
      return INPUT;
    }
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  @Override
  public void validate() {
    if (save) {
      // Remove the empty objectives
      for (int c = 0; c < activity.getObjectives().size(); c++) {
        if (activity.getObjectives().get(c).getDescription().isEmpty()) {
          activity.getObjectives().remove(c);
          c--;
        }
      }

      // Activity must have at least one objective
      if (activity.getObjectives().isEmpty()) {
        validationMessage.append(getText("planning.objectives.validation.atLeastOne"));
      }
    }
  }

}
