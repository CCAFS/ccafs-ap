package org.cgiar.ccafs.ap.action.summaries;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.utils.APConfig;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PartnersXMLAction extends BaseAction {

  private static final long serialVersionUID = -6383250876172016481L;

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(PartnersXMLAction.class);

  // Managers
  // private PartnerManager partnerManager;
  // private ThemeManager themeManager;

  // Models
  // private Partner[] partners;
  // private Map<Integer, Theme[]> themesByPartner;
  // private int year;

  @Inject
  public PartnersXMLAction(APConfig config/*
   * , LogframeManager logframeManager, PartnerManager partnerManager,
   * ThemeManager themeManager
   */) {
    super(config);
    // this.partnerManager = partnerManager;
    // this.themeManager = themeManager;
  }

  @Override
  public String execute() throws Exception {
    return super.execute();
  }

  // public Partner[] getPartners() {
  // return partners;
  // }
  //
  // public Map<Integer, Theme[]> getThemesByPartner() {
  // return themesByPartner;
  // }
  //
  // public boolean isPartnerActive(int partnerID) {
  // return partnerManager.isCurrentlyActive(partnerID, year);
  // }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    // year = getCurrentReportingLogframe().getYear();

    LOG.info("The Partner XML file with partner list is being generated.");
    // partners = partnerManager.getPartnerForXML();

    // if (partners == null) {
    // partners = new Partner[0];
    // } else {
    // themesByPartner = new HashMap<Integer, Theme[]>();
    // // For each partner get the list of themes in which the partner works
    // for (Partner partner : partners) {
    // Theme[] themes = themeManager.getThemesByPartner(partner);
    // if (themes != null) {
    // themesByPartner.put(partner.getId(), themes);
    // }
    // }
    // }
  }
}