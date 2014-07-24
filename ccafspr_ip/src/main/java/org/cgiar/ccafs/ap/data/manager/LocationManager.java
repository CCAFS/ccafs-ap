/*
 * ****************************************************************
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
 * ***************************************************************
 */
package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.LocationManagerImpl;
import org.cgiar.ccafs.ap.data.model.Location;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andres Gallego B.
 */

@ImplementedBy(LocationManagerImpl.class)
public interface LocationManager {

  /**
   * Get a Location identified with the given type id and location id.
   * 
   * @param typeID is an integer that represents the id of the location element type .
   * @param locationID is an integer that represents the id of the location to search
   * @return an Location object or null if the id does not exist in the database.
   */
  public Location getLocation(int typeID, int locationID);

  /**
   * Return all the locations with the give type Id.
   * 
   * @param typeID is an integer that represents the id of the location element type .
   * @return an List of Locations object or an empty list if not exist in the database.
   */
  public List<Location> getLocationsByType(int typeID);

}