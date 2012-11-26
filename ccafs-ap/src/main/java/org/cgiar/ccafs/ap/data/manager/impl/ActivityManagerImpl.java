package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ActivityDAO;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Milestone;
import org.cgiar.ccafs.ap.data.model.Objective;
import org.cgiar.ccafs.ap.data.model.Output;
import org.cgiar.ccafs.ap.data.model.Theme;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
  public Activity[] getActivities(int year, Leader leader) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
    List<Map<String, String>> activitiesDAO;

    // if leader is null the user must be an admin.
    if (leader == null) {
      activitiesDAO = activityDAO.getActivities(year);
    } else {
      // get all activities added by the specified leader.
      activitiesDAO = activityDAO.getActivities(year, leader.getCode());
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
      activityLeader.setCode(Integer.parseInt(activitiesDAO.get(c).get("leader_id")));
      activityLeader.setName(activitiesDAO.get(c).get("leader_name"));

      activity.setLeader(activityLeader);

      activities[c] = activity;
    }
    return activities;
  }

}
