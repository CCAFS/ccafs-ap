package org.cgiar.ccafs.ap.config;

/**
 * All Constants should be here.
 * 
 * @author hftobon
 */
public final class APConstants {

  public static final String SESSION_USER = "current_user";
  public static final String ACTIVITY_REQUEST_ID = "activityID";
  public static final String DELIVERABLE_REQUEST_ID = "deliverableID";
  public static final String DELIVERABLE_TYPE_REQUEST_ID = "deliverableTypeID";
  public static final String PUBLIC_ACTIVITY_ID = "id";
  public static final String ACTIVITY_YEAR_REQUEST = "year";
  public static final String ACTIVITY_LIMIT_REQUEST = "limit";
  public static final String MILESTONE_REQUEST_ID = "milestoneID";
  public static final String REGION_REQUEST_ID = "regionID";
  public static final String COUNTRY_REQUEST_ID = "countryID";
  public static final String PARTNER_TYPE_REQUEST_ID = "partnerTypeID";
  public static final String PLANNING_SECTION = "Planning";
  public static final String REPORTING_SECTION = "Reporting";

  // Constants for the deliverables types
  public static final int DELIVERABLE_TYPE_DATA = 1;
  public static final int DELIVERABLE_TYPE_PUBLICATION = 3;
  public static final int DELIVERABLE_TYPE_CASE_STUDIES = 5;
  public static final int DELIVERABLE_SUBTYPE_JOURNAL = 21;
  public static final int DELIVERABLE_SUBTYPE_DATA = 10;


  // Constants for the deliverables status
  public static final int DELIVERABLE_STATUS_INCOMPLETE = 3;

  // Constants for the type of host used in the deliverable type
  public static final String DELIVERABLE_FILE_LOCALLY_HOSTED = "Locally";
  public static final String DELIVERABLE_FILE_EXTERNALLY_HOSTED = "Externally";
  public static final String DELIVERABLE_FILE_TO_DOWNLOAD = "To download";
}
