package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityBenchmarkSiteManager;
import org.cgiar.ccafs.ap.data.manager.ActivityCountryManager;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.ActivityObjectiveManager;
import org.cgiar.ccafs.ap.data.manager.ActivityOtherSiteManager;
import org.cgiar.ccafs.ap.data.manager.BenchmarkSiteManager;
import org.cgiar.ccafs.ap.data.manager.CountryManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.RegionManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.BenchmarkSite;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.OtherSite;
import org.cgiar.ccafs.ap.data.model.Region;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LocationsPlanningAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(LocationsPlanningAction.class);
  private static final long serialVersionUID = 2758017898245135320L;

  // Managers
  private ActivityManager activityManager;
  private ActivityCountryManager activityCountryManager;
  private ActivityBenchmarkSiteManager activityBenchmarkSiteManager;
  private ActivityOtherSiteManager activityOtherSiteManager;
  private CountryManager countryManager;
  private RegionManager regionManager;
  private BenchmarkSiteManager benchmarkSiteManager;

  // Model
  private int activityID;
  private Activity activity;
  private BenchmarkSite[] benchmarkSites;
  private Country[] countries;
  private Region[] regions;

  /*
   * regionsSelected var is true when all the region countries
   * are selected.
   * regionDisplayed var is true when at least one country of the
   * region is selected.
   */
  private Map<Integer, Boolean> regionsSelected;
  private Map<Integer, Boolean> regionsDisplayed;


  @Inject
  public LocationsPlanningAction(APConfig config, LogframeManager logframeManager, ActivityManager activityManager,
    ActivityObjectiveManager activityObjectiveManager, ActivityCountryManager activityCountryManager,
    ActivityBenchmarkSiteManager activityBenchmarkSiteManager, ActivityOtherSiteManager activityOtherSiteManager,
    CountryManager countryManager, RegionManager regionManager, BenchmarkSiteManager benchmarkSiteManager) {
    super(config, logframeManager);
    this.activityManager = activityManager;
    this.activityCountryManager = activityCountryManager;
    this.activityBenchmarkSiteManager = activityBenchmarkSiteManager;
    this.activityOtherSiteManager = activityOtherSiteManager;
    this.countryManager = countryManager;
    this.regionManager = regionManager;
    this.benchmarkSiteManager = benchmarkSiteManager;
  }

  /**
   * Count the number of countries by region and check
   * if the activity has all the countries of some region to
   * select the corresponding checkbox.
   */
  private void checkRegionsLoaded() {
    regionsSelected = new HashMap<>();
    regionsDisplayed = new HashMap<>();
    int regionId = 0;
    int[] countriesByRegionTotal = new int[regions.length];
    int[] countriesByRegionLoaded = new int[regions.length];

    // Initialize all the values to zero
    for (int c = 0; c < regions.length; c++) {
      countriesByRegionTotal[c] = 0;
      countriesByRegionLoaded[c] = 0;
    }

    for (int c = 0; c < countries.length; c++) {

      // If the counter is a valid value in the activity.countries list,
      // region to which the country belongs
      if (c < activity.getCountries().size()) {
        regionId = activity.getCountries().get(c).getRegion().getId();
        countriesByRegionLoaded[regionId - 1]++;
      }

      // take the next element in countries list and check
      // region to which the country belongs
      regionId = countries[c].getRegion().getId();
      countriesByRegionTotal[regionId - 1]++;
    }

    // If the values in both list are the same, the region is
    // selected
    for (int c = 0; c < regions.length; c++) {
      // Set regionDisplayed
      if (countriesByRegionLoaded[c] > 0) {
        regionsDisplayed.put(c + 1, true);
      }

      // Set regionsSelected
      if (countriesByRegionLoaded[c] == countriesByRegionTotal[c]) {
        regionsSelected.put(c, true);
      } else {
        regionsSelected.put(c, false);
      }
    }
  }

  /**
   * If there is some region selected delete the countries that
   * belongs to the region selected from activity.countries list
   */
  private void deleteCountriesOfRegionSelected() {
    int regionId;
    for (int c = 0; c < activity.getCountries().size(); c++) {
      regionId = activity.getCountries().get(c).getRegion().getId();
      // If the country in the list belongs to a region selected, delete it from the list
      if (regionsSelected.get(regionId)) {
        activity.getCountries().remove(c);
        // As the list change its size we need re-check the position deleted
        c--;
        break;
      }
    }
  }

  public Activity getActivity() {
    return activity;
  }

  public String getActivityRequestParameter() {
    return APConstants.ACTIVITY_REQUEST_ID;
  }

  public BenchmarkSite[] getBenchmarkSites() {
    return benchmarkSites;
  }

  public Country[] getCountries() {
    return countries;
  }

  public List<Country> getCountriesByRegion(int regionId) {
    List<Country> countryList = new ArrayList<>();
    for (Country country : countries) {
      if (country.getRegion().getId() == regionId) {
        countryList.add(country);
      }
    }
    return countryList;
  }

  public Region[] getRegions() {
    return regions;
  }

  public Map<Integer, Boolean> getRegionsDisplayed() {
    return regionsDisplayed;
  }

  public Map<Integer, Boolean> getRegionsSelected() {
    return regionsSelected;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    LOG.info("User {} load the activity locations for leader {} in planing section", getCurrentUser().getEmail(),
      getCurrentUser().getLeader().getId());

    String activityStringID = StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID));
    try {
      activityID = Integer.parseInt(activityStringID);
    } catch (NumberFormatException e) {
      LOG.error("There was an error parsing the activity identifier '{}'.", activityStringID, e);
    }

    // Get the basic information about the activity
    activity = activityManager.getActivityStatusInfo(activityID);

    // Set activity countries
    activity.setCountries(activityCountryManager.getActvitiyCountries(activityID));

    // Set activity benchmark sites
    activity.setBsLocations(activityBenchmarkSiteManager.getActivityBenchmarkSites(activityID));

    // Set activity other sites
    activity.setOtherLocations(activityOtherSiteManager.getActivityOtherSites(activityID));

    // Get the region list
    regions = regionManager.getRegionList();

    // Get the country list
    countries = countryManager.getCountryList();

    // Get the benchmark sites list
    benchmarkSites = benchmarkSiteManager.getActiveBenchmarkSiteList();

    // Check which regions are selected
    checkRegionsLoaded();
    // After knowing which regions are selected, it must be deleted the
    // countries of that regions to prevent show them in countries select
    // deleteCountriesOfRegionSelected();

    if (getRequest().getMethod().equalsIgnoreCase("post")) {
      activity.getOtherLocations().clear();
      // Global is set to false to prevent the value is always true.
      activity.setGlobal(false);
    }
  }

  @Override
  public String save() {
    boolean saved = true;
    boolean result;

    // Save the activity global attribute
    activityManager.updateGlobalAttribute(activity);

    // After, delete all the values from the database

    // Delete the activity countries
    result = activityCountryManager.deleteActivityCountries(activityID);
    if (!result) {
      saved = false;
      LOG.warn("There was a problem deleting the countries for activity {}.", activityID);
    }

    // Delete the activity other sites
    result = activityOtherSiteManager.deleteActivityOtherSites(activityID);
    // If there was a problem deleting the other sites show it in the log.
    if (!result) {
      saved = false;
      LOG.warn("There was a problem deleting the other sites for activity {}.", activityID);
    }
    activityManager.saveActivity(activity);
    // Delete the activity benchmark sites
    result = activityBenchmarkSiteManager.deleteActivityBenchmarkSites(activityID);
    // If there was a problem deleting the benchmark sites show it in the log.
    if (!result) {
      saved = false;
      LOG.warn("There was a problem deleting the benchmark sites for activity {}.", activityID);
    }

    // If the activity is not global, save the values selected.
    if (!activity.isGlobal()) {

      // If there are regions selected
      if (!regionsSelected.isEmpty()) {
        // Save all the countries of that region
        for (Entry<Integer, Boolean> entry : regionsSelected.entrySet()) {
          if (entry.getValue()) {
            activityCountryManager.saveCountriesByRegion(entry.getKey().intValue(), activityID);
          }
        }

        // Then delete the countries from activity.countries list to prevent duplicate entries into the DB
        deleteCountriesOfRegionSelected();
      }

      // Save the countries
      activityCountryManager.saveActivityCountries(activity.getCountries(), activityID);

      // Save the other sites
      activityOtherSiteManager.saveActivityOtherSites(activity.getOtherLocations(), activityID);

      // Save the benchmark sites
      activityBenchmarkSiteManager.saveActivityBenchmarkSites(activity.getBsLocations(), activityID);
    }


    if (saved) {
      addActionMessage(getText("saving.success", new String[] {getText("planning.objectives")}));
      return SUCCESS;
    } else {
      addActionError(getText("saving.problem"));
      return INPUT;
    }
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public void setRegionsDisplayed(Map<Integer, Boolean> regionsDisplayed) {
    this.regionsDisplayed = regionsDisplayed;
  }


  public void setRegionsSelected(Map<Integer, Boolean> regionsSelected) {
    this.regionsSelected = regionsSelected;
  }

  @Override
  public void validate() {
    boolean problem = false;

    if (save) {

      // If there is an other site, validate its fields
      if (!activity.getOtherLocations().isEmpty()) {
        for (int c = 0; c < activity.getOtherLocations().size(); c++) {
          OtherSite os = activity.getOtherLocations().get(c);

          // Validate the latitude
          if (os.getLatitude() > 91 || os.getLatitude() < -91) {
            addFieldError("activity.otherLocations[" + c + "].latitude",
              getText("validation.invalid", new String[] {getText("planning.locations.latitude")}));
          }

          // Validate the longitude
          if (os.getLongitude() > 181 || os.getLongitude() < -181) {
            addFieldError("activity.otherLocations[" + c + "].longitude",
              getText("validation.invalid", new String[] {getText("planning.locations.longitude")}));
          }
        }
      }

      if (problem) {
        addActionError(getText("saving.fields.required"));
      }
    }
  }
}
