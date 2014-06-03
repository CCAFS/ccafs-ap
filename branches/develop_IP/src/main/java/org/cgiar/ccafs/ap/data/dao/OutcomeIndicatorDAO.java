package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLOutcomeIndicatorDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLOutcomeIndicatorDAO.class)
public interface OutcomeIndicatorDAO {

  /**
   * Get all the outcome indicators specified to the given year.
   * 
   * @param year
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getOutcomeIndicators(int year);
}
