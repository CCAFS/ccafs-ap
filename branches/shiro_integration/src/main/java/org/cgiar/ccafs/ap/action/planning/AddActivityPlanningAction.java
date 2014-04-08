package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddActivityPlanningAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(AddActivityPlanningAction.class);
  private static final long serialVersionUID = 9154840925469694494L;

  // Managers
  private ActivityManager activityManager;
  private LeaderManager leaderManager;
  private MilestoneManager milestoneManager;

  // Model
  private Activity activity;
  private Map<Integer, String> continuousActivityList;
  private Milestone[] milestones;
  private List<Leader> leaders;
  private int year;

  @Inject
  public AddActivityPlanningAction(APConfig config, LogframeManager logframeManager, SecurityManager securityManager,
    ActivityManager activityManager, LeaderManager leaderManager, MilestoneManager milestoneManager) {
    super(config, logframeManager, securityManager);
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

  public int getEndYear() {
    return config.getEndYear();
  }

  public List<Leader> getLeaders() {
    return leaders;
  }

  public Milestone[] getMilestones() {
    return milestones;
  }

  public int getStartYear() {
    return config.getStartYear();
  }

  public int getYear() {
    return year;
  }

  @Override
  public void prepare() throws Exception {
    LOG.info("User {} load the Add activity section", this.getCurrentUser().getEmail());

    milestones = milestoneManager.getMilestoneList(this.getCurrentPlanningLogframe());

    leaders = new ArrayList<>();
    // Fake leader
    Leader leader = new Leader(-1, getText("planning.addActivity.selectLeader"), "");
    leaders.add(leader);
    leaders.addAll(Arrays.asList(leaderManager.getAllLeaders()));

    String yearString;
    yearString = StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_YEAR_REQUEST));

    try {
      if (yearString == null) {
        // Check if the activity already has a year defined
        if (activity != null && activity.getYear() != 0) {
          year = activity.getYear();
        } else {
          year = getCurrentPlanningLogframe().getYear();
        }
      } else {
        year = (yearString != null) ? Integer.parseInt(yearString) : getCurrentPlanningLogframe().getYear();
      }
    } catch (NumberFormatException e) {
      LOG.error("-- prepare() > There was an error parsing the year to create a new activity: '{}'.", yearString, e);
      year = getCurrentPlanningLogframe().getYear();
    }

    Activity[] oldActivities;
    if (getCurrentUser().isAdmin()) {
      oldActivities = activityManager.getActivitiesToContinue(year - 1);
    } else {
      oldActivities = activityManager.getActivitiesToContinue(year - 1, getCurrentUser().getLeader().getId());
    }

    if (oldActivities != null) {
      continuousActivityList.put(-1, getText("planning.addActivity.selectActivity"));
      StringBuilder text;
      for (Activity oldActivitie : oldActivities) {
        text = new StringBuilder();
        text.append("[");
        text.append(oldActivitie.getActivityId());
        text.append("] ");

        if (oldActivitie.getTitle().length() > 40) {
          text.append(oldActivitie.getTitle().substring(0, 40));
          text.append("...");
        } else {
          text.append(oldActivitie.getTitle());
        }

        continuousActivityList.put(oldActivitie.getId(), text.toString());
      }
    } else {
      // If the user doesn't have activities from previous years show a message
      continuousActivityList.put(-1, getText("planning.addActivity.noActivityFromPreviousYear"));
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
      // hasPartners
      activity.setHasPartners(oldActivity.isHasPartners());
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
      LOG.info("A new activity was saved successfully");
      addActionMessage(getText("saving.success", new String[] {getText("planning.addActivity.newActivity")}));
      return SUCCESS;
    } else {
      LOG.warn("There was a problem saving the new activity into the database");
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
      if (activity.getTitle().isEmpty()) {
        addFieldError("activity.title", getText("validation.field.required"));
        problem = true;
      }
      if (activity.getLeader() == null) {
        addFieldError("activity.leader", getText("validation.field.required"));
        problem = true;
      }

      if (problem) {
        addActionError(getText("Please fill the required fields or correct the indicated values."));
      }
    }
  }
}
