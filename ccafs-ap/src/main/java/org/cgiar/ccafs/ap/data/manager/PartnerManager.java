package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.PartnerManagerImpl;
import org.cgiar.ccafs.ap.data.model.Partner;

import java.util.ArrayList;

import com.google.inject.ImplementedBy;

@ImplementedBy(PartnerManagerImpl.class)
public interface PartnerManager {

  /**
   * Get all partners.
   * 
   * @return an array of Partner objects, or null if there are no partners.
   */
  public Partner[] getAllPartners();

  /**
   * Find the partners of the activity identified with the
   * given identifier.
   * 
   * @param activityID - Activity identifier.
   * @return a list of partners of the activity identified
   *         with the given id or null if nothing found.
   */
  public ArrayList<Partner> getPartners(int activityID);

}
