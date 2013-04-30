package org.cgiar.ccafs.ap.action.json.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.CountryManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.Country;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CountriesByRegionAction extends BaseAction {

  private static final long serialVersionUID = 5161337773231767226L;

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(CountriesByRegionAction.class);

  // Managers
  CountryManager countryManager;

  // Model
  private Country[] countries;
  private int regionID;

  @Inject
  public CountriesByRegionAction(APConfig config, LogframeManager logframeManager, CountryManager countryManager) {
    super(config, logframeManager);
    this.countryManager = countryManager;
  }

  @Override
  public String execute() throws Exception {

    countries = countryManager.getCountriesByRegion(String.valueOf(regionID));
    return SUCCESS;
  }

  public Country[] getCountries() {
    return countries;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    // Verify if there is a activityID parameter
    if (this.getRequest().getParameter(APConstants.REGION_REQUEST_ID) == null) {
      regionID = -1;
      return;
    }

    try {
      // If there is a parameter take its values
      regionID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.REGION_REQUEST_ID)));
    } catch (NumberFormatException e) {
      // If there was an error trying to parse the URL parameter
      LOG.error("There was an error trying to parse the regionID parameter");
      // Set an invalid value to the activityId to prevent the page load in execute function
      regionID = -1;
      return;
    }
  }

}
