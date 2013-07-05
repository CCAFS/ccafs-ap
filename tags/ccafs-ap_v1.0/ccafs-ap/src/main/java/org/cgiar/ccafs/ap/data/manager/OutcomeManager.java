package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.OutcomeManagerImpl;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.Outcome;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(OutcomeManagerImpl.class)
public interface OutcomeManager {

  /**
   * Add a list of outcomes into the DAO.
   * 
   * @param newOutcomes - List of Outcome objects to be added.
   * @param leader - Leader object
   * @param logframe - Logframe object
   * @return true if all the outcomes were successfully added into the database, or false if any problem occurred.
   */
  public boolean addOutcomes(List<Outcome> newOutcomes, Leader leader, Logframe logframe);

  /**
   * Get a list with information of all outcomes that belong to a given leader and logframe.
   * 
   * @param leader - Leader object.
   * @param logframe - logframe object.
   * @return a list of Outcome objects.
   */
  public List<Outcome> getOutcomes(Leader leader, Logframe logframe);

  /**
   * Remove a list of outcomes that belong to a specific leader and logframe.
   * 
   * @param leader - Leader object.
   * @param logframe - Logframe object.
   * @return true if all the outcomes were successfully removed, or false otherwise.
   */
  public boolean removeOutcomes(Leader leader, Logframe logframe);
}
