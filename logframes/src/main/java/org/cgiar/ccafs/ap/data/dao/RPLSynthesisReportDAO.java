package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLRPLSynthesisReportDAO;

import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLRPLSynthesisReportDAO.class)
public interface RPLSynthesisReportDAO {

  /**
   * Get a Regional Program Leader Synthesis Report that belong to a specific leader and logframe.
   * 
   * @param leader - Leader object.
   * @param logframe - Logframe object.
   * @return a Map with the synthesis report information, or null if nothing found.
   */
  public Map<String, Object> getRPLSynthesisReport(int leaderId, int logframeId);

  /**
   * Save or Update the Regional Program Leader Synthesis Report into the DAO.
   * 
   * @param synthesisReport - a Map with the synthesis report information to be saved.
   * @return true if the save/update was successfully made, or false if any other problem occur.
   */
  public boolean saveRPLSynthesisReport(Map<String, Object> synthesisReport);
}
