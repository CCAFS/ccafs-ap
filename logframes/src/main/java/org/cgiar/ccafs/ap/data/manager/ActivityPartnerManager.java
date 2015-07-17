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

  /**
   * Remove all activity parters that belong to a specified activity.
   * 
   * @param activityID - Activity identifier
   * @return true if the remove process was successfully made, false otherwise.
   */
  public boolean removeActivityPartners(int activityID);

  /**
   * Save into the DAO the list of activity partners.
   * 
   * @param activityPartners - List of ActivityPartner objects.
   * @param activityID - Activity identifier.
   * @return true if the list was successfully saved, false otherwise.
   */
  public boolean saveActivityPartners(List<ActivityPartner> activityPartners, int activityID);

}
