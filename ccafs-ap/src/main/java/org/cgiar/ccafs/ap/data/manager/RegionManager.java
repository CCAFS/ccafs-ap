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

import org.cgiar.ccafs.ap.data.manager.impl.RegionManagerImpl;
import org.cgiar.ccafs.ap.data.model.Region;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(RegionManagerImpl.class)
public interface RegionManager {

  /**
   * Get a list with all the regions.
   * 
   * @return an array of Region objects
   */
  public Region[] getRegionList();

  /**
   * Get a list of region objects corresponding to the given array of ids
   * 
   * @param ids - Array of region identifiers
   * @return a list of Region objects
   */
  public List<Region> getRegionList(String[] ids);
}
