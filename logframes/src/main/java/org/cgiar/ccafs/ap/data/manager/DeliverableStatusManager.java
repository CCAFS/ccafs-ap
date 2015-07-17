package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.DeliverableStatusManagerImpl;
import org.cgiar.ccafs.ap.data.model.DeliverableStatus;

import com.google.inject.ImplementedBy;

@ImplementedBy(DeliverableStatusManagerImpl.class)
public interface DeliverableStatusManager {


  /**
   * Get all the deliverables status
   * 
   * @return a List whit all the Product status.
   */
  public DeliverableStatus[] getDeliverableStatus();

  /**
   * Find the deliverable status object identified with the given id.
   * 
   * @param id - Product status identifier.
   * @return a DeliverableStatus object identified with the given id or null if nothing found.
   */
  public DeliverableStatus getDeliverableStatus(String id);

  /**
   * Update the status of the deliverable identified with the given id.
   * 
   * @param id - deliverable id.
   * @param status - new status to be updated.
   * @return true if the update was successfully made, false if any problem appear.
   */
  public boolean setDeliverableStatus(int id, DeliverableStatus status);

}
