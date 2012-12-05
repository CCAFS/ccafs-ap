package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.LeaderManagerImpl;
import org.cgiar.ccafs.ap.data.model.Leader;

import com.google.inject.ImplementedBy;

@ImplementedBy(LeaderManagerImpl.class)
public interface LeaderManager {

  /**
   * Find the Leader of a given activity id.
   * 
   * @param activityID - activity identifier.
   * @return a leader object or null if no leader was found.
   */
  public Leader getActivityLeader(int activityID);

  /**
   * Find the Leader of a given user id.
   * 
   * @param activityID - user identifier.
   * @return a leader object or null if no leader was found.
   */
  public Leader getUserLeader(int userID);

}
