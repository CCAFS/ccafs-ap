package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.ActivityBenchmarkSiteManagerImpl;
import org.cgiar.ccafs.ap.data.model.BenchmarkSiteLocation;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ActivityBenchmarkSiteManagerImpl.class)
public interface ActivityBenchmarkSiteManager {

  /**
   * Get all the benchmark sites related to the activity given
   * 
   * @param activityID - the activity identifier
   * @return a list of BenchmarkSiteLocation objects with the information
   */
  public List<BenchmarkSiteLocation> getActivityBenchmarkSites(int activityID);
}
