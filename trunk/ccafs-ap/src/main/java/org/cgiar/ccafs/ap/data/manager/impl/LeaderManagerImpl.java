package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.LeaderDAO;
import org.cgiar.ccafs.ap.data.manager.LeaderManager;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.LeaderType;

import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeaderManagerImpl implements LeaderManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(LeaderManagerImpl.class);
  private LeaderDAO leaderDAO;

  @Inject
  public LeaderManagerImpl(LeaderDAO leaderDAO) {
    this.leaderDAO = leaderDAO;
  }

  @Override
  public Leader getActivityLeader(int activityID) {
    Map<String, String> leaderData = leaderDAO.getActivityLeader(activityID);
    if (!leaderData.isEmpty()) {
      Leader leader = new Leader();
      leader.setId(Integer.parseInt(leaderData.get("id")));
      leader.setName(leaderData.get("name"));
      LeaderType leaderType = new LeaderType();
      leaderType.setId(Integer.parseInt(leaderData.get("leader_type_id")));
      leaderType.setName(leaderData.get("leader_type_name"));
      leader.setLeaderType(leaderType);
      LOG.debug("Loaded activity leader for activity {}.", activityID);
      return leader;
    }
    LOG.warn("Activity leader wasn't found for activity {}.", activityID);
    return null;
  }

  @Override
  public Leader getUserLeader(int userID) {
    Map<String, String> leaderData = leaderDAO.getUserLeader(userID);
    if (!leaderData.isEmpty()) {
      Leader leader = new Leader();
      leader.setId(Integer.parseInt(leaderData.get("id")));
      leader.setName(leaderData.get("name"));
      LeaderType leaderType = new LeaderType();
      leaderType.setId(Integer.parseInt(leaderData.get("leader_type_id")));
      leaderType.setName(leaderData.get("leader_type_name"));
      leader.setLeaderType(leaderType);
      return leader;
    }
    return null;
  }

}
