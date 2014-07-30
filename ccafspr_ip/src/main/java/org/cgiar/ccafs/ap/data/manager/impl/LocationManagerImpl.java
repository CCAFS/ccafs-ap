/*****************************************************************
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
 *****************************************************************/
package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.dao.LocationDAO;
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Location;
import org.cgiar.ccafs.ap.data.model.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

/**
 * @author Javier Andres Gallego B.
 */
public class LocationManagerImpl implements LocationManager {


  private LocationDAO locationDAO;

  @Inject
  public LocationManagerImpl(LocationDAO locationDAO) {
    this.locationDAO = locationDAO;
  }


  @Override
  public List<Country> getAllCountries() {
    List<Country> countries = new ArrayList<>();
    List<Map<String, String>> countriesDataList = locationDAO.getAllCountries();

    for (Map<String, String> lData : countriesDataList) {
      Country country = new Country();
      country.setId(Integer.parseInt(lData.get("id")));
      country.setName(lData.get("name"));
      country.setCode(lData.get("code"));
      // Region
      Region region = new Region();
      region.setId(Integer.parseInt(lData.get("region_id")));
      region.setName(lData.get("region_name"));
      region.setCode(lData.get("region_code"));
      country.setRegion(region);

      // Adding object to the array.
      countries.add(country);
    }
    return countries;
  }

  @Override
  public List<Region> getAllRegions() {
    List<Region> regions = new ArrayList<>();
    List<Map<String, String>> regionsDataList = locationDAO.getAllRegions();

    for (Map<String, String> lData : regionsDataList) {
      Region region = new Region();
      region.setId(Integer.parseInt(lData.get("id")));
      region.setName(lData.get("name"));
      region.setCode(lData.get("code"));

      // Adding object to the array.
      regions.add(region);
    }
    return regions;
  }

  @Override
  public Country getCountry(int countryID) {
    Map<String, String> lData = locationDAO.getCountry(countryID);
    if (!lData.isEmpty()) {
      // Country
      Country country = new Country();
      country.setId(Integer.parseInt(lData.get("id")));
      country.setName(lData.get("name"));
      country.setCode(lData.get("code"));
      // Region
      Region region = new Region();
      region.setId(Integer.parseInt(lData.get("region_id")));
      region.setName(lData.get("region_name"));
      region.setCode(lData.get("region_code"));
      country.setRegion(region);
      return country;
    }
    return null;
  }


  @Override
  public Country getCountryByCode(String code) {
    Map<String, String> lData = locationDAO.getCountryByCode(code);
    if (!lData.isEmpty()) {
      // Country
      Country country = new Country();
      country.setId(Integer.parseInt(lData.get("id")));
      country.setName(lData.get("name"));
      country.setCode(lData.get("code"));
      // Region
      Region region = new Region();
      region.setId(Integer.parseInt(lData.get("region_id")));
      region.setName(lData.get("region_name"));
      region.setCode(lData.get("region_code"));
      country.setRegion(region);
      return country;
    }
    return null;
  }

  @Override
  public Location getLocation(int typeID, int locationID) {
    Map<String, String> lData = locationDAO.getLocation(typeID, locationID);
    if (!lData.isEmpty()) {
      Location location = new Country();
      location.setId(Integer.parseInt(lData.get("id")));
      location.setName(lData.get("name"));
      location.setCode(lData.get("code"));
      return location;
    }
    return null;
  }

  @Override
  public List<Location> getLocationsByType(int typeID) {
    List<Location> locations = new ArrayList<>();
    List<Map<String, String>> locationDataList = locationDAO.getLocationsByType(typeID);
    // TODO JG - Ask about this, when you have to search another location elements type
    if (typeID == APConstants.LOCATION_ELEMENT_TYPE_COUNTRY) { // validation if the typeID is Country
      for (Map<String, String> lData : locationDataList) {
        Country location = new Country();
        location.setId(Integer.parseInt(lData.get("id")));
        location.setName(lData.get("name"));
        location.setCode(lData.get("code"));
        // Region
        // if (lData.get("region_id") != null) {
        // TODO JG - The region is never going to be reachable since the object is defined as 'Location'.
        // Region region = new Region();
        // region.setId(Integer.parseInt(lData.get("region_id")));
        // region.setName(lData.get("region_name"));
        // region.setCode(lData.get("region_code"));
        // location.setRegion(region);
        // }

        // Adding object to the array.
        locations.add(location);
      }
    }
    return locations;
  }

  @Override
  public Region getRegion(int regionID) {
    Map<String, String> lData = locationDAO.getRegion(regionID);
    if (!lData.isEmpty()) {
      // Region
      Region region = new Region();
      region.setId(Integer.parseInt(lData.get("region_id")));
      region.setName(lData.get("region_name"));
      region.setCode(lData.get("region_code"));
      return region;
    }
    return null;
  }
}
