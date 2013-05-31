package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.LeaderManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.MilestoneManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.ActivityObjective;
import org.cgiar.ccafs.ap.data.model.ActivityPartner;
import org.cgiar.ccafs.ap.data.model.ContactPerson;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Milestone;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.inject.Inject;


public class AddActivityPlanningAction extends BaseAction {

  private static final long serialVersionUID = 9154840925469694494L;

  // Managers
  private ActivityManager activityManager;
  private LeaderManager leaderManager;
  private MilestoneManager milestoneManager;

  // Model
  private Activity activity;
  private Map<Integer, String> continuousActivityList;
  private Milestone[] milestones;
  private Leader[] leaders;

  @Inject
  public AddActivityPlanningAction(APConfig config, LogframeManager logframeManager, ActivityManager activityManager,
    LeaderManager leaderManager, MilestoneManager milestoneManager) {
    super(config, logframeManager);
    this.activityManager = activityManager;
    this.leaderManager = leaderManager;
    this.milestoneManager = milestoneManager;
    this.continuousActivityList = new TreeMap<>();
  }

  public Activity getActivity() {
    return activity;
  }

  public Map<Integer, String> getContinuousActivityList() {
    return continuousActivityList;
  }

  public Leader[] getLeaders() {
    return leaders;
  }

  public Milestone[] getMilestones() {
    return milestones;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    milestones = milestoneManager.getMilestoneList(this.getCurrentPlanningLogframe());
    leaders = leaderManager.getAllLeaders();
    Activity[] oldActivities = activityManager.getActivitiesTitle(this.getCurrentPlanningLogframe().getYear() - 1);
    String text;
    continuousActivityList.put(-1, "Select an activity.");
    for (Activity oldActivitie : oldActivities) {
      text =
        oldActivitie.getId()
          + " - "
          + (oldActivitie.getTitle().length() > 40 ? oldActivitie.getTitle().substring(0, 40) : oldActivitie.getTitle())
          + "...";
      continuousActivityList.put(oldActivitie.getId(), text);
    }
  }

  @Override
  public String save() {
    // Validate if the activity is a continuation of another previous activity.
    // If so, the main information will be copied to the current activity (dates, descriptions, deliverables, partners,
    // etc..).
    if (activity.getContinuousActivity() != null) {
      Activity oldActivity = activity.getContinuousActivity();
      // description
      activity.setDescription(oldActivity.getDescription());
      // dates
      activity.setStartDate(oldActivity.getStartDate());
      activity.setEndDate(oldActivity.getEndDate());
      // Contact Persons
      activity.setContactPersons(oldActivity.getContactPersons());
      // Resetting contact person ids so they can be considered as new records.
      if (activity.getContactPersons() != null) {
        for (ContactPerson cp : activity.getContactPersons()) {
          cp.setId(-1);
        }
      }
      // Objectives
      activity.setObjectives(oldActivity.getObjectives());
      // Resetting objective ids so they can be considered as new records.
      if (activity.getObjectives() != null) {
        for (ActivityObjective obj : activity.getObjectives()) {
          obj.setId(-1);
        }
      }
      // Partners
      activity.setActivityPartners(oldActivity.getActivityPartners());
      // Resetting activity partner uds so they can be considered as new records.
      for (ActivityPartner ap : activity.getActivityPartners()) {
        ap.setId(-1);
      }
      // Deliverables
      List<Deliverable> newDeliverables = new ArrayList<>();
      for (Deliverable deliverable : oldActivity.getDeliverables()) {
        // Only keep deliverables that end in the the current year or in the future.
        if (deliverable.getYear() >= this.getCurrentPlanningLogframe().getYear()) {
          deliverable.setId(-1);
          newDeliverables.add(deliverable);
        }
      }
      activity.setDeliverables(newDeliverables);
    }

    boolean saved = activityManager.saveActivity(activity);
    if (saved) {
      // TODO success message
      return SUCCESS;
    } else {
      // TODO error message
    }
    return super.save();
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

}
