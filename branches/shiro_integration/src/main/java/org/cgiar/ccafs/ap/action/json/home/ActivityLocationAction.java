package org.cgiar.ccafs.ap.action.json.home;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityBenchmarkSiteManager;
import org.cgiar.ccafs.ap.data.manager.ActivityCountryManager;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.ActivityOtherSiteManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.BenchmarkSite;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.OtherSite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ActivityLocationAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ActivityLocationAction.class);
  private static final long serialVersionUID = -7910041519418474107L;

  // Managers
  private ActivityManager activityManager;
  private ActivityCountryManager activityCountryManager;
  private ActivityBenchmarkSiteManager activityBenchmarkSiteManager;
  private ActivityOtherSiteManager activityOtherSiteManager;

  // Model
  private int activityID;
  private List<Country> countries;
  private List<BenchmarkSite> benchmarkSites;
  private List<OtherSite> otherSites;

  @Inject
  public ActivityLocationAction(APConfig config, LogframeManager logframeManager, SecurityManager securityManager,
    ActivityManager activityManager, ActivityCountryManager activityCountryManager,
    ActivityBenchmarkSiteManager activityBenchmarkSiteManager, ActivityOtherSiteManager activityOtherSiteManager) {
    super(config, logframeManager, securityManager);
    this.activityManager = activityManager;
    this.activityCountryManager = activityCountryManager;
    this.activityBenchmarkSiteManager = activityBenchmarkSiteManager;
    this.activityOtherSiteManager = activityOtherSiteManager;
  }

  @Override
  public String execute() throws Exception {
    super.execute();
    // Otherwise check if it is a valid activity identifier
    if (!activityManager.isValidId(activityID)) {
      // There is no countries to show
      return SUCCESS;
    }

    countries = activityCountryManager.getActvitiyCountries(activityID);
    benchmarkSites = activityBenchmarkSiteManager.getActivityBenchmarkSites(activityID);
    otherSites = activityOtherSiteManager.getActivityOtherSites(activityID);

    LOG.info("Activity locations for activity {} was loaded successfully", activityID);
    return SUCCESS;
  }

  public List<BenchmarkSite> getBenchmarkSites() {
    return benchmarkSites;
  }

  public List<Map<String, String>> getCountries() {
    List<Map<String, String>> la = new ArrayList<>();
    for (Country country : countries) {
      Map<String, String> a = new HashMap<>();
      a.put("id", country.getName());
      la.add(a);
    }
    return la;
  }

  public List<OtherSite> getOtherSites() {
    return otherSites;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    // Verify if there is a activityID parameter
    if (this.getRequest().getParameter(APConstants.PUBLIC_ACTIVITY_ID) == null) {
      activityID = -1;
      return;
    }

    try {
      // If there is a parameter take its values
      activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PUBLIC_ACTIVITY_ID)));
    } catch (NumberFormatException e) {
      // If there was an error trying to parse the URL parameter
      LOG.error("There was an error trying to parse the activityId parameter");
      // Set an invalid value to the activityId to prevent the page load in execute function
      activityID = -1;
      return;
    }
  }
}
