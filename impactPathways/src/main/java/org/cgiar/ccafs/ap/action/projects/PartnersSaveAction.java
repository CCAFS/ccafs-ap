package org.cgiar.ccafs.ap.action.projects;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.ActivityPartner;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.InstitutionType;
import org.cgiar.ccafs.utils.APConfig;
import org.cgiar.ccafs.utils.SendMail;

import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PartnersSaveAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(PartnersSaveAction.class);
  private static final long serialVersionUID = -5598244190394106332L;

  // Managers
  private LocationManager locationManager;
  private InstitutionManager institutionManager;
  private ActivityManager activityManager;
  private ProjectManager projectManager;

  // Model
  private List<Country> countriesList;
  private List<InstitutionType> institutionTypesList;
  private ActivityPartner activityPartner;
  private boolean messageSent;

  private String partnerWebPage;
  private int projectID;
  private int activityID;

  @Inject
  public PartnersSaveAction(APConfig config, LocationManager locationManager, InstitutionManager institutionManager,
    ActivityManager activityManager, ProjectManager projectManager) {
    super(config);
    this.locationManager = locationManager;
    this.institutionManager = institutionManager;
    this.activityManager = activityManager;
    this.projectManager = projectManager;
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

  public List<InstitutionType> getInstitutionTypesList() {
    return institutionTypesList;
  }

  public String getPartnerWebPage() {
    return partnerWebPage;
  }

  public int getProjectID() {
    return projectID;
  }

  public boolean isMessageSent() {
    return messageSent;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    // Take the activity id only the first time the page loads

    if (this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID) != null) {
      activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));
      LOG.info("The user {} load the request partner section related to the activity {}.",
        this.getCurrentUser().getEmail(), activityID);
    } else if (this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID) != null) {
      projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));
      LOG.info("The user {} load the request partner section related to the project {}.",
        this.getCurrentUser().getEmail(), projectID);
    }

    this.countriesList = locationManager.getAllCountries();
    this.institutionTypesList = institutionManager.getAllInstitutionTypes();
  }

  @Override
  public String save() {
    String institutionName, institutionAcronym, institutionTypeName, countryId, countryName, city;
    String subject;
    StringBuilder message = new StringBuilder();

    int partnerTypeId;

    // Take the values to create the message
    institutionName = activityPartner.getPartner().getName();
    institutionAcronym = activityPartner.getPartner().getAcronym();
    partnerTypeId = activityPartner.getPartner().getType().getId();
    countryId = String.valueOf(activityPartner.getPartner().getCountry().getId());
    city = activityPartner.getPartner().getCity();

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
    subject = "[CCAFS P&R] Partner verification - " + institutionName;
    // Message content
    message.append(this.getCurrentUser().getFirstName() + " " + this.getCurrentUser().getLastName() + " ");
    message.append("(" + this.getCurrentUser().getEmail() + ") ");
    message.append("is requesting to add the following partner information:");
    message.append("\n\n");
    message.append("Partner Name: ");
    message.append(institutionName);
    message.append("\n");
    message.append("Acronym: ");
    message.append(institutionAcronym);
    message.append(" \n");
    message.append("Partner type: ");
    message.append(institutionTypeName);
    message.append(" \n");
    message.append("City: ");
    message.append(city);
    message.append(" \n");
    message.append("Country: ");
    message.append(countryName);
    message.append(" \n");
    // Is there a web page?
    if (this.partnerWebPage != null && this.partnerWebPage.isEmpty()) {
      message.append("Web Page: ");
      message.append(partnerWebPage);
      message.append(" \n");
    }
    message.append(" \n");

    if (activityID > 0) {
      message.append("Activity: (");
      message.append(activityID);
      message.append(") - ");
      message.append(activityManager.getActivityById(activityID).getTitle());
    } else if (projectID > 0) {
      message.append("Project: (");
      message.append(projectID);
      message.append(") - ");
      message.append(projectManager.getProject(projectID).getTitle());
    }

    message.append(".\n");
    message.append("\n");
    SendMail sendMail = new SendMail(this.config);
    sendMail.send(config.getEmailNotification(), null, null, subject, message.toString(), null, null, null);
    messageSent = true;

    LOG.info("The user {} send a message requesting add partners to the project {}", this.getCurrentUser().getEmail(),
      projectID);
    return INPUT;
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

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

  @Override
  public void validate() {
    boolean anyError = false;

    // If the page is loading don't validate
    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {

      // Check the partner name
      if (activityPartner.getPartner().getName().isEmpty()) {
        this.addFieldError("activityPartner.partner.name", this.getText("validation.field.required"));
        anyError = true;
      }

      if (anyError) {
        this.addActionError(this.getText("saving.fields.required"));
      }
    }
    super.validate();
  }
}