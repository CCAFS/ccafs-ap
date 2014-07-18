package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.UserDAO;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.util.MD5Convert;
import org.cgiar.ciat.auth.ADConexion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
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
    System.out.println("-- testing AD --- ");
    System.out.println(user.getUsername() + " : " + user.getPassword());
    ADConexion con = new ADConexion(user.getUsername(), user.getPassword(), "CIAT.CGIARAD.ORG");
    if (con != null) {
      System.out.println("-------- " + con.getAuthenticationMessage());
      con.closeContext();
    } else {
      System.out.println("Connection don't established");
    }
    return false;
  }

  @Override
  public User getUser(String email) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
    Map<String, String> userData = userDAO.getUser(email);

    if (!userData.isEmpty()) {

      User user = new User();
      user.setId(Integer.parseInt(userData.get("id")));
      user.setUsername(userData.get("username"));
      user.setPassword(userData.get("password"));
      user.setCcafsUser(userData.get("is_ccafs_user").equals("1"));
      user.setFirstName(userData.get("first_name"));
      user.setLastName(userData.get("last_name"));
      user.setEmail(userData.get("email"));
      user.setPhone(userData.get("phone"));
      try {
        user.setLastLogin(dateFormat.parse(userData.get("last_login")));
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
  public User login(String email, String password) {
    if (email != null && password != null) {
      User userFound = this.getUser(email);
      if (userFound != null) {
        System.out.println(userFound.isCcafsUser());
        if (userFound.isCcafsUser()) {
          // User brougth from the database has the pass
          // encrypted with MD5, to connect to the AD the pass
          // shouldn't be encrypted
          // userFound.setPassword(password);
          activeDirectoryLogin(userFound);
        } else {
          User tempUser = new User();
          tempUser.setMD5Password(password);
          System.out.println(password);
          System.out.println(userFound.getPassword());
          System.out.println(tempUser.getPassword());
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
    // userData.put("name", user.getName());
    userData.put("email", user.getEmail());
    userData.put("password", MD5Convert.stringToMD5(user.getPassword()));
    // userData.put("activity_leader_id", String.valueOf(user.getLeader().getId()));
    userData.put("role", String.valueOf(user.getRole()));

    return userDAO.saveUser(userData);
  }
}
