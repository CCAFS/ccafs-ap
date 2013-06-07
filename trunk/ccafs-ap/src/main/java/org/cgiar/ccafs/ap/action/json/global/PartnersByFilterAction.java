package org.cgiar.ccafs.ap.action.json.global;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.PartnerManager;
import org.cgiar.ccafs.ap.data.model.Partner;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PartnersByFilterAction extends BaseAction {

  private static final long serialVersionUID = 1946972405939522041L;

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(PartnersByFilterAction.class);

  // Model
  private Partner[] partners;
  private String countryID;
  private String partnerTypeID;

  // Managers
  private PartnerManager partnerManager;

  @Inject
  public PartnersByFilterAction(APConfig config, LogframeManager logframeManager, PartnerManager partnerManager) {
    super(config, logframeManager);
    this.partnerManager = partnerManager;
  }

  @Override
  public String execute() throws Exception {
    partners = partnerManager.getPartnersByFilter(countryID, partnerTypeID);
    return SUCCESS;
  }

  public Partner[] getPartners() {
    return partners;
  }

  @Override
  public void prepare() throws Exception {
    // If there is a country ID take its values
    countryID = StringUtils.trim(this.getRequest().getParameter(APConstants.COUNTRY_REQUEST_ID));
    countryID = (countryID == null) ? "" : countryID;

    // If there is a partner type ID take its values
    partnerTypeID = StringUtils.trim(this.getRequest().getParameter(APConstants.PARTNER_TYPE_REQUEST_ID));
    partnerTypeID = (partnerTypeID == null) ? "" : partnerTypeID;
  }

}
