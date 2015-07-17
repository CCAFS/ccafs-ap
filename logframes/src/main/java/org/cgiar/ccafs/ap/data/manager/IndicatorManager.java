package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.IndicatorManagerImpl;
import org.cgiar.ccafs.ap.data.model.Indicator;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(IndicatorManagerImpl.class)
public interface IndicatorManager {

  /**
   * Get the list of all the indicators stored in the database.
   * 
   * @return a list of Indicator objects with the information.
   */
  public List<Indicator> getIndicatorList();
}
