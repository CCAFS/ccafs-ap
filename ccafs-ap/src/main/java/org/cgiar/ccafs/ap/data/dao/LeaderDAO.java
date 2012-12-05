package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLLeaderDAO;

import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLLeaderDAO.class)
public interface LeaderDAO {

  /**
   * Find the Leader of a given activity id.
   * 
   * @param activityID - activity identifier.
   * @return a Map with the leader data or null if no leader was found.
   */
  public Map<String, String> getActivityLeader(int activityID);

  /**
   * Find the Leader of a given user id.
   * 
   * @param activityID - user identifier.
   * @return a Map with the leader data or null if no leader was found.
   */
  public Map<String, String> getUserLeader(int userID);

}
