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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLTLOutputSummaryDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLTLOutputSummaryDAO.class)
public interface TLOutputSummaryDAO {

  /**
   * Get a list of Summaries by Output that belong to a specific leader and a specific logframe.
   * 
   * @param leader - Leader identifier.
   * @param logframe - Logframe identifier.
   * @return a List of Maps with all the summary by outputs information.
   */
  public List<Map<String, Object>> getTLOutputSummaries(int leader_id, int logframe_id);

  /**
   * Save a list of Summaries by Output.
   * 
   * @param outputs - List of Maps with the summaries by output information.
   * @return true if all the outputs were successfully saved. False if any problem appear.
   */
  public boolean saveTLOutputSummaries(List<Map<String, Object>> outputs);
}
