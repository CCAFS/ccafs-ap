package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ActivityPartnerDAO;
import org.cgiar.ccafs.ap.data.dao.PartnerDAO;
import org.cgiar.ccafs.ap.data.manager.PartnerManager;
import org.cgiar.ccafs.ap.data.model.Partner;
import org.cgiar.ccafs.ap.data.model.PartnerType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class PartnerManagerImpl implements PartnerManager {

  private PartnerDAO partnerDAO;
  private ActivityPartnerDAO activityPartnerDAO;

  @Inject
  public PartnerManagerImpl(PartnerDAO partnerDAO, ActivityPartnerDAO activityPartnerDAO) {
    this.partnerDAO = partnerDAO;
    this.activityPartnerDAO = activityPartnerDAO;
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
      return null;
    }
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
      return partner;
    }
    return null;
  }


  @Override
  public ArrayList<Partner> getPartners(int activityID) {
    // TODO Since the model changed, this method must be rewritten.

    // List<Map<String, String>> activityPartnerDataList;
    // List<Map<String, String>> partnerDataList = partnerDAO.getPartnersList(activityID);
    //
    // Map<String, String> partnerData;
    // ArrayList<Partner> partnerList = new ArrayList<Partner>();
    //
    // if (partnerDataList == null) {
    // return null;
    // }
    //
    // for (int c = 0; c < partnerDataList.size(); c++) {
    // partnerData = partnerDataList.get(c);
    // Partner partner = new Partner();
    // partner.setId(Integer.parseInt(partnerData.get("id")));
    // partner.setName(partnerData.get("name"));
    // partner.setAcronym(partnerData.get("acronym"));
    // partner.setCity(partnerData.get("city"));
    //
    // // Country
    // Country country = new Country(partnerData.get("country_id"), partnerData.get("country_name"));
    // partner.setCountry(country);
    //
    // // Partner type
    // PartnerType partnerType =
    // new PartnerType(Integer.parseInt(partnerData.get("partner_type_id")), partnerData.get("partner_type_name"));
    // partner.setType(partnerType);
    //
    // // Activity partner (Contact points)
    // activityPartnerDataList = activityPartnerDAO.getActivityPartnersList(activityID, partner.getId());
    //
    // if (activityPartnerDataList != null) {
    // ArrayList<ActivityPartner> activityPartners = new ArrayList<ActivityPartner>();
    // ActivityPartner activityPartner;
    //
    // for (int i = 0; i < activityPartnerDataList.size(); i++) {
    // activityPartner = new ActivityPartner();
    // activityPartner.setId(Integer.parseInt(activityPartnerDataList.get(i).get("id")));
    // activityPartner.setContactName(activityPartnerDataList.get(i).get("name"));
    // activityPartner.setContactEmail(activityPartnerDataList.get(i).get("email"));
    // activityPartners.add(activityPartner);
    // }
    //
    // partner.setContactPoints(activityPartners);
    // }
    //
    // partnerList.add(partner);
    // }
    //
    // if (partnerDataList.size() > 0) {
    // return partnerList;
    // }

    return null;
  }
}
