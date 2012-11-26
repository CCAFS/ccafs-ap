package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLLogframeDAO;

import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLLogframeDAO.class)
public interface LogframeDAO {

  /**
   * Get a logframe that represent the given year.
   * 
   * @param year
   * @return a Map with all the logframe data.
   */
  public Map<String, String> getLogframe(int year);
}
