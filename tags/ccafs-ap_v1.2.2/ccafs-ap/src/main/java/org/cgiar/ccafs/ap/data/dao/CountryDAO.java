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

package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLCountryDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLCountryDAO.class)
public interface CountryDAO {

  /**
   * Get all the countries that belongs to the given region.
   * 
   * @param regionID - Region identifier
   * @return a list of maps with the information.
   */
  public List<Map<String, String>> getCountriesByRegion(String regionID);

  /**
   * Get all the countries from the DAO
   * 
   * @return a List of Maps with the information
   */
  public List<Map<String, String>> getCountriesList();

  /**
   * Get the country information of the given country identifier.
   * 
   * @param id - identifier
   * @return
   */
  public Map<String, String> getCountryInformation(String id);
}
