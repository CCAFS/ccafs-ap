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

  @Inject
  public ActivityManagerImpl(ActivityDAO activityDAO, InstitutionManager institutionManager, UserManager userManager) {
    this.activityDAO = activityDAO;
    this.institutionManager = institutionManager;
    this.userManager = userManager;
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
  public boolean deleteActivityOutput(int activityID, int outputID) {
    return activityDAO.deleteActivityOutput(activityID, outputID);
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
      if (activityData.get("expected_leader_id") != null) {
        activity.setExpectedLeader(this.getExpectedActivityLeader(Integer.parseInt(activityData.get("id"))));
      }
      if (activityData.get("leader_id") != null) {
        activity.setLeader(userManager.getUser(Integer.parseInt(activityData.get("leader_id"))));
      }
      if (activityData.get("is_global") != null) {
        activity.setGlobal((activityData.get("is_global").equals("1")));
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
      if (activityData.get("expected_leader_id") != null) {
        activity.setExpectedLeader(this.getExpectedActivityLeader(activityID));
      }
      if (activityData.get("leader_id") != null) {
        activity.setLeader(userManager.getOwner(Integer.parseInt(activityData.get("leader_id"))));
      }
      if (activityData.get("is_global") != null) {
        activity.setGlobal(activityData.get("is_global").equals("1"));
      }
      activity.setExpectedResearchOutputs(activityData.get("expected_research_outputs"));
      activity.setExpectedGenderContribution(activityData.get("expected_gender_contribution"));
      if (activityData.get("gender_percentage") != null) {
        activity.setGenderPercentage(Double.parseDouble(activityData.get("gender_percentage")));
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
        if (activityData.get("expected_leader_id") != null) {
          activity.setExpectedLeader(this.getExpectedActivityLeader(Integer.parseInt(activityData.get("id"))));
        }
        if (activityData.get("leader_id") != null) {
          activity.setLeader(userManager.getUser(Integer.parseInt(activityData.get("leader_id"))));
        }
        if (activityData.get("is_global") != null) {
          activity.setGlobal((activityData.get("is_global").equals("1")));
        }
      }
      activity.setCreated(Long.parseLong(activityData.get("created")));

      // adding information of the object to the array
      activityList.add(activity);
    }
    return activityList;
  }

  @Override
  public User getExpectedActivityLeader(int activityID) {
    Map<String, String> expectedActivityLeaderData = activityDAO.getExpectedActivityLeader(activityID);
    if (!expectedActivityLeaderData.isEmpty()) {
      User expectedActivityLeader = new User();
      expectedActivityLeader.setId(Integer.parseInt(expectedActivityLeaderData.get("id")));
      expectedActivityLeader.setFirstName(expectedActivityLeaderData.get("name"));
      expectedActivityLeader.setEmail(expectedActivityLeaderData.get("email"));
      if (expectedActivityLeaderData.get("institution_id") != null) {
        expectedActivityLeader.setCurrentInstitution(institutionManager.getInstitution(Integer
          .parseInt(expectedActivityLeaderData.get("institution_id"))));
      }
      return expectedActivityLeader;
    }
    return null;
  }

  @Override
  public List<Integer> getLedActivityIds(User user) {
    return activityDAO.getLedActivities(user.getId());
  }

  @Override
  public boolean isOfficialExpectedLeader(int activityID) {
    return activityDAO.isOfficialExpectedLeader(activityID);
  }

  @Override
  public int saveActivity(int projectID, Activity activity) {
    Map<String, Object> activityData = new HashMap<>();
    if (activity.getId() > 0) {
      activityData.put("id", activity.getId());
    }
    activityData.put("title", activity.getTitle());
    activityData.put("description", activity.getDescription());
    activityData.put("startDate", activity.getStartDate());
    activityData.put("endDate", activity.getEndDate());
    if (activity.getExpectedLeader() != null) {
      activityData.put("expected_leader_id", activity.getExpectedLeader().getId());
    }
    activityData.put("is_global", activity.isGlobal());
    activityData.put("expected_research_outputs", activity.getExpectedResearchOutputs());
    activityData.put("expected_gender_contribution", activity.getExpectedGenderContribution());
    activityData.put("gender_percentage", activity.getGenderPercentage());
    return activityDAO.saveActivity(projectID, activityData);
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
  public boolean saveActivityLeader(int activityID, User user) {
    boolean allSaved = true;

    int result = activityDAO.saveActivityLeader(activityID, user.getId());

    if (result > 0) {
      LOG.debug("saveExpectedActivityLeader > New Activity Leader added with id {}", result);
    } else if (result == 0) {
      LOG.debug("saveExpectedActivityLeader > Activity Leader with id={} was updated", user.getId());
    } else {
      LOG.error(
        "saveExpectedActivityLeader > There was an error trying to save/update an Activity Leader for activityID={}",
        activityID);
      allSaved = false;
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

  @Override
  public int saveExpectedActivityLeader(int activityID, User expectedActivityLeader, boolean isOfficialLeader) {

    Map<String, Object> activityData = new HashMap<>();
    if (expectedActivityLeader.getId() > 0) {
      activityData.put("id", expectedActivityLeader.getId());
    }
    activityData.put("institution_id", expectedActivityLeader.getCurrentInstitution().getId());
    activityData.put("name", expectedActivityLeader.getFirstName());
    activityData.put("email", expectedActivityLeader.getEmail());
    activityData.put("is_official", isOfficialLeader);

    int result = activityDAO.saveExpectedActivityLeader(activityID, activityData, isOfficialLeader);

    if (result > 0) {
      LOG.debug("saveExpectedActivityLeader > New Expected Activity Leader added with id {}", result);
    } else if (result == 0) {
      LOG.debug("saveExpectedActivityLeader > Expected Activity Leader with id={} was updated",
        expectedActivityLeader.getId());
    } else {
      LOG
        .error(
          "saveExpectedActivityLeader > There was an error trying to save/update an Expected Activity Leader for activityId={}",
          activityID);
    }

    return result;
  }
}
