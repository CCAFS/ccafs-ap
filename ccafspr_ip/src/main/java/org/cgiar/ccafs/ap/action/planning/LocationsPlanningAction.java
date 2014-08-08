package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Country;
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
  LocationManager locationManager;
  ActivityManager activityManager;

  // Model
  private Activity activity;
  private List<Country> countries;
  private List<Region> regions;
  private int activityID;

  @Inject
  public LocationsPlanningAction(APConfig config, LocationManager locationManager, ActivityManager activityManager) {
    super(config);
    this.locationManager = locationManager;
    this.activityManager = activityManager;
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

    System.out.println(activity);

    countries = locationManager.getAllCountries();
    regions = locationManager.getAllRegions();
  }


}
