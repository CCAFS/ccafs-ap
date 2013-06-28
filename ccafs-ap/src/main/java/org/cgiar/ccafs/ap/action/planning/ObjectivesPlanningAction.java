package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.ActivityObjectiveManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.Activity;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ObjectivesPlanningAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ObjectivesPlanningAction.class);
  private static final long serialVersionUID = -8764463530270764535L;

  // Managers
  ActivityManager activityManager;
  ActivityObjectiveManager activityObjectiveManager;

  // Model
  private int activityID;
  private Activity activity;


  @Inject
  public ObjectivesPlanningAction(APConfig config, LogframeManager logframeManager, ActivityManager activityManager,
    ActivityObjectiveManager activityObjectiveManager) {
    super(config, logframeManager);
    this.activityManager = activityManager;
    this.activityObjectiveManager = activityObjectiveManager;
  }

  public Activity getActivity() {
    return activity;
  }

  public String getActivityRequestParameter() {
    return APConstants.ACTIVITY_REQUEST_ID;
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

    LOG.info("-- prepare() > User {} load the objectives for activity {} in planing section", getCurrentUser()
      .getEmail(), activityID);

    // Get the basic information about the activity
    activity = activityManager.getSimpleActivity(activityID);

    // Get the activity objectives
    activity.setObjectives(activityObjectiveManager.getActivityObjectives(activityID));

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
      addActionMessage(getText("saving.success", new String[] {getText("planning.objectives")}));
      LOG.info("-- save() > User {} save successfully the objectives for activity {}",
        this.getCurrentUser().getEmail(), activityID);
      if (save) {
        return SUCCESS;
      } else {
        return SAVE_NEXT;
      }
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
    boolean problem = false;

    if (save) {
      if (activity.getObjectives().size() == 0) {
        problem = true;
        addActionError(getText("saving.fields.atLeastOne", new String[] {getText("planning.objectives.objective")
          .toLowerCase()}));
      } else {
        for (int c = 0; c < activity.getObjectives().size(); c++) {
          if (activity.getObjectives().get(c).getDescription().isEmpty()) {
            problem = true;
            addFieldError("activity.objectives[" + c + "].description", getText("validation.field.required"));
          }
        }
      }

      if (problem) {
        LOG.info(
          "-- validate() > User {} try to save the the objectives for activity {} but don't fill all required fields.",
          this.getCurrentUser().getEmail(), activityID);
        addActionError(getText("saving.fields.required"));
      }
    }
  }
}
