package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.RegionManagerImpl;
import org.cgiar.ccafs.ap.data.model.Region;

import com.google.inject.ImplementedBy;

@ImplementedBy(RegionManagerImpl.class)
public interface RegionManager {

  /**
   * Get a list with all the regions.
   * 
   * @return an array of Region objects
   */
  public Region[] getRegionList();
}
