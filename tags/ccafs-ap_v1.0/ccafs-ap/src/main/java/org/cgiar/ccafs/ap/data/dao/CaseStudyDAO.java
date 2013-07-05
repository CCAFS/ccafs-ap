package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLCaseStudyDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLCaseStudyDAO.class)
public interface CaseStudyDAO {

  /**
   * Get all the cases studies that belongs to a given activity leader
   * and are related to the given logframe
   * 
   * @param activityLeaderId - The activity leader identifier
   * @param logframeId - The logframe identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getCaseStudyList(int activityLeaderId, int logframeId);

  /**
   * Remove all the case studies related to the activity leader and logframe given
   * 
   * @param activityLeaderId activity leader identifier
   * @param logframeId logframe identifier
   * @return true if it was successfully removed. False otherwise
   */
  public boolean removeAllCaseStudies(int activityLeaderId, int logframeId);

  /**
   * Save a caseStudy into the database
   * 
   * @param caseStudyData - A map of objects with the information
   * @return the identifier assigned to the new record
   */
  public int saveCaseStudy(Map<String, Object> caseStudyData);
}
