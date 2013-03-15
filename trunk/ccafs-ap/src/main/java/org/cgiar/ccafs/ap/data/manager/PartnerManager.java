package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.PartnerManagerImpl;
import org.cgiar.ccafs.ap.data.model.Partner;

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
   * Get the partner identified with the given id.
   * 
   * @param id - identifier.
   * @return a Partner object or null if no partner were found.
   */
  public Partner getPartner(int id);
}
