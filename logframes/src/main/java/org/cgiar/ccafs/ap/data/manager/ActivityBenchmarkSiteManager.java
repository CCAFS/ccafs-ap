package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.ActivityBenchmarkSiteManagerImpl;
import org.cgiar.ccafs.ap.data.model.BenchmarkSite;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ActivityBenchmarkSiteManagerImpl.class)
public interface ActivityBenchmarkSiteManager {

  /**
   * Delete all the activity benchmark site locations related with the
   * activity given.
   * 
   * @param activityID - Activity identifier
   * @return true if the locations were deleted successfully. False otherwise.
   */
  public boolean deleteActivityBenchmarkSites(int activityID);

  /**
   * Get all the benchmark sites related to the activity given
   * 
   * @param activityID - the activity identifier
   * @return a list of BenchmarkSiteLocation objects with the information
   */
  public List<BenchmarkSite> getActivityBenchmarkSites(int activityID);

  /**
   * Save a list of benchmark sites locations into the database.
   * 
   * @param benchmarkSites - The locations to save
   * @param activityID - the activity identifier
   * @return true if ALL the sites was successfully saved. False otherwise
   */
  public boolean saveActivityBenchmarkSites(List<BenchmarkSite> benchmarkSites, int activityID);
}
