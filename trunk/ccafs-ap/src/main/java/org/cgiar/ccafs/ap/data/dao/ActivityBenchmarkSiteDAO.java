package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLActivityBenchmarkSiteDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLActivityBenchmarkSiteDAO.class)
public interface ActivityBenchmarkSiteDAO {

  /**
   * Get all the benchmark sites related to the activity given
   * 
   * @param activityID - The activity identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getActivityBenchmarkSites(int activityID);
}
