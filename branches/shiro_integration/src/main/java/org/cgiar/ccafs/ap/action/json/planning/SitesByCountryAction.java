package org.cgiar.ccafs.ap.action.json.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.BenchmarkSiteManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.BenchmarkSite;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SitesByCountryAction extends BaseAction {

  private static final long serialVersionUID = -269112198169563443L;

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(SitesByCountryAction.class);

  // Model
  private String countryID;
  private BenchmarkSite[] benchmarkSites;

  // Managers
  private BenchmarkSiteManager benchmarkSiteManager;

  @Inject
  public SitesByCountryAction(APConfig config, LogframeManager logframeManager, SecurityManager securityManager,
    BenchmarkSiteManager benchmarkSiteManager) {
    super(config, logframeManager, securityManager);
    this.benchmarkSiteManager = benchmarkSiteManager;
  }

  @Override
  public String execute() throws Exception {
    benchmarkSites = benchmarkSiteManager.getActiveBenchmarkSitesByCountry(countryID);

    LOG.info("-- execute() > CCAFS sites in country '{}' was loaded.", countryID);
    return SUCCESS;
  }

  public BenchmarkSite[] getBenchmarkSites() {
    return benchmarkSites;
  }

  @Override
  public void prepare() throws Exception {

    // Verify if there is a activityID parameter
    if (this.getRequest().getParameter(APConstants.COUNTRY_REQUEST_ID) == null) {
      countryID = "";
      return;
    }

    // If there is a parameter take its values
    countryID = StringUtils.trim(this.getRequest().getParameter(APConstants.COUNTRY_REQUEST_ID));
  }

}
