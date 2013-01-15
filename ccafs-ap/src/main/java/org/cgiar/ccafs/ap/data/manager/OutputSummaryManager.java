package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.OutputSummaryManagerImpl;
import org.cgiar.ccafs.ap.data.model.OutputSummary;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(OutputSummaryManagerImpl.class)
public interface OutputSummaryManager {

  /**
   * Get the summary of output made by the activity leader.
   * 
   * @param outputID the output identifier
   * @param activityLeaderId The activity leader identifier
   * @return an outputSummary object with the information
   */
  public OutputSummary getOutputSummary(int outputID, int activityLeaderId);

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
