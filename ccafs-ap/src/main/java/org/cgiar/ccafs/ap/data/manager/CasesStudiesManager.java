package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.CasesStudiesManagerImpl;
import org.cgiar.ccafs.ap.data.model.CasesStudy;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(CasesStudiesManagerImpl.class)
public interface CasesStudiesManager {

  /**
   * Get a list with all the cases studies that belongs to
   * the activityLeader and are related to the given logframe
   * 
   * @param leader - an object with the leader information
   * @param logframe - an object with the logframe information
   * @return a list of cases studies objects
   */
  public List<CasesStudy> getCasesStudiesList(Leader leader, Logframe logframe);

  /**
   * Remove all the study cases related to the activity leader and logframe specified
   * 
   * @param activityLeaderId - Activity leader identifier
   * @param logframeId - Logframe identifier
   * @return true if the remove process was successful, false otherwise
   */
  public boolean removeAllStudyCases(int activityLeaderId, int logframeId);

  /**
   * Store a case studies in the DAO, Also save the countries related to it.
   * 
   * @param casesStudies the object to store
   * @param activityLeaderId the activity leader identifier
   * @param logframeId the logframe identifier
   * @return true if the data was successfully stored. False otherwise
   */
  public boolean storeCaseStudies(CasesStudy caseStudies, int activityLeaderId, int logframeId);

}
