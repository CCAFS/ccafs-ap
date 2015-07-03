package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
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

public class ProjectLocationsPlanningAction extends BaseAction {

  public static Logger LOG = LoggerFactory.getLogger(ProjectLocationsPlanningAction.class);
  private static final long serialVersionUID = -3960647459588960260L;

  // Managers
  private LocationManager locationManager;
  private LocationTypeManager locationTypeManager;
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
  private int projectID;
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
  public ProjectLocationsPlanningAction(APConfig config, LocationManager locationManager,
    LocationTypeManager locationTypeManager, ProjectManager projectManager) {
    super(config);
    this.locationManager = locationManager;
    this.locationTypeManager = locationTypeManager;
    this.projectManager = projectManager;
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
    return "ProjectLocationsTemplate-" + project.getComposedId();
  }

  public String getLocationsFileURL() {
    return config.getDownloadURL() + "/" + config.getLocationsTemplateFolder() + this.getUploadFileName();
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


  public int getProjectID() {
    return projectID;
  }

  private File getProjectLocationsFile() {
    String fileLocation = config.getUploadsBaseFolder() + config.getLocationsTemplateFolder();
    File f = new File(fileLocation);

    File[] matchingFiles = f.listFiles(new FilenameFilter() {

      @Override
      public boolean accept(File dir, String name) {
        return name.startsWith(ProjectLocationsPlanningAction.this.getLocationsFileName());
      }
    });

    if (matchingFiles.length > 0) {
      return matchingFiles[0];
    } else {
      return null;
    }
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
    File file = this.getProjectLocationsFile();
    if (file == null) {
      return "";
    } else {
      return file.getName();
    }
  }

  @Override
  public String next() {
    String result = this.save();
    if (result.equals(BaseAction.SUCCESS)) {
      return BaseAction.NEXT;
    } else {
      return result;
    }
  }

  /**
   * As we receive the project locations in several lists,
   * we need to re-organize the locations elements in order to save them
   * in the same order entered by the user
   */
  public List<Location> organizeProjectLocations() {
    locationsOrganized = new ArrayList<>();
    int maxSizeList, totalLocations;
    maxSizeList = regionsSaved.size();
    maxSizeList = (countriesSaved.size() > maxSizeList) ? countriesSaved.size() : maxSizeList;
    maxSizeList = (otherLocationsSaved.size() > maxSizeList) ? otherLocationsSaved.size() : maxSizeList;

    totalLocations = maxSizeList + project.getLocations().size();
    for (int i = 0, j = 0; i < totalLocations; i++) {

      if (i < regionsSaved.size() && regionsSaved.get(i) != null) {
        locationsOrganized.add(regionsSaved.get(i));
      } else if (i < countriesSaved.size() && countriesSaved.get(i) != null) {
        locationsOrganized.add(countriesSaved.get(i));
      } else if (i < otherLocationsSaved.size() && otherLocationsSaved.get(i) != null) {
        locationsOrganized.add(otherLocationsSaved.get(i));
      } else {
        if (j < project.getLocations().size()) {
          locationsOrganized.add(project.getLocations().get(j));
          j++;
        }
      }
    }
    return locationsOrganized;
  }

  @Override
  public void prepare() throws Exception {
    // parseProjectID();
    projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));
    project = projectManager.getProject(projectID);
    project.setLocations(locationManager.getProjectLocations(projectID));
    previousLocations = new ArrayList<>();
    previousLocations.addAll(project.getLocations());

    locationTypes = locationTypeManager.getLocationTypes();
    countries = locationManager.getAllCountries();
    regions = locationManager.getAllRegions();
    ccafsSites = locationManager.getLocationsByType(APConstants.LOCATION_TYPE_CCAFS_SITE);
    climateSmartVillages = locationManager.getLocationsByType(APConstants.LOCATION_TYPE_CLIMATE_SMART_VILLAGE);

    regionsSaved = new ArrayList<>();
    countriesSaved = new ArrayList<>();
    otherLocationsSaved = new ArrayList<>();
    csvSaved = new ArrayList<>();

    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      if (project.getLocations() != null) {
        project.getLocations().clear();
      }
    }
  }

  @Override
  public String save() {
    LOG.debug(">> save");
    if (this.isSaveable()) {

      boolean success = true;
      // Saving the project information again (with the new global attribute on it).
      int saved = projectManager.saveProjectDescription(project, project.getOwner(), "");
      if (saved == -1) {
        success = false;
      }

      // Updating all previous added locations.
      boolean removed = locationManager.removeProjectLocation(previousLocations, projectID);
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

      locations.addAll(project.getLocations());

      // Then, saving locations received
      boolean added =
        locationManager.saveProjectLocation(locations, projectID, this.getCurrentUser(), this.getJustification());
      if (!added) {
        success = false;
      }

      // Check if user uploaded an excel file
      if (excelTemplate != null) {
        String fileLocation = config.getUploadsBaseFolder() + config.getLocationsTemplateFolder();
        String fileExtension = FilenameUtils.getExtension(excelTemplateFileName);

        // First, move the uploaded file to the corresponding folder
        FileManager.copyFile(excelTemplate, fileLocation + this.getLocationsFileName() + "." + fileExtension);
        LOG.trace("The locations template uploaded was moved to: " + fileLocation + this.getLocationsFileName()
          + fileExtension);
        // Send a message with the file received
        this.sendNotificationMessage(fileLocation, excelTemplateFileName);
      }

      // Displaying user messages.
      if (success == false) {
        this.addActionError(this.getText("saving.problem"));
        return BaseAction.INPUT;
      }
      this.addActionMessage(this.getText("saving.success",
        new String[] {this.getText("planning.projects.locations.title")}));
      return BaseAction.SUCCESS;
    } else {
      return BaseAction.NOT_AUTHORIZED;
    }
  }

  private void sendNotificationMessage(String filePath, String fileName) {
    StringBuilder messageContent = new StringBuilder();
    String subject, recipients;
    subject = "[CCAFS P&R] Project locations template to save into the database";
    recipients = "ccafsap@gmail.com";

    messageContent.append("User ");
    messageContent.append(this.getCurrentUser().getFirstName() + " " + this.getCurrentUser().getLastName());
    messageContent.append(" <" + this.getCurrentUser().getEmail() + "> ");
    messageContent.append("has uploaded a file with locations to be saved into the database.");

    SendMail sendMail = new SendMail(config);
    sendMail.sendMailWithAttachment(recipients, subject, messageContent.toString(), filePath + fileName, fileName);
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

  public void setProject(Project project) {
    this.project = project;
  }

  public void setProjectID(String projectID) {
    this.projectID = Integer.parseInt(projectID);
  }

  public void setRegions(List<Region> regions) {
    this.regions = regions;
  }

  public void setRegionsSaved(List<Region> regionsSaved) {
    this.regionsSaved = regionsSaved;
  }

  @Override
  public void validate() {
    LOG.debug(">> validate() ");
  }
}
