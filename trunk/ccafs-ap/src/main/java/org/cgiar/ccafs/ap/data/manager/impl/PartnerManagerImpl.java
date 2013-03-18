package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ActivityPartnerDAO;
import org.cgiar.ccafs.ap.data.dao.PartnerDAO;
import org.cgiar.ccafs.ap.data.manager.PartnerManager;
import org.cgiar.ccafs.ap.data.model.Partner;
import org.cgiar.ccafs.ap.data.model.PartnerType;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PartnerManagerImpl implements PartnerManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(PartnerManagerImpl.class);
  private PartnerDAO partnerDAO;

  @Inject
  public PartnerManagerImpl(PartnerDAO partnerDAO, ActivityPartnerDAO activityPartnerDAO) {
    this.partnerDAO = partnerDAO;
  }

  @Override
  public Partner[] getAllPartners() {
    List<Map<String, String>> partnerList = partnerDAO.getAllPartners();
    Partner[] partners = new Partner[partnerList.size()];
    for (int c = 0; c < partners.length; c++) {
      Map<String, String> partnerData = partnerList.get(c);
      partners[c] = new Partner();
      partners[c].setId(Integer.parseInt(partnerData.get("id")));
      partners[c].setAcronym(partnerData.get("acronym"));
      partners[c].setName(partnerData.get("name"));
      // Partner Type
      PartnerType partnerType = new PartnerType();
      partnerType.setId(Integer.parseInt(partnerData.get("partner_type_id")));
      partnerType.setName(partnerData.get("partner_type_acronym"));
      partners[c].setType(partnerType);

    }
    if (partnerList.size() == 0) {
      LOG.warn("Partner list loaded is empty");
      return null;
    }
    LOG.debug("Partner list loaded successfully");
    return partners;
  }


  @Override
  public Partner getPartner(int id) {
    Map<String, String> partnerData = partnerDAO.getPartner(id);
    if (partnerData != null) {
      Partner partner = new Partner();
      partner.setId(id);
      partner.setAcronym(partnerData.get("acronym"));
      partner.setName(partnerData.get("name"));
      LOG.debug("Loaded information about partner identified by {}", id);
      return partner;
    }
    LOG.warn("Partner identified by {} wasn't found", id);
    return null;
  }
}
