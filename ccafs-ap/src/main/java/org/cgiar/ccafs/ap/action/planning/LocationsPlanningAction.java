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
import org.cgiar.ccafs.ap.data.model.Region;

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
  private List<String> regionsSelected;

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

  public Region[] getRegions() {
    return regions;
  }

  public List<String> getRegionsSelected() {
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

    if (getRequest().getMethod().equalsIgnoreCase("save")) {
    }
  }

  @Override
  public String save() {
    boolean saved = false;

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

  public void setRegionsSelected(List<String> regionsSelected) {
    this.regionsSelected = regionsSelected;
  }

  @Override
  public void validate() {
    boolean problem = false;

    if (save) {
      if (problem) {
        addActionError(getText("saving.fields.required"));
      }
    }
  }
}
