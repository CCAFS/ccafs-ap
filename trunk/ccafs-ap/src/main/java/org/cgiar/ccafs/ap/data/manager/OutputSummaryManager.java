package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.OutputSummaryManagerImpl;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.OutputSummary;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(OutputSummaryManagerImpl.class)
public interface OutputSummaryManager {

  /**
   * Get a list of Summaries by Output that belongs to a specific activity leader and a specific logframe.
   * 
   * @param leader - Leader object.
   * @param logframe - Logframe object.
   * @return a List of OutputSummary objects.
   */
  public OutputSummary[] getOutputSummaries(Leader activityLeader, Logframe logframe);

  /**
   * Save into the DAO the outputs summary information
   * 
   * @param outputSummary The list of objects that contains the data
   * @return true if the data was successfully saved, false otherwise
   */
  public boolean saveOutputSummary(List<OutputSummary> outputSummaries);

  /**
   * Update into the DAO the outputs summary information
   * 
   * @param outputSummaries the list of objects that contains the data
   * @return true if the data was successfully save, false otherwise
   */
  public boolean updateOutputSummary(List<OutputSummary> outputSummaries);
}
