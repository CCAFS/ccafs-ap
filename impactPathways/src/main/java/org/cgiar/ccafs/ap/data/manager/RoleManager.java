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
package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.RoleManagerImpl;
import org.cgiar.ccafs.ap.data.model.Role;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(RoleManagerImpl.class)
public interface RoleManager {

  /**
   * This method removes a role assigned to a user.
   * 
   * @param user - The user to who the role is being removed.
   * @param role
   * @return true if the role was removed successfully. False otherwise.
   */
  public boolean deleteRole(User user, Role role);

  /**
   * This method gets all Roles information
   * 
   * @return a list of roles with the information, or an empty list if no information were found
   */
  public List<Role> getAllRoles();

  /**
   * This method get the role name according to its acronym
   * 
   * @param acronym - The Role Acronym
   * @return String with the role name or null if the acronym consulted not exist
   */
  public String getRoleNameByAcronym(String acronym);

  /**
   * This method saves into the database the role assigned to the user.
   * 
   * @param user - User who is been assigned the role
   * @param role
   * @return true if the role was assigned successfully. False otherwise.
   */
  public boolean saveRole(User user, Role role);
}
