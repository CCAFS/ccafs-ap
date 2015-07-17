package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLOutcomeIndicatorReportDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLOutcomeIndicatorReportDAO.class)
public interface OutcomeIndicatorReportDAO {

  /**
   * Get all the outcome indicators reported in the given year.
   * 
   * @param year
   * @return a list of maps with the information.
   */
  public List<Map<String, String>> getOutcomeIndicatorReports(int year, int leaderId);

  /**
   * Save all the information related to the outcome indicators.
   * 
   * @param outcomeIndicatorsData
   * @return
   */
  public boolean saveOutcomeIndicators(List<Map<String, String>> outcomeIndicatorsData);
}
