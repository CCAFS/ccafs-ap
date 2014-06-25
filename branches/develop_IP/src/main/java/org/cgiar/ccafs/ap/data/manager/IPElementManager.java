package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.IPElementManagerImpl;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPProgram;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(IPElementManagerImpl.class)
public interface IPElementManager {

  /**
   * This method gets all the IPElements related to the IP program
   * given
   * 
   * @param program - Object with the program information
   * @return a list with IPElements
   */
  public List<IPElement> getIPElementsByProgram(IPProgram program);
}
