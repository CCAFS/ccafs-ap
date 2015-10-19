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

package org.cgiar.ccafs.security.data.dao;

import org.cgiar.ccafs.security.data.dao.mysql.MySQLProjectSpecificUserRoleDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;


/**
 * This class will get data from the table project_roles which saves specific roles for specific projects.
 * 
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

@ImplementedBy(MySQLProjectSpecificUserRoleDAO.class)
public interface ProjectSpecificUserRoleDAO {


  /**
   * This method gets the specific project roles from a given user identifier.
   * 
   * @param userID - user identifier
   * @return a list of maps with the project roles and permissions.
   */
  public List<Map<String, String>> getProjectSpecificUserRoles(int userID);
}
