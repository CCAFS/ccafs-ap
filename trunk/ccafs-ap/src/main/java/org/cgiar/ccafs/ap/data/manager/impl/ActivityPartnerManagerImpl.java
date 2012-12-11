package org.cgiar.ccafs.ap.data.manager.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.cgiar.ccafs.ap.data.dao.ActivityPartnerDAO;
import org.cgiar.ccafs.ap.data.manager.ActivityPartnerManager;
import org.cgiar.ccafs.ap.data.model.ActivityPartner;


public class ActivityPartnerManagerImpl implements ActivityPartnerManager {

  private ActivityPartnerDAO activityPartnerDAO;

  @Inject
  public ActivityPartnerManagerImpl(ActivityPartnerDAO activityPartnerDAO) {
    this.activityPartnerDAO = activityPartnerDAO;
  }

  @Override
  public ArrayList<ActivityPartner> getActivityPartnersList(int activityID, int partnerID) {
    List<Map<String, String>> activityPartnerDataList =
      activityPartnerDAO.getActivityPartnersList(activityID, partnerID);
    Map<String, String> activityPartnerData;
    ArrayList<ActivityPartner> activityPartnerList = new ArrayList<ActivityPartner>();
    for (int c = 0; c < activityPartnerDataList.size(); c++) {
      activityPartnerData = activityPartnerDataList.get(c);
      activityPartnerList.add(new ActivityPartner(Integer.parseInt(activityPartnerData.get("id")), activityPartnerData
        .get("name")));
    }
    if (activityPartnerDataList.size() > 0) {
      return activityPartnerList;
    }
    return null;
  }
}
