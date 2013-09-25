package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLSubmissionDAO;

import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLSubmissionDAO.class)
public interface SubmissionDAO {

  /**
   * Get if there is a submission by the given leader
   * corresponding to the given logframe in the given section.
   * 
   * @param activityLeaderId - Leader identifier
   * @param logframeId - Logframe identifier
   * @param section
   * @return true if the workplan have been submitted. False otherwise.
   */
  public Map<String, String> getSubmission(int activityLeaderId, int logframeId, String section);

  /**
   * Submit the leader's workplan for the logframe given in the section indicated.
   * 
   * @param activityLeaderId - Leader identifier
   * @param logframeId - Logframe identifier
   * @return true if the submission was successfully saved. False otherwise.
   */
  public boolean submit(int activityLeaderId, int logframeId, String section);

}
