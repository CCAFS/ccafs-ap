package org.cgiar.ccafs.ap.data.manager;

import java.util.ArrayList;

import com.google.inject.ImplementedBy;
import org.cgiar.ccafs.ap.data.manager.impl.ActivityPartnerManagerImpl;
import org.cgiar.ccafs.ap.data.model.ActivityPartner;

@ImplementedBy(ActivityPartnerManagerImpl.class)
public interface ActivityPartnerManager {


  /**
   * Get the status list.
   * 
   * @return an array of Status objects or null if no data found.
   */
  public ArrayList<ActivityPartner> getActivityPartnersList(int activityID, int partnerID);

}
