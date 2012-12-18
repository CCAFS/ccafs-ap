package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ActivityDAO;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
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

public class ActivityManagerImpl implements ActivityManager {

  private ActivityDAO activityDAO;

  @Inject
  public ActivityManagerImpl(ActivityDAO activityDAO) {
    this.activityDAO = activityDAO;
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
      activity.setId(Integer.parseInt(activitiesDAO.get(c).get("id")));
      activity.setTitle(activitiesDAO.get(c).get("title"));
      activity.setDescription(activitiesDAO.get(c).get("description"));
      try {
        activity.setStartDate(dateFormat.parse(activitiesDAO.get(c).get("start_date")));
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      try {
        activity.setEndDate(dateFormat.parse(activitiesDAO.get(c).get("end_date")));
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

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

      activities[c] = activity;
    }
    return activities;
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
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      try {
        activity.setEndDate(dateFormat.parse(activityDB.get("end_date")));
      } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      activity.setDescription(activityDB.get("description"));
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
