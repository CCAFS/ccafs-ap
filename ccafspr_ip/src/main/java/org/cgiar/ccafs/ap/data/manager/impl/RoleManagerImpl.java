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
package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.RoleDAO;
import org.cgiar.ccafs.ap.data.manager.RoleManager;
import org.cgiar.ccafs.ap.data.model.Role;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.Map;

import com.google.inject.Inject;


public class RoleManagerImpl implements RoleManager {

  private RoleDAO roleDAO;

  @Inject
  public RoleManagerImpl(RoleDAO roleDAO) {
    this.roleDAO = roleDAO;
  }

  @Override
  public Role getRole(User user) {
    Map<String, String> roleData = roleDAO.getRole(user.getId(), user.getCurrentInstitution().getId());
    if (!roleData.isEmpty()) {
      Role role = new Role();
      role.setId(Integer.parseInt(roleData.get("id")));
      role.setName(roleData.get("acronym"));
      role.setAcronym(roleData.get("acronym"));

      return role;
    }
    return null;
  }

}
