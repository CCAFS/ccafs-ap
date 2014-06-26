package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.IPElementManagerImpl;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPElementType;
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
  public List<IPElement> getIPElements(IPProgram program);

  /**
   * This method gets all the IPElements of the type given and which are
   * related to the IP program given.
   * 
   * @param program - IPProgram object
   * @param type - IPElementType object
   * @return a list of IPElements which fill the conditions.
   */
  public List<IPElement> getIPElements(IPProgram program, IPElementType type);
}
