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

import org.cgiar.ccafs.security.data.dao.mysql.MySQLUserRoleDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;


/**
 * @author Hernán David Carvajal
 */

@ImplementedBy(MySQLUserRoleDAO.class)
public interface UserRoleDAO {

  /**
   * This method gets the permissions that the user has assigned to
   * specific projects if any.
   * 
   * @param UserID
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getProjectUserRoles(String userID);

  /**
   * This method gets all the permissions assigned to the role
   * identified by the value received by parameter.
   * 
   * @param roleID
   * @return a list of strings with the permissions.
   */
  public List<String> getRolePermissions(String roleID);

  /**
   * This method gets all the roles that belong to the user
   * identified by the email received as parameter
   * 
   * @param userID
   * @return a list of maps with the permissions.
   */
  public List<Map<String, String>> getUserRolesByUserID(String userID);
}
