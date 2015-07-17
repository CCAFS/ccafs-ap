package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.IndicatorReportManagerImpl;
import org.cgiar.ccafs.ap.data.model.IndicatorReport;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(IndicatorReportManagerImpl.class)
public interface IndicatorReportManager {

  /**
   * Get the list of indicator's reports made by the leader and corresponding
   * to the given logframe.
   * 
   * @param leader
   * @param logframe
   * @return a list of IndicatorReport objects with the information.
   */
  public List<IndicatorReport> getIndicatorReportsList(Leader leader, Logframe logframe);

  /**
   * @param indicatorReports
   * @param leader
   * @param logframe
   * @return
   */
  public boolean saveIndicatorReportsList(List<IndicatorReport> indicatorReports, Leader leader);
}
