package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.CommunicationManagerImpl;
import org.cgiar.ccafs.ap.data.model.Communication;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;

import com.google.inject.ImplementedBy;

@ImplementedBy(CommunicationManagerImpl.class)
public interface CommunicationManager {

  /**
   * Get all the communications that belongs to the given leader and that corresponds to
   * the given logframe.
   * 
   * @param leader
   * @param logframe
   * @return a list of Communication object with the information
   */
  public Communication getCommunicationReport(Leader leader, Logframe logframe);

  /**
   * Save the list of communications given.
   * 
   * @param communication - Information to be saved
   * @param leader
   * @param logframe
   * @return true if the information was saved successfully. False otherwise.
   */
  public boolean saveCommunicationReport(Communication communication, Leader leader, Logframe logframe);
}
