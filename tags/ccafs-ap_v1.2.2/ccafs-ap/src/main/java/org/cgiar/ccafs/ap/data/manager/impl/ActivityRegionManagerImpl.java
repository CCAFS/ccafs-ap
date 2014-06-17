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

import org.cgiar.ccafs.ap.data.dao.ActivityRegionDAO;
import org.cgiar.ccafs.ap.data.manager.ActivityRegionManager;
import org.cgiar.ccafs.ap.data.model.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivityRegionManagerImpl implements ActivityRegionManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ActivityRegionManagerImpl.class);
  private ActivityRegionDAO activityRegionDAO;

  @Inject
  public ActivityRegionManagerImpl(ActivityRegionDAO activityRegionDAO) {
    this.activityRegionDAO = activityRegionDAO;
  }

  @Override
  public boolean deleteActivityRegions(int activityID) {
    return activityRegionDAO.deleteActivityRegions(activityID);
  }

  @Override
  public List<Region> getActvitiyRegions(int activityID) {
    List<Region> activityRegions = new ArrayList<>();
    List<Map<String, String>> regionDataList = activityRegionDAO.getActivityRegions(activityID);
    for (Map<String, String> RData : regionDataList) {
      Region regionTemp = new Region();
      regionTemp.setId(Integer.parseInt(RData.get("id")));
      regionTemp.setName(RData.get("name"));

      activityRegions.add(regionTemp);
    }
    return activityRegions;
  }

  @Override
  public boolean saveActivityRegions(List<Region> regions, int activityID) {
    boolean saved = true;
    boolean regionSaved;
    for (Region region : regions) {
      regionSaved = activityRegionDAO.saveActivityRegion(activityID, String.valueOf(region.getId()));
      if (!regionSaved) {
        saved = false;
      }
    }
    return saved;
  }
}