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

import org.cgiar.ccafs.security.data.dao.ProjectSpecificUserRoleDAO;
import org.cgiar.ccafs.security.data.dao.UserRoleDAO;
import org.cgiar.ccafs.security.data.model.ProjectUserRole;
import org.cgiar.ccafs.security.data.model.UserRole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


/**
 * This class gets the project specific roles for a user.
 * 
 * @author Héctor fabio Tobón R. - CIAT/CCAFS
 */

public class ProjectSpecificUserRoleManagerImpl {

  private ProjectSpecificUserRoleDAO projectUserRoleDAO;
  private UserRoleDAO userRoleDAO;

  @Inject
  public ProjectSpecificUserRoleManagerImpl(ProjectSpecificUserRoleDAO projectUserRoleDAO, UserRoleDAO userRoleDAO) {
    this.projectUserRoleDAO = projectUserRoleDAO;
    this.userRoleDAO = userRoleDAO;
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

  public List<ProjectUserRole> getProjectSpecificUserRoles(int userID) {
    List<ProjectUserRole> projectUserRoles = new ArrayList<>();
    List<Map<String, String>> projectUserRoleDataList = projectUserRoleDAO.getProjectSpecificUserRoles(userID);

    for (Map<String, String> projectUserRoleData : projectUserRoleDataList) {
      ProjectUserRole projectUserRole = new ProjectUserRole();
      projectUserRole.setId(Integer.parseInt(projectUserRoleData.get("id")));
      projectUserRole.setProjectID(Integer.parseInt(projectUserRoleData.get("project_id")));
      UserRole userRole = new UserRole();
      userRole.setId(Integer.parseInt(projectUserRoleData.get("role_id")));
      userRole.setName(projectUserRoleData.get("role_name"));
      userRole.setAcronym(projectUserRoleData.get("role_acronym"));
      userRole.setPermissions(userRoleDAO.getRolePermissions(projectUserRoleData.get("role_id")));
      projectUserRole.setUserRole(userRole);
      projectUserRoles.add(projectUserRole);
    }

    return projectUserRoles;
  }


}
