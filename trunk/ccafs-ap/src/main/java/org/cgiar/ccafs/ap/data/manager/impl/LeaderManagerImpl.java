package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.LeaderDAO;
import org.cgiar.ccafs.ap.data.manager.LeaderManager;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.LeaderType;

import java.util.Map;

import com.google.inject.Inject;


public class LeaderManagerImpl implements LeaderManager {

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
      return leader;
    }
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
