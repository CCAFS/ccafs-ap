package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.UserDAO;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.LeaderType;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserManagerImp implements UserManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(UserManagerImp.class);
  private UserDAO userDAO;

  @Inject
  public UserManagerImp(UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @Override
  public User getUser(String email) {
    Map<String, String> userData = userDAO.getUser(email);
    if (!userData.isEmpty()) {
      User user = new User();
      user.setId(Integer.parseInt(userData.get("id")));
      user.setEmail(email);
      user.setMD5Password(userData.get("password"));
      user.setRole(userData.get("role"));
      Leader leader = new Leader();
      leader.setId(Integer.parseInt(userData.get("leader_id")));
      leader.setName(userData.get("leader_name"));
      leader.setAcronym(userData.get("leader_acronym"));
      LeaderType leaderType = new LeaderType();
      leaderType.setId(Integer.parseInt(userData.get("leader_type_id")));
      leaderType.setName(userData.get("leader_type_name"));
      leader.setLeaderType(leaderType);
      user.setLeader(leader);
      LOG.debug("Information related to the user {} successfully loaded.", email);
      return user;
    }
    LOG.warn("Information related to the user {} wasn't found.", email);
    return null;
  }

  @Override
  public User login(String email, String password) {
    if (email != null && password != null) {
      User userFound = this.getUser(email);
      if (userFound != null) {
        User temp = new User();
        temp.setMD5Password(password);
        if (userFound.getPassword().equals(temp.getPassword())) {
          return userFound;
        }
      }
    }
    return null;
  }


}
