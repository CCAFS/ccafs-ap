package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.ActivityOtherSiteManagerImpl;
import org.cgiar.ccafs.ap.data.model.OtherSite;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ActivityOtherSiteManagerImpl.class)
public interface ActivityOtherSiteManager {

  /**
   * Delete the other sites related with the activity given.
   * 
   * @param activityID - The activity identifier
   * @return true if it was successfully deleted. False otherwise.
   */
  public boolean deleteActivityOtherSites(int activityID);

  /**
   * Get all the other sites related to the activity given
   * 
   * @param activityID - the activity identifier
   * @return a list of OtherSite objects with the information
   */
  public List<OtherSite> getActivityOtherSites(int activityID);

  /**
   * Save the list of other sites into the DAO
   * 
   * @param otherSites - The data to be saved
   * @param activityID - The activity identifier
   * @return true if ALL the otherSites were successfully saved. False otherwise.
   */
  public boolean saveActivityOtherSites(List<OtherSite> otherSites, int activityID);
}
