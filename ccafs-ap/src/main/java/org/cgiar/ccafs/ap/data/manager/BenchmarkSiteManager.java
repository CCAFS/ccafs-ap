package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.BenchmarkSiteManagerImpl;
import org.cgiar.ccafs.ap.data.model.BenchmarkSite;

import com.google.inject.ImplementedBy;

@ImplementedBy(BenchmarkSiteManagerImpl.class)
public interface BenchmarkSiteManager {

  /**
   * Get all the benchmark sites that are actives from the DAO
   * 
   * @return a list of BenchmarkSite objects.
   */

  public BenchmarkSite[] getActiveBenchmarkSiteList();
}
