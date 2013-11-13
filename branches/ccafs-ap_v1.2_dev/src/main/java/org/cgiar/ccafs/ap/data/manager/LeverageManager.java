package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.LeverageManagerImpl;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Leverage;
import org.cgiar.ccafs.ap.data.model.Logframe;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(LeverageManagerImpl.class)
public interface LeverageManager {

  /**
   * Get all the leverages that belongs to the given leader and
   * corresponding to the given logframe.
   * 
   * @param leader
   * @param logframe
   * @return a list of Leverages objects with the information
   */
  public List<Leverage> getLeverages(Leader leader, Logframe logframe);

  /**
   * Remove all the leverages that bleongs to the given leader for
   * the corresponding logframe.
   * 
   * @param leader
   * @param logframe
   * @return true if the leverages were successfully removed. False otherwise.
   */
  public boolean removeLeverages(Leader leader, Logframe logframe);

  /**
   * Save the leverages reported by the leader corresponding to the logframe given.
   * 
   * @param leverages
   * @param leader
   * @param logframe
   * @return true if All the leverages were saved successfully. False otherwise.
   */
  public boolean saveLeverages(List<Leverage> leverages, Leader leader);
}
