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

import org.cgiar.ccafs.ap.data.dao.BenchmarkSiteDAO;
import org.cgiar.ccafs.ap.data.manager.BenchmarkSiteManager;
import org.cgiar.ccafs.ap.data.model.BenchmarkSite;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class BenchmarkSiteManagerImpl implements BenchmarkSiteManager {

  private BenchmarkSiteDAO benchmarkSiteDAO;

  @Inject
  public BenchmarkSiteManagerImpl(BenchmarkSiteDAO benchmarkSiteDAO) {
    this.benchmarkSiteDAO = benchmarkSiteDAO;
  }

  @Override
  public BenchmarkSite[] getActiveBenchmarkSiteList() {
    List<Map<String, String>> bsDataList = benchmarkSiteDAO.getActiveBenchmarkSiteList();
    BenchmarkSite[] benchmarkSites = new BenchmarkSite[bsDataList.size()];

    for (int c = 0; c < bsDataList.size(); c++) {
      BenchmarkSite benchmarkSite = new BenchmarkSite();
      benchmarkSite.setId(bsDataList.get(c).get("id"));
      benchmarkSite.setLatitude(Double.parseDouble(bsDataList.get(c).get("latitude")));
      benchmarkSite.setLongitud(Double.parseDouble(bsDataList.get(c).get("longitude")));
      benchmarkSite.setName(bsDataList.get(c).get("name"));

      // Temporal country
      Country country = new Country();
      country.setId(bsDataList.get(c).get("country_iso2"));
      country.setName(bsDataList.get(c).get("country_name"));

      benchmarkSite.setCountry(country);
      benchmarkSites[c] = benchmarkSite;
    }
    return benchmarkSites;
  }

  @Override
  public BenchmarkSite[] getActiveBenchmarkSitesByCountry(String countryID) {
    List<Map<String, String>> bsDataList = benchmarkSiteDAO.getActiveBenchmarkSitesByCountry(countryID);
    BenchmarkSite[] benchmarkSites = new BenchmarkSite[bsDataList.size()];

    for (int c = 0; c < bsDataList.size(); c++) {
      BenchmarkSite benchmarkSite = new BenchmarkSite();
      benchmarkSite.setId(bsDataList.get(c).get("id"));
      benchmarkSite.setLatitude(Double.parseDouble(bsDataList.get(c).get("latitude")));
      benchmarkSite.setLongitud(Double.parseDouble(bsDataList.get(c).get("longitude")));
      benchmarkSite.setName(bsDataList.get(c).get("name"));

      // Temporal country
      Country country = new Country();
      country.setId(bsDataList.get(c).get("country_iso2"));
      country.setName(bsDataList.get(c).get("country_name"));

      benchmarkSite.setCountry(country);
      benchmarkSites[c] = benchmarkSite;
    }
    return benchmarkSites;
  }

  @Override
  public BenchmarkSite[] getActiveBenchmarkSitesByRegion(String regionID) {
    List<Map<String, String>> bsDataList = benchmarkSiteDAO.getActiveBenchmarkSitesByRegion(regionID);
    BenchmarkSite[] benchmarkSites = new BenchmarkSite[bsDataList.size()];

    for (int c = 0; c < bsDataList.size(); c++) {
      BenchmarkSite benchmarkSite = new BenchmarkSite();
      benchmarkSite.setId(bsDataList.get(c).get("id"));
      benchmarkSite.setLatitude(Double.parseDouble(bsDataList.get(c).get("latitude")));
      benchmarkSite.setLongitud(Double.parseDouble(bsDataList.get(c).get("longitude")));
      benchmarkSite.setName(bsDataList.get(c).get("name"));

      // Temporal country
      Country country = new Country();
      country.setId(bsDataList.get(c).get("country_iso2"));
      country.setName(bsDataList.get(c).get("country_name"));

      // Temporal region
      Region region = new Region();
      region.setId(Integer.parseInt(bsDataList.get(c).get("region_id")));
      region.setName(bsDataList.get(c).get("region_name"));
      country.setRegion(region);

      benchmarkSite.setCountry(country);
      benchmarkSites[c] = benchmarkSite;
    }
    return benchmarkSites;
  }

  @Override
  public List<BenchmarkSite> getBenchmarkSiteList(String[] ids) {
    List<BenchmarkSite> benchmarkSites = new ArrayList<>();
    for (BenchmarkSite bs : getActiveBenchmarkSiteList()) {
      for (String id : ids) {
        if (bs.getId().equals(id)) {
          benchmarkSites.add(bs);
        }
      }
    }
    return benchmarkSites;
  }

}