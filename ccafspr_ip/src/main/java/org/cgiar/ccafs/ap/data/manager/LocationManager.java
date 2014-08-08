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

package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.LocationManagerImpl;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Location;
import org.cgiar.ccafs.ap.data.model.Region;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andres Gallego B.
 */

@ImplementedBy(LocationManagerImpl.class)
public interface LocationManager {

  /**
   * This method returns all the locations related with the activity
   * identified by the value passed as parameter.
   * 
   * @param activityID - activity identifier
   * @return a list of Location objects with the information
   */
  public List<Location> getActivityLocations(int activityID);

  /**
   * This method return all the information of the countries
   * 
   * @return a list of all countries with the information or null if there is none.
   */
  public List<Country> getAllCountries();

  /**
   * This method return all the information of the regions
   * 
   * @return a list of all regions with the information or null if there is none.
   */
  public List<Region> getAllRegions();

  /**
   * This method return the information of a Country by a given Country ID
   * 
   * @param countryID - is the ID of a Country
   * @return a Country Object with the information.
   */
  public Country getCountry(int countryID);

  /**
   * This method finds a Country identified with the given code.
   * 
   * @param code must be in format ISO 3166-1 alpha-2 - lower case (eg. "co" for Colombia)
   * @return a Country object representing the country found, or null if the code doesn't exist.
   */
  public Country getCountryByCode(String code);

  /**
   * This method returns all the countries in which at least
   * one institution is located
   * 
   * @return a list of countries objects.
   */
  public List<Country> getInstitutionCountries();

  /**
   * Get a Location identified with the given type id and location id.
   * 
   * @param typeID is an integer that represents the id of the location element type .
   * @param locationID is an integer that represents the id of the location to search
   * @return an Location object or null if the id does not exist in the database.
   */
  public Location getLocation(int typeID, int locationID);

  /**
   * This method return the information of a Region by a given Region ID
   * 
   * @param regionID - is the ID of a Region
   * @return a Region Object with the information.
   */
  public Region getRegion(int regionID);

}