/*
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 */

package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.RegionDAO;
import org.cgiar.ccafs.ap.data.manager.RegionManager;
import org.cgiar.ccafs.ap.data.model.Region;

import java.util.ArrayList;
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

  @Override
  public List<Region> getRegionList(String[] ids) {
    List<Region> regions = new ArrayList<>();
    for (Region region : getRegionList()) {
      for (String id : ids) {
        if (region.getId() == Integer.parseInt(id)) {
          regions.add(region);
        }
      }
    }
    return regions;
  }

}
