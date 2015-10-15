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

package org.cgiar.ccafs.security.data.model;

/**
 * This class will save all the specific project roles for a particular user.
 * 
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class ProjectUserRole {

  private int id;
  private UserRole userRole; // Here are the permissions.
  private int projectID;

  public int getId() {
    return id;
  }

  public int getProjectID() {
    return projectID;
  }

  public UserRole getUserRole() {
    return userRole;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

  public void setUserRole(UserRole userRole) {
    this.userRole = userRole;
  }

}
