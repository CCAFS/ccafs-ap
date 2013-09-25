package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityBenchmarkSiteManager;
import org.cgiar.ccafs.ap.data.manager.ActivityCountryManager;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.ActivityOtherSiteManager;
import org.cgiar.ccafs.ap.data.manager.ActivityRegionManager;
import org.cgiar.ccafs.ap.data.manager.BenchmarkSiteManager;
import org.cgiar.ccafs.ap.data.manager.CountryManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.RegionManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.BenchmarkSite;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.OtherSite;
import org.cgiar.ccafs.ap.data.model.Region;
import org.cgiar.ccafs.ap.data.model.Submission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
  private ActivityRegionManager activityRegionManager;
  private ActivityBenchmarkSiteManager activityBenchmarkSiteManager;
  private ActivityOtherSiteManager activityOtherSiteManager;
  private CountryManager countryManager;
  private RegionManager regionManager;
  private BenchmarkSiteManager benchmarkSiteManager;
  private SubmissionManager submissionManager;

  // Model
  private int activityID;
  private StringBuilder validationMessages;
  private Activity activity;
  private BenchmarkSite[] benchmarkSites;
  private Country[] countries;
  private Region[] regions;
  private List<Integer> activeRegions;
  private boolean canSubmit;

  @Inject
  public LocationsPlanningAction(APConfig config, LogframeManager logframeManager, ActivityManager activityManager,
    ActivityCountryManager activityCountryManager, ActivityRegionManager activityRegionManager,
    ActivityBenchmarkSiteManager activityBenchmarkSiteManager, ActivityOtherSiteManager activityOtherSiteManager,
    CountryManager countryManager, RegionManager regionManager, BenchmarkSiteManager benchmarkSiteManager,
    SubmissionManager submissionManager) {
    super(config, logframeManager);
    this.activityManager = activityManager;
    this.activityCountryManager = activityCountryManager;
    this.activityRegionManager = activityRegionManager;
    this.activityBenchmarkSiteManager = activityBenchmarkSiteManager;
    this.activityOtherSiteManager = activityOtherSiteManager;
    this.countryManager = countryManager;
    this.regionManager = regionManager;
    this.benchmarkSiteManager = benchmarkSiteManager;
    this.submissionManager = submissionManager;
  }

  public List<Integer> getActiveRegions() {
    return activeRegions;
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

  public boolean isCanSubmit() {
    return canSubmit;
  }


  @Override
  public void prepare() throws Exception {
    super.prepare();
    LOG.info("-- prepare() > User {} load the activity locations for leader {} in planing section", getCurrentUser()
      .getEmail(), getCurrentUser().getLeader().getId());

    validationMessages = new StringBuilder();

    String activityStringID = StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID));
    try {
      activityID = Integer.parseInt(activityStringID);
    } catch (NumberFormatException e) {
      LOG.error("-- prepare() > There was an error parsing the activity identifier '{}'.", activityStringID, e);
    }

    // Get the basic information about the activity
    activity = activityManager.getActivityStatusInfo(activityID);

    // Set activity countries
    activity.setCountries(activityCountryManager.getActvitiyCountries(activityID));

    // Set activity regions
    activity.setRegions(activityRegionManager.getActvitiyRegions(activityID));

    // Set activity benchmark sites
    activity.setBsLocations(activityBenchmarkSiteManager.getActivityBenchmarkSites(activityID));


    // Set activity other sites
    activity.setOtherLocations(activityOtherSiteManager.getActivityOtherSites(activityID));

    // Get the region list
    regions = regionManager.getRegionList();

    // Get the country list
    countries = countryManager.getCountryList();

    // Get the benchmark sites list
    // If the activity doesn't have benchmark sites, load the benchmark
    // sites for the countries loaded, if there is any
    if (activity.getBenchmarkSitesIds().size() == 0) {
      List<BenchmarkSite> benchmarkSitesTemp = new ArrayList<>();
      for (String countryID : activity.getCountriesIds()) {
        benchmarkSitesTemp.addAll(Arrays.asList(benchmarkSiteManager.getActiveBenchmarkSitesByCountry(countryID)));
      }

      benchmarkSites = benchmarkSitesTemp.toArray(new BenchmarkSite[benchmarkSitesTemp.size()]);
    }

    // A region is active if the region is selected or if there are
    // selected countries in that region.
    activeRegions = new ArrayList<>();
    for (Region region : regions) {
      if (activity.getRegionsIds().contains(String.valueOf(region.getId()))
        || (activity.getCountriesIdsByRegion(region.getId()).size() > 0)) {
        activeRegions.add(region.getId());
      }
    }

    // If the workplan was submitted before the user can't save new information
    Submission submission =
      submissionManager.getSubmission(getCurrentUser().getLeader(), getCurrentPlanningLogframe(),
        APConstants.PLANNING_SECTION);
    canSubmit = (submission == null) ? true : false;

    if (getRequest().getMethod().equalsIgnoreCase("post")) {
      activity.getOtherLocations().clear();
      activity.getRegions().clear();
      // Global and regions selected values are set to false to prevent the value is always true.
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
      LOG.warn("-- save() > There was a problem deleting the countries for activity {}.", activityID);
    }

    // Delete the activity regions
    result = activityRegionManager.deleteActivityRegions(activityID);
    if (!result) {
      saved = false;
      LOG.warn("-- save() > There was a problem deleting the countries for activity {}.", activityID);
    }

    // Delete the activity other sites
    result = activityOtherSiteManager.deleteActivityOtherSites(activityID);
    // If there was a problem deleting the other sites show it in the log.
    if (!result) {
      saved = false;
      LOG.warn("-- save() > There was a problem deleting the other sites for activity {}.", activityID);
    }

    // Delete the activity benchmark sites
    result = activityBenchmarkSiteManager.deleteActivityBenchmarkSites(activityID);
    // If there was a problem deleting the benchmark sites show it in the log.
    if (!result) {
      saved = false;
      LOG.warn("-- save() > There was a problem deleting the benchmark sites for activity {}.", activityID);
    }

    // If the activity is not global, save the values selected.
    if (!activity.isGlobal()) {

      // Save the regions
      activityRegionManager.saveActivityRegions(activity.getRegions(), activityID);

      // Save the countries
      activityCountryManager.saveActivityCountries(activity.getCountries(), activityID);

      // Save the other sites
      activityOtherSiteManager.saveActivityOtherSites(activity.getOtherLocations(), activityID);

      // Save the benchmark sites
      activityBenchmarkSiteManager.saveActivityBenchmarkSites(activity.getBsLocations(), activityID);
    }


    if (saved) {

      if (validationMessages.toString().isEmpty()) {
        addActionMessage(getText("saving.success", new String[] {getText("planning.locations")}));
      } else {
        String finalMessage = getText("saving.success", new String[] {getText("planning.locations")});
        finalMessage += " " + getText("savind.fields.however") + " ";
        finalMessage += validationMessages.toString();

        AddActionWarning(finalMessage);
      }

      LOG.info("-- save() > User {} save locations for activity {} successfully", this.getCurrentUser().getEmail(),
        activityID);
      return SUCCESS;
    } else {
      LOG.warn("-- save() > User {} had problems to save locations for activity {}.", this.getCurrentUser().getEmail(),
        activityID);
      addActionError(getText("saving.problem"));
      return INPUT;
    }
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  @Override
  public void validate() {
    boolean problem = false;
    if (save) {

      // Activity should be global or have at least one location
      if (!activity.isGlobal() && activity.getCountries().isEmpty() && activity.getRegions().isEmpty()
        && activity.getOtherLocations().isEmpty()) {
        validationMessages.append(getText("planning.locations.validation.atLeastOneLocation"));
      }

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
        LOG.info(
          "-- validate() > User {} try to save the locations for activity {} but don't fill all required fields.", this
            .getCurrentUser().getEmail(), activityID);
        addActionError(getText("saving.fields.required"));
      }
    }
  }
}
