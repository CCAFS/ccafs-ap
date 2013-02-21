package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ActivityObjectiveDAO;
import org.cgiar.ccafs.ap.data.manager.ActivityObjectiveManager;
import org.cgiar.ccafs.ap.data.model.ActivityObjective;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class ActivityObjectiveManagerImpl implements ActivityObjectiveManager {

  ActivityObjectiveDAO activityObjectiveDAO;

  @Inject
  public ActivityObjectiveManagerImpl(ActivityObjectiveDAO activityObjectiveDAO) {
    this.activityObjectiveDAO = activityObjectiveDAO;
  }

  @Override
  public ActivityObjective[] getActivityObjectives(int activityID) {
    List<Map<String, String>> activityObjectivesDataList = activityObjectiveDAO.getActivityObjectives(activityID);
    ActivityObjective[] activityObjectives = new ActivityObjective[activityObjectivesDataList.size()];

    for (int c = 0; c < activityObjectives.length; c++) {
      activityObjectives[c] = new ActivityObjective();
      activityObjectives[c].setId(Integer.parseInt(activityObjectivesDataList.get(c).get("id")));
      activityObjectives[c].setDescription(activityObjectivesDataList.get(c).get("description"));
    }
    return activityObjectives;
  }
}
