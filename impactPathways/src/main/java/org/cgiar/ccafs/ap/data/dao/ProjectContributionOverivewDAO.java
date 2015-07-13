/*****************************************************************
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
 *****************************************************************/

package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLProjectContributionOverivewDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

@ImplementedBy(MySQLProjectContributionOverivewDAO.class)
public interface ProjectContributionOverivewDAO {

  /**
   * This method deletes a contribution overview made by a project to some output.
   * 
   * @param outputOverviewID - output overview identifier
   * @param userID - The user identifier who is making the change
   * @param justification - Justification of the change
   * @return True if the relation was deleted successfully. False otherwise.
   */
  public boolean deleteProjectContributionOverview(int outputOverviewID, int userID, String justification);

  /**
   * This method returns the overview made for each output linked to the project identified by the value received
   * by parameter.
   * 
   * @param projectID - Project Identifier
   * @return a list of maps with the information.
   */
  public List<Map<String, String>> getProjectContributionOverviews(int projectID);
}
