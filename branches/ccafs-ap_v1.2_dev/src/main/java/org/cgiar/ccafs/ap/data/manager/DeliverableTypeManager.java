package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.DeliverableTypeManagerImpl;
import org.cgiar.ccafs.ap.data.model.DeliverableType;

import com.google.inject.ImplementedBy;

@ImplementedBy(DeliverableTypeManagerImpl.class)
public interface DeliverableTypeManager {


  /**
   * Get the deliverable type identified with the given id.
   * 
   * @param id - Identifier.
   * @return a DeliverableType object or null if nothing was found.
   */
  public Object getDeliverableType(String id);

  /**
   * Get all the deliverables types
   * 
   * @return a List whit all the Deliverable types.
   */
  public DeliverableType[] getDeliverableTypes();
}
