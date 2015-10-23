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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class UserRoleManagerImpl {

  private UserRoleDAO userRoleDAO;

  @Inject
  public UserRoleManagerImpl(UserRoleDAO userRoleDAO) {
    this.userRoleDAO = userRoleDAO;
  }

  public Map<String, UserRole> getContactPointProjects(int userID) {
    List<Map<String, String>> userRoleData = userRoleDAO.getContactPointProjects(userID);
    return this.getData(userRoleData);
  }

  /**
   * This method gets all the permissions assigned to the user identified by userID over specific projects if any.
   * 
   * @param userID
   * @return a Map <projectID, UserRole> that specifies the role assigned for the project identified by the key value.
   */
  private Map<String, UserRole> getData(List<Map<String, String>> userRoleData) {
    Map<String, UserRole> projectRoles = new HashMap<>();

    for (Map<String, String> roleData : userRoleData) {

      UserRole userRole = new UserRole();
      userRole.setId(Integer.parseInt(roleData.get("id")));
      userRole.setName(roleData.get("name"));
      userRole.setAcronym(roleData.get("acronym"));
      userRole.setPermissions(userRoleDAO.getRolePermissions(roleData.get("id")));

      projectRoles.put(roleData.get("project_id"), userRole);
    }

    return projectRoles;
  }

  public Map<String, UserRole> getManagementLiaisonProjects(int userID) {
    List<Map<String, String>> userRoleData = userRoleDAO.getManagementLiaisonProjects(userID);
    return this.getData(userRoleData);
  }


  public Map<String, UserRole> getProjectLeaderProjects(int userID) {
    List<Map<String, String>> userRoleData = userRoleDAO.getProjectLeaderProjects(userID);
    return this.getData(userRoleData);
  }

  /**
   * This method get a specific role from the database.
   * 
   * @param roleID is some role identifier.
   * @return a UserRole object with the information populated.
   */
  public UserRole getUserRole(int roleID) {
    UserRole role = new UserRole();
    Map<String, String> roleData = userRoleDAO.getUserRole(roleID);
    if (roleData.size() > 0) {
      role.setId(Integer.parseInt(roleData.get("id")));
      role.setName(roleData.get("name"));
      role.setAcronym(roleData.get("acronym"));
      role.setPermissions(userRoleDAO.getRolePermissions(roleData.get("id")));
      return role;
    }
    return null;
  }

  /**
   * This method gets all the roles from a given user.
   * 
   * @param userID is a user identifier.
   * @return a List of UserRole objects with the information requested, an empty list if no role was found for the user
   *         or null if some error occurred.
   */
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
