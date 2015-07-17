package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.MilestoneStatusManagerImpl;
import org.cgiar.ccafs.ap.data.model.MilestoneStatus;

import com.google.inject.ImplementedBy;

@ImplementedBy(MilestoneStatusManagerImpl.class)
public interface MilestoneStatusManager {

  /**
   * Get a list with all the milestone status list
   * 
   * @return an array of Milestone Status objects
   */
  public MilestoneStatus[] getMilestoneStatusList();
}