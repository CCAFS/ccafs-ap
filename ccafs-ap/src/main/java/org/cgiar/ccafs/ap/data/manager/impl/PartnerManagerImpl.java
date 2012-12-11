package org.cgiar.ccafs.ap.data.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.cgiar.ccafs.ap.data.dao.ActivityPartnerDAO;
import org.cgiar.ccafs.ap.data.dao.PartnerDAO;
import org.cgiar.ccafs.ap.data.manager.PartnerManager;
import org.cgiar.ccafs.ap.data.model.ActivityPartner;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Partner;
import org.cgiar.ccafs.ap.data.model.PartnerType;


public class PartnerManagerImpl implements PartnerManager {

  private PartnerDAO partnerDAO;
  private ActivityPartnerDAO activityPartnerDAO;

  @Inject
  public PartnerManagerImpl(PartnerDAO partnerDAO, ActivityPartnerDAO activityPartnerDAO) {
    this.partnerDAO = partnerDAO;
    this.activityPartnerDAO = activityPartnerDAO;
  }


  @Override
  public ArrayList<Partner> getPartners(int activityID) {
    List<Map<String, String>> activityPartnerDataList;
    List<Map<String, String>> partnerDataList = partnerDAO.getPartnersList(activityID);

    Map<String, String> partnerData;
    ArrayList<Partner> partnerList = new ArrayList<Partner>();

    if (partnerDataList == null) {
      return null;
    }

    for (int c = 0; c < partnerDataList.size(); c++) {
      partnerData = partnerDataList.get(c);
      Partner partner = new Partner();
      partner.setId(Integer.parseInt(partnerData.get("id")));
      partner.setName(partnerData.get("name"));
      partner.setAcronym(partnerData.get("acronym"));
      partner.setCity(partnerData.get("city"));

      // Country
      Country country = new Country(partnerData.get("country_id"), partnerData.get("country_name"));
      partner.setCountry(country);

      // Partner type
      PartnerType partnerType =
        new PartnerType(Integer.parseInt(partnerData.get("partner_type_id")), partnerData.get("partner_type_name"));
      partner.setType(partnerType);

      // Activity partner (Contact points)
      activityPartnerDataList = activityPartnerDAO.getActivityPartnersList(activityID, partner.getId());

      if (activityPartnerDataList != null) {
        ArrayList<ActivityPartner> activityPartners = new ArrayList<ActivityPartner>();
        ActivityPartner activityPartner;

        for (int i = 0; i < activityPartnerDataList.size(); i++) {
          activityPartner = new ActivityPartner();
          activityPartner.setId(Integer.parseInt(activityPartnerDataList.get(i).get("id")));
          activityPartner.setName(activityPartnerDataList.get(i).get("name"));
          activityPartner.setEmail(activityPartnerDataList.get(i).get("email"));
          activityPartners.add(activityPartner);
        }

        partner.setContactPoints(activityPartners);
      }

      partnerList.add(partner);
    }

    if (partnerDataList.size() > 0) {
      return partnerList;
    }

    return null;
  }
}
