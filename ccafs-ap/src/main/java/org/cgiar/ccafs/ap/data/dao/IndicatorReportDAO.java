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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLIndicatorReportDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLIndicatorReportDAO.class)
public interface IndicatorReportDAO {

  /**
   * This method return the list of indicator's reports made
   * by the activity leader corresponding to the given logframe.
   * 
   * @param activityLeaderId - Activity leader identifier
   * @param year
   * @return A list of maps with the information
   */
  public List<Map<String, String>> getIndicatorReports(int activityLeaderId, int year);

  /**
   * This method save the Indicator's report made by the leader
   * corresponding to the logframe.
   * 
   * @param indicatorsReport - Data with the report about the indicator
   * @param activityLeaderId - Activity leader identifier
   * @param year
   * @return true if the information was successfully saved, false otherwise.
   */
  public boolean saveIndicatorReport(Map<String, String> indicatorReportData, int activityLeaderId, int year);
}
