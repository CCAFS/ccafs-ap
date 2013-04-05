package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.BenchmarkSiteDAO;
import org.cgiar.ccafs.ap.data.manager.BenchmarkSiteManager;
import org.cgiar.ccafs.ap.data.model.BenchmarkSite;
import org.cgiar.ccafs.ap.data.model.Country;

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
      benchmarkSite.setId(bsDataList.get(c).get("bs_id"));
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
}
