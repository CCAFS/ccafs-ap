package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLActivityObjectiveDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLActivityObjectiveDAO.class)
public interface ActivityObjectiveDAO {

  /**
   * Get the objectives related to the activity given
   * 
   * @param activityID - the activity identifier
   * @return a list of activityObjective objects with the information
   */
  public List<Map<String, String>> getActivityObjectives(int activityID);
}
