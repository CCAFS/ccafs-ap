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

package org.cgiar.ccafs.security;


/**
 * @author Hernán David Carvajal
 */

public class SecurityContext extends BaseSecurityContext {

  /**
   * Verify if can add bilateral projects in the planning section
   * 
   * @return
   */
  public boolean canAddBilateralProject() {
    return hasPermission(Permission.PLANNING_BILATERAL_PROJECT_BUTTON);
  }

  /**
   * Verify if can add core projects in the planning section
   * 
   * @return
   */
  public boolean canAddCoreProject() {
    return hasPermission(Permission.PLANNING_CORE_PROJECT_BUTTON);
  }

  /**
   * Verify if can delete projects
   * 
   * @return
   */
  public boolean canDeleteProject() {
    return hasPermission(Permission.PLANNING_DELETE_PROJECT_BUTTON);
  }

  /**
   * Verify if can edit the end date of a project in the planning section
   * 
   * @return
   */
  public boolean canEditEndDate() {
    return hasPermission(Permission.PLANNING_PROJECT_END_DATE_UPDATE);
  }

  /**
   * Verify if can edit the management liaison person of a project in the planning section
   * 
   * @return
   */
  public boolean canEditManagementLiaison() {
    return hasPermission(Permission.PLANNING_MANAGEMENT_LIAISON_UPDATE);
  }

  public boolean canEditProjectFlagships() {
    return hasPermission(Permission.PLANNING_PROJECT_FLAGSHIPS_UPDATE);
  }

  /**
   * Verify if can edit the regions linked to the project in the planning section
   * 
   * @return
   */
  public boolean canEditProjectRegions() {
    return hasPermission(Permission.PLANNING_PROJECT_REGIONS_UPDATE);
  }

  /**
   * Verify if can edit the start date of a project in the planning section
   * 
   * @return
   */
  public boolean canEditStartDate() {
    return hasPermission(Permission.PLANNING_PROJECT_START_DATE_UPDATE);
  }

  /**
   * Verify if can upload a bilateral contract proposal in the planning section
   * 
   * @return
   */
  public boolean canUploadBilateralContract() {
    return hasPermission(Permission.PLANNING_PROJECT_BILATERAL_CONTRACT_UPDATE);
  }

  /**
   * Verify if can upload a project workplan in the planning section
   * 
   * @return
   */
  public boolean canUploadProjectWorkplan() {
    return hasPermission(Permission.PLANNING_PROJECT_WORKPLAN_UPDATE);
  }

  /**
   * Verify if the user has the administrator role
   * 
   * @return
   */
  public boolean isAdmin() {
    return hasRole(Role.ADMIN);
  }

  /**
   * Verify if the user has the activity leader role
   * 
   * @return
   */
  public boolean isAL() {
    return hasRole(Role.AL);
  }

  /**
   * Verify if the user has the Contact point role
   * 
   * @return
   */
  public boolean isCP() {
    return hasRole(Role.CP);
  }

  /**
   * Verify if the user has the coordinating unit role
   * 
   * @return
   */
  public boolean isCU() {
    return hasRole(Role.CU);
  }

  /**
   * Verify if the user has the FPL role
   * 
   * @return
   */
  public boolean isFPL() {
    return hasRole(Role.FPL);
  }

  /**
   * Verify if the user has the guest role
   * 
   * @return
   */
  public boolean isGuest() {
    return hasRole(Role.GUEST);
  }

  /**
   * Verify if the user has the Project leader role
   * 
   * @return
   */
  public boolean isPL() {
    return hasRole(Role.PL);
  }

  /**
   * Verify if the user has the RPL role
   * 
   * @return
   */
  public boolean isRPL() {
    return hasRole(Role.RPL);
  }
}
