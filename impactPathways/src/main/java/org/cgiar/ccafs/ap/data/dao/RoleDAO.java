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
 * ***************************************************************
 */
package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLRoleDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andrés Gallego.
 */
@ImplementedBy(MySQLRoleDAO.class)
public interface RoleDAO {

  /**
   * This method removes a role assigned to a user.
   * 
   * @param userID - user identifier
   * @param roleID - role identifier
   * @return true if the role was removed successfully. False otherwise.
   */
  public boolean deleteRole(int userID, int roleID);

  /**
   * This method gets all the Roles
   * 
   * @return a List of Map with all the information of the roles. If no information were found, return an empty list
   */
  public List<Map<String, String>> getAllRoles();

  /**
   * This method, query the role name according to its acronym
   * 
   * @param acronym - The Role Acronym
   * @return the map with the role information
   */
  public Map<String, String> getRoleByAcronym(String acronym);

  /**
   * This method saves into the database the role assigned to the user.
   * 
   * @param userID - User identifier
   * @param roleID - role identifier
   * @return true if the role was assigned successfully. False otherwise.
   */
  public boolean saveRole(int userID, int roleID);
}
