package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.RegionDAO;
import org.cgiar.ccafs.ap.data.manager.RegionManager;
import org.cgiar.ccafs.ap.data.model.Region;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class RegionManagerImpl implements RegionManager {

  private RegionDAO regionDAO;

  @Inject
  public RegionManagerImpl(RegionDAO regionDAO) {
    this.regionDAO = regionDAO;
  }

  @Override
  public Region[] getRegionList() {
    List<Map<String, String>> regionsDataList = regionDAO.getRegionsList();
    Region[] regions = new Region[regionsDataList.size()];

    for (int c = 0; c < regionsDataList.size(); c++) {
      Region region = new Region();
      region.setId(Integer.parseInt(regionsDataList.get(c).get("id")));
      region.setName(regionsDataList.get(c).get("name"));
      region.setDescription(regionsDataList.get(c).get("description"));
      regions[c] = region;
    }
    return regions;
  }

}
