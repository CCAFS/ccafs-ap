package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.DeliverableTypeManagerImpl;
import org.cgiar.ccafs.ap.data.model.DeliverableType;

import com.google.inject.ImplementedBy;

@ImplementedBy(DeliverableTypeManagerImpl.class)
public interface DeliverableTypeManager {


  /**
   * Get all the deliverables types
   * 
   * @return a List whit all the Deliverable types.
   */
  public DeliverableType[] getDeliverableTypes();
}
