package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.OutcomeIndicatorManagerImpl;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.OutcomeIndicator;

import com.google.inject.ImplementedBy;

@ImplementedBy(OutcomeIndicatorManagerImpl.class)
public interface OutcomeIndicatorManager {

  /**
   * This function gets all the outcome indicators specified
   * to the given year.
   * 
   * @param year
   * @return an array of OutcomeIndicator objects with the info.
   */
  public OutcomeIndicator[] getOutcomeIndicators(Logframe logframe);
}
