package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.OutputSummaryManagerImpl;
import org.cgiar.ccafs.ap.data.model.OutputSummary;

import com.google.inject.ImplementedBy;

@ImplementedBy(OutputSummaryManagerImpl.class)
public interface OutputSummaryManager {

  /**
   * Get a list with all the summary by outputs of each
   * activity that belongs to the activity leader
   * 
   * @return
   */
  public OutputSummary[] getOutputSumariesList(int activityLeaderID);
}
