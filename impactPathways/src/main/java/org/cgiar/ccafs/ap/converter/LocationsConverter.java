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
package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.Location;
import org.cgiar.ccafs.ap.data.model.LocationGeoposition;
import org.cgiar.ccafs.ap.data.model.LocationType;
import org.cgiar.ccafs.ap.data.model.OtherLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LocationsConverter extends StrutsTypeConverter {

  // LOG
  private static final Logger LOG = LoggerFactory.getLogger(LocationsConverter.class);

  // Manager
  private LocationManager locationManager;

  @Inject
  public LocationsConverter(LocationManager locationManager) {
    this.locationManager = locationManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == List.class) {
      List<String> existingLocations = new ArrayList<>();
      List<OtherLocation> newLocations = new ArrayList<>();
      List<Location> allLocations = new ArrayList<>();

      for (String id : values) {
        if (!id.contains("|s|")) {
          // If the location already exists we are going to get its information using the location manager.
          existingLocations.add(id);
        } else {

          String[] tokens = id.split("\\|s\\|");
          String typeID = tokens[0];
          String latitude = tokens[1];
          String longitude = tokens[2];
          String name = tokens[3];
          String locationID = tokens[4];

          if (typeID.isEmpty() || latitude.isEmpty() || longitude.isEmpty() || name.isEmpty()) {
            continue;
          }
          OtherLocation newLocation = new OtherLocation();
          newLocation.setId(Integer.parseInt(locationID));
          newLocation.setName(name);

          LocationGeoposition geoposition = new LocationGeoposition();
          geoposition.setId(-1);
          geoposition.setLatitude(Double.parseDouble(latitude));
          geoposition.setLongitude(Double.parseDouble(longitude));
          newLocation.setGeoPosition(geoposition);

          LocationType type = new LocationType(Integer.parseInt(typeID));
          newLocation.setType(type);

          newLocations.add(newLocation);
        }
      }


      allLocations.addAll(newLocations);

      if (!existingLocations.isEmpty()) {
        List<Location> temp =
          locationManager.getLocationsByIDs(existingLocations.toArray(new String[existingLocations.size()]));
        allLocations.addAll(temp);
      }
      return allLocations;
    }
    return null;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public String convertToString(Map context, Object o) {
    List<IPIndicator> indicatorArray = (List<IPIndicator>) o;
    ArrayList<String> temp = new ArrayList<>();
    for (IPIndicator indicator : indicatorArray) {
      temp.add(indicator.getId() + "");
    }

    return temp.toString();
  }
}
