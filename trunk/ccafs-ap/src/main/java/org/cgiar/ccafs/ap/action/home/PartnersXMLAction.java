package org.cgiar.ccafs.ap.action.home;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.PartnerManager;
import org.cgiar.ccafs.ap.data.model.Partner;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PartnersXMLAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(PartnersXMLAction.class);
  private static final long serialVersionUID = 6086474589587634639L;

  // Managers
  private PartnerManager partnerManager;

  // Models
  private Partner[] partners;

  @Inject
  public PartnersXMLAction(APConfig config, LogframeManager logframeManager, PartnerManager partnerManager) {
    super(config, logframeManager);
    this.partnerManager = partnerManager;
  }

  @Override
  public String execute() throws Exception {
    return super.execute();
  }

  public Partner[] getPartners() {
    return partners;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    LOG.info("The Partner XML file with partner list is being generated.");

    partners = partnerManager.getPartnerForXML();
    if (partners == null) {
      partners = new Partner[0];
    }
  }
}