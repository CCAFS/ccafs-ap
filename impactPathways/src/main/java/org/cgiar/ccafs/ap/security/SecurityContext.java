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

package org.cgiar.ccafs.ap.security;

import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.security.BaseSecurityContext;
import org.cgiar.ccafs.security.Permission;
import org.cgiar.ccafs.security.data.model.Role;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class SecurityContext extends BaseSecurityContext {

  private User user;

  @Inject
  public SecurityContext(User user) {
    this.user = user;
  }

  /**
   * Verify if can add bilateral projects in the planning section
   * 
   * @return
   */
  public boolean canAddBilateralProject() {
    return this.hasPermission(Permission.PLANNING_BILATERAL_PROJECT_BUTTON);
  }

  /**
   * Verify if can add co-founded projects in the planning section
   * 
   * @return
   */
  public boolean canAddCofoundedProject() {
    return this.hasPermission(Permission.PLANNING_COFUNDED_PROJECT_BUTTON);
  }

  /**
   * Verify if can add core projects in the planning section
   * 
   * @return
   */
  public boolean canAddCoreProject() {
    return this.hasPermission(Permission.PLANNING_CORE_PROJECT_BUTTON);
  }

  /**
   * Verify if can upload a project workplan in the planning section
   * 
   * @return
   */
  public boolean canAllowProjectWorkplanUpload(int projectID) {
    return this
      .hasPermission(Permission.PLANNING_PROJECT_WORKPLAN_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if can delete projects
   * 
   * @return
   */
  // TODO Fix this method for specific project
  public boolean canDeleteProject() {
    return this.hasPermission(Permission.PLANNING_DELETE_PROJECT_BUTTON);
  }

  /**
   * Verify if the project activities description can be updated
   * 
   * @return
   */
  public boolean canEditActivityDescription(int projectID) {
    return this.hasPermission(
      Permission.PLANNING_PROJECT_ACTIVITIES_INFO_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if the project activity leader can be updated
   * 
   * @return
   */
  public boolean canEditActivityLeader(int projectID) {
    return this.hasPermission(
      Permission.PLANNING_PROJECT_ACTIVITIES_LEADER_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if the end date of a project in the planning section can be edited
   * 
   * @return
   */
  public boolean canEditEndDate(int projectID) {
    return this
      .hasPermission(Permission.PLANNING_PROJECT_END_DATE_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if the end date of an activity in the planning section can be edited
   * 
   * @return
   */
  public boolean canEditEndDateActivities(int projectID) {
    return this.hasPermission(
      Permission.PLANNING_PROJECT_ACTIVITIES_END_DATE_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if the lead organization of an activity in the planning section can be edited
   * 
   * @return
   */
  public boolean canEditLeadOrganization(int projectID) {
    return this.hasPermission(Permission.PLANNING_PROJECT_ACTIVITIES_LEAD_ORGANIZATION_UPDATE.replace("projects:",
      "projects:" + projectID + ":"));
  }

  /**
   * Verify if can edit the management liaison person of a project in the planning section
   * 
   * @return
   */
  public boolean canEditManagementLiaison(int projectID) {
    return this
      .hasPermission(Permission.PLANNING_MANAGEMENT_LIAISON_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  public boolean canEditProjectFlagships(int projectID) {
    return this
      .hasPermission(Permission.PLANNING_PROJECT_FLAGSHIPS_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  public boolean canEditProjectPlanningSection(String sectionName, int projectID) {
    String permission = "planning:projects:" + projectID + ":" + sectionName + ":update";
    return this.hasPermission(permission);
  }

  /**
   * Verify if can edit the regions linked to the project in the planning section
   * 
   * @return
   */
  public boolean canEditProjectRegions(int projectID) {
    return this
      .hasPermission(Permission.PLANNING_PROJECT_REGIONS_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if the start date of a project in the planning section can be edited
   * 
   * @return
   */
  public boolean canEditStartDate(int projectID) {
    return this
      .hasPermission(Permission.PLANNING_PROJECT_START_DATE_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if the end date of an activity in the planning section can be edited
   * 
   * @return
   */
  public boolean canEditStartDateActivities(int projectID) {
    return this.hasPermission(
      Permission.PLANNING_PROJECT_ACTIVITIES_START_DATE_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if can submit the project.
   * 
   * @param projectID
   * @return
   */
  public boolean canSubmitProject(int projectID) {
    return this.hasPermission(Permission.PLANNING_SUBMIT_BUTTON.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if can update the annual W3/Bilateral budget in project budgets section
   * 
   * @return
   */
  public boolean canUpdateAnnualBilateralBudget(int projectID) {
    return this.hasPermission(
      Permission.PLANNING_PROJECT_ANNUAL_BUDGET_W3BILATERAL_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if can update the annual W1/W2 budget in project budgets section
   * 
   * @return
   */
  public boolean canUpdateAnnualW1W2Budget(int projectID) {
    return this.hasPermission(
      Permission.PLANNING_PROJECT_BUDGET_ANNUAL_W1W2_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if Can update the project cordinatir of a project
   * 
   * @return
   */
  public boolean canUpdatePartnerCordinator(int projectID) {
    return this.hasPermission(
      Permission.PLANNING_PROJECT_PARTNER_CORDINATOR_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if Can update the project leader of a project
   * 
   * @return
   */
  public boolean canUpdatePartnerLeader(int projectID) {

    return this.hasPermission(
      Permission.PLANNING_PROJECT_PARTNER_LEADER_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if Can update the PPA partners of a project
   * 
   * @return
   */
  public boolean canUpdatePPAPartners(int projectID) {
    return this.hasPermission(
      Permission.PLANNING_PROJECT_PARTNER_PPA_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if can update the project activities section
   * 
   * @return
   */
  public boolean canUpdateProjectActivities(int projectID) {
    return this.hasPermission(
      Permission.PLANNING_PROJECT_ACTIVITIES_LIST_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if can update the project budget section
   * 
   * @return
   */
  public boolean canUpdateProjectBudget(int projectID) {
    return this
      .hasPermission(Permission.PLANNING_PROJECT_BUDGET_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if can update the project budget section
   * 
   * @return
   */
  public boolean canUpdateProjectBudgetByMOG(int projectID) {
    return this.hasPermission(
      Permission.PLANNING_PROJECT_BUDGET_BY_MOG_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if can update the project CCAFS outcomes section
   * 
   * @return
   */
  public boolean canUpdateProjectCCAFSOutcomes(int projectID) {
    return this.hasPermission(
      Permission.PLANNING_PROJECT_CCAFS_OUTCOMES_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if can update the project deliverables section
   * 
   * @return
   */
  public boolean canUpdateProjectDeliverables(int projectID) {
    return this.hasPermission(
      Permission.PLANNING_PROJECT_DELIVERABLE_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if can update the deliverables list section
   * 
   * @return
   */
  public boolean canUpdateProjectDeliverablesList(int projectID) {
    return this.hasPermission(
      Permission.PLANNING_PROJECT_DELIVERABLES__LIST_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if can update the project description section
   * 
   * @return
   */
  public boolean canUpdateProjectDescription(int projectID) {
    return this
      .hasPermission(Permission.PLANNING_PROJECT_INFO_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if can update the project highlights section
   * 
   * @return
   */
  public boolean canUpdateProjectHighlights(int projectID) {
    return this
      .hasPermission(Permission.REPORTING_PROJECT_HIGHLIGHT_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if can update the project locations section
   * 
   * @return
   */
  public boolean canUpdateProjectLocations(int projectID) {
    return this
      .hasPermission(Permission.PLANNING_PROJECT_LOCATIONS_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if can update the project outcomes - Other contributions section
   * 
   * @return
   */
  public boolean canUpdateProjectOtherContributions(int projectID) {
    return this.hasPermission(
      Permission.PLANNING_PROJECT_OTHER_CONTRIBUTIONS_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if can update the project outcomes section
   * 
   * @return
   */
  public boolean canUpdateProjectOutcomes(int projectID) {
    return this
      .hasPermission(Permission.PLANNING_PROJECT_OUTCOMES_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if can update the project overview by MOGs section
   * 
   * @return
   */
  public boolean canUpdateProjectOverviewMOGs(int projectID) {
    return this
      .hasPermission(Permission.PLANNING_PROJECT_OUTPUTS_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if can update the project partners section for the project identified by the value received by paramter.
   * 
   * @param projectID - project identifier
   * @return
   */
  public boolean canUpdateProjectPartners(int projectID) {
    return this
      .hasPermission(Permission.PLANNING_PROJECT_PARTNER_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }

  /**
   * Verify if can upload a bilateral contract proposal in the planning section
   * 
   * @return
   */
  public boolean canUploadBilateralContract(int projectID) {
    return this.hasPermission(
      Permission.PLANNING_PROJECT_BILATERAL_CONTRACT_UPDATE.replace("projects:", "projects:" + projectID + ":"));
  }


  /**
   * Verify if the user has the administrator role
   * 
   * @return
   */
  public boolean isAdmin() {
    return this.hasRole(Role.Admin);
  }

  /**
   * Verify if the user has the activity leader role
   * 
   * @return
   */
  public boolean isAL() {
    return this.hasRole(Role.AL);
  }

  /**
   * Verify if the user has the Contact point role
   * 
   * @return
   */
  public boolean isCP() {
    return this.hasRole(Role.CP);
  }

  /**
   * Verify if the user has the coordinating unit role
   * 
   * @return
   */
  public boolean isCU() {
    return this.hasRole(Role.CU);
  }

  /**
   * Verify if the user has the FPL role
   * 
   * @return
   */
  public boolean isFPL() {
    return this.hasRole(Role.FPL);
  }

  /**
   * Verify if the user has the guest role
   * 
   * @return
   */
  public boolean isGuest() {
    return this.hasRole(Role.G);
  }

  /**
   * Verify if the user has the FPL role
   * 
   * @return
   */
  public boolean isML() {
    return this.hasRole(Role.ML);
  }

  /**
   * Verify if the user has the Project coordinator role
   * 
   * @return
   */
  public boolean isPC() {
    return this.hasRole(Role.PC);
  }

  /**
   * Verify if the user has the Project leader role
   * 
   * @return
   */
  public boolean isPL() {
    return this.hasRole(Role.PL);
  }


  /**
   * Verify if the user has the RPL role
   * 
   * @return
   */
  public boolean isRPL() {
    return this.hasRole(Role.RPL);
  }
}
