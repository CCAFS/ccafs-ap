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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLMilestoneDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLMilestoneDAO.class)
public interface MilestoneDAO {

  /**
   * Get the milestone data whit a given identifier.
   * 
   * @param milestoneID - milestone identifier.
   * @return the Map with the milestone data.
   */
  Map<String, String> getMilestone(int milestoneID);

  /**
   * Get the complete list of milestones that belongs to the given logframe
   * 
   * @param logframeID - Logframe identifier
   * @return a list of maps with the information
   */
  List<Map<String, String>> getMilestoneList(int logframeID);

}
