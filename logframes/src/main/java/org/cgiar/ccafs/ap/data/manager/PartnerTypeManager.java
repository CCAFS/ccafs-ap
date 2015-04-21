package org.cgiar.ccafs.ap.data.manager;

import com.google.inject.ImplementedBy;
import org.cgiar.ccafs.ap.data.manager.impl.PartnerTypeManagerImpl;
import org.cgiar.ccafs.ap.data.model.PartnerType;

@ImplementedBy(PartnerTypeManagerImpl.class)
public interface PartnerTypeManager {

  /**
   * Get the partner types list.
   * 
   * @return an array of Partner type objects or null if no data found.
   */
  public PartnerType[] getPartnerTypeList();

}
