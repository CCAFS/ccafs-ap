package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ActivityBenchmarkSiteDAO;
import org.cgiar.ccafs.ap.data.manager.ActivityBenchmarkSiteManager;
import org.cgiar.ccafs.ap.data.model.BenchmarkSite;
import org.cgiar.ccafs.ap.data.model.BenchmarkSiteLocation;
import org.cgiar.ccafs.ap.data.model.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ActivityBenchmarkSiteManagerImpl implements ActivityBenchmarkSiteManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ActivityBenchmarkSiteManagerImpl.class);
  private ActivityBenchmarkSiteDAO activityBSDAO;

  @Inject
  public ActivityBenchmarkSiteManagerImpl(ActivityBenchmarkSiteDAO activityBSDAO) {
    this.activityBSDAO = activityBSDAO;
  }

  @Override
  public List<BenchmarkSiteLocation> getActivityBenchmarkSites(int activityID) {
    List<BenchmarkSiteLocation> benchmarkSites = new ArrayList<>();
    List<Map<String, String>> bsDataList = activityBSDAO.getActivityBenchmarkSites(activityID);
    for (Map<String, String> bsData : bsDataList) {
      Country countryTemp = new Country();
      countryTemp.setId(bsData.get("country_iso2"));
      countryTemp.setName(bsData.get("country_name"));

      BenchmarkSite bsTemp = new BenchmarkSite();
      bsTemp.setId(bsData.get("bs_id"));
      bsTemp.setName(bsData.get("name"));
      bsTemp.setLatitude(Double.parseDouble(bsData.get("latitude")));
      bsTemp.setLongitud(Double.parseDouble(bsData.get("longitude")));
      bsTemp.setCountry(countryTemp);

      BenchmarkSiteLocation bsLocationTemp = new BenchmarkSiteLocation();
      bsLocationTemp.setDetails(bsData.get("details"));
      bsLocationTemp.setBenchmarkSite(bsTemp);

      benchmarkSites.add(bsLocationTemp);
    }

    return benchmarkSites;
  }
}