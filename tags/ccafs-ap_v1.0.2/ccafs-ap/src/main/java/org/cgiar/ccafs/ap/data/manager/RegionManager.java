package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.RegionManagerImpl;
import org.cgiar.ccafs.ap.data.model.Region;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(RegionManagerImpl.class)
public interface RegionManager {

  /**
   * Get a list with all the regions.
   * 
   * @return an array of Region objects
   */
  public Region[] getRegionList();

  /**
   * Get a list of region objects corresponding to the given array of ids
   * 
   * @param ids - Array of region identifiers
   * @return a list of Region objects
   */
  public List<Region> getRegionList(String[] ids);
}
