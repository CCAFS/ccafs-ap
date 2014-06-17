/*
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
 */

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
  public SitesByCountryAction(APConfig config, LogframeManager logframeManager,
    BenchmarkSiteManager benchmarkSiteManager) {
    super(config, logframeManager);
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
