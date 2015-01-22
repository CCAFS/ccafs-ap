package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.UserDAO;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.LeaderType;
import org.cgiar.ccafs.ap.data.model.Region;
import org.cgiar.ccafs.ap.data.model.Theme;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.util.MD5Convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

  /**
   * This method make the login process against the active directory
   * if the user has an institutional account
   * 
   * @param user
   * @return true if it was successfully logged in. False otherwise
   */
  private boolean activeDirectoryLogin(User user) {
    boolean logued = false;

    if (user.getUsername() != null) {
      try {
        ADConexion con = new ADConexion(user.getUsername(), user.getPassword());
        if (con != null) {
          if (con.getLogin() != null) {
            logued = true;
          }
          con.closeContext();
        }
      } catch (Exception e) {
        LOG.error("Exception raised trying to log in the user {} against the active directory.", user.getId(),
          e.getMessage());
      }
    }
    return logued;

  }

  @Override
  public List<User> getAllUsers() {
    List<User> allUsers = new ArrayList<>();
    List<Map<String, String>> allUsersDataList = userDAO.getAllUsers();
    for (Map<String, String> pData : allUsersDataList) {
      User userData = new User();
      userData.setId(Integer.parseInt(pData.get("id")));
      userData.setName(pData.get("name"));
      userData.setEmail(pData.get("email"));
      // Adding object to the array.
      allUsers.add(userData);
    }
    return allUsers;
  }

  @Override
  public User getUser(String email) {
    Map<String, String> userData = userDAO.getUser(email);
    if (!userData.isEmpty()) {
      User user = new User();
      user.setId(Integer.parseInt(userData.get("id")));
      user.setName(userData.get("name"));
      user.setEmail(email);
      user.setUsername(userData.get("username"));
      user.setPassword(userData.get("password"));
      user.setCcafsUser(userData.get("is_ccafs_user").equals("1"));
      user.setRole(userData.get("role"));
      // Leader
      Leader leader = new Leader();
      leader.setId(Integer.parseInt(userData.get("leader_id")));
      leader.setName(userData.get("leader_name"));
      leader.setAcronym(userData.get("leader_acronym"));
      // Leader Type
      LeaderType leaderType = new LeaderType();
      leaderType.setId(Integer.parseInt(userData.get("leader_type_id")));
      leaderType.setName(userData.get("leader_type_name"));
      leader.setLeaderType(leaderType);
      // Region
      if (userData.get("region_id") != null) {
        Region region = new Region();
        region.setId(Integer.parseInt(userData.get("region_id")));
        region.setName(userData.get("region_name"));
        leader.setRegion(region);
      }
      // Theme
      if (userData.get("theme_id") != null) {
        Theme theme = new Theme();
        theme.setId(Integer.parseInt(userData.get("theme_id")));
        theme.setCode(userData.get("theme_code"));
        leader.setTheme(theme);
      }

      user.setLeader(leader);
      return user;
    }
    LOG.warn("Information related to the user {} wasn't found.", email);
    return null;
  }

  @Override
  public User getUserByEmail(String email) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
    Map<String, String> userData = userDAO.getUser(email);

    if (!userData.isEmpty()) {

      User user = new User();
      user.setId(Integer.parseInt(userData.get("id")));
      user.setPassword(userData.get("password"));
      user.setCcafsUser(userData.get("is_ccafs_user").equals("1"));
      user.setName(userData.get("name"));
      user.setEmail(userData.get("email"));
      user.setRole(userData.get("role"));
      user.setUsername(userData.get("username"));
      try {
        // If the user has never logged in, this value is null.
        if (userData.get("last_login") != null) {
          user.setLastLogin(dateFormat.parse(userData.get("last_login")));
        }
      } catch (ParseException e) {
        String msg = "There was an error parsing the last login of user " + user.getId() + ".";
        LOG.error(msg, e);
      }

      return user;
    }
    LOG.warn("Information related to the user {} wasn't found.", email);
    return null;
  }

  @Override
  public User getUserByUsername(String username) {
    String email = userDAO.getEmailByUsername(username);
    if (email != null) {
      return this.getUser(email);
    }
    return null;
  }

  @Override
  public User login(String email, String password) {
    if (email != null && password != null) {

      User userFound;
      // If user is logging-in with their email account.
      if (email.contains("@")) {
        userFound = this.getUser(email);
      } else {
        // if user is loggin with his username, we must attach the cgiar.org.
        userFound = this.getUserByUsername(email);
      }
      if (userFound != null) {
        if (userFound.isCcafsUser()) {
          // User brought from the database has the pass
          // encrypted with MD5, to connect to the AD the pass
          // shouldn't be encrypted
          userFound.setPassword(password);

          if (activeDirectoryLogin(userFound)) {
            // Encrypt the password again
            userFound.setMD5Password(password);
            return userFound;
          }
        } else {
          User tempUser = new User();
          tempUser.setMD5Password(password);
          if (userFound.getPassword().equals(tempUser.getPassword())) {
            return userFound;
          }
        }

      }
    }
    return null;
  }

  @Override
  public boolean saveLastLogin(User user) {
    Map<String, String> userData = new HashMap<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    userData.put("user_id", String.valueOf(user.getId()));
    userData.put("last_login", sdf.format(user.getLastLogin()));
    return userDAO.saveLastLogin(userData);
  }

  @Override
  public boolean saveUser(User user) {
    Map<String, String> userData = new HashMap<>();
    userData.put("name", user.getName());
    userData.put("email", user.getEmail());
    userData.put("password", MD5Convert.stringToMD5(user.getPassword()));
    userData.put("activity_leader_id", String.valueOf(user.getLeader().getId()));
    userData.put("role", String.valueOf(user.getRole()));

    return userDAO.saveUser(userData);
  }

  @Override
  public boolean updateCCAFSUser(int userId, String userName) {
    return userDAO.updateCCAFSUser(userId, userName);
  }
}
