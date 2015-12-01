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
package org.cgiar.ccafs.ap.config;

/**
 * All Constants should be here.
 * 
 * @author Héctor Fabio Tobón R.
 */
public final class APConstants {


  public static final String SESSION_USER = "current_user";
  public static final String ACTIVITY_REQUEST_ID = "activityID";
  public static final String PUBLIC_ACTIVITY_ID = "id";
  public static final String YEAR_REQUEST = "year";
  public static final String ACTIVITY_LIMIT_REQUEST = "limit";
  public static final String MILESTONE_REQUEST_ID = "milestoneID";
  public static final String REGION_REQUEST_ID = "regionID";
  public static final String COUNTRY_REQUEST_ID = "countryID";
  public static final String PARTNER_TYPE_REQUEST_ID = "partnerTypeID";
  public static final String PLANNING_SECTION = "Planning";
  public static final String REPORTING_SECTION = "Reporting";
  public static final String PROGRAM_REQUEST_ID = "programID";
  public static final String IP_ELEMENT_TYPE_REQUEST_ID = "elementTypeId";
  public static final String IP_ELEMENT_REQUEST_ID = "elementID";
  public static final String PROJECT_REQUEST_ID = "projectID";
  public static final String CORE_PROJECT_REQUEST_ID = "coreProjectID";
  public static final String BILATERAL_PROJECT_REQUEST_ID = "bilateralProjectID";
  public static final String PROJECT_PARTNER_REQUEST_ID = "projectPartnerID";
  public static final String INSTITUTION_REQUEST_ID = "institutionID";
  public static final String INSTITUTION_TYPE_REQUEST_ID = "institutionTypeID";
  public static final String INDICATOR_ID = "indicatorID";
  public static final String EMPLOYEE_REQUEST_ID = "employeeID";
  public static final String DELIVERABLE_TYPE_REQUEST_ID = "deliverableTypeID";
  public static final String EDITABLE_REQUEST = "edit";
  public static final String DELIVERABLE_REQUEST_ID = "deliverableID";
  public static final String HIGHLIGHT_REQUEST_ID = "highlightID";
  public static final String CCAFS_ORGANIZATION_IDENTIFIER = "XM-DAC-47015-CRP7";
  public static final String SECTION_NAME = "sectionName";
  public static final String CYCLE = "cycle";

  // Identifiers for element types which come from the database
  public static final int ELEMENT_TYPE_IDOS = 1;
  public static final int ELEMENT_TYPE_OUTCOME2025 = 2;
  public static final int ELEMENT_TYPE_OUTCOME2019 = 3;
  public static final int ELEMENT_TYPE_OUTPUTS = 4;

  // Identifiers for programs types which come from the database
  public static final int COORDINATION_PROGRAM_TYPE = 3;
  public static final int FLAGSHIP_PROGRAM_TYPE = 4;
  public static final int REGION_PROGRAM_TYPE = 5;

  // Identifier for types of program element relations
  public static final int PROGRAM_ELEMENT_CREATED_BY = 1;
  public static final int PROGRAM_ELEMENT_USED_BY = 2;

  // Identifiers for types of ip elements relationships
  public static final int ELEMENT_RELATION_CONTRIBUTION = 1;
  public static final int ELEMENT_RELATION_TRANSLATION = 2;

  // Identifier for types of relations between programs and elements
  public static final int PROGRAM_ELEMENT_RELATION_CREATION = 1;
  public static final int PROGRAM_ELEMENT_RELATION_USE = 2;

  // Identifiers for Location Elements Type which come from the database
  public static final int LOCATION_ELEMENT_TYPE_REGION = 1;
  public static final int LOCATION_ELEMENT_TYPE_COUNTRY = 2;

  // Identifiers for the IP Programs
  public static final int COORDINATING_UNIT_PROGRAM = 10;
  public static final int GLOBAL_PROGRAM = 11;
  public static final int CCAFS_PROGRAM = 14;
  public static final int SYSTEM_ADMIN_PROGRAM = 13;

  // Identifier for Format Date
  public static final String DATE_FORMAT = "yyyy-MM-dd";
  public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
  public static final String DATE_TIME_FORMAT_TIMEZONE = "yyyy-MM-dd HH:mm z";

  // Identifier for role
  public static final int ROLE_ADMIN = 1;
  public static final int ROLE_MANAGEMENT_LIAISON = 2;
  public static final int ROLE_CONTACT_POINT = 4;
  public static final int ROLE_PROJECT_LEADER = 7;
  public static final int ROLE_PROJECT_COORDINATOR = 9;
  public static final int ROLE_COORDINATING_UNIT = 6;
  public static final int ROLE_FINANCING_PROJECT = 10;

  // Location types identifiers
  public static final int LOCATION_TYPE_CLIMATE_SMART_VILLAGE = 10;
  public static final int LOCATION_TYPE_CCAFS_SITE = 11;

  // Types of project
  public static final String PROJECT_CORE = "CCAFS_CORE";
  public static final String PROJECT_CCAFS_COFUNDED = "CCAFS_COFUNDED";
  public static final String PROJECT_BILATERAL = "BILATERAL";

  // Types of Project Partners
  public static final String PROJECT_PARTNER_PL = "PL";
  public static final String PROJECT_PARTNER_PC = "PC";
  public static final String PROJECT_PARTNER_CP = "CP";

  // Types of Deliverable Partners
  public static final String DELIVERABLE_PARTNER_RESP = "Resp";
  public static final String DELIVERABLE_PARTNER_OTHER = "Other";

  // Deliverable Sub-type Other
  public static final int DELIVERABLE_SUBTYPE_OTHER_ID = 38;

  // Deliverable open access statuses
  public static final String OA_OPEN = "OPEN";
  public static final String OA_LIMITED = "LIMITED";

  // Query parameter
  public static final String QUERY_PARAMETER = "q";

  // Outlook institutional email
  public static final String OUTLOOK_EMAIL = "cgiar.org";

}
