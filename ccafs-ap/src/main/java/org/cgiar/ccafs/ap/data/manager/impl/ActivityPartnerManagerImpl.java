package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ActivityPartnerDAO;
import org.cgiar.ccafs.ap.data.manager.ActivityPartnerManager;
import org.cgiar.ccafs.ap.data.model.ActivityPartner;
import org.cgiar.ccafs.ap.data.model.Partner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class ActivityPartnerManagerImpl implements ActivityPartnerManager {

  private ActivityPartnerDAO activityPartnerDAO;

  @Inject
  public ActivityPartnerManagerImpl(ActivityPartnerDAO activityPartnerDAO) {
    this.activityPartnerDAO = activityPartnerDAO;
  }

  @Override
  public List<ActivityPartner> getActivityPartners(int activityID) {
    List<Map<String, String>> activityPartnerDataList = activityPartnerDAO.getActivityPartnersList(activityID);
    Map<String, String> activityPartnerData;
    ArrayList<ActivityPartner> activityPartnerList = new ArrayList<ActivityPartner>();
    for (int c = 0; c < activityPartnerDataList.size(); c++) {
      activityPartnerData = activityPartnerDataList.get(c);
      ActivityPartner activityPartner = new ActivityPartner();
      activityPartner.setId(Integer.parseInt(activityPartnerData.get("id")));
      activityPartner.setContactName(activityPartnerData.get("contact_name"));
      activityPartner.setContactEmail(activityPartnerData.get("contact_email"));
      // Partner
      Partner partner = new Partner();
      partner.setId(Integer.parseInt(activityPartnerData.get("partner_id")));
      partner.setAcronym(activityPartnerData.get("partner_acronym"));
      partner.setName(activityPartnerData.get("partner_name"));

      activityPartner.setPartner(partner);
      activityPartnerList.add(activityPartner);
    }
    if (activityPartnerDataList.size() > 0) {
      return activityPartnerList;
    }
    return null;
  }

}
