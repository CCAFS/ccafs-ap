package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.ActivityOtherSiteManagerImpl;
import org.cgiar.ccafs.ap.data.model.OtherSite;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ActivityOtherSiteManagerImpl.class)
public interface ActivityOtherSiteManager {

  /**
   * Get all the other sites related to the activity given
   * 
   * @param activityID - the activity identifier
   * @return a list of OtherSite objects with the information
   */
  public List<OtherSite> getActivityOtherSites(int activityID);
}
