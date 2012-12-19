package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.DeliverableStatusManagerImpl;
import org.cgiar.ccafs.ap.data.model.DeliverableStatus;

import com.google.inject.ImplementedBy;

@ImplementedBy(DeliverableStatusManagerImpl.class)
public interface DeliverableStatusManager {


  /**
   * Get all the deliverables status
   * 
   * @return a List whit all the Deliverable status.
   */
  public DeliverableStatus[] getDeliverableStatus();

  /**
   * Find the deliverable status object identified with the given id.
   * 
   * @param id - Deliverable status identifier.
   * @return a DeliverableStatus object identified with the given id or null if nothing found.
   */
  public DeliverableStatus getDeliverableStatus(String id);

}
