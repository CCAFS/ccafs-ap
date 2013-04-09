package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.BenchmarkSiteManagerImpl;
import org.cgiar.ccafs.ap.data.model.BenchmarkSite;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(BenchmarkSiteManagerImpl.class)
public interface BenchmarkSiteManager {

  /**
   * Get all the benchmark sites that are actives from the DAO
   * 
   * @return a list of BenchmarkSite objects.
   */

  public BenchmarkSite[] getActiveBenchmarkSiteList();

  /**
   * Get a list of benchmarkSite objects corresponding to the given array of ids
   * 
   * @param ids - Array of benchmarkSites identifiers
   * @return a list of BenchmarkSite objects
   */
  public List<BenchmarkSite> getBenchmarkSiteList(String[] ids);
}
