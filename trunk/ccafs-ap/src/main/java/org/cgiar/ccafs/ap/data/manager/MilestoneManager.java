package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.MilestoneManagerImpl;
import org.cgiar.ccafs.ap.data.model.Milestone;

import com.google.inject.ImplementedBy;

@ImplementedBy(MilestoneManagerImpl.class)
public interface MilestoneManager {


  /**
   * Get a milestone object identified with the given id.
   * 
   * @param id
   * @return a Milestione object or null if no milestone was found.
   */
  public Milestone getMilestone(int id);

  /**
   * Get the complete milestone list corresponding to the logframe given
   * 
   * @param logframeID - the logframe identifier
   * @return a list of Milestone objects
   */
  public Milestone[] getMilestoneList(String logframeID);
}
