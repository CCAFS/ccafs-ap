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

import org.cgiar.ccafs.ap.data.dao.ActivityOtherSiteDAO;
import org.cgiar.ccafs.ap.data.manager.ActivityOtherSiteManager;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.OtherSite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ActivityOtherSiteManagerImpl implements ActivityOtherSiteManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ActivityOtherSiteManagerImpl.class);
  private ActivityOtherSiteDAO activityOtherSiteDAO;

  @Inject
  public ActivityOtherSiteManagerImpl(ActivityOtherSiteDAO activityOtherSiteDAO) {
    this.activityOtherSiteDAO = activityOtherSiteDAO;
  }

  @Override
  public boolean deleteActivityOtherSites(int activityID) {
    return activityOtherSiteDAO.deleteActivityOtherSites(activityID);
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

  @Override
  public boolean saveActivityOtherSites(List<OtherSite> otherSites, int activityID) {
    boolean success = true;
    for (OtherSite os : otherSites) {
      Map<String, String> otherSiteData = new HashMap<String, String>();
      if (os.getId() == -1) {
        otherSiteData.put("id", null);
      } else {
        otherSiteData.put("id", String.valueOf(os.getId()));
      }
      otherSiteData.put("latitude", String.valueOf(os.getLatitude()));
      otherSiteData.put("longitude", String.valueOf(os.getLongitude()));
      otherSiteData.put("details", os.getDetails());
      otherSiteData.put("country_iso2", os.getCountry().getId());
      if (!activityOtherSiteDAO.saveActivityOtherSites(otherSiteData, activityID)) {
        success = false;
      }
    }
    return success;
  }
}
