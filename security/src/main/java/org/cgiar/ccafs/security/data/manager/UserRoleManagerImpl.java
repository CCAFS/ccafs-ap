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

package org.cgiar.ccafs.security.data.manager;

import org.cgiar.ccafs.security.data.dao.UserRoleDAO;
import org.cgiar.ccafs.security.data.model.UserRole;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal
 */

public class UserRoleManagerImpl {

  private UserRoleDAO userRoleDAO;

  @Inject
  public UserRoleManagerImpl(UserRoleDAO userRoleDAO) {
    this.userRoleDAO = userRoleDAO;
  }

  public List<UserRole> getUserRolesByUserID(String userID) {
    List<UserRole> roles = new ArrayList<>();
    List<Map<String, String>> userRoleData = userRoleDAO.getUserRolesByUserID(userID);

    for (Map<String, String> roleData : userRoleData) {
      UserRole userRole = new UserRole();
      userRole.setId(Integer.parseInt(roleData.get("id")));
      userRole.setName(roleData.get("name"));
      userRole.setAcronym(roleData.get("acronym"));
      userRole.setPermissions(userRoleDAO.getRolePermissions(roleData.get("id")));

      roles.add(userRole);
    }

    return roles;
  }
}
