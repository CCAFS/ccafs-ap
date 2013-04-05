package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLBenchmarkSiteDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLBenchmarkSiteDAO.class)
public interface BenchmarkSiteDAO {

  /**
   * Get all the benchmark sites that are active from the DAO
   * 
   * @return a list of maps with the information.
   */
  public List<Map<String, String>> getActiveBenchmarkSiteList();
}
