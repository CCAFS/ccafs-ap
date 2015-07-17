package org.cgiar.ccafs.ap.action.reporting.activities;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.CountryManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.PartnerTypeManager;
import org.cgiar.ccafs.ap.data.model.ActivityPartner;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.PartnerType;
import org.cgiar.ccafs.ap.util.SendMail;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PartnersSaveReportingAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(PartnersSaveReportingAction.class);
  private static final long serialVersionUID = -5598244190394106332L;

  // Managers
  private CountryManager countryManager;
  private PartnerTypeManager partnerTypeManager;
  private ActivityManager activityManager;

  // Model
  private Country[] countriesList;
  private PartnerType[] partnerTypesList;
  private ActivityPartner activityPartner;

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


  public int getActivityID() {
    return activityID;
  }

  public ActivityPartner getActivityPartner() {
    return activityPartner;
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

    if (this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID) != null) {
      activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));
    }
    LOG.info("The user {} load the request partner section related to the activity {}.", getCurrentUser().getEmail(),
      activityID);
    this.countriesList = countryManager.getCountryList();
    this.partnerTypesList = partnerTypeManager.getPartnerTypeList();
  }

  @Override
  public String save() {
    String partnerName, partnerAcronym, partnerTypeName, countryId, countryName, city;
    String contactName, contactEmail;
    String subject;
    StringBuilder message = new StringBuilder();

    int partnerTypeId;

    // Take the values to create the message
    partnerName = activityPartner.getPartner().getName();
    partnerAcronym = activityPartner.getPartner().getAcronym();
    partnerTypeId = activityPartner.getPartner().getType().getId();
    countryId = activityPartner.getPartner().getCountry().getId();
    city = activityPartner.getPartner().getCity();
    contactName = activityPartner.getContactName();
    contactEmail = activityPartner.getContactEmail();

    // Get the country name
    countryName = countryManager.getCountry(countryId).getName();

    // Get the partner type name
    partnerTypeName = "";
    for (PartnerType pt : partnerTypesList) {
      if (pt.getId() == partnerTypeId) {
        partnerTypeName = pt.getName();
      }
    }
    // message subject
    subject = "[CCAFS P&R - Logframes] Partner verification - " + partnerName;
    // Message content
    message.append(getCurrentUser().getName() + " ");
    message.append("(" + getCurrentUser().getEmail() + ") ");
    message.append("who is the user in charge of \"" + getCurrentUser().getLeader().getName()
      + "\" is requesting to add the following partner information:");
    message.append("\n\n");
    message.append("Partner Name: ");
    message.append(partnerName);
    message.append("\n");
    message.append("Acronym: ");
    message.append(partnerAcronym);
    message.append("\n");
    message.append("Partner type: ");
    message.append(partnerTypeName);
    message.append("\n");
    message.append("Location (City, Country): ");
    message.append(city);
    message.append(", ");
    message.append(countryName);
    message.append("\n");
    // Is there a web page?
    if (this.partnerWebPage != null) {
      message.append("Web Page: ");
      message.append(partnerWebPage);
      message.append("\n");
    }
    message.append("Activity: (");
    message.append(activityID);
    message.append(") - ");
    message.append(activityManager.getSimpleActivity(activityID).getTitle());
    message.append(".\n");
    message.append("\n");
    SendMail sendMail = new SendMail(this.config);
    sendMail.send(config.getGmailUsername(), subject, message.toString());
    LOG.info("The user {} send a message requesting add partners to the activity {}", getCurrentUser().getEmail(),
      activityID);
    return SUCCESS;
  }

  public void setActivityID(int activityID) {
    this.activityID = activityID;
  }

  public void setActivityPartner(ActivityPartner activityPartner) {
    this.activityPartner = activityPartner;
  }

  public void setPartnerWebPage(String partnerWebPage) {
    this.partnerWebPage = partnerWebPage;
  }


  @Override
  public void validate() {
    boolean anyError = false;

    // If the page is loading don't validate
    if (getRequest().getMethod().equalsIgnoreCase("post")) {

      // Check the partner name
      if (activityPartner.getPartner().getName().isEmpty()) {
        addFieldError("activityPartner.partner.name", getText("validation.field.required"));
        anyError = true;
      }

      if (anyError) {
        addActionError(getText("saving.fields.required"));
      }
    }
    super.validate();
  }
}