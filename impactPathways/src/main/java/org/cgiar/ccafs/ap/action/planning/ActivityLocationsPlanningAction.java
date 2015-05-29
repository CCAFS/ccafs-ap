package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.manager.LocationTypeManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.ClimateSmartVillage;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Location;
import org.cgiar.ccafs.ap.data.model.LocationType;
import org.cgiar.ccafs.ap.data.model.OtherLocation;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.Region;
import org.cgiar.ccafs.ap.util.FileManager;
import org.cgiar.ccafs.ap.util.SendMail;
import org.cgiar.ccafs.utils.APConfig;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivityLocationsPlanningAction extends BaseAction {

  public static Logger LOG = LoggerFactory.getLogger(ActivityLocationsPlanningAction.class);
  private static final long serialVersionUID = -3960647459588960260L;

  // Managers
  private LocationManager locationManager;
  private LocationTypeManager locationTypeManager;
  private ActivityManager activityManager;
  private ProjectManager projectManager;

  // Model
  private List<LocationType> locationTypes;
  private Activity activity;
  private List<Country> countries;
  private List<Region> regions;
  private List<Location> climateSmartVillages;
  private List<Location> ccafsSites;
  private List<Location> previousLocations;
  private List<Location> locationsOrganized;
  private int activityID;
  private Project project;

  // Variables needed to upload the excel file
  private File excelTemplate;
  private String excelTemplateContentType;
  private String excelTemplateFileName;

  // Temporal lists to save the locations
  private List<Region> regionsSaved;
  private List<Country> countriesSaved;
  private List<ClimateSmartVillage> csvSaved;
  private List<OtherLocation> otherLocationsSaved;

  @Inject
  public ActivityLocationsPlanningAction(APConfig config, LocationManager locationManager,
    ActivityManager activityManager, LocationTypeManager locationTypeManager, ProjectManager projectManager) {
    super(config);
    this.locationManager = locationManager;
    this.activityManager = activityManager;
    this.locationTypeManager = locationTypeManager;
    this.projectManager = projectManager;
  }

  public Activity getActivity() {
    return activity;
  }

  public int getActivityID() {
    return activityID;
  }

  private File getActivityLocationsFile() {
    String fileLocation = config.getUploadsBaseFolder() + config.getLocationsTemplateFolder();
    File f = new File(fileLocation);

    File[] matchingFiles = f.listFiles(new FilenameFilter() {

      public boolean accept(File dir, String name) {
        return name.startsWith(getLocationsFileName());
      }
    });

    if (matchingFiles.length > 0) {
      return matchingFiles[0];
    } else {
      return null;
    }
  }

  public List<Location> getCcafsSites() {
    return ccafsSites;
  }

  public int getCcafsSiteTypeID() {
    return APConstants.LOCATION_TYPE_CCAFS_SITE;
  }

  public List<Location> getClimateSmartVillages() {
    return climateSmartVillages;
  }

  public int getClimateSmartVillageTypeID() {
    return APConstants.LOCATION_TYPE_CLIMATE_SMART_VILLAGE;
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

  public List<ClimateSmartVillage> getCsvSaved() {
    return csvSaved;
  }


  private String getLocationsFileName() {
    return "ActivityLocationsTemplate-" + activity.getComposedId();
  }


  public String getLocationsFileURL() {
    return config.getDownloadURL() + "/" + config.getLocationsTemplateFolder() + getUploadFileName();
  }


  public List<LocationType> getLocationTypes() {
    return locationTypes;
  }

  public List<OtherLocation> getOtherLocationsSaved() {
    return otherLocationsSaved;
  }

  public Project getProject() {
    return project;
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


  public String getUploadFileName() {
    File file = getActivityLocationsFile();
    if (file == null) {
      return "";
    } else {
      return file.getName();
    }
  }

  @Override
  public String next() {
    String result = save();
    if (result.equals(BaseAction.SUCCESS)) {
      return BaseAction.NEXT;
    } else {
      return result;
    }
  }

  /**
   * As we receive the activity locations in several lists,
   * we need to re-organize the locations elements in order to save them
   * in the same order entered by the user
   */
  public List<Location> organizeActivityLocations() {
    locationsOrganized = new ArrayList<>();
    int maxSizeList, totalLocations;
    maxSizeList = regionsSaved.size();
    maxSizeList = (countriesSaved.size() > maxSizeList) ? countriesSaved.size() : maxSizeList;
    maxSizeList = (otherLocationsSaved.size() > maxSizeList) ? otherLocationsSaved.size() : maxSizeList;

    totalLocations = maxSizeList + activity.getLocations().size();
    for (int i = 0, j = 0; i < totalLocations; i++) {

      if (i < regionsSaved.size() && regionsSaved.get(i) != null) {
        locationsOrganized.add(regionsSaved.get(i));
      } else if (i < countriesSaved.size() && countriesSaved.get(i) != null) {
        locationsOrganized.add(countriesSaved.get(i));
      } else if (i < otherLocationsSaved.size() && otherLocationsSaved.get(i) != null) {
        locationsOrganized.add(otherLocationsSaved.get(i));
      } else {
        if (j < activity.getLocations().size()) {
          locationsOrganized.add(activity.getLocations().get(j));
          j++;
        }
      }
    }
    return locationsOrganized;
  }

  @Override
  public void prepare() throws Exception {
    // parseActivityID();
    activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));
    activity = activityManager.getActivityById(activityID);
    activity.setLocations(locationManager.getActivityLocations(activityID));
    previousLocations = new ArrayList<>();
    previousLocations.addAll(activity.getLocations());

    project = projectManager.getProjectFromActivityId(activityID);

    locationTypes = locationTypeManager.getLocationTypes();
    countries = locationManager.getAllCountries();
    regions = locationManager.getAllRegions();
    ccafsSites = locationManager.getLocationsByType(APConstants.LOCATION_TYPE_CCAFS_SITE);
    climateSmartVillages = locationManager.getLocationsByType(APConstants.LOCATION_TYPE_CLIMATE_SMART_VILLAGE);

    regionsSaved = new ArrayList<>();
    countriesSaved = new ArrayList<>();
    otherLocationsSaved = new ArrayList<>();
    csvSaved = new ArrayList<>();

    if (getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      if (activity.getLocations() != null) {
        activity.getLocations().clear();
      }
    }
  }

  @Override
  public String save() {
    if (this.isSaveable()) {

      boolean success = true;
      // Saving the activity information again (with the new global attribute on it).
      int saved = activityManager.saveActivity(project.getId(), activity);
      if (saved == -1) {
        success = false;
      }

      // removing all previous added locations.
      boolean removed = locationManager.removeActivityLocation(previousLocations, activityID);
      if (removed == false) {
        success = false;
      }

      // if Activity is not global.
      List<Location> locations = new ArrayList<Location>();
      // Grouping regions in the locations list.
      for (Region region : regionsSaved) {
        if (region != null) {
          locations.add(region);
        }
      }
      // Grouping countries in the locations list.
      for (Country country : countriesSaved) {
        if (country != null) {
          locations.add(country);
        }
      }

      // Grouping other locations to the locations list.
      for (OtherLocation location : otherLocationsSaved) {
        if (location != null) {
          locations.add(location);
        }
      }

      // Grouping csv locations to the locations list.
      for (ClimateSmartVillage location : csvSaved) {
        if (location != null) {
          locations.add(location);
        }
      }

      locations.addAll(activity.getLocations());

      // Then, saving locations received
      boolean added = locationManager.saveActivityLocations(locations, activityID);
      if (!added) {
        success = false;
      }

      // Check if user uploaded an excel file
      if (excelTemplate != null) {
        String fileLocation = config.getUploadsBaseFolder() + config.getLocationsTemplateFolder();
        String fileExtension = FilenameUtils.getExtension(excelTemplateFileName);

        // First, move the uploaded file to the corresponding folder
        FileManager.copyFile(excelTemplate, fileLocation + getLocationsFileName() + "." + fileExtension);
        LOG.trace("The locations template uploaded was moved to: " + fileLocation + getLocationsFileName()
          + fileExtension);
        // Send a message with the file received
        sendNotificationMessage(fileLocation, excelTemplateFileName);
      }

      // Displaying user messages.
      if (success == false) {
        addActionError(getText("saving.problem"));
        return BaseAction.INPUT;
      }
      addActionMessage(getText("saving.success", new String[] {getText("planning.activities.locations.title")}));
      return BaseAction.SUCCESS;
    } else {
      return BaseAction.NOT_AUTHORIZED;
    }
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

  public void setCsvSaved(List<ClimateSmartVillage> csvSaved) {
    this.csvSaved = csvSaved;
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
