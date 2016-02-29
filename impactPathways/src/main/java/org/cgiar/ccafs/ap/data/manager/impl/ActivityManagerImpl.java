/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/
package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.dao.ActivityDAO;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.PartnerPersonManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Gallego
 * @author Héctor Fabio Tobón R.
 * @author Hernán David Carvajal B.
 */
public class ActivityManagerImpl implements ActivityManager {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ActivityManagerImpl.class);

  // DAO's
  private ActivityDAO activityDAO;

  // Managers
  private PartnerPersonManager partnerPersonManager;

  @Inject
  public ActivityManagerImpl(ActivityDAO activityDAO, PartnerPersonManager partnerPersonManager) {
    this.activityDAO = activityDAO;
    this.partnerPersonManager = partnerPersonManager;
  }

  @Override
  public boolean deleteActivitiesByProject(int projectID, User user, String justification) {
    return activityDAO.deleteActivitiesByProject(projectID, user.getId(), justification);
  }

  @Override
  public boolean deleteActivity(int activityId, User user, String justification) {
    return activityDAO.deleteActivity(activityId, user.getId(), justification);
  }

  @Override
  public boolean existActivity(int activityID) {
    return activityDAO.existActivity(activityID);
  }

  @Override
  public List<Activity> getActivitiesByProject(int projectID) {
    DateFormat dateformatter = new SimpleDateFormat(APConstants.DATE_FORMAT);
    List<Activity> activityList = new ArrayList<>();
    List<Map<String, String>> activityDataList = activityDAO.getActivitiesByProject(projectID);
    Activity activity;
    for (Map<String, String> activityData : activityDataList) {
      activity = new Activity();
      activity.setId(Integer.parseInt(activityData.get("id")));
      activity.setTitle(activityData.get("title"));
      activity.setDescription(activityData.get("description"));
      if (activityData.get("activityStatus") != null) {
        activity.setActivityStatus(Integer.parseInt(activityData.get("activityStatus")));
      }

      activity.setActivityProgress(activityData.get("activityProgress"));

      activity.setTitle(activityData.get("title"));
      // Format the date of the activity
      if (activityData.get("startDate") != null) {
        try {
          Date startDate = dateformatter.parse(activityData.get("startDate"));
          activity.setStartDate(startDate);
        } catch (ParseException e) {
          LOG.error("There was an error formatting the start date", e);
        }
      }
      if (activityData.get("endDate") != null) {
        try {
          Date endDate = dateformatter.parse(activityData.get("endDate"));
          activity.setEndDate(endDate);
        } catch (ParseException e) {
          LOG.error("There was an error formatting the end date", e);
        }
      }
      if (activityData.get("leader_id") != null) {
        activity.setLeader(partnerPersonManager.getPartnerPerson(Integer.parseInt(activityData.get("leader_id"))));
      }
      activity.setCreated(Long.parseLong(activityData.get("created")));

      // adding information of the object to the array
      activityList.add(activity);
    }
    return activityList;
  }

  @Override
  public List<Activity> getActivitiesByProjectPartner(int projectPartnerID) {
    DateFormat dateformatter = new SimpleDateFormat(APConstants.DATE_FORMAT);
    List<Activity> activityList = new ArrayList<>();
    List<Map<String, String>> activityDataList = activityDAO.getActivitiesByProjectPartner(projectPartnerID);
    Activity activity;
    for (Map<String, String> activityData : activityDataList) {
      activity = new Activity();
      activity.setId(Integer.parseInt(activityData.get("id")));
      activity.setTitle(activityData.get("title"));
      activity.setDescription(activityData.get("description"));
      if (activityData.get("activityStatus") != null) {
        activity.setActivityStatus(Integer.parseInt(activityData.get("activityStatus")));
      }
      activity.setActivityProgress(activityData.get("activityProgress"));
      // Format the date of the activity
      if (activityData.get("startDate") != null) {
        try {
          Date startDate = dateformatter.parse(activityData.get("startDate"));
          activity.setStartDate(startDate);
        } catch (ParseException e) {
          LOG.error("There was an error formatting the start date", e);
        }
      }
      if (activityData.get("endDate") != null) {
        try {
          Date endDate = dateformatter.parse(activityData.get("endDate"));
          activity.setEndDate(endDate);
        } catch (ParseException e) {
          LOG.error("There was an error formatting the end date", e);
        }
      }
      activity.setCreated(Long.parseLong(activityData.get("created")));

      // adding information of the object to the array
      activityList.add(activity);
    }
    return activityList;
  }

  @Override
  public Activity getActivityById(int activityID) {
    DateFormat dateformatter = new SimpleDateFormat(APConstants.DATE_FORMAT);
    Map<String, String> activityData = activityDAO.getActivityById(activityID);
    if (!activityData.isEmpty()) {
      Activity activity = new Activity();
      activity.setId(Integer.parseInt(activityData.get("id")));
      activity.setTitle(activityData.get("title"));
      activity.setDescription(activityData.get("description"));
      if (activityData.get("activityStatus") != null) {
        activity.setActivityStatus(Integer.parseInt(activityData.get("activityStatus")));
      }
      activity.setActivityProgress(activityData.get("activityProgress"));
      // Format the date of the activity
      if (activityData.get("startDate") != null) {
        try {
          Date startDate = dateformatter.parse(activityData.get("startDate"));
          activity.setStartDate(startDate);
        } catch (ParseException e) {
          LOG.error("There was an error formatting the start date", e);
        }
      }
      if (activityData.get("endDate") != null) {
        try {
          Date endDate = dateformatter.parse(activityData.get("endDate"));
          activity.setEndDate(endDate);
        } catch (ParseException e) {
          LOG.error("There was an error formatting the end date", e);
        }
      }
      if (activityData.get("leader_id") != null) {
        activity.setLeader(partnerPersonManager.getPartnerPerson(Integer.parseInt(activityData.get("leader_id"))));
      }
      activity.setCreated(Long.parseLong(activityData.get("created")));
      return activity;
    }
    return null;
  }

  @Override
  public List<Activity> getAllActivities() {
    DateFormat dateformatter = new SimpleDateFormat(APConstants.DATE_FORMAT);
    List<Activity> activityList = new ArrayList<>();
    List<Map<String, String>> activityDataList = activityDAO.getAllActivities();
    Activity activity;
    for (Map<String, String> activityData : activityDataList) {
      activity = new Activity();
      activity.setId(Integer.parseInt(activityData.get("id")));
      activity.setTitle(activityData.get("title"));
      activity.setDescription(activityData.get("description"));
      if (activityData.get("activityStatus") != null) {
        activity.setActivityStatus(Integer.parseInt(activityData.get("activityStatus")));
      }
      activity.setActivityProgress(activityData.get("activityProgress"));
      // Format the date of the activity
      if (activityData.get("startDate") != null) {
        try {
          Date startDate = dateformatter.parse(activityData.get("startDate"));
          activity.setStartDate(startDate);
        } catch (ParseException e) {
          LOG.error("There was an error formatting the start date", e);
        }
      }
      if (activityData.get("endDate") != null) {
        try {
          Date endDate = dateformatter.parse(activityData.get("endDate"));
          activity.setEndDate(endDate);
        } catch (ParseException e) {
          LOG.error("There was an error formatting the end date", e);
        }
        if (activityData.get("leader_id") != null) {
          activity.setLeader(partnerPersonManager.getPartnerPerson(Integer.parseInt(activityData.get("leader_id"))));
        }
      }
      activity.setCreated(Long.parseLong(activityData.get("created")));

      // adding information of the object to the array
      activityList.add(activity);
    }
    return activityList;
  }

  @Override
  public List<Activity> getProjectActivitiesLedByUser(int projectID, int userID) {
    List<Activity> activities = new ArrayList<>();
    List<Map<String, String>> activitiesData = activityDAO.getProjectActivitiesLedByUser(projectID, userID);
    Activity activity;
    for (Map<String, String> activityData : activitiesData) {
      activity = new Activity();
      activity.setId(Integer.parseInt(activityData.get("id")));
      activity.setTitle(activityData.get("title"));
      if (activityData.get("activityStatus") != null) {
        activity.setActivityStatus(Integer.parseInt(activityData.get("activityStatus")));
      }
      activity.setActivityProgress(activityData.get("activityProgress"));
      activities.add(activity);
    }
    return activities;
  }

  @Override
  public String getStandardIdentifier(Project project, Activity activity, boolean useComposedCodification) {
    StringBuilder result = new StringBuilder();
    if (useComposedCodification) {
      result.append(APConstants.CCAFS_ORGANIZATION_IDENTIFIER);
      result.append("-P");
      result.append(project.getId());
      result.append("-A");
      result.append(activity.getId());
    } else {
      result.append("P");
      result.append(project.getId());
      result.append("-A");
      result.append(activity.getId());
    }
    return result.toString();
  }

  @Override
  public int saveActivity(int projectID, Activity activity, User user, String justification) {
    Map<String, Object> activityData = new HashMap<>();
    if (activity.getId() > 0) {
      activityData.put("id", activity.getId());
    }
    activityData.put("title", activity.getTitle());
    activityData.put("description", activity.getDescription());
    activityData.put("activityStatus", activity.getActivityStatus());
    activityData.put("activityProgress", activity.getActivityProgress());
    SimpleDateFormat format = new SimpleDateFormat(APConstants.DATE_FORMAT);
    if (activity.getEndDate() != null) {
      activityData.put("endDate", format.format(activity.getEndDate()));
    }
    if (activity.getStartDate() != null) {
      activityData.put("startDate", format.format(activity.getStartDate()));
    }
    if (activity.getLeader() != null) {
      activityData.put("leader_id", activity.getLeader().getId());
    } else {
      activityData.put("leader_id", 0);
    }

    activityData.put("modified_by", String.valueOf(user.getId()));
    activityData.put("modification_justification", justification);

    return activityDAO.saveActivity(projectID, activityData, user, justification);
  }

  @Override
  public boolean saveActivityList(int projectID, List<Activity> activityList, User user, String justification) {
    boolean allSaved = true;
    int result;
    for (Activity activity : activityList) {
      result = this.saveActivity(projectID, activity, user, justification);
      if (result == -1) {
        allSaved = false;
      }
    }
    return allSaved;
  }

}