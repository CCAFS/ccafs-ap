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
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.ActivityLeader;

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
 */
public class ActivityManagerImpl implements ActivityManager {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ActivityManagerImpl.class);

  // DAO's
  private ActivityDAO activityDAO;

  // Managers
  private ActivityManager activityManager;
  private InstitutionManager institutionManager;

  @Inject
  public ActivityManagerImpl(ActivityDAO activityDAO, ActivityManager activityManager,
    InstitutionManager institutionManager) {
    this.activityDAO = activityDAO;
    this.activityManager = activityManager;
    this.institutionManager = institutionManager;
  }

  @Override
  public boolean deleteActivitiesByProject(int projectID) {
    return activityDAO.deleteActivitiesByProject(projectID);
  }

  @Override
  public boolean deleteActivity(int activityId) {
    return activityDAO.deleteActivity(activityId);
  }

  @Override
  public List<Activity> getActivitiesByProject(int projectID) {
    DateFormat dateformatter = new SimpleDateFormat(APConstants.DATE_FORMAT);
    List<Activity> activityList = new ArrayList<>();
    List<Map<String, String>> activityDataList = activityDAO.getActivitiesByProject(projectID);
    for (Map<String, String> activityData : activityDataList) {
      Activity activity = new Activity();
      activity.setId(Integer.parseInt(activityData.get("id")));
      activity.setCustomId(activityData.get("custom_id"));
      activity.setTitle(activityData.get("title"));
      activity.setDescription(activityData.get("description"));

      // Format the date of the activity
      if (activityData.get("startDate") != null) {
        try {
          Date startDate = dateformatter.parse(activityData.get("startDate"));
          activity.setStart(startDate);
        } catch (ParseException e) {
          LOG.error("There was an error formatting the start date", e);
        }
      }
      if (activityData.get("endDate") != null) {
        try {
          Date endDate = dateformatter.parse(activityData.get("endDate"));
          activity.setEnd(endDate);
        } catch (ParseException e) {
          LOG.error("There was an error formatting the end date", e);
        }
        if (activityData.get("leader_id") != null) {
          activity.setLeader(activityManager.getActivityLeader(Integer.parseInt(activityData.get("id"))));
        }
        activity.setCreated(Long.parseLong(activityData.get("created")));

        // adding information of the object to the array
        activityList.add(activity);
      }
    }
    return activityList;
  }

  @Override
  public Activity getActivityById(int activityID) {
    DateFormat dateformatter = new SimpleDateFormat(APConstants.DATE_FORMAT);
    Map<String, String> activityData = activityDAO.getActivityById(activityID);
    if (!activityData.isEmpty()) {
      Activity activity = new Activity();
      activity.setCustomId(activityData.get("custom_id"));
      activity.setTitle(activityData.get("title"));
      activity.setDescription(activityData.get("description"));

      // Format the date of the activity
      if (activityData.get("startDate") != null) {
        try {
          Date startDate = dateformatter.parse(activityData.get("startDate"));
          activity.setStart(startDate);
        } catch (ParseException e) {
          LOG.error("There was an error formatting the start date", e);
        }
      }
      if (activityData.get("endDate") != null) {
        try {
          Date endDate = dateformatter.parse(activityData.get("endDate"));
          activity.setEnd(endDate);
        } catch (ParseException e) {
          LOG.error("There was an error formatting the end date", e);
        }
      }
      if (activityData.get("leader_id") != null) {
        activity.setLeader(activityManager.getActivityLeader(activityID));
      }
      activity.setCreated(Long.parseLong(activityData.get("created")));
      return activity;
    }
    return null;
  }

  @Override
  public ActivityLeader getActivityLeader(int activityID) {
    Map<String, String> activityLeaderData = activityDAO.getActivityLeaderById(activityID);
    if (!activityLeaderData.isEmpty()) {
      ActivityLeader activityLeader = new ActivityLeader();
      activityLeader.setId(Integer.parseInt(activityLeaderData.get("id")));
      activityLeader.setName(activityLeaderData.get("name"));
      activityLeader.setEmail(activityLeaderData.get("email"));
      if (activityLeaderData.get("institution_id") != null) {
        activityLeader.setInstitution(institutionManager.getInstitution(Integer.parseInt(activityLeaderData
          .get("institution_id"))));
      }
      return activityLeader;
    }
    return null;
  }

  @Override
  public boolean saveActivity(int projectID, Activity activity) {
    boolean allSaved = true;
    Map<String, Object> activityData = new HashMap<>();
    if (activity.getId() > 0) {
      activityData.put("id", activity.getId());
    }
    activityData.put("title", activity.getTitle());
    activityData.put("description", activity.getDescription());
    activityData.put("startDate", activity.getStart());
    activityData.put("endDate", activity.getEnd());

    int result = activityDAO.saveActivity(projectID, activityData);

    if (result > 0) {
      LOG.debug("saveActivity > New Activity added with id {}", result);
    } else if (result == 0) {
      LOG.debug("saveActivity > Activity with id={} was updated", activity.getId());
    } else {
      LOG.error("saveActivity > There was an error trying to save/update a Activity from projectId={}", projectID);
      allSaved = false;
    }

    return allSaved;

  }
}
