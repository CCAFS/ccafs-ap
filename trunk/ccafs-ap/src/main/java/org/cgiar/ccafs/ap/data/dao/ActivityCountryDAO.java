package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLActivityCountryDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLActivityCountryDAO.class)
public interface ActivityCountryDAO {

  /**
   * Get all the country locations related to the activity given
   * 
   * @param activityID - Activity identifier
   * @return a List of maps with the information
   */
  public List<Map<String, String>> getActivityCountries(int activityID);
}
