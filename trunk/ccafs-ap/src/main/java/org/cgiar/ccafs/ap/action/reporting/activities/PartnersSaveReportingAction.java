package org.cgiar.ccafs.ap.action.reporting.activities;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.CountryManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.PartnerTypeManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.PartnerType;
import org.cgiar.ccafs.ap.util.EmailValidator;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PartnersSaveReportingAction extends BaseAction {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(PartnersSaveReportingAction.class);

  // Managers
  private CountryManager countryManager;
  private PartnerTypeManager partnerTypeManager;
  private ActivityManager activityManager;

  // Model
  private Country[] countriesList;
  private PartnerType[] partnerTypesList;
  private Activity activity;

  private String partnerWebPage;
  private int activityID;


  @Inject
  public PartnersSaveReportingAction(APConfig config, LogframeManager logframeManager, CountryManager countryManager,
    PartnerTypeManager partnerTypeManager, ActivityManager activityManager) {
    super(config, logframeManager);
    this.countryManager = countryManager;
    this.partnerTypeManager = partnerTypeManager;
    this.activityManager = activityManager;
  }


  public Activity getActivity() {
    return activity;
  }

  public int getActivityID() {
    return activityID;
  }

  public Country[] getCountriesList() {
    return countriesList;
  }


  public PartnerType[] getPartnerTypesList() {
    return partnerTypesList;
  }


  public String getPartnerWebPage() {
    return partnerWebPage;
  }


  @Override
  public void prepare() throws Exception {
    super.prepare();

    // Take the activity id only the first time the page loads

    // TODO - when the request parameter is null the window must be closed
    if (this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID) != null) {
      activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));
    }


    this.countriesList = countryManager.getCountriesList();
    this.partnerTypesList = partnerTypeManager.getPartnerTypeList();
  }

  @Override
  public String save() {
    // TODO - The message body is ready but the send process is not
    // working. It can be the proxie or the function

    // TODO - Also have to create a gmail account and put the settings in the
    // properties file

    String partnerName, partnerAcronym, partnerTypeName, countryId, countryName, city;
    String contactName, contactEmail;
    String subject, message, recipients;
    int partnerTypeId;
    boolean anyWebPage = false;

    // Take the values to create the message
    partnerName = activity.getActivityPartners().get(0).getPartner().getName();
    partnerAcronym = activity.getActivityPartners().get(0).getPartner().getAcronym();
    partnerTypeId = activity.getActivityPartners().get(0).getPartner().getType().getId();
    countryId = activity.getActivityPartners().get(0).getPartner().getCountry().getId();
    city = activity.getActivityPartners().get(0).getPartner().getCity();
    contactName = activity.getActivityPartners().get(0).getContactName();
    contactEmail = activity.getActivityPartners().get(0).getContactEmail();

    // Get the country name
    countryName = countryManager.getCountry(countryId).getName();

    // Get the partner type name
    for (PartnerType pt : partnerTypesList) {
      if (pt.getId() == partnerTypeId) {
        partnerTypeName = pt.getName();
      }
    }

    // Get the activity title
    activity.setTitle(this.activityManager.getSimpleActivity(activityID).getTitle());

    // message subject
    subject = "Partner verification";

    // Set the flag for the web page
    if (this.partnerWebPage != null) {
      anyWebPage = (!this.partnerWebPage.isEmpty()) ? true : false;
    }

    // Message content
    message =
      "The user in charge of \"" + getCurrentUser().getLeader().getName() + "\" is looking for the partner \""
        + partnerName + " - " + partnerAcronym + "\"" + " from " + city + ", " + countryName
        + " to vinculate it to the activity " + activityID + " - " + activity.getTitle() + ".\n";

    if (anyWebPage) {
      message += "The partner web page is: " + this.partnerWebPage + "\n\n";
    }

    message +=
      "First you must look for the partner in the database, if it isn't stored use the "
        + "next SQL query to insert it: \n";

    // This is the SQL Query to insert into the partners table
    message +=
      "\"INSERT INTO partners VALUES (NULL, '" + partnerName + "', '" + partnerAcronym + "', " + "'" + countryId
        + "', '" + city + "', '" + partnerTypeId + "');\n";

    // This is the SQL Query to insert into the activity_partners table
    // the query takes the last id inserted and use it in the sentence
    message +=
      "INSERT INTO activity_partners VALUES (NULL, last_insert_id(), '" + activityID + "', '" + contactName + "', '"
        + contactEmail + "');";

    System.out.println(message);
    // SendMail.send("carvajal.hernandavid@gmail.com", subject, message);
    return SUCCESS;
  }


  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public void setActivityID(int activityID) {
    this.activityID = activityID;
  }

  public void setPartnerWebPage(String partnerWebPage) {
    this.partnerWebPage = partnerWebPage;
  }


  @Override
  public void validate() {
    boolean anyError = false;

    // If the page is loading dont validate
    if (getRequest().getMethod().equalsIgnoreCase("post")) {

      // Check the partner name
      if (activity.getActivityPartners().get(0).getPartner().getName().isEmpty()) {
        addFieldError("activity.activityPartners[0].partner.name", getText("validation.field.required"));
        anyError = true;
      }

      // Check the city name
      if (activity.getActivityPartners().get(0).getPartner().getCity().isEmpty()) {
        addFieldError("activity.activityPartners[0].partner.city", getText("validation.field.required"));
        anyError = true;
      }

      // Check the contact name
      if (activity.getActivityPartners().get(0).getContactName().isEmpty()) {
        addFieldError("activity.activityPartners[0].contactName", getText("validation.field.required"));
        anyError = true;
      }

      // Check if there is a contact email
      if (activity.getActivityPartners().get(0).getContactEmail().isEmpty()) {
        addFieldError("activity.activityPartners[0].contactEmail", getText("validation.field.required"));
        anyError = true;
      }

      // Check if the contact email is valid
      else if (!EmailValidator.isValidEmail(activity.getActivityPartners().get(0).getContactEmail())) {
        String[] invalidMail = {"Email "};
        addFieldError("activity.activityPartners[0].contactEmail", getText("validation.invalid", invalidMail));
        anyError = true;
      }

      if (anyError) {
        addActionError(getText("reporting.activityPartners.error"));
      }
    }
    super.validate();
  }
}