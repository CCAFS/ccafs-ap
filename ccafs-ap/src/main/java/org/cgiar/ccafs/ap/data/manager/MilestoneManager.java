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
}
