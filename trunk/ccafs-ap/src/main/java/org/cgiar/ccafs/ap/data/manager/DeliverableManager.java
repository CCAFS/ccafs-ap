package org.cgiar.ccafs.ap.data.manager;

import java.util.List;

import org.cgiar.ccafs.ap.data.manager.impl.DeliverableManagerImpl;
import org.cgiar.ccafs.ap.data.model.Deliverable;

import com.google.inject.ImplementedBy;

@ImplementedBy(DeliverableManagerImpl.class)
public interface DeliverableManager {


  /**
   * Get all the deliverables objects belongs to an activity identified
   * with the given id.
   * 
   * @param id
   * @return a Deliverables array or null if no deliverables was found.
   */
  public List<Deliverable> getDeliverables(int activityId);
}
