package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.StatusManagerImpl;
import org.cgiar.ccafs.ap.data.model.Status;

import com.google.inject.ImplementedBy;

@ImplementedBy(StatusManagerImpl.class)
public interface StatusManager {

  /**
   * Find the status object identified with the given id.
   * 
   * @param id - Status identifier.
   * @return a Status object identified with the given id or null if nothing found.
   */
  public Status getStatus(String id);

  /**
   * Get the status list.
   * 
   * @return an array of Status objects or null if no data found.
   */
  public Status[] getStatusList();

}
