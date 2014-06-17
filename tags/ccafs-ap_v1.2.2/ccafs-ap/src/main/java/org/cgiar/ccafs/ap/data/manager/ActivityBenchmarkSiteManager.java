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

import org.cgiar.ccafs.ap.data.manager.impl.ActivityBenchmarkSiteManagerImpl;
import org.cgiar.ccafs.ap.data.model.BenchmarkSite;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ActivityBenchmarkSiteManagerImpl.class)
public interface ActivityBenchmarkSiteManager {

  /**
   * Delete all the activity benchmark site locations related with the
   * activity given.
   * 
   * @param activityID - Activity identifier
   * @return true if the locations were deleted successfully. False otherwise.
   */
  public boolean deleteActivityBenchmarkSites(int activityID);

  /**
   * Get all the benchmark sites related to the activity given
   * 
   * @param activityID - the activity identifier
   * @return a list of BenchmarkSiteLocation objects with the information
   */
  public List<BenchmarkSite> getActivityBenchmarkSites(int activityID);

  /**
   * Save a list of benchmark sites locations into the database.
   * 
   * @param benchmarkSites - The locations to save
   * @param activityID - the activity identifier
   * @return true if ALL the sites was successfully saved. False otherwise
   */
  public boolean saveActivityBenchmarkSites(List<BenchmarkSite> benchmarkSites, int activityID);
}
