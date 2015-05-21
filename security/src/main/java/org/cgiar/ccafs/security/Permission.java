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

  /*
   * FULL PRIVILEGES
   */

  public static final String FULL_PRIVILEGES = "*";

  /*
   * PLANNING SECTION
   */

  /**
   * Can see all the planning contents
   */
  public static final String PLANNING_READ = "planning:read";

  /**
   * Can update all the planning contents
   */
  public static final String PLANNING_UPDATE = "planning:update";

  /**
   * Can read the projects list in the planning section
   */
  public static final String PLANNING_PROJECT_LIST_READ = "planning:projectList:read";

  /**
   * Can use all the functions in the planning projects list
   */
  public static final String PLANNING_PROJECT_LIST_ALL = "planning:projectList:*";

  /**
   * Can use the "add core project" button in the planning section
   */
  public static final String PLANNING_CORE_PROJECT_BUTTON = "planning:projectList:coreProjectButton:*";

  /**
   * Can use the "add bilateral project" button in the planning section
   */
  public static final String PLANNING_BILATERAL_PROJECT_BUTTON = "planning:projectList:bilateralProjectButton:*";

  /**
   * Can use the "Submit" button in the planning section
   */
  public static final String PLANNING_SUBMIT_BUTTON = "planning:projectList:submitButton:*";

  /**
   * Can use the "Delete project" button in the planning section
   */
  public static final String PLANNING_DELETE_PROJECT_BUTTON = "planning:projectList:deleteProjectButton:*";

  /**
   * Can read the planning project information section
   */
  public static final String PLANNING_PROJECT_INFO_READ = "planning:projectInfo:read";

  /**
   * Can read the planning project information section
   */
  public static final String PLANNING_PROJECT_INFO_UPDATE = "planning:projectInfo:update";

  /**
   * Can read the planning project information section
   */
  public static final String PLANNING_PROJECT_INFO_ALL = "planning:projectInfo:*";

  /**
   * Can update the management liaison of a project in the planning section
   */
  public static final String PLANNING_MANAGEMENT_LIAISON_UPDATE = "planning:projectInfo:managementLiaison:update";

  /**
   * Can update the start date of a project in the planning section
   */
  public static final String PLANNING_PROJECT_START_DATE_UPDATE = "planning:projectInfo:startDate:update";

  /**
   * Can upload a project work plan for a project in the planning section
   */
  public static final String PLANNING_PROJECT_WORKPLAN_UPDATE = "planning:projectInfo:workplan:*";

  /**
   * Can upload a bilateral contract proposal for a project in the planning section
   */
  public static final String PLANNING_PROJECT_BILATERAL_CONTRACT_UPDATE = "planning:projectInfo:bilateralContract:*";

  /**
   * Can update the flagships linked to a project in the planning section
   */
  public static final String PLANNING_PROJECT_FLAGSHIPS_UPDATE = "planning:projectInfo:flagships:*";

  /**
   * Can update the end date of a project in the planning section
   */
  public static final String PLANNING_PROJECT_REGIONS_UPDATE = "planning:projectInfo:regions:update";

  /**
   * Can update the end date of a project in the planning section
   */
  public static final String PLANNING_PROJECT_END_DATE_UPDATE = "planning:projectInfo:endDate:update";

  /**
   * Can read the planning project partners section
   */
  public static final String PLANNING_PROJECT_PARTNERS_READ = "planning:projectPartners:read";

  /**
   * Can update the planning project partners section
   */
  public static final String PLANNING_PROJECT_PARTNERS_UPDATE = "planning:projectPartners:update";

  /**
   * Can read the planning project locations section
   */
  public static final String PLANNING_PROJECT_LOCATIONS_READ = "planning:projectLocations:read";

  /**
   * Can update the planning project locations section
   */
  public static final String PLANNING_PROJECT_LOCATIONS_UPDATE = "planning:projectLocations:update";

  /**
   * Can read the planning project outcomes section
   */
  public static final String PLANNING_PROJECT_OUTCOMES_READ = "planning:projectOutcomes:read";

  /**
   * Can update the planning project outcomes section
   */
  public static final String PLANNING_PROJECT_OUTCOMES_UPDATE = "planning:projectOutcomes:update";

  /**
   * Can read the planning project outputs section
   */
  public static final String PLANNING_PROJECT_OUTPUTS_READ = "planning:projectOutputs:read";

  /**
   * Can update the planning project outputs section
   */
  public static final String PLANNING_PROJECT_OUTPUTS_UPDATE = "planning:projectOutputs:update";

  /**
   * Can read the planning project activities list section
   */
  public static final String PLANNING_PROJECT_ACTIVITIES_LIST_READ = "planning:projectActivitiesList:read";

  /**
   * Can update the planning project activities list section
   */
  public static final String PLANNING_PROJECT_ACTIVITIES_LIST_UPDATE = "planning:projectActivitiesList:update";

  /**
   * Can read the planning project activities section
   */
  public static final String PLANNING_PROJECT_ACTIVITIES_READ = "planning:projectActivities:read";

  /**
   * Can update the planning project activities section
   */
  public static final String PLANNING_PROJECT_ACTIVITIES_UPDATE = "planning:projectActivities:update";

  /**
   * Can read the planning project budget section
   */
  public static final String PLANNING_PROJECT_BUDGET_READ = "planning:projectBudget:read";

  /**
   * Can update the planning project budget section
   */
  public static final String PLANNING_PROJECT_BUDGET_UPDATE = "planning:projectBudget:update";


}
