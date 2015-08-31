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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLProjectRoleDAO;

import com.google.inject.ImplementedBy;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

@ImplementedBy(MySQLProjectRoleDAO.class)
public interface ProjectRoleDAO {

  /**
   * This method assign all the roles of project leader and project coordinator to the corresponding users based on the
   * information saved in the table project_partner_persons.
   * 
   * @param projectID - Project identifier
   * @return true if the query run successfully. False otherwise
   */
  public boolean addProjectRoles(int projectID);

  /**
   * This method removes all the roles that an user has for a certain project.
   * 
   * @param projectID - Project identifier
   * @param userID - user identifier
   * @return true if the roles were removed successfully, or if no roles were found. False otherwise.
   */
  public boolean removeProjectRoles(int projectID);
}
