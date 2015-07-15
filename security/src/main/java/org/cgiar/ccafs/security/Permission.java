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

public class Permission {

  // Permission settings

  /**
   * FULL PRIVILEGES
   */
  public static final String FULL_PRIVILEGES = "*";

  /*
   * --------------------------------------------------------------
   * PERMISSIONS APPLIED TO ALL THE PLANNING SECTION
   * --------------------------------------------------------------
   */

  /**
   * Can update all the planning contents
   */
  public static final String PLANNING_ALL = "planning:*";

  // ---------- Project list ---------------------------------

  /**
   * Can use all the functions in the planning projects list
   */
  public static final String PLANNING_PROJECT_LIST_ALL = "planning:projects:projectList:*";

  /**
   * Can use the "add core project" button in the planning projects list section
   */
  public static final String PLANNING_CORE_PROJECT_BUTTON = "planning:projects:projectList:coreProjectButton:*";

  /**
   * Can use the "add bilateral project" button in the planning projects list section
   */
  public static final String PLANNING_BILATERAL_PROJECT_BUTTON =
    "planning:projects:projectList:bilateralProjectButton:*";

  /**
   * Can use the "Submit" button in the planning section
   */
  public static final String PLANNING_SUBMIT_BUTTON = "planning:projects:projectList:submitButton:*";

  /**
   * Can use the "Delete project" button in the planning section
   */
  public static final String PLANNING_DELETE_PROJECT_BUTTON = "planning:projects:projectList:deleteProjectButton:*";

  // --------------------------- End of Project list ---------------------------------

  // --------------------------- Project Description ---------------------------------

  /**
   * Can update the planning project information section
   */
  public static final String PLANNING_PROJECT_INFO_ALL = "planning:projects:description:*";

  /**
   * Can update the planning project information section
   */
  public static final String PLANNING_PROJECT_INFO_UPDATE = "planning:projects:description:update";

  /**
   * Can update the management liaison of a project in the planning section
   */
  public static final String PLANNING_MANAGEMENT_LIAISON_UPDATE = "planning:projects:description:managementLiaison:*";

  /**
   * Can update the start date of a project in the planning section
   */
  public static final String PLANNING_PROJECT_START_DATE_UPDATE = "planning:projects:description:startDate:*";

  /**
   * Can update the end date of a project in the planning section
   */
  public static final String PLANNING_PROJECT_END_DATE_UPDATE = "planning:projects:description::endDate:*";

  /**
   * Can upload a project work plan for a project in the planning section
   */
  public static final String PLANNING_PROJECT_WORKPLAN_UPDATE = "planning:projects:description:workplan:*";

  /**
   * Can upload a bilateral contract proposal for a project in the planning section
   */
  public static final String PLANNING_PROJECT_BILATERAL_CONTRACT_UPDATE =
    "planning:projects:description:bilateralContract:*";

  /**
   * Can update the flagships linked to a project in the planning section
   */
  public static final String PLANNING_PROJECT_FLAGSHIPS_UPDATE = "planning:projects:description:flagships:*";

  /**
   * Can update the end date of a project in the planning section
   */
  public static final String PLANNING_PROJECT_REGIONS_UPDATE = "planning:projects:description:regions:*";

  // --------------------------- End of project Description ---------------------------------

  // --------------------------- Project Partners ---------------------------------

  /**
   * Can update the planning project Lead partner section
   */
  public static final String PLANNING_PROJECT_LEAD_PARTNER_UPDATE = "planning:projects:partnerLead:update";

  /**
   * Can update the planning project PPA partners section
   */
  public static final String PLANNING_PROJECT_PPA_PARTNER_UPDATE = "planning:projects:ppaPartners:update";

  /**
   * Can update the planning project partners section
   */
  public static final String PLANNING_PROJECT_PARTNER_UPDATE = "planning:projects:partners:update";

  // --------------------------- End of Project Partners ---------------------------------

  // --------------------------- Project Location ---------------------------------

  /**
   * Can update the planning project locations section
   */
  public static final String PLANNING_PROJECT_LOCATIONS_UPDATE = "planning:projects:locations:update";

  // --------------------------- End of project Location ---------------------------------

  // --------------------------- Project outcomes ---------------------------------

  /**
   * Can update the planning project outcomes section
   */
  public static final String PLANNING_PROJECT_OUTCOMES_UPDATE = "planning:projects:outcomes:update";

  /**
   * Can update the planning project CCAFS outcomes section
   */
  public static final String PLANNING_PROJECT_CCAFS_OUTCOMES_UPDATE = "planning:projects:ccafsOutcomes:update";

  /**
   * Can update the planning project ip other contributions section
   */
  public static final String PLANNING_PROJECT_OTHER_CONTRIBUTIONS_UPDATE =
    "planning:projects:otherContributions:update";

  // --------------------------- End of Project outcomes ---------------------------------

  // --------------------------- Project outputs ---------------------------------

  /**
   * Can update the planning project outputs section
   */
  public static final String PLANNING_PROJECT_OUTPUTS_UPDATE = "planning:projects:outputs:update";

  /**
   * Can update the planning project deliverables list section
   */
  public static final String PLANNING_PROJECT_DELIVERABLES__LIST_UPDATE = "planning:projects:deliverablesList:update";

