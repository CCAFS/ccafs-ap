package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.RPLSynthesisReportManagerImpl;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.RPLSynthesisReport;

import com.google.inject.ImplementedBy;

@ImplementedBy(RPLSynthesisReportManagerImpl.class)
public interface RPLSynthesisReportManager {

  /**
   * Get a Regional Program Leader Synthesis Report that belong to a specific leader and logframe.
   * 
   * @param leader - Leader object.
   * @param logframe - Logframe object.
   * @return a Map with the synthesis report information, or null if nothing found.
   */
  public RPLSynthesisReport getRPLSynthesisReport(Leader leader, Logframe logframe);

  /**
   * Save or Update the Regional Program Leader Synthesis Report into the DAO.
   * 
   * @param synthesisReport - RPLSynthesisReport object.
   * @param leader - Leader object
   * @param logframe - Logframe object
   * @return true if the save/update was successfully made, or false if any other problem occur.
   */
  public boolean saveRPLSynthesisReport(RPLSynthesisReport synthesisReport, Leader leader, Logframe logframe);
}
