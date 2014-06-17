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

import org.cgiar.ccafs.ap.data.dao.ActivityBenchmarkSiteDAO;
import org.cgiar.ccafs.ap.data.manager.ActivityBenchmarkSiteManager;
import org.cgiar.ccafs.ap.data.model.BenchmarkSite;
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
  public boolean deleteActivityBenchmarkSites(int activityID) {
    return activityBSDAO.deleteActivityBenchmarkSites(activityID);
  }

  @Override
  public List<BenchmarkSite> getActivityBenchmarkSites(int activityID) {
    List<BenchmarkSite> benchmarkSites = new ArrayList<>();
    List<Map<String, String>> bsDataList = activityBSDAO.getActivityBenchmarkSites(activityID);
    for (Map<String, String> bsData : bsDataList) {
      Country countryTemp = new Country();
      countryTemp.setId(bsData.get("country_iso2"));
      countryTemp.setName(bsData.get("country_name"));

      BenchmarkSite bsTemp = new BenchmarkSite();
      bsTemp.setId(bsData.get("id"));
      bsTemp.setName(bsData.get("name"));
      bsTemp.setLatitude(Double.parseDouble(bsData.get("latitude")));
      bsTemp.setLongitud(Double.parseDouble(bsData.get("longitude")));
      bsTemp.setCountry(countryTemp);

      benchmarkSites.add(bsTemp);
    }

    return benchmarkSites;
  }

  @Override
  public boolean saveActivityBenchmarkSites(List<BenchmarkSite> benchmarkSites, int activityID) {
    boolean saved = true;
    for (BenchmarkSite bs : benchmarkSites) {
      if (!activityBSDAO.saveActivityBenchmarkSite(bs.getId(), activityID)) {
        saved = false;
      }
    }
    return saved;
  }
}