package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLIndicatorDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLIndicatorDAO.class)
public interface IndicatorDAO {

  /**
   * Get a list with all the indicators.
   * 
   * @return A list of maps with the information.
   */
  public List<Map<String, String>> getIndicatorsList();
}
