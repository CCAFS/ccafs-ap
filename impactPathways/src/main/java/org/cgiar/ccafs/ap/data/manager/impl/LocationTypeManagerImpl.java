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

import org.cgiar.ccafs.ap.data.dao.LocationTypeDAO;
import org.cgiar.ccafs.ap.data.manager.LocationTypeManager;
import org.cgiar.ccafs.ap.data.model.LocationType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal
 */

public class LocationTypeManagerImpl implements LocationTypeManager {

  private LocationTypeDAO locationTypeDAO;

  @Inject
  public LocationTypeManagerImpl(LocationTypeDAO locationTypeDAO) {
    this.locationTypeDAO = locationTypeDAO;
  }

  @Override
  public List<LocationType> getLocationTypes() {
    List<LocationType> locationTypes = new ArrayList<>();
    List<Map<String, String>> locationTypesData = locationTypeDAO.getLocationTypes();

    for (Map<String, String> ltData : locationTypesData) {
      LocationType type = new LocationType();
      type.setId(Integer.parseInt(ltData.get("id")));
      type.setName(ltData.get("name"));

      locationTypes.add(type);
    }

    return locationTypes;
  }
}
