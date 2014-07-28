package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.IPCrossCuttingManagerImpl;
import org.cgiar.ccafs.ap.data.model.IPCrossCutting;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * This class represents the Manager of IP Cross Cutting Theme
 * 
 * @author Javier Andrés Gallego B.
 */

@ImplementedBy(IPCrossCuttingManagerImpl.class)
public interface IPCrossCuttingManager {


  /**
   * This method gets the information of a IP Cross Cutting Theme by a given ID
   * 
   * @param iD - is the ID of a IP Cross Cutting Theme
   * @return an object with the information of a IP Cross Cutting Theme
   */
  public IPCrossCutting getIPCrossCutting(int iD);

  /**
   * This method gets all the IP Cross Cutting Themes
   * 
   * @return a List with the information of IP Cross Cutting Themes
   */
  public List<IPCrossCutting> getIPCrossCuttings();
}
