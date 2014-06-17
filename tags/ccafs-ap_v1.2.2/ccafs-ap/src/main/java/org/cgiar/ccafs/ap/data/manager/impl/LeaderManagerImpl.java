/*
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 */

package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.LeaderDAO;
import org.cgiar.ccafs.ap.data.manager.LeaderManager;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.LeaderType;
import org.cgiar.ccafs.ap.data.model.Region;
import org.cgiar.ccafs.ap.data.model.Theme;

import java.util.List;
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
      leader.setAcronym(leaderData.get("acronym"));
      // Leader type
      LeaderType leaderType = new LeaderType();
      leaderType.setId(Integer.parseInt(leaderData.get("leader_type_id")));
      leaderType.setName(leaderData.get("leader_type_name"));
      leader.setLeaderType(leaderType);

      if (leaderData.get("region_id") != null) {
        Region region = new Region();
        region.setId(Integer.parseInt(leaderData.get("region_id")));
        region.setName(leaderData.get("region_name"));
        leader.setRegion(region);
      }

      if (leaderData.get("theme_id") != null) {
        Theme theme = new Theme();
        theme.setId(Integer.parseInt(leaderData.get("theme_id")));
        theme.setCode(leaderData.get("theme_code"));
        leader.setTheme(theme);
      }

      return leader;
    }
    LOG.warn("Activity leader wasn't found for activity {}.", activityID);
    return null;
  }

  @Override
  public Leader[] getAllLeaders() {
    List<Map<String, String>> leadersData = leaderDAO.getAllLeaders();
    if (leadersData.size() > 0) {
      Leader[] leaders = new Leader[leadersData.size()];
      for (int c = 0; c < leadersData.size(); c++) {
        leaders[c] = new Leader();
        leaders[c].setId(Integer.parseInt(leadersData.get(c).get("id")));
        leaders[c].setName(leadersData.get(c).get("name"));
        leaders[c].setAcronym(leadersData.get(c).get("acronym"));
        LeaderType leaderType = new LeaderType();
        leaderType.setId(Integer.parseInt(leadersData.get(c).get("leader_type_id")));
        leaderType.setName(leadersData.get(c).get("leader_type_name"));
        leaders[c].setLeaderType(leaderType);
      }
      return leaders;
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

      if (leaderData.get("region_id") != null) {
        Region region = new Region();
        region.setId(Integer.parseInt(leaderData.get("region_id")));
        region.setName(leaderData.get("region_name"));
        leader.setRegion(region);
      }

      if (leaderData.get("theme_id") != null) {
        Theme theme = new Theme();
        theme.setId(Integer.parseInt(leaderData.get("theme_id")));
        theme.setCode(leaderData.get("theme_code"));
        leader.setTheme(theme);
      }

      return leader;
    }
    return null;
  }

}
