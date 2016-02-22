package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.IndicatorReportManagerImpl;
import org.cgiar.ccafs.ap.data.model.IndicatorReport;
import org.cgiar.ccafs.ap.data.model.IndicatorType;
import org.cgiar.ccafs.ap.data.model.LiaisonInstitution;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(IndicatorReportManagerImpl.class)
public interface IndicatorReportManager {

  /**
   * Get the list of indicator's reports made by the leader and corresponding
   * to the given logframe.
   * 
   * @param leader
   * @return a list of IndicatorReport objects with the information.
   */
  public List<IndicatorReport> getIndicatorReportsList(int leader, int year);


  /**
   * Get the list of indicator's type
   * 
   * @param leader
   * @return a list of IndicatorReport objects with the information.
   */
  public List<IndicatorType> getIndicatorReportsType();

  /**
   * @param indicatorReports
   * @param leader
   * @param logframe
   * @return
   */
  public boolean saveIndicatorReportsList(List<IndicatorReport> indicatorReports, LiaisonInstitution leader);
}
