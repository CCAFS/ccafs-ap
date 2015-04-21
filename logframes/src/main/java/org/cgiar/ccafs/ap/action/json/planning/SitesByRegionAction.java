package org.cgiar.ccafs.ap.action.json.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.BenchmarkSiteManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.BenchmarkSite;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SitesByRegionAction extends BaseAction {

  private static final long serialVersionUID = -2263403278337110114L;

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(SitesByRegionAction.class);

  // Model
  private String regionID;
  private BenchmarkSite[] benchmarkSites;

  // Managers
  private BenchmarkSiteManager benchmarkSiteManager;

  @Inject
  public SitesByRegionAction(APConfig config, LogframeManager logframeManager, BenchmarkSiteManager benchmarkSiteManager) {
    super(config, logframeManager);
    this.benchmarkSiteManager = benchmarkSiteManager;
  }

  @Override
  public String execute() throws Exception {
    if (regionID == "") {
      return SUCCESS;
    }

    benchmarkSites = benchmarkSiteManager.getActiveBenchmarkSitesByRegion(String.valueOf(regionID));

    LOG.info("-- execute() > CCAFS sites of region '{}' were loaded successfully", regionID);
    return SUCCESS;
  }

  public BenchmarkSite[] getBenchmarkSites() {
    return benchmarkSites;
  }

  @Override
  public void prepare() throws Exception {

    // Verify if there is a activityID parameter
    if (this.getRequest().getParameter(APConstants.REGION_REQUEST_ID) == null) {
      regionID = "";
      return;
    }

    // If there is a parameter take its values
    regionID = StringUtils.trim(this.getRequest().getParameter(APConstants.REGION_REQUEST_ID));
  }

}
