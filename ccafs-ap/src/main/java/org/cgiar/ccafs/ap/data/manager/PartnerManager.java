package org.cgiar.ccafs.ap.data.manager;

import java.util.ArrayList;

import com.google.inject.ImplementedBy;
import org.cgiar.ccafs.ap.data.manager.impl.PartnerManagerImpl;
import org.cgiar.ccafs.ap.data.model.Partner;

@ImplementedBy(PartnerManagerImpl.class)
public interface PartnerManager {

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
