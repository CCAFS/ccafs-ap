package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLCasesStudiesDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLCasesStudiesDAO.class)
public interface CasesStudiesDAO {

  /**
   * Get all the cases studies that belongs to a given activity leader
   * and are related to the given logframe
   * 
   * @param activityLeaderId - The activity leader identifier
   * @param logframeId - The logframe identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getCasesStudiesList(int activityLeaderId, int logframeId);

  /**
   * Remove all the case studies related to the activity leader and logframe given
   * 
   * @param activityLeaderId activity leader identifier
   * @param logframeId logframe identifier
   * @return true if it was successfully removed. False otherwise
   */
  public boolean removeAllStudyCases(int activityLeaderId, int logframeId);

  /**
   * Save a caseStudies into the database
   * 
   * @param casesStudiesData - A map of objects with the information
   * @return the identifier assigned to the new record
   */
  public int saveCaseStudies(Map<String, Object> casesStudiesData);
}
