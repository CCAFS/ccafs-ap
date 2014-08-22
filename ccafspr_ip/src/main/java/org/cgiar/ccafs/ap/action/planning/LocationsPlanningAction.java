package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.manager.LocationTypeManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Location;
import org.cgiar.ccafs.ap.data.model.LocationType;
import org.cgiar.ccafs.ap.data.model.OtherLocation;
import org.cgiar.ccafs.ap.data.model.Region;
import org.cgiar.ccafs.ap.util.FileManager;
import org.cgiar.ccafs.ap.util.SendMail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LocationsPlanningAction extends BaseAction {

  public static Logger LOG = LoggerFactory.getLogger(LocationsPlanningAction.class);
  private static final long serialVersionUID = -3960647459588960260L;

  // Managers
  private LocationManager locationManager;
  private LocationTypeManager locationTypeManager;
  private ActivityManager activityManager;

  // Model
  private List<LocationType> locationTypes;
  private Activity activity;
  private List<Country> countries;
  private List<Region> regions;
  private int activityID;

  // Variables needed to upload the excel file
  private File excelTemplate;
  private String excelTemplateContentType;
  private String excelTemplateFileName;

  // Temporal lists to save the locations
  private List<Region> regionsSaved;
  private List<Country> countriesSaved;
  private List<OtherLocation> otherLocationsSaved;

  @Inject
  public LocationsPlanningAction(APConfig config, LocationManager locationManager, ActivityManager activityManager,
    LocationTypeManager locationTypeManager) {
    super(config);
    this.locationManager = locationManager;
    this.activityManager = activityManager;
    this.locationTypeManager = locationTypeManager;
  }

  public Activity getActivity() {
    return activity;
  }

  public int getActivityID() {
    return activityID;
  }

  public List<Country> getCountries() {
    return countries;
  }

  public List<Country> getCountriesSaved() {
    return countriesSaved;
  }

  public int getCountryTypeID() {
    return APConstants.LOCATION_ELEMENT_TYPE_COUNTRY;
  }

  public List<LocationType> getLocationTypes() {
    return locationTypes;
  }


  public List<OtherLocation> getOtherLocationsSaved() {
    return otherLocationsSaved;
  }


  public List<Region> getRegions() {
    return regions;
  }


  public List<Region> getRegionsSaved() {
    return regionsSaved;
  }

  public int getRegionTypeID() {
    return APConstants.LOCATION_ELEMENT_TYPE_REGION;
  }

  private void parseActivityID() {
    // Getting the activity id from the URL parameter
    try {
      String _id = StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID));
      activityID = (_id != null) ? Integer.parseInt(_id) : -1;
    } catch (NumberFormatException e) {
      LOG.error("-- parseActivityID() > There was an error parsing the activity identifier '{}'.", activityID);
      activityID = -1;
    }
  }

  @Override
  public void prepare() throws Exception {
    // parseActivityID();
    activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));
    activity = activityManager.getActivityById(activityID);
    activity.setLocations(locationManager.getActivityLocations(activityID));

    locationTypes = locationTypeManager.getLocationTypes();
    countries = locationManager.getAllCountries();
    regions = locationManager.getAllRegions();

    regionsSaved = new ArrayList<>();
    countriesSaved = new ArrayList<>();
    otherLocationsSaved = new ArrayList<>();
  }

  @Override
  public String save() {
    List<Location> locations = new ArrayList<Location>();

    for (Region region : regionsSaved) {
      if (region != null) {
        locations.add(region);
      }
    }

    for (Country country : countriesSaved) {
      if (country != null) {
        locations.add(country);
      }
    }

    for (OtherLocation location : otherLocationsSaved) {
      if (location != null) {
        locations.add(location);
      }
    }

    // First remove the existent locations
    locationManager.removeActivityLocation(activity.getLocations(), activityID);
    // Then, save locations received
    locationManager.saveActivityLocations(locations, activityID);

    // Check if user upload an excel file
    if (excelTemplate != null) {
      String fileLocation = config.getUploadsBaseFolder() + config.getLocationsTemplateFolder();

      // First, move the uploaded file to the corresponding folder
      FileManager.copyFile(excelTemplate, fileLocation + excelTemplateFileName);

      LOG.trace("The locations template uploaded was moved to: " + fileLocation + excelTemplateFileName);
      // Send a message with the file received
      sendNotificationMessage(fileLocation, excelTemplateFileName);
    }

    return super.save();
  }

  private void sendNotificationMessage(String filePath, String fileName) {
    StringBuilder messageContent = new StringBuilder();
    String subject, recipients;
    subject = "[CCAFS P&R] Activity locations template to save into the database";
    recipients = "ccafsap@gmail.com";

    messageContent.append("User ");
    messageContent.append(getCurrentUser().getFirstName() + " " + getCurrentUser().getLastName());
    messageContent.append(" <" + getCurrentUser().getEmail() + "> ");
    messageContent.append("has uploaded a file with locations to be saved into the database.");

    SendMail sendMail = new SendMail(config);
    sendMail.sendMailWithAttachment(recipients, subject, messageContent.toString(), filePath + fileName, fileName);
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public void setActivityID(String activityID) {
    this.activityID = Integer.parseInt(activityID);
  }

  public void setCountries(List<Country> countries) {
    this.countries = countries;
  }

  public void setCountriesSaved(List<Country> countriesSaved) {
    this.countriesSaved = countriesSaved;
  }

  public void setExcelTemplate(File excelTemplate) {
    this.excelTemplate = excelTemplate;
  }


  public void setExcelTemplateContentType(String excelTemplateContentType) {
    this.excelTemplateContentType = excelTemplateContentType;
  }

  public void setExcelTemplateFileName(String excelTemplateFileName) {
    this.excelTemplateFileName = excelTemplateFileName;
  }

  public void setLocationTypes(List<LocationType> locationTypes) {
    this.locationTypes = locationTypes;
  }

  public void setOtherLocationsSaved(List<OtherLocation> otherLocationsSaved) {
    this.otherLocationsSaved = otherLocationsSaved;
  }

  public void setRegions(List<Region> regions) {
    this.regions = regions;
  }

  public void setRegionsSaved(List<Region> regionsSaved) {
    this.regionsSaved = regionsSaved;
  }
}
