package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ActivityObjectiveDAO;
import org.cgiar.ccafs.ap.data.manager.ActivityObjectiveManager;
import org.cgiar.ccafs.ap.data.model.ActivityObjective;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ActivityObjectiveManagerImpl implements ActivityObjectiveManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ActivityObjectiveManagerImpl.class);
  ActivityObjectiveDAO activityObjectiveDAO;

  @Inject
  public ActivityObjectiveManagerImpl(ActivityObjectiveDAO activityObjectiveDAO) {
    this.activityObjectiveDAO = activityObjectiveDAO;
  }

  @Override
  public boolean deleteActivityObjectives(int activityId) {
    return activityObjectiveDAO.deleteActivityObjectives(activityId);
  }

  @Override
  public List<ActivityObjective> getActivityObjectives(int activityID) {
    List<Map<String, String>> activityObjectivesDataList = activityObjectiveDAO.getActivityObjectives(activityID);
    List<ActivityObjective> activityObjectives = new ArrayList<>();

    for (Map<String, String> ao : activityObjectivesDataList) {
      ActivityObjective activityObjective = new ActivityObjective();
      activityObjective.setId(Integer.parseInt(ao.get("id")));
      activityObjective.setDescription(ao.get("description"));
      activityObjectives.add(activityObjective);
    }
    return activityObjectives;
  }

  @Override
  public boolean saveActivityObjectives(List<ActivityObjective> objectives, int activityID) {
    boolean saved = true;
    for (ActivityObjective objective : objectives) {
      Map<String, String> actObjData = new HashMap<>();
      if (objective.getId() == -1) {
        actObjData.put("id", null);
      } else {
        actObjData.put("id", String.valueOf(objective.getId()));
      }
      actObjData.put("description", objective.getDescription());
      if (!activityObjectiveDAO.saveActivityObjectives(actObjData, activityID)) {
        saved = false;
      }
    }
    return saved;
  }
}
