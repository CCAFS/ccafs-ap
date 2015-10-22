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
  public static final String PLANNING_PROJECT_LIST_ALL = "planning:projectList:*";

  /**
   * Can use all the functions adding a Co-Funded Project
   */
  public static final String PLANNING_COFUNDED_PROJECT_BUTTON = "planning:projectList:addCoFundedProject:*";

  /**
   * Can use the "add core project" button in the planning projects list section
   */
  public static final String PLANNING_CORE_PROJECT_BUTTON = "planning:projectList:coreProjectButton:*";

  /**
   * Can use the "add bilateral project" button in the planning projects list section
   */
  public static final String PLANNING_BILATERAL_PROJECT_BUTTON = "planning:projectList:bilateralProjectButton:*";

  /**
   * Can use the "Delete project" button in the planning section
   */
  public static final String PLANNING_DELETE_PROJECT_BUTTON = "planning:projectList:deleteProjectButton:*";


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
   * Can update the planning project partners section
   */
  public static final String PLANNING_PROJECT_PARTNER_UPDATE = "planning:projects:partners:update";

  /**
   * Can update the project leader in the planning project partners section
   */
  public static final String PLANNING_PROJECT_PARTNER_LEADER_UPDATE = "planning:projects:partner:leader:update";
  /**
   * Can update the project leader in the planning project partners section
   */
  public static final String PLANNING_PROJECT_PARTNER_CORDINATOR_UPDATE =
    "planning:projects:partners:cordinator:update";

  /**
   * Can update the planning project partners section
   */
  public static final String PLANNING_PROJECT_PARTNER_PPA_UPDATE = "planning:projects:partner:ppa:update";


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
   * the planning project activities list section can be updated
   */
  public static final String PLANNING_PROJECT_ACTIVITIES_LIST_UPDATE = "planning:projects:activities:update";

  /**
   * the planning project activities info can be updated
   */
  public static final String PLANNING_PROJECT_ACTIVITIES_INFO_UPDATE = "planning:projects:activities:info:update";

  /**
   * the planning project activities end date can be updated
   */
  public static final String PLANNING_PROJECT_ACTIVITIES_END_DATE_UPDATE =
    "planning:projects:activities:endDate:update";

  /**
   * the planning project activities lead organization can be updated
   */
  public static final String PLANNING_PROJECT_ACTIVITIES_LEAD_ORGANIZATION_UPDATE =
    "planning:projects:activities:leadOrganization:update";

  /**
   * the planning project activities leader can be updated
   */
  public static final String PLANNING_PROJECT_ACTIVITIES_LEADER_UPDATE = "planning:projects:activities:leader:update";

  /**
   * the planning project activities leader can be updated
   */

  /**
   * the planning project activities start date can be updated
   */
  public static final String PLANNING_PROJECT_ACTIVITIES_START_DATE_UPDATE =
    "planning:projects:activities:startDate:update";


  // --------------------------- End of Project activities list---------------------------------

  // --------------------------- Project Budget ---------------------------------

  /**
   * Can update the planning project budget section
   */
  public static final String PLANNING_PROJECT_BUDGET_UPDATE = "planning:projects:budget:update";

  /**
   * Can update the W1/W2 budget in the planning project budget section
   */
  public static final String PLANNING_PROJECT_BUDGET_ANNUAL_W1W2_UPDATE = "planning:projects:budget:annualW1w2:update";

  /**
   * Can update the W3/Bilateral budget in the planning project budget section
   */
  public static final String PLANNING_PROJECT_ANNUAL_BUDGET_W3BILATERAL_UPDATE =
    "planning:projects:budget:annualBilateral:update";


  /**
   * Can update the planning project budget by MOG section
   */
  public static final String PLANNING_PROJECT_BUDGET_BY_MOG_UPDATE = "planning:projects:budgetByMog:update";

  // --------------------------- End of project Budget ---------------------------------

  // --------------------------- Project Submit ---------------------------------

  /**
   * Can use the "Submit" button in the planning section
   */
  public static final String PLANNING_SUBMIT_BUTTON = "planning:projects:submitButton:*";

  // --------------------------- End Project Submit ---------------------------------

  /*
   * --------------------------------------------------------------
   * PERMISSIONS APPLIED TO ALL THE SUMMARIES SECTION
   * --------------------------------------------------------------
   */

  /**
   * Can update all the summaries contents
   */
  public static final String SUMMARIES_ALL = "summaries:*";

  /**
   * Can use all the functions in the summaries board
   */
  public static final String SUMMARIES_BOARD_ALL = "summaries:board:*";

}