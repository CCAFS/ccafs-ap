package org.cgiar.ccafs.ap.data.manager;

import com.google.inject.ImplementedBy;
import org.cgiar.ccafs.ap.data.manager.impl.DeliverableStatusManagerImpl;
import org.cgiar.ccafs.ap.data.model.DeliverableStatus;

@ImplementedBy(DeliverableStatusManagerImpl.class)
public interface DeliverableStatusManager {


  /**
   * Get all the deliverables status
   * 
   * @return a List whit all the Deliverable status.
   */
  public DeliverableStatus[] getDeliverableStatus();
}
