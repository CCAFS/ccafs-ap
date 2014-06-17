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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLOutputSummaryDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLOutputSummaryDAO.class)
public interface OutputSummaryDAO {

  /**
   * Get a list with all the output summaries that belongs to the activity
   * leader which are related to the logframeId
   * 
   * @param activityLeaderId the activity leader identifier
   * @param logframeId the logframe identifier
   * @return a list of maps with all the information
   */
  public List<Map<String, String>> getOutputSummariesList(int activityLeaderId, int logframeId);

  /**
   * Save all the outputs summary into the database
   * 
   * @param outputsSummaryData - List of maps with the information of each summary by output to be added
   * @return true if all the information was successfully saved. False otherwise
   */
  public boolean saveOutputsSummaryList(List<Map<String, Object>> outputsSummaryData);

  /**
   * Update all the outputs summary into the database
   * 
   * @param outputsSummaryData - List of maps with the information of each summary by output to be updated
   * @return true if all the information was successfully saved. False otherwise
   */
  public boolean updateOutputsSummaryList(List<Map<String, Object>> outputsSummaryData);
}
