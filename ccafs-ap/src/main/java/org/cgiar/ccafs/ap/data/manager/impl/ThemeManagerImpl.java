package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ThemeDAO;
import org.cgiar.ccafs.ap.data.manager.ThemeManager;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.Theme;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class ThemeManagerImpl implements ThemeManager {

  private ThemeDAO themeDAO;

  @Inject
  public ThemeManagerImpl(ThemeDAO themeDAO) {
    this.themeDAO = themeDAO;
  }

  @Override
  public Theme[] getThemes(Logframe logframe) {
    List<Map<String, String>> themesDB = themeDAO.getThemes(logframe.getId());
    if (themesDB.size() > 0) {
      Theme[] themes = new Theme[themesDB.size()];
      for (int c = 0; c < themesDB.size(); c++) {
        themes[c] = new Theme();
        themes[c].setId(Integer.parseInt(themesDB.get(c).get("id")));
        themes[c].setCode(themesDB.get(c).get("code"));
        themes[c].setDescription(themesDB.get(c).get("description"));
        themes[c].setLogframe(logframe);
      }
      return themes;
    }
    return null;
  }


  /*
   * @Override
   * public List<BenchmarkSiteLocation> getActivityBenchmarkSites(int activityID) {
   * List<BenchmarkSiteLocation> benchmarkSites = new ArrayList<>();
   * List<Map<String, String>> bsDataList = activityBSDAO.getActivityBenchmarkSites(activityID);
   * for (Map<String, String> bsData : bsDataList) {
   * Country countryTemp = new Country();
   * countryTemp.setId(bsData.get("country_iso2"));
   * countryTemp.setName(bsData.get("country_name"));
   * BenchmarkSite bsTemp = new BenchmarkSite();
   * bsTemp.setId(bsData.get("bs_id"));
   * bsTemp.setName(bsData.get("name"));
   * bsTemp.setLatitude(Double.parseDouble(bsData.get("latitude")));
   * bsTemp.setLongitud(Double.parseDouble(bsData.get("longitude")));
   * bsTemp.setCountry(countryTemp);
   * BenchmarkSiteLocation bsLocationTemp = new BenchmarkSiteLocation();
   * bsLocationTemp.setDetails(bsData.get("details"));
   * bsLocationTemp.setBenchmarkSite(bsTemp);
   * benchmarkSites.add(bsLocationTemp);
   * }
   * return benchmarkSites;
   * }
   */

}