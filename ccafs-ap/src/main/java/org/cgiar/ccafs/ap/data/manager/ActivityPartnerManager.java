package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.ActivityPartnerManagerImpl;
import org.cgiar.ccafs.ap.data.model.ActivityPartner;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ActivityPartnerManagerImpl.class)
public interface ActivityPartnerManager {


  /**
   * Get a list of activity partners that belong to a specified activity.
   * 
   * @param activityID - activity identifier.
   * @return a list of ActivityPartner objects.
   */
  public List<ActivityPartner> getActivityPartners(int activityID);

}
