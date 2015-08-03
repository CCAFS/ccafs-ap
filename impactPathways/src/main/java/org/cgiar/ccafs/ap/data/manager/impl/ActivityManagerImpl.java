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
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
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
  private InstitutionManager institutionManager;
  private UserManager userManager;
  private ProjectPartnerManager projectPartnerManager;

  @Inject
  public ActivityManagerImpl(ActivityDAO activityDAO, InstitutionManager institutionManager,
    ProjectPartnerManager projectPartnerManager, UserManager userManager) {
    this.activityDAO = activityDAO;
    this.institutionManager = institutionManager;
    this.projectPartnerManager = projectPartnerManager;
    this.userManager = userManager;
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
  public boolean deleteIndicator(int activityID, int indicatorID) {
    return activityDAO.deleteActivityIndicator(activityID, indicatorID);
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
    for (Map<String, String> activityData : activityDataList) {
      Activity activity = new Activity();
      activity.setId(Integer.parseInt(activityData.get("id")));
      activity.setTitle(activityData.get("title"));
      activity.setDescription(activityData.get("description"));

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
        activity
          .setLeader(projectPartnerManager.getProjectPartnerById(Integer.parseInt(activityData.get("leader_id"))));
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
        activity
        .setLeader(projectPartnerManager.getProjectPartnerById(Integer.parseInt(activityData.get("leader_id"))));
      }
      activity.setCreated(Long.parseLong(activityData.get("created")));
      return activity;
    }
    return null;
  }

  @Override
  public List<Integer> getActivityIdsEditable(User user) {
    return activityDAO.getActivityIdsEditable(user.getCurrentInstitution().getProgram().getId());
  }

  @Override
  public List<IPIndicator> getActivityIndicators(int activityID) {
    List<IPIndicator> indicators = new ArrayList<>();
    List<Map<String, String>> indicatorsData = activityDAO.getActivityIndicators(activityID);

    for (Map<String, String> iData : indicatorsData) {
      IPIndicator indicator = new IPIndicator();
      indicator.setId(Integer.parseInt(iData.get("id")));
      indicator.setDescription(iData.get("description"));
      indicator.setTarget(iData.get("target"));

      // Parent indicator
      IPIndicator parent = new IPIndicator(Integer.parseInt(iData.get("parent_id")));
      parent.setDescription(iData.get("parent_description"));
      parent.setTarget(iData.get("parent_target"));
      indicator.setParent(parent);

      indicators.add(indicator);
    }

    return indicators;
  }

  @Override
  public User getActivityLeader(int activityID) {
    int activityLeaderId = activityDAO.getActivityLeaderId(activityID);
    if (activityLeaderId != -1) {
      User activityLeader = userManager.getOwner(activityLeaderId);
      return activityLeader;
    }
    return null;
  }

  @Override
  public String getActivityOutcome(int activityID) {
    return activityDAO.getActivityOutcome(activityID);
  }

  @Override
  public List<IPElement> getActivityOutputs(int activityID) {
    List<IPElement> outputs = new ArrayList<>();
    List<Map<String, String>> outputsData = activityDAO.getActivityOutputs(activityID);

    for (Map<String, String> oData : outputsData) {
      IPElement output = new IPElement();
      output.setId(Integer.parseInt(oData.get("id")));
      output.setDescription(oData.get("description"));

      IPElement parent = new IPElement();
      parent.setId(Integer.parseInt(oData.get("parent_id")));
      parent.setDescription(oData.get("parent_description"));

      List<IPElement> parentList = new ArrayList<>();
      parentList.add(parent);
      output.setContributesTo(parentList);

      outputs.add(output);
    }

    return outputs;
  }

  @Override
  public List<Activity> getAllActivities() {
    DateFormat dateformatter = new SimpleDateFormat(APConstants.DATE_FORMAT);
    List<Activity> activityList = new ArrayList<>();
    List<Map<String, String>> activityDataList = activityDAO.getAllActivities();
    for (Map<String, String> activityData : activityDataList) {
      Activity activity = new Activity();
      activity.setId(Integer.parseInt(activityData.get("id")));
      activity.setTitle(activityData.get("title"));
      activity.setDescription(activityData.get("description"));

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
          activity
            .setLeader(projectPartnerManager.getProjectPartnerById(Integer.parseInt(activityData.get("leader_id"))));
        }
      }
      activity.setCreated(Long.parseLong(activityData.get("created")));

      // adding information of the object to the array
      activityList.add(activity);
    }
    return activityList;
  }

  @Override
  public List<Integer> getLedActivityIds(User user) {
    return activityDAO.getLedActivities(user.getId());
  }

  @Override
  public int saveActivity(int projectID, Activity activity, User user, String justification) {
    Map<String, Object> activityData = new HashMap<>();
    if (activity.getId() > 0) {
      activityData.put("id", activity.getId());
    }
    activityData.put("title", activity.getTitle());
    activityData.put("description", activity.getDescription());
    SimpleDateFormat format = new SimpleDateFormat(APConstants.DATE_FORMAT);
    if (activity.getEndDate() != null) {
      activityData.put("startDate", format.format(activity.getEndDate()));
    }
    if (activity.getStartDate() != null) {
      activityData.put("endDate", format.format(activity.getStartDate()));
    }
    activityData.put("leader_id", activity.getLeader().getId());
    activityData.put("modified_by", String.valueOf(user.getId()));
    activityData.put("modification_justification", justification);

    return activityDAO.saveActivity(projectID, activityData, user, justification);
  }

  @Override
  public boolean saveActivityIndicators(List<IPIndicator> indicators, int activityID) {
    Map<String, String> indicatorData;
    boolean saved = true;

    for (IPIndicator indicator : indicators) {
      if (indicator == null) {
        continue;
      }
      indicatorData = new HashMap<>();
      if (indicator.getId() == -1) {
        indicatorData.put("id", null);
      } else {
        indicatorData.put("id", String.valueOf(indicator.getId()));
      }

      indicatorData.put("description", indicator.getDescription());
      indicatorData.put("target", indicator.getTarget());
      indicatorData.put("parent_id", String.valueOf(indicator.getParent().getId()));
      indicatorData.put("activity_id", String.valueOf(activityID));

      saved = activityDAO.saveActivityIndicators(indicatorData) && saved;
    }

    return saved;
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

  @Override
  public boolean saveActivityOutcome(Activity activity) {
    return activityDAO.saveActivityOutcome(activity.getId(), activity.getOutcome());
  }

  @Override
  public boolean saveActivityOutputs(List<IPElement> outputs, int activityID) {
    Map<String, String> outputData;
    boolean saved = true;

    for (IPElement output : outputs) {
      if (output == null) {
        continue;
      }
      outputData = new HashMap<>();
      outputData.put("activity_id", String.valueOf(activityID));
      outputData.put("mog_id", String.valueOf(output.getId()));
      outputData.put("midOutcome_id", String.valueOf(output.getContributesTo().get(0).getId()));

      int relationID = activityDAO.saveActivityOutput(outputData);
      saved = (relationID != -1) && saved;
    }
    return saved;
  }

}
