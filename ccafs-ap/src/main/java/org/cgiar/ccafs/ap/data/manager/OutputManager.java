package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.OutputManagerImpl;
import org.cgiar.ccafs.ap.data.model.Output;

import com.google.inject.ImplementedBy;

@ImplementedBy(OutputManagerImpl.class)
public interface OutputManager {

  /**
   * Get a list with all the outputs of each activity that
   * belongs to the activityLeader
   * 
   * @param activityLeaderID
   * @return a list of Output objects
   */
  public Output[] getOutputList(int activityLeaderID);

}
