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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLActivityBenchmarkSiteDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLActivityBenchmarkSiteDAO.class)
public interface ActivityBenchmarkSiteDAO {

  /**
   * Delete all the benchmark sites locations related with the activity given
   * from the DAO.
   * 
   * @param activityID - activity identifier
   * @return true if the data was successfully deleted. False otherwise
   */
  public boolean deleteActivityBenchmarkSites(int activityID);


  /**
   * Get all the benchmark sites related to the activity given
   * 
   * @param activityID - The activity identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getActivityBenchmarkSites(int activityID);

  /**
   * Save a benchmark sites location into the database.
   * 
   * @param benchmarkSiteID - The locations identifier
   * @param activityID - the activity identifier
   * @return true if the site was successfully saved. False otherwise
   */
  public boolean saveActivityBenchmarkSite(String benchmarkSiteID, int activityID);
}
