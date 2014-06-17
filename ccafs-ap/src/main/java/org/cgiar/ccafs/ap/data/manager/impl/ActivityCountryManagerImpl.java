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

import org.cgiar.ccafs.ap.data.dao.ActivityCountryDAO;
import org.cgiar.ccafs.ap.data.manager.ActivityCountryManager;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivityCountryManagerImpl implements ActivityCountryManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ActivityCountryManagerImpl.class);
  private ActivityCountryDAO activityCountryDAO;

  @Inject
  public ActivityCountryManagerImpl(ActivityCountryDAO activityCountryDAO) {
    this.activityCountryDAO = activityCountryDAO;
  }

  @Override
  public boolean deleteActivityCountries(int activityID) {
    return activityCountryDAO.deleteActivityCountries(activityID);
  }

  @Override
  public List<Country> getActvitiyCountries(int activityID) {
    List<Country> activityCountries = new ArrayList<>();
    List<Map<String, String>> countryDataList = activityCountryDAO.getActivityCountries(activityID);
    for (Map<String, String> CData : countryDataList) {
      Country countryTemp = new Country();
      countryTemp.setId(CData.get("iso2"));
      countryTemp.setName(CData.get("name"));

      // Temporal region
      Region regionTemp = new Region();
      regionTemp.setId(Integer.parseInt(CData.get("region_id")));
      regionTemp.setName(CData.get("region_name"));

      countryTemp.setRegion(regionTemp);

      activityCountries.add(countryTemp);
    }
    return activityCountries;
  }

  @Override
  public boolean saveActivityCountries(List<Country> countries, int activityID) {
    boolean saved = true;
    boolean countrySaved;
    for (Country country : countries) {
      countrySaved = activityCountryDAO.saveActivityCountry(activityID, country.getId());
      if (!countrySaved) {
        saved = false;
      }
    }
    return saved;
  }
}