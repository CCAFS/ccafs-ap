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
import org.cgiar.ccafs.ap.data.model.ClimateSmartVillage;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Location;
import org.cgiar.ccafs.ap.data.model.LocationGeoposition;
import org.cgiar.ccafs.ap.data.model.LocationType;
import org.cgiar.ccafs.ap.data.model.OtherLocation;
import org.cgiar.ccafs.ap.data.model.Region;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andres Gallego B.
 * @author Hernán David Carvajal B.
 */
public class LocationManagerImpl implements LocationManager {

  public static Logger LOG = LoggerFactory.getLogger(LocationManagerImpl.class);


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
  public List<Country> getInstitutionCountries() {
    List<Country> countries = new ArrayList<>();
    List<Map<String, String>> countriesDataList = locationDAO.getInstitutionCountries();

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
  public List<Location> getLocationsByIDs(String[] locationsIDs) {

    List<Location> locations = new ArrayList<>();
    List<Map<String, String>> locationsData = locationDAO.getLocationsByIDs(locationsIDs);

    for (Map<String, String> lData : locationsData) {
      // Evaluating different cases depending on the type of location
      switch (Integer.parseInt(lData.get("type_id"))) {

        case APConstants.LOCATION_ELEMENT_TYPE_REGION:
          Region region = new Region();
          region.setId(Integer.parseInt(lData.get("id")));
          region.setName(lData.get("name"));
          region.setCode(lData.get("code"));
          locations.add(region);
          break;

        case APConstants.LOCATION_ELEMENT_TYPE_COUNTRY:
          Country country = new Country();
          country.setId(Integer.parseInt(lData.get("id")));
          country.setName(lData.get("name"));

          if (lData.get("code") != null) {
            country.setCode(lData.get("code"));
          }
          Region reg = new Region();
          reg.setId(Integer.parseInt(lData.get("parent_id")));
          reg.setName(lData.get("parent_name"));
          country.setRegion(reg);

          locations.add(country);
          break;

        case APConstants.LOCATION_TYPE_CLIMATE_SMART_VILLAGE:
          ClimateSmartVillage csv = new ClimateSmartVillage();
          csv.setId(Integer.parseInt(lData.get("id")));
          csv.setName(lData.get("name"));

          if (lData.get("code") != null) {
            csv.setCode(lData.get("code"));
          }

          OtherLocation loc = new OtherLocation();
          loc.setId(Integer.parseInt(lData.get("parent_id")));
          loc.setName(lData.get("parent_name"));
          csv.setCcafsSite(loc);

          locations.add(csv);
          break;

        default:
          OtherLocation location = new OtherLocation();
          location.setId(Integer.parseInt(lData.get("id")));
          location.setName(lData.get("name"));

          if (lData.get("code") != null) {
            location.setCode(lData.get("code"));
          }

          LocationType type = new LocationType();
          type.setId(Integer.parseInt(lData.get("type_id")));
          ;
          type.setName(lData.get("type_name"));
          location.setType(type);

          Country count = new Country();
          count.setId(Integer.parseInt(lData.get("parent_id")));
          count.setName(lData.get("parent_name"));
          location.setCountry(count);

          LocationGeoposition geoposition = new LocationGeoposition();
          geoposition.setId(Integer.parseInt(lData.get("loc_geo_id")));
          geoposition.setLatitude(Double.parseDouble(lData.get("loc_geo_latitude")));
          geoposition.setLongitude(Double.parseDouble(lData.get("loc_geo_longitude")));
          location.setGeoPosition(geoposition);

          locations.add(location);
          break;

      }


    }
    return locations;
  }


  @Override
  public List<Location> getLocationsByType(int locationTypeID) {
    // TODO - HC At this moment, this method only returns other locations
    // This method should be able to return any type of location (Region, country, etc)
    List<Location> locations = new ArrayList<>();
    List<Map<String, String>> locationsData = locationDAO.getLocationsByType(locationTypeID);

    for (Map<String, String> lData : locationsData) {
      OtherLocation location = new OtherLocation();
      location.setId(Integer.parseInt(lData.get("id")));
      location.setName(lData.get("name"));

      if (lData.get("code") != null) {
        location.setCode(lData.get("code"));
      }

      LocationType type = new LocationType();
      type.setId(Integer.parseInt(lData.get("type_id")));
      type.setName(lData.get("type_name"));

      location.setType(type);

      if (lData.get("parent_id") != null) {
        Country country = new Country();
        country.setId(Integer.parseInt(lData.get("parent_id")));
        country.setName(lData.get("parent_name"));
        location.setCountry(country);
      }

      if (lData.get("loc_geo_id") != null) {
        LocationGeoposition geoposition = new LocationGeoposition();
        geoposition.setId(Integer.parseInt(lData.get("loc_geo_id")));
        geoposition.setLatitude(Double.parseDouble(lData.get("loc_geo_latitude")));
        geoposition.setLongitude(Double.parseDouble(lData.get("loc_geo_longitude")));
        location.setGeoPosition(geoposition);
      }

      locations.add(location);
    }
    return locations;
  }

  @Override
  public List<Location> getProjectLocations(int projectID) {
    List<Location> locations = new ArrayList<>();
    List<Map<String, String>> locationsData = locationDAO.getProjectLocations(projectID);
    int locationTypeID;

    for (Map<String, String> lData : locationsData) {
      locationTypeID = Integer.parseInt(lData.get("type_id"));

      if (locationTypeID == APConstants.LOCATION_ELEMENT_TYPE_REGION) {
        // Region Location
        Region location = new Region();

        location.setId(Integer.parseInt(lData.get("id")));
        location.setCode(lData.get("code"));
        location.setName(lData.get("name"));

        locations.add(location);

      } else if (locationTypeID == APConstants.LOCATION_ELEMENT_TYPE_COUNTRY) {
        // Country location
        Country location = new Country();

        location.setId(Integer.parseInt(lData.get("id")));
        location.setCode(lData.get("code"));
        location.setName(lData.get("name"));

        // Set the parent location
        Region region = new Region();
        region.setId(Integer.parseInt(lData.get("location_parent_id")));
        region.setName(lData.get("location_parent_name"));
        location.setRegion(region);

        locations.add(location);
      } else if (locationTypeID == APConstants.LOCATION_TYPE_CLIMATE_SMART_VILLAGE) {
        // CSV Location
        ClimateSmartVillage location = new ClimateSmartVillage();

        location.setId(Integer.parseInt(lData.get("id")));
        location.setCode(lData.get("code"));
        location.setName(lData.get("name"));

        // Set the parent location
        OtherLocation ccafsSite = new OtherLocation();
        ccafsSite.setId(Integer.parseInt(lData.get("location_parent_id")));
        ccafsSite.setName(lData.get("location_parent_name"));
        location.setCcafsSite(ccafsSite);

        if (lData.get("loc_geo_id") != null) {
          LocationGeoposition geoposition = new LocationGeoposition();
          geoposition.setId(Integer.parseInt(lData.get("loc_geo_id")));
          geoposition.setLatitude(Double.parseDouble(lData.get("loc_geo_latitude")));
          geoposition.setLongitude(Double.parseDouble(lData.get("loc_geo_longitude")));
          location.setGeoPosition(geoposition);
        }

        locations.add(location);
      } else {
        // Other location
        OtherLocation location = new OtherLocation();

        location.setId(Integer.parseInt(lData.get("id")));
        location.setCode(lData.get("code"));
        location.setName(lData.get("name"));

        // Set the parent location
        if (lData.get("location_parent_id") != null) {
          Country country = new Country();
          country.setId(Integer.parseInt(lData.get("location_parent_id")));
          country.setName(lData.get("location_parent_name"));
          country.setCode(lData.get("location_parent_code"));
          location.setCountry(country);
        }

        LocationType type = new LocationType();
        type.setId(Integer.parseInt(lData.get("type_id")));
        type.setName(lData.get("type_name"));
        location.setType(type);

        if (lData.get("loc_geo_id") != null) {
          LocationGeoposition geoposition = new LocationGeoposition();
          geoposition.setId(Integer.parseInt(lData.get("loc_geo_id")));
          geoposition.setLatitude(Double.parseDouble(lData.get("loc_geo_latitude")));
          geoposition.setLongitude(Double.parseDouble(lData.get("loc_geo_longitude")));
          location.setGeoPosition(geoposition);
        }

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

  @Override
  public boolean removeProjectLocation(List<Location> projectLocations, int projectID) {
    boolean allRemoved = true, removed;
    allRemoved = locationDAO.removeProjectLocation(projectID);

    for (Location location : projectLocations) {
      if (location.isOtherLocation()) {
        OtherLocation otherLocation = (OtherLocation) location;
        if (otherLocation.getType().getId() == APConstants.LOCATION_TYPE_CCAFS_SITE) {
          continue;
        }

        removed = locationDAO.removeLocation(location.getId());
        allRemoved = (removed && allRemoved);
      }
    }

    return allRemoved;
  }

  private int saveLocation(OtherLocation location) {
    int locationID = -1;
    Map<String, String> locationData = new HashMap<>();

    if (location.getId() == -1) {
      locationData.put("id", null);
    } else {
      locationData.put("id", String.valueOf(location.getId()));
    }

    locationData.put("name", location.getName());
    locationData.put("code", location.getCode());

    if (location.getCountry() != null) {
      locationData.put("parent_id", String.valueOf(location.getCountry().getId()));
    } else {
      locationData.put("parent_id", null);
    }

    locationData.put("element_type_id", String.valueOf(location.getType().getId()));

    int geoPositionID = this.saveLocationGeoposition(location);
    // If the geoPosition was updated and the returned value was 0, then
    // we have to get the geopositionId brought with the object
    geoPositionID = (geoPositionID == 0) ? location.getGeoPosition().getId() : geoPositionID;
    if (geoPositionID == -1) {
      locationData.put("geoposition_id", null);
    } else {
      locationData.put("geoposition_id", String.valueOf(geoPositionID));
    }

    locationID = locationDAO.saveLocation(locationData);
    // If the location was updated and the returned value was 0, then
    // we have to get the locationId brought with the object
    locationID = (locationID == 0) ? location.getId() : locationID;
    return locationID;
  }

  private int saveLocationGeoposition(OtherLocation location) {
    int geoPositionID = -1;
    Map<String, String> geoPositionData = new HashMap<>();

    if (location.getGeoPosition() != null) {

      if (location.getGeoPosition().getId() != -1) {
        geoPositionData.put("id", String.valueOf(location.getGeoPosition().getId()));
      } else {
        geoPositionData.put("id", null);
      }

      geoPositionData.put("latitude", String.valueOf(location.getGeoPosition().getLatitude()));
      geoPositionData.put("longitude", String.valueOf(location.getGeoPosition().getLongitude()));

      geoPositionID = locationDAO.saveLocationGeoPosition(geoPositionData);
      if (geoPositionID == -1) {
        LOG.warn("There was a problem trying to save the geoposition of the location with id {}", location.getId());
      }
    }
    return geoPositionID;
  }

  @Override
  public boolean saveProjectLocation(List<Location> locations, int projectID, User user, String justification) {
    boolean saved = true;

    for (Location location : locations) {
      Map<String, String> locationData = new HashMap<>();
      locationData.put("project_id", String.valueOf(projectID));

      if (location.isRegion() || location.isCountry() || location.isClimateSmartVillage()) {
        locationData.put("loc_element_id", String.valueOf(location.getId()));
      } else {
        OtherLocation oLocation = (OtherLocation) location;
        int oLocationID = oLocation.getId();
        if (oLocation.getType().getId() != APConstants.LOCATION_TYPE_CCAFS_SITE) {
          oLocationID = this.saveLocation(oLocation);
        }
        locationData.put("loc_element_id", String.valueOf(oLocationID));
      }
      locationData.put("modified_by", String.valueOf(user.getId()));
      locationData.put("created_by", String.valueOf(user.getId()));
      locationData.put("modification_justification", justification);

      int recordSaved = locationDAO.saveProjectLocation(locationData);
      saved = saved && (recordSaved != -1);
    }


    return saved;
  }

  @Override
  public boolean updateProjectGlobal(int projectID, User user, String justification) {
    boolean updated = false;

    int recordUpdated = locationDAO.updateProjectGlobal(projectID, user, justification);
    updated = (recordUpdated != -1);
    return updated;
  }
}
