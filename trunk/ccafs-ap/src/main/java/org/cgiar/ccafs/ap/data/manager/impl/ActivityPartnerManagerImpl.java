package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ActivityPartnerDAO;
import org.cgiar.ccafs.ap.data.manager.ActivityPartnerManager;
import org.cgiar.ccafs.ap.data.model.ActivityPartner;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Partner;
import org.cgiar.ccafs.ap.data.model.PartnerType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ActivityPartnerManagerImpl implements ActivityPartnerManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ActivityPartnerManagerImpl.class);
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
      // Partner type
      PartnerType partnerType = new PartnerType();
      partnerType.setId(Integer.parseInt(activityPartnerData.get("partner_type_id")));
      partnerType.setName(activityPartnerData.get("partner_type_name"));
      partner.setType(partnerType);
      // Country
      Country country = new Country();
      country.setId(activityPartnerData.get("country_iso2"));
      country.setName(activityPartnerData.get("country_name"));
      partner.setCountry(country);

      activityPartner.setPartner(partner);
      activityPartnerList.add(activityPartner);
    }


    // LOG.debug("The activity partners for the activity {} loaded.", activityID);

    return activityPartnerList;
  }

  @Override
  public boolean removeActivityPartners(int activityID) {
    return activityPartnerDAO.removeActivityPartners(activityID);
  }

  @Override
  public boolean saveActivityPartners(List<ActivityPartner> activityPartners, int activityID) {
    boolean problem = false;
    List<Map<String, Object>> activityPartnersData = new ArrayList<>();
    for (ActivityPartner cp : activityPartners) {
      Map<String, Object> cpData = new HashMap<>();
      if (cp.getId() != -1) {
        cpData.put("id", cp.getId());
      } else {
        cpData.put("id", null);
      }
      cpData.put("partner_id", cp.getPartner().getId());
      cpData.put("activity_id", activityID);
      if (cp.getContactName().isEmpty()) {
        cpData.put("contact_name", null);
      } else {
        cpData.put("contact_name", cp.getContactName());
      }
      if (cp.getContactEmail().isEmpty()) {
        cpData.put("contact_email", null);
      } else {
        cpData.put("contact_email", cp.getContactEmail());
      }
      activityPartnersData.add(cpData);
    }

    // LOG.debug("The activity partners information for activity {} was send to the DAO to save it", activityID);
    problem = !activityPartnerDAO.saveActivityPartnerList(activityPartnersData);
    return !problem;
  }
}
