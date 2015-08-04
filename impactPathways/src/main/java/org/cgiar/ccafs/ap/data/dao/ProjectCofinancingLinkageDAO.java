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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLProjectCofinancingLinkageDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

@ImplementedBy(MySQLProjectCofinancingLinkageDAO.class)
public interface ProjectCofinancingLinkageDAO {

  /**
   * This method gets the basic information (id, title) of the projects that are linked to the project
   * identified by the value received by parameter.
   * 
   * @param projectID
   * @return a list of maps with the project information.
   */
  public List<Map<String, String>> getLinkedBilateralProjects(int projectID);

  /**
   * This method gets the basic information (id, title) of the projects that are linked to the project
   * identified by the value received by parameter.
   * 
   * @param projectID
   * @return a list of maps with the project information.
   */
  public List<Map<String, String>> getLinkedCoreProjects(int projectID);

  /**
   * This method remove from the database the link between the project identified by the first parameter and the
   * projects listed in the second parameter.
   * 
   * @param projectID
   * @param linkedProjects
   * @param userID
   * @param justification
   * @return true if all the core projects were removed successfully. False otherwise.
   */
  public boolean removeLinkedProjects(int projectID, List<Integer> linkedProjects, int userID, String justification);

  /**
   * This method saves into the database the bilateral projects linked to the core project identified by the value
   * received by parameter.
   * 
   * @param coreProjectID
   * @param listBilateralProjectsIDs - a list of bilateral projects' IDs
   * @param user - Identifier of the user who is making the change
   * @param justification
   * @return true if the information was saved successfully, false otherwise.
   */
  public boolean saveLinkedProjects(int coreProjectID, List<Integer> listBilateralProjectsIDs, int userID,
    String justification);
}
