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

package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.CountryManagerImpl;
import org.cgiar.ccafs.ap.data.model.Country;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(CountryManagerImpl.class)
public interface CountryManager {

  /**
   * Get all the countries that belongs to the region given
   * 
   * @param regionID - Region identifier
   * @return a list of Country objects with the information.
   */
  public Country[] getCountriesByRegion(String regionID);

  /**
   * Get the country object corresponding to the given id
   * 
   * @param id - The country identifier
   * @return An object with the country information or null if no data found
   */
  public Country getCountry(String id);


  /**
   * Get the countries list.
   * 
   * @return an array of Country objects or null if no data found
   */
  public Country[] getCountryList();

  /**
   * Get a list of countries object corresponding to the given array of ids
   * 
   * @param ids - Array of country identifiers
   * @return a list of Country objects
   */
  public List<Country> getCountryList(String[] ids);
}
