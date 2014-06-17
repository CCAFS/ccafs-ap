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

import org.cgiar.ccafs.ap.data.manager.impl.BenchmarkSiteManagerImpl;
import org.cgiar.ccafs.ap.data.model.BenchmarkSite;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(BenchmarkSiteManagerImpl.class)
public interface BenchmarkSiteManager {

  /**
   * Get all the benchmark sites that are actives from the DAO
   * 
   * @return a list of BenchmarkSite objects.
   */

  public BenchmarkSite[] getActiveBenchmarkSiteList();

  /**
   * Get all the active benchmark sites that belongs to the given country
   * 
   * @param countryID - Country identifier
   * @return a list of BenchmarkSite objects with the information
   */
  public BenchmarkSite[] getActiveBenchmarkSitesByCountry(String countryID);

  /**
   * Get all the active benchmark sites that belongs to the given region
   * 
   * @param regionID - Region identifier
   * @return a list of BenchmarkSite objects with the information
   */
  public BenchmarkSite[] getActiveBenchmarkSitesByRegion(String regionID);

  /**
   * Get a list of benchmarkSite objects corresponding to the given array of ids
   * 
   * @param ids - Array of benchmarkSites identifiers
   * @return a list of BenchmarkSite objects
   */
  public List<BenchmarkSite> getBenchmarkSiteList(String[] ids);
}
