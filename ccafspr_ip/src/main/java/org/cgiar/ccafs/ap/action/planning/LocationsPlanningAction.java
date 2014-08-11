package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.manager.LocationTypeManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.LocationType;
import org.cgiar.ccafs.ap.data.model.Region;

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

  public List<Country> getCountries() {
    return countries;
  }

  public int getCountryTypeID() {
    return APConstants.LOCATION_ELEMENT_TYPE_COUNTRY;
  }

  public List<LocationType> getLocationTypes() {
    return locationTypes;
  }

  public List<Region> getRegions() {
    return regions;
  }

  public int getRegionTypeID() {
    return APConstants.LOCATION_ELEMENT_TYPE_REGION;
  }

  @Override
  public void prepare() throws Exception {

    // Getting the activity id from the URL parameter
    try {
      activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));
    } catch (NumberFormatException e) {
      LOG.error("-- prepare() > There was an error parsing the activity identifier '{}'.", activityID);
      activityID = -1;
      return; // Stop here and go to execute method.
    }

    activity = activityManager.getActivityById(activityID);
    activity.setLocations(locationManager.getActivityLocations(activityID));

    locationTypes = locationTypeManager.getLocationTypes();
    countries = locationManager.getAllCountries();
    regions = locationManager.getAllRegions();
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public void setCountries(List<Country> countries) {
    this.countries = countries;
  }

  public void setLocationTypes(List<LocationType> locationTypes) {
    this.locationTypes = locationTypes;
  }

  public void setRegions(List<Region> regions) {
    this.regions = regions;
  }
}
