package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.TLOutputSummaryManagerImpl;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.TLOutputSummary;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(TLOutputSummaryManagerImpl.class)
public interface TLOutputSummaryManager {

  /**
   * Get a list of Summaries by Output that belong to a specific leader and a specific logframe.
   * 
   * @param leader - Leader object.
   * @param logframe - Logframe object.
   * @return a List of TLOutputSummary objects.
   */
  public List<TLOutputSummary> getTLOutputSummaries(Leader leader, Logframe logframe);

  /**
   * Save a list of Summaries by Output that belongs to a specified leader.
   * 
   * @param outputs - List of TLOutputSummary objects to be saved.
   * @param leader - Leader object
   * @return true if all the outputs were successfully saved. False if any problem appear.
   */
  public boolean saveTLOutputSummaries(List<TLOutputSummary> outputs, Leader leader);

}
