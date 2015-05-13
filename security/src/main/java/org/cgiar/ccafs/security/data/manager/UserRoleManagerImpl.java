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

  public UserRole getUserRolesByEmail(String email) {
    UserRole userRole = new UserRole();
    Map<String, String> userRoleData = userRoleDAO.getUserRolesByEmail(email);

    userRole.setId(Integer.parseInt(userRoleData.get("id")));
    userRole.setRoleName(userRoleData.get("role"));

    return userRole;
  }
}
