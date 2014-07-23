package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.IPIndicatorManagerImpl;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPProgram;

import com.google.inject.ImplementedBy;

@ImplementedBy(IPIndicatorManagerImpl.class)
public interface IPIndicatorManager {

  /**
   * This method removes from the database the indicators which are related with the
   * ipProgram and ipElement passed as parameters
   * 
   * @param element
   * @param program
   * @return true if the indicators were removed successfully. False otherwise.
   */
  public boolean removeElementIndicators(IPElement element, IPProgram program);
}
