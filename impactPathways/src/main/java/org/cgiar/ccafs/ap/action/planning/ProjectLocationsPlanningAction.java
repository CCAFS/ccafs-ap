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
package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.HistoryManager;
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.manager.LocationTypeManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.ClimateSmartVillage;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Location;
import org.cgiar.ccafs.ap.data.model.LocationType;
import org.cgiar.ccafs.ap.data.model.OtherLocation;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.Region;
import org.cgiar.ccafs.ap.validation.planning.ProjectLocationsValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProjectLocationsPlanningAction extends BaseAction {

  private static final long serialVersionUID = 1958577179378224020L;

  public static Logger LOG = LoggerFactory.getLogger(ProjectLocationsPlanningAction.class);

  // Managers
  private LocationManager locationManager;
  private LocationTypeManager locationTypeManager;
  private ProjectManager projectManager;
  private ProjectLocationsValidator validator;
  private HistoryManager historyManager;

  // Model
  private List<LocationType> locationTypes;
  private List<Country> countries;
  private List<Region> regions;
  private List<Location> climateSmartVillages;
  private List<Location> ccafsSites;
  private List<Location> previousLocations;
  private List<Location> locationsOrganized;
  private int projectID;
  private Project project;
  private int previousLocationsSize;

  // Temporal lists to save the locations
  private List<Region> regionsSaved;
  private List<Country> countriesSaved;
  private List<ClimateSmartVillage> csvSaved;
  private List<OtherLocation> otherLocationsSaved;

  @Inject
  public ProjectLocationsPlanningAction(APConfig config, LocationManager locationManager,
    LocationTypeManager locationTypeManager, ProjectManager projectManager, ProjectLocationsValidator validator,
    HistoryManager historyManager) {
    super(config);
    this.locationManager = locationManager;
    this.locationTypeManager = locationTypeManager;
    this.projectManager = projectManager;
    this.historyManager = historyManager;
    this.validator = validator;
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


  public List<Region> getRegions() {
    return regions;
  }


  public List<Region> getRegionsSaved() {
    return regionsSaved;
  }

  public int getRegionTypeID() {
    return APConstants.LOCATION_ELEMENT_TYPE_REGION;
  }


  public boolean isNewProject() {
    return project.isNew(config.getCurrentPlanningStartDate());
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
   * 
   * @return a list with the locations organized
   **/
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
    previousLocationsSize = previousLocations.size();

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

    // Getting the Project lessons for this section.
    this.setProjectLessons(
      lessonManager.getProjectComponentLesson(projectID, this.getActionName(), this.getCurrentPlanningYear()));

    super.setHistory(historyManager.getProjectLocationsHistory(project.getId()));

    // Initializing Section Statuses:
    this.initializeProjectSectionStatuses(project, "Planning");
  }

  @Override
  public String save() {
    if (securityContext.canUpdateProjectLocations(projectID)) {

      if (!this.isNewProject()) {
        super.saveProjectLessons(projectID);
      }

      boolean success = true;

      // Updating all previous added locations.
      boolean removed = locationManager.removeProjectLocation(previousLocations, projectID);
      if (removed == false) {
        success = false;
      }

      boolean added = locationManager.saveProjectLocation(project.getLocations(), projectID, this.getCurrentUser(),
        this.getJustification());
      if (!added) {
        success = false;
      }


      // Then, updating projects received
      boolean updated = locationManager.updateProjectGlobal(project, this.getCurrentUser(), this.getJustification());
      if (!updated) {
        success = false;
      }

      // Displaying user messages.
      if (success == false) {
        this.addActionError(this.getText("planning.project.locations.saving.problem"));
        return BaseAction.INPUT;
      }

      // Get the validation messages and append them to the save message
      Collection<String> messages = this.getActionMessages();
      if (!messages.isEmpty()) {
        String validationMessage = messages.iterator().next();
        // Action messages coming from the validation.
        this.setActionMessages(null);
        this.addActionWarning(this.getText("saving.saved") + validationMessage);
      } else {
        this.addActionMessage(this.getText("saving.saved"));
      }
      return SUCCESS;

    } else {
      return BaseAction.NOT_AUTHORIZED;
    }
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
    if (save) {
      validator.validate(this, project, "Planning");
    }
  }
}
