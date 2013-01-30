package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.DeliverableManagerImpl;
import org.cgiar.ccafs.ap.data.model.Deliverable;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(DeliverableManagerImpl.class)
public interface DeliverableManager {


  /**
   * Add a deliverable to the database which its corresponding file formats.
   * 
   * @param deliverable - Deliverable object.
   * @param activityID - Activity identifier in which the deliverable belongs to.
   * @return true y the deliverable was successfully added, false otherwise.
   */
  public boolean addDeliverable(Deliverable deliverable, int activityID);

  /**
   * Get all the deliverables objects belongs to an activity identified
   * with the given id.
   * 
   * @param id
   * @return a Deliverables array or null if no deliverables was found.
   */
  public List<Deliverable> getDeliverables(int activityId);

  /**
   * Remove all those not expected deliverables that belongs to the specified activity id.
   * 
   * @param activityID - activity identifier.
   * @return true if all the not expected deliverables were removed, or false if any problem occur.
   */
  public boolean removeNotExpected(int activityID);
}