  /**
   * Can update the planning project deliverable section
   */
  public static final String PLANNING_PROJECT_DELIVERABLE_UPDATE = "planning:projects:deliverable:update";

  // --------------------------- End of Project outputs ---------------------------------

  // --------------------------- Project activities list---------------------------------

  /**
   * Can update the planning project activities list section
   */
  public static final String PLANNING_PROJECT_ACTIVITIES_LIST_UPDATE = "planning:projects:activities:update";

  // --------------------------- End of Project activities list---------------------------------

  // TODO - HC create permission to the new section of activity

  // --------------------------- Project Budget ---------------------------------

  /**
   * Can update the planning project budget section
   */
  public static final String PLANNING_PROJECT_BUDGET_UPDATE = "planning:projects:budget:update";

  /**
   * Can update the planning project budget by MOG section
   */
  public static final String PLANNING_PROJECT_BUDGET_BY_MOG_UPDATE = "planning:projects:budgetByMog:update";

  // --------------------------- End of project Budget ---------------------------------


  /*
   * PROJECTS PLANNING - The following permissions are applied specific to projects
   */

  // --------------------------- Project Description ---------------------------------

  /**
   * Can change all the project description information of some specific project
   */
  public static final String PROJECT_INFO_ALL = "project:description:*";

  /**
   * Can update the project description information of some specific project
   */
  public static final String PROJECT_INFO_UPDATE = "project:description:update";

  /**
   * Can update the management liaison of some specific project
   */
  public static final String MANAGEMENT_LIAISON_UPDATE = "project:description:managementLiaison:*";

  /**
   * Can update the start date of some specific project
   */
  public static final String PROJECT_START_DATE_UPDATE = "project:description:startDate:*";

  /**
   * Can update the end date of some specific project
   */
  public static final String PROJECT_END_DATE_UPDATE = "project:description::endDate:*";

  /**
   * Can upload a project work plan of some specific project
   */
  public static final String PROJECT_WORKPLAN_UPDATE = "project:description:workplan:*";

  /**
   * Can upload a bilateral contract proposal of some specific project
   */
  public static final String PROJECT_BILATERAL_CONTRACT_UPDATE = "project:description:bilateralContract:*";

  /**
   * Can update the flagships linked to a some specific project
   */
  public static final String PROJECT_FLAGSHIPS_UPDATE = "project:description:flagships:*";

  /**
   * Can update the end date of some specific project
   */
  public static final String PROJECT_REGIONS_UPDATE = "project:description:regions:*";

  // --------------------------- End of project Description ---------------------------------

  // --------------------------- Project Partners ---------------------------------

  /**
   * Can update the Lead partner section of some specific project
   */
  public static final String PROJECT_LEAD_PARTNER_UPDATE = "project:partnerLead:update";

  /**
   * Can update the project PPA partners section of some specific project
   */
  public static final String PROJECT_PPA_PARTNER_UPDATE = "project:ppaPartners:update";

  /**
   * Can update the project partners section of some specific project
   */
  public static final String PROJECT_PARTNER_UPDATE = "project:partners:update";

  // --------------------------- End of Project Partners ---------------------------------

  // --------------------------- Project Location ---------------------------------

  /**
   * Can update the project locations section of some specific project
   */
  public static final String PROJECT_LOCATIONS_UPDATE = "project:locations:update";

  // --------------------------- End of project Location ---------------------------------

  // --------------------------- Project outcomes ---------------------------------

  /**
   * Can update the project outcomes section of some specific project
   */
  public static final String PROJECT_OUTCOMES_UPDATE = "project:outcomes:update";

  /**
   * Can update the project CCAFS outcomes section of some specific project
   */
  public static final String PROJECT_CCAFS_OUTCOMES_UPDATE = "project:ccafsOutcomes:update";

  /**
   * Can update the project ip other contributions section of some specific project
   */
  public static final String PROJECT_OTHER_CONTRIBUTIONS_UPDATE = "project:otherContributions:update";

  // --------------------------- End of Project outcomes ---------------------------------

  // --------------------------- Project outputs ---------------------------------

  /**
   * Can update the project outputs section of some specific project
   */
  public static final String PROJECT_OUTPUTS_UPDATE = "project:outputs:update";

  /**
   * Can update the project deliverables list section of some specific project
   */
  public static final String PROJECT_DELIVERABLES_LIST_UPDATE = "project:deliverablesList:update";

  /**
   * Can update the project deliverables section of some specific project
   */
  public static final String PROJECT_DELIVERABLE_UPDATE = "project:deliverable:update";

  // --------------------------- End of Project outputs ---------------------------------

  // --------------------------- Project activities list---------------------------------

  /**
   * Can update the project activities list section of some specific project
   */
  public static final String PROJECT_ACTIVITIES_LIST_UPDATE = "project:activities:update";

  // --------------------------- End of Project activities list---------------------------------

  // TODO - HC create permission to the new section of activity

  // --------------------------- Project Budget ---------------------------------

  /**
   * Can update the project budget section of some specific project
   */
  public static final String PROJECT_BUDGET_UPDATE = "project:budget:update";

  /**
   * Can update the project budget section of some specific project
   */
  public static final String PROJECT_BUDGET_BY_MOG_UPDATE = "project:budgetByMog:update";

  // --------------------------- End of project Budget ---------------------------------

}
