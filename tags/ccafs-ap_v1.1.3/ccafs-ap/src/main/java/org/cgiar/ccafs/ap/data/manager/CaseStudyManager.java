package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.CaseStudyManagerImpl;
import org.cgiar.ccafs.ap.data.model.CaseStudy;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(CaseStudyManagerImpl.class)
public interface CaseStudyManager {

  /**
   * Get a list with all the case studies that belongs to
   * the activityLeader and are related to the given logframe
   * 
   * @param leader - an object with the leader information
   * @param logframe - an object with the logframe information
   * @return a list of cases studies objects
   */
  public List<CaseStudy> getCaseStudyList(Leader leader, Logframe logframe);

  /**
   * Get all the case studies that belongs to the given leader, were
   * carried out the given year in the countries given and that have
   * the types indicated. Or return the complete list if no valid parameters
   * were given.
   * 
   * @param activityLeaderId - Activity leader identifier
   * @param year
   * @param countriesIds - Country identifiers
   * @param typesIds - Case study types identifiers
   * @return a list of CaseStudy objects with the information
   */
  public List<CaseStudy>
    getCaseStudyListForSummary(int activityLeaderId, int year, String countriesIds, String typesIds);

  /**
   * Remove all the case studies related to the activity leader and logframe specified
   * 
   * @param activityLeaderId - Activity leader identifier
   * @param logframeId - Logframe identifier
   * @return true if the remove process was successful, false otherwise
   */
  public boolean removeAllCaseStudies(int activityLeaderId, int logframeId);

  /**
   * Store a case study in the DAO, Also save the countries related to it.
   * 
   * @param casesStudy the object to store
   * @param activityLeaderId the activity leader identifier
   * @param logframeId the logframe identifier
   * @return true if the data was successfully stored. False otherwise
   */
  public boolean saveCaseStudy(CaseStudy caseStudy, int activityLeaderId, int logframeId);
}
