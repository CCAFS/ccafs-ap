package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.OutcomeIndicatorReportManagerImpl;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.OutcomeIndicatorReport;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(OutcomeIndicatorReportManagerImpl.class)
public interface OutcomeIndicatorReportManager {

  /**
   * Get the outcome indicator reports existent for the logframe given.
   * 
   * @param logframe
   * @return
   */
  public List<OutcomeIndicatorReport> getOutcomeIndicatorReports(Logframe logframe);

  /**
   * Save the information about the outcome indicators reports given into the database.
   * 
   * @param outcomeIndicatorReports - Information to save
   * @param leader
   * @param logframe
   * @return
   */
  public boolean saveOutcomeIndicatorReports(List<OutcomeIndicatorReport> outcomeIndicatorReports, Leader leader,
    Logframe logframe);
}
