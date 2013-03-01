package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ActivityOtherSiteDAO;
import org.cgiar.ccafs.ap.data.manager.ActivityOtherSiteManager;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.OtherSite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class ActivityOtherSiteManagerImpl implements ActivityOtherSiteManager {

  private ActivityOtherSiteDAO activityOtherSiteDAO;

  @Inject
  public ActivityOtherSiteManagerImpl(ActivityOtherSiteDAO activityOtherSiteDAO) {
    this.activityOtherSiteDAO = activityOtherSiteDAO;
  }

  @Override
  public List<OtherSite> getActivityOtherSites(int activityID) {
    List<OtherSite> otherSites = new ArrayList<>();
    List<Map<String, String>> osDataList = activityOtherSiteDAO.getActivityOtherSites(activityID);

    for (Map<String, String> osData : osDataList) {
      Country countryTemp = new Country();
      countryTemp.setId(osData.get("country_iso2"));
      countryTemp.setName(osData.get("country_name"));

      OtherSite otherSiteTemp = new OtherSite();
      otherSiteTemp.setId(Integer.parseInt(osData.get("id")));
      otherSiteTemp.setLatitude(Double.parseDouble(osData.get("latitude")));
      otherSiteTemp.setLongitude(Double.parseDouble(osData.get("longitude")));
      otherSiteTemp.setDetails(osData.get("details"));
      otherSiteTemp.setCountry(countryTemp);

      otherSites.add(otherSiteTemp);
    }
    return otherSites;
  }
}
