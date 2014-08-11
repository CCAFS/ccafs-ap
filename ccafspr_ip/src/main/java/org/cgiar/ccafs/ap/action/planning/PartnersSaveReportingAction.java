package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.model.ActivityPartner;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.InstitutionType;
import org.cgiar.ccafs.ap.util.SendMail;

import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PartnersSaveReportingAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(PartnersSaveReportingAction.class);
  private static final long serialVersionUID = -5598244190394106332L;

  // Managers
  private LocationManager locationManager;
  private InstitutionManager institutionManager;
  private ActivityManager activityManager;

  // Model
  private List<Country> countriesList;
  private List<InstitutionType> institutionTypesList;
  private ActivityPartner activityPartner;

  private String partnerWebPage;
  private int activityID;


  @Inject
  public PartnersSaveReportingAction(APConfig config, LocationManager locationManager,
    InstitutionManager institutionManager, ActivityManager activityManager) {
    super(config);
    this.locationManager = locationManager;
    this.institutionManager = institutionManager;
    this.activityManager = activityManager;
  }


  public int getActivityID() {
    return activityID;
  }

  public ActivityPartner getActivityPartner() {
    return activityPartner;
  }

  public List<Country> getCountriesList() {
    return countriesList;
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
    this.countriesList = locationManager.getAllCountries();
    this.institutionTypesList = institutionManager.getAllInstitutionTypes();
  }

  @Override
  public String save() {
    String institutionName, institutionAcronym, institutionTypeName, countryId, countryName, city;
    String contactName, contactEmail;
    String subject;
    StringBuilder message = new StringBuilder();

    int partnerTypeId;

    // Take the values to create the message
    institutionName = activityPartner.getPartner().getName();
    institutionAcronym = activityPartner.getPartner().getAcronym();
    partnerTypeId = activityPartner.getPartner().getType().getId();
    countryId = String.valueOf(activityPartner.getPartner().getCountry().getId());
    city = activityPartner.getPartner().getCity();
    contactName = activityPartner.getContactName();
    contactEmail = activityPartner.getContactEmail();

    // Get the country name
    countryName = locationManager.getCountry(Integer.parseInt(countryId)).getName();

    // Get the partner type name
    institutionTypeName = "";
    for (InstitutionType pt : institutionTypesList) {
      if (pt.getId() == partnerTypeId) {
        institutionTypeName = pt.getName();
      }
    }
    // message subject
    subject = "Partner verification - " + institutionName;
    // Message content
    message.append(getCurrentUser().getFirstName() + " " + getCurrentUser().getLastName() + " ");
    message.append("(" + getCurrentUser().getEmail() + ") ");
    message.append("who is the user in charge of \"" + getCurrentUser().getCurrentInstitution().getName()
      + "\" is requesting to add the following partner information:");
    message.append("\n\n");
    message.append("Partner Name: ");
    message.append(institutionName);
    message.append("\n");
    message.append("Acronym: ");
    message.append(institutionAcronym);
    message.append("\n");
    message.append("Partner type: ");
    message.append(institutionTypeName);
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
    message.append(activityManager.getActivityById(activityID).getTitle());
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