package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ActivityDAO;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.ActivityPartnerManager;
import org.cgiar.ccafs.ap.data.manager.ContactPersonManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Milestone;
import org.cgiar.ccafs.ap.data.model.Objective;
import org.cgiar.ccafs.ap.data.model.Output;
import org.cgiar.ccafs.ap.data.model.Status;
import org.cgiar.ccafs.ap.data.model.Theme;
import org.cgiar.ccafs.ap.data.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivityManagerImpl implements ActivityManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ActivityManagerImpl.class);
  private ActivityDAO activityDAO;

  private DeliverableManager deliverableManager;
  private ActivityPartnerManager activityPartnerManager;
  private ContactPersonManager contactPersonManager;

  @Inject
  public ActivityManagerImpl(ActivityDAO activityDAO, DeliverableManager deliverableManager,
    ActivityPartnerManager activityPartnerManager, ContactPersonManager contactPersonManager) {
    this.activityDAO = activityDAO;
    this.deliverableManager = deliverableManager;
    this.activityPartnerManager = activityPartnerManager;
    this.contactPersonManager = contactPersonManager;
  }

  @Override
  public Activity[] getActivities(int year, User user) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
    List<Map<String, String>> activitiesDAO;

    // if leader is null the user must be an admin.
    if (user.getRole() == User.UserRole.Admin) {
      activitiesDAO = activityDAO.getActivities(year);
    } else {
      // get all activities added by the specified leader.
      activitiesDAO = activityDAO.getActivities(year, user.getLeader().getId());
    }
    Activity[] activities = new Activity[activitiesDAO.size()];
    for (int c = 0; c < activitiesDAO.size(); c++) {
      Activity activity = new Activity();
      /* --- MAIN INFORMATION --- */
      activity.setId(Integer.parseInt(activitiesDAO.get(c).get("id")));
      activity.setTitle(activitiesDAO.get(c).get("title"));
      activity.setDescription(activitiesDAO.get(c).get("description"));
      try {
        activity.setStartDate(dateFormat.parse(activitiesDAO.get(c).get("start_date")));
      } catch (ParseException e) {
        String msg =
          "There was an error parsing start date '" + activitiesDAO.get(c).get("start_date") + "' for the activity "
            + activity.getId() + ".";
        LOG.error(msg, e);
      }
      try {
        activity.setEndDate(dateFormat.parse(activitiesDAO.get(c).get("end_date")));
      } catch (ParseException e) {
        String msg =
          "There was an error parsing end date '" + activitiesDAO.get(c).get("end_date") + "' for the activity "
            + activity.getId() + ".";
        LOG.error(msg, e);
      }
      // Contact Persons
      activity.setContactPersons(contactPersonManager.getContactPersons(activity.getId()));
      Theme theme = new Theme(Integer.parseInt(activitiesDAO.get(c).get("theme_id")));
      theme.setCode(activitiesDAO.get(c).get("theme_code"));
      // Creating fake objects just to save correctly the Theme data of the current activity.
      Objective objective = new Objective(-1);
      objective.setTheme(theme);
      Output output = new Output(-1);
      output.setObjective(objective);
      // end fakeObjects
      Milestone milestone = new Milestone(Integer.parseInt(activitiesDAO.get(c).get("milestone_id")));
      milestone.setCode(activitiesDAO.get(c).get("milestone_code"));
      milestone.setOutput(output);
      activity.setMilestone(milestone);
      Leader activityLeader = new Leader();
      activityLeader.setId(Integer.parseInt(activitiesDAO.get(c).get("leader_id")));
      activityLeader.setAcronym(activitiesDAO.get(c).get("leader_acronym"));
      activityLeader.setName(activitiesDAO.get(c).get("leader_name"));
      activity.setLeader(activityLeader);

      /* --- ACTIVITY STATUS --- */
      Map<String, String> statusInfo = activityDAO.getActivityStatusInfo(activity.getId());
      Status status = new Status();
      status.setId(Integer.parseInt(statusInfo.get("status_id")));
      status.setName(statusInfo.get("status_name"));
      activity.setStatus(status);
      // Status Description
      activity.setStatusDescription(statusInfo.get("status_description"));
      // Gender Integration
      activity.setGenderIntegrationsDescription(statusInfo.get("gender_description"));

      /* --- DELIVERABLES --- */
      activity.setDeliverables(deliverableManager.getDeliverables(activity.getId()));

      /* --- PARTNERS --- */
      activity.setActivityPartners(activityPartnerManager.getActivityPartners(activity.getId()));


      activities[c] = activity;
    }
    return activities;
  }

  @Override
  public Activity[] getActivitiesForRSS(int year, int limit) {
    List<Map<String, String>> activitiesDB = activityDAO.getActivitiesForRSS(year, limit);
    if (activitiesDB.size() > 0) {
      Activity[] activities = new Activity[activitiesDB.size()];
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
      int c = 0;
      for (Map<String, String> activityDB : activitiesDB) {
        Activity activity = new Activity();
        activity.setId(Integer.parseInt(activityDB.get("id")));
        activity.setTitle(activityDB.get("title"));
        try {
          activity.setStartDate(dateFormat.parse(activityDB.get("start_date")));
        } catch (ParseException e) {
          String msg =
            "There was an error parsing start date '" + activityDB.get("start_date") + "' for the activity "
              + activity.getId() + ".";
          LOG.error(msg, e);
        }
        try {
          activity.setEndDate(dateFormat.parse(activityDB.get("end_date")));
        } catch (ParseException e) {
          String msg =
            "There was an error parsing end date '" + activityDB.get("end_date") + "' for the activity "
              + activity.getId() + ".";
          LOG.error(msg, e);
        }
        activity.setDescription(activityDB.get("description"));
        try {
          activity.setDateAdded(dateFormat.parse(activityDB.get("date_added")));
        } catch (ParseException e) {
          String msg =
            "There was an error parsing date_added '" + activityDB.get("date_added") + "' for the activity "
              + activity.getId() + ".";
          LOG.error(msg, e);
        }
        activities[c] = activity;
        c++;
      }
      return activities;
    }
    return null;
  }

  @Override
  public Activity getActivity(int id) {
    Map<String, String> activityData = activityDAO.getActivityStatusInfo(id);

    Activity activity = new Activity();
    activity.setId(id);
    // Leader
    Leader leader = new Leader();
    leader.setId(Integer.parseInt(activityData.get("leader_id")));
    leader.setAcronym(activityData.get("leader_acronym"));
    leader.setName(activityData.get("leader_name"));
    activity.setLeader(leader);
    // Main information
    activity.setTitle(activityData.get("title"));
    activity.setDescription(activityData.get("description"));
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
    try {
      activity.setStartDate(dateFormat.parse(activityData.get("start_date")));
    } catch (ParseException e) {
      String msg =
        "There was an error parsing the start date '" + activityData.get("start_date") + "' for the activity "
          + activity.getId() + ".";
      LOG.error(msg, e);
    }
    try {
      activity.setEndDate(dateFormat.parse(activityData.get("end_date")));
    } catch (ParseException e) {
      String msg =
        "There was an error parsing the end date '" + activityData.get("end_date") + "' for the activity "
          + activity.getId() + ".";
      LOG.error(msg, e);
    }
    // Contact Persons
    /*
     * activity.setContactPersons(contactPersonManager.getContactPersons(activity.getId()));
     * Theme theme = new Theme(Integer.parseInt(activitiesDAO.get(c).get("theme_id")));
     * theme.setCode(activitiesDAO.get(c).get("theme_code"));
     * // Creating fake objects just to save correctly the Theme data of the current activity.
     * Objective objective = new Objective(-1);
     * objective.setTheme(theme);
     * Output output = new Output(-1);
     * output.setObjective(objective);
     * // end fakeObjects
     * Milestone milestone = new Milestone(Integer.parseInt(activitiesDAO.get(c).get("milestone_id")));
     * milestone.setCode(activitiesDAO.get(c).get("milestone_code"));
     * milestone.setOutput(output);
     * activity.setMilestone(milestone);
     */

    /*
     * Activity activity = new Activity();
     * /* --- MAIN INFORMATION ---
     * activity.setId(Integer.parseInt(activitiesDAO.get(c).get("id")));
     * activity.setTitle(activitiesDAO.get(c).get("title"));
     * activity.setDescription(activitiesDAO.get(c).get("description"));
     * try {
     * activity.setStartDate(dateFormat.parse(activitiesDAO.get(c).get("start_date")));
     * } catch (ParseException e) {
     * String msg =
     * "There was an error parsing start date '" + activitiesDAO.get(c).get("start_date") + "' for the activity "
     * + activity.getId() + ".";
     * LOG.error(msg, e);
     * }
     * try {
     * activity.setEndDate(dateFormat.parse(activitiesDAO.get(c).get("end_date")));
     * } catch (ParseException e) {
     * String msg =
     * "There was an error parsing end date '" + activitiesDAO.get(c).get("end_date") + "' for the activity "
     * + activity.getId() + ".";
     * LOG.error(msg, e);
     * }
     * // Contact Persons
     * activity.setContactPersons(contactPersonManager.getContactPersons(activity.getId()));
     * Theme theme = new Theme(Integer.parseInt(activitiesDAO.get(c).get("theme_id")));
     * theme.setCode(activitiesDAO.get(c).get("theme_code"));
     * // Creating fake objects just to save correctly the Theme data of the current activity.
     * Objective objective = new Objective(-1);
     * objective.setTheme(theme);
     * Output output = new Output(-1);
     * output.setObjective(objective);
     * // end fakeObjects
     * Milestone milestone = new Milestone(Integer.parseInt(activitiesDAO.get(c).get("milestone_id")));
     * milestone.setCode(activitiesDAO.get(c).get("milestone_code"));
     * milestone.setOutput(output);
     * activity.setMilestone(milestone);
     * Leader activityLeader = new Leader();
     * activityLeader.setId(Integer.parseInt(activitiesDAO.get(c).get("leader_id")));
     * activityLeader.setAcronym(activitiesDAO.get(c).get("leader_acronym"));
     * activityLeader.setName(activitiesDAO.get(c).get("leader_name"));
     * activity.setLeader(activityLeader);
     * /* --- ACTIVITY STATUS ---
     * Map<String, String> statusInfo = activityDAO.getActivityStatusInfo(activity.getId());
     * Status status = new Status();
     * status.setId(Integer.parseInt(statusInfo.get("status_id")));
     * status.setName(statusInfo.get("status_name"));
     * activity.setStatus(status);
     * // Status Description
     * activity.setStatusDescription(statusInfo.get("status_description"));
     * // Gender Integration
     * activity.setGenderIntegrationsDescription(statusInfo.get("gender_description"));
     * /* --- DELIVERABLES ---
     * activity.setDeliverables(deliverableManager.getDeliverables(activity.getId()));
     * /* --- PARTNERS ---
     * activity.setActivityPartners(activityPartnerManager.getActivityPartners(activity.getId()));
     */
    return activity;
  }

  @Override
  public Activity getActivityStatusInfo(int id) {
    Map<String, String> activityDB = activityDAO.getActivityStatusInfo(id);
    if (activityDB != null) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
      Activity activity = new Activity();
      activity.setId(id);
      activity.setTitle(activityDB.get("title"));
      try {
        activity.setStartDate(dateFormat.parse(activityDB.get("start_date")));
      } catch (ParseException e) {
        String msg =
          "There was an error parsing start date '" + activityDB.get("start_date") + "' for the activity " + id + ".";
        LOG.error(msg, e);
      }
      try {
        activity.setEndDate(dateFormat.parse(activityDB.get("end_date")));
      } catch (ParseException e) {
        String msg =
          "There was an error parsing end date '" + activityDB.get("end_date") + "' for the activity " + id + ".";
        LOG.error(msg, e);
      }
      activity.setDescription(activityDB.get("description"));
      activity.setGlobal(activityDB.get("is_global").equals("1"));
      // Status
      Status status = new Status();
      status.setId(Integer.parseInt(activityDB.get("status_id")));
      status.setName(activityDB.get("status_name"));
      activity.setStatus(status);

      // Status Description
      activity.setStatusDescription(activityDB.get("status_description"));

      // Milestone
      Milestone milestone = new Milestone();
      milestone.setId(Integer.parseInt(activityDB.get("milestone_id")));
      milestone.setCode(activityDB.get("milestone_code"));
      activity.setMilestone(milestone);

      // Activity leader
      Leader activityLeader = new Leader();
      activityLeader.setId(Integer.parseInt(activityDB.get("leader_id")));
      activityLeader.setAcronym(activityDB.get("leader_acronym"));
      activityLeader.setName(activityDB.get("leader_name"));

      activity.setLeader(activityLeader);

      // Gender Integration
      activity.setGenderIntegrationsDescription(activityDB.get("gender_description"));

      return activity;
    }
    return null;
  }

  @Override
  public Activity getSimpleActivity(int id) {
    Map<String, String> activityDB = activityDAO.getSimpleActivity(id);
    if (activityDB != null) {
      Activity activity = new Activity();
      activity.setId(id);
      activity.setTitle(activityDB.get("title"));

      Leader activityLeader = new Leader();
      activityLeader.setId(Integer.parseInt(activityDB.get("leader_id")));
      activityLeader.setName(activityDB.get("leader_name"));
      activityLeader.setAcronym(activityDB.get("leader_acronym"));
      activity.setLeader(activityLeader);

      return activity;
    }
    return null;
  }

  @Override
  public boolean isValidId(int id) {
    return activityDAO.isValidId(id);
  }

  @Override
  public boolean saveStatus(Activity activity) {
    Map<String, String> activityData = new HashMap<>();
    activityData.put("activity_id", "" + activity.getId());
    activityData.put("activity_status_id", "" + activity.getStatus().getId());
    activityData.put("status_description", activity.getStatusDescription());
    activityData.put("gender_integrations_description", activity.getGenderIntegrationsDescription());
    return activityDAO.saveStatus(activityData);
  }
}
