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