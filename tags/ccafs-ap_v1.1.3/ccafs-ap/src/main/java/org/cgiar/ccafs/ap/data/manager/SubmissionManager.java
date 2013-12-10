package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.SubmissionManagerImpl;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.Submission;

import com.google.inject.ImplementedBy;

@ImplementedBy(SubmissionManagerImpl.class)
public interface SubmissionManager {

  /**
   * Get the submission information related with the given data.
   * 
   * @param activityLeaderId - Leader identifier
   * @param logframeId - Logframe identifier
   * @param section
   * @return A submission object with the information if exists the submission. Null otherwise.
   */
  public Submission getSubmission(Leader leader, Logframe logframe, String section);

  /**
   * Make a submission relating the given leader, the given logframe and the section
   * indicated.
   * 
   * @param submission
   * @return
   */
  public boolean submit(Submission submission);
}
