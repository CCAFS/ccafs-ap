package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLActivityBenchmarkSiteDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLActivityBenchmarkSiteDAO.class)
public interface ActivityBenchmarkSiteDAO {

  /**
   * Delete all the benchmark sites locations related with the activity given
   * from the DAO.
   * 
   * @param activityID - activity identifier
   * @return true if the data was successfully deleted. False otherwise
   */
  public boolean deleteActivityBenchmarkSites(int activityID);


  /**
   * Get all the benchmark sites related to the activity given
   * 
   * @param activityID - The activity identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getActivityBenchmarkSites(int activityID);

  /**
   * Save a benchmark sites location into the database.
   * 
   * @param benchmarkSiteID - The locations identifier
   * @param activityID - the activity identifier
   * @return true if the site was successfully saved. False otherwise
   */
  public boolean saveActivityBenchmarkSite(String benchmarkSiteID, int activityID);
}
