/*****************************************************************
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
 *****************************************************************/
package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.UserDAO;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.utils.MD5Convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hernán David Carvajal.
 */
public class UserManagerImp implements UserManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(UserManagerImp.class);

  // DAO
  private UserDAO userDAO;

  // Managers
  private InstitutionManager institutionManager;

  @Inject
  public UserManagerImp(UserDAO userDAO, InstitutionManager institutionManager) {
    this.userDAO = userDAO;
    this.institutionManager = institutionManager;
  }


  @Override
  public List<User> getAllEmployees() {
    List<User> employees = new ArrayList<>();
    List<Map<String, String>> employeesDataList = userDAO.getAllEmployees();
    for (Map<String, String> eData : employeesDataList) {
      User employee = new User();
      employee.setId(Integer.parseInt(eData.get("id")));
      employee.setFirstName(eData.get("first_name"));
      employee.setLastName(eData.get("last_name"));
      employee.setEmail(eData.get("email"));
      // Institution
      if (eData.get("institution_id") != null) {
        employee
          .setCurrentInstitution(institutionManager.getInstitution(Integer.parseInt(eData.get("institution_id"))));
      }

      // Adding object to the array.
      employees.add(employee);
    }
    return employees;
  }

  @Override
  public List<User> getAllOwners() {
    List<User> projectContacts = new ArrayList<>();
    List<Map<String, String>> projectContactsDataList = userDAO.getAllOwners();
    for (Map<String, String> pData : projectContactsDataList) {
      // User
      User projectContact = new User();
      projectContact.setId(Integer.parseInt(pData.get("id")));
      projectContact.setFirstName(pData.get("first_name"));
      projectContact.setLastName(pData.get("last_name"));
      projectContact.setEmail(pData.get("email"));
      // Adding object to the array.
      projectContacts.add(projectContact);
    }
    return projectContacts;
  }

  @Override
  public List<User> getAllOwners(IPProgram program) {
    List<User> projectContacts = new ArrayList<>();
    List<Map<String, String>> projectContactsDataList = userDAO.getAllOwners(program.getId());
    for (Map<String, String> pData : projectContactsDataList) {
      // User
      User projectContact = new User();
      projectContact.setId(Integer.parseInt(pData.get("id")));
      projectContact.setFirstName(pData.get("first_name"));
      projectContact.setLastName(pData.get("last_name"));
      projectContact.setEmail(pData.get("email"));

      // Institution
      projectContact
      .setCurrentInstitution(institutionManager.getInstitution(Integer.parseInt(pData.get("institution_id"))));
      // Adding object to the array.
      projectContacts.add(projectContact);
    }
    return projectContacts;
  }

  @Override
  public List<User> getAllUsers() {
    List<User> projectLeaders = new ArrayList<>();
    List<Map<String, String>> projectLeadersDataList = userDAO.getAllUsers();
    for (Map<String, String> pData : projectLeadersDataList) {
      User projectLeader = new User();
      projectLeader.setId(Integer.parseInt(pData.get("id")));
      projectLeader.setFirstName(pData.get("first_name"));
      projectLeader.setLastName(pData.get("last_name"));
      projectLeader.setEmail(pData.get("email"));
      // Adding object to the array.
      projectLeaders.add(projectLeader);
    }
    return projectLeaders;
  }


  @Override
  public int getEmployeeID(User user) {
    int userId = user.getId();
    int institutionId = user.getCurrentInstitution().getId();
    // TODO HC - Adjust this code, perhaps we can delete it.
    int roleId = -1; // user.getRole().getId();
    int result = userDAO.getEmployeeID(userId, institutionId, roleId);

    return result;
  }

  @Override
  public User getOwner(int ownerId) {

    Map<String, String> userData = userDAO.getOwner(ownerId);
    // User
    User owner = new User();
    owner.setId(Integer.parseInt(userData.get("id")));
    owner.setFirstName(userData.get("first_name"));
    owner.setLastName(userData.get("last_name"));
    owner.setEmail(userData.get("email"));
    // Institution
    owner.setCurrentInstitution(institutionManager.getInstitution(Integer.parseInt(userData.get("institution_id"))));

    return owner;
  }

  @Override
  public User getOwnerByProjectId(int projectID) {
    Map<String, String> userData = userDAO.getOwnerByProjectId(projectID);
    if (!userData.isEmpty()) {
      User user = new User();
      user.setId(Integer.parseInt(userData.get("id")));
      user.setFirstName(userData.get("first_name"));
      user.setLastName(userData.get("last_name"));
      user.setEmail(userData.get("email"));
      // Institution
      user.setCurrentInstitution(institutionManager.getInstitution(Integer.parseInt(userData.get("institution_id"))));

      return user;
    }
    LOG.warn("Information related to the user with Project id {} wasn't found.", projectID);

    return null;
  }


  @Override
  public User getUser(int userId) {
    Map<String, String> userData = userDAO.getUser(userId);
    if (!userData.isEmpty()) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
      User user = new User();
      user.setId(userId);
      user.setPassword(userData.get("password"));
      user.setCcafsUser(userData.get("is_ccafs_user").equals("1"));
      user.setFirstName(userData.get("first_name"));
      user.setLastName(userData.get("last_name"));
      user.setEmail(userData.get("email"));
      user.setPhone(userData.get("phone"));
      try {
        if (userData.get("last_login") != null) {
          user.setLastLogin(dateFormat.parse(userData.get("last_login")));
        } else {
          user.setLastLogin(null);
        }
      } catch (ParseException e) {
        String msg = "There was an error parsing the last login date of user " + user.getId() + ".";
        LOG.error(msg, e);
      }
      return user;
    }
    LOG.warn("Information related to the user with id {} wasn't found.", userId);

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
      user.setFirstName(userData.get("first_name"));
      user.setLastName(userData.get("last_name"));
      user.setEmail(userData.get("email"));
      user.setActive(userData.get("is_active").equals("1"));
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
      return this.getUserByEmail(email);
    }
    return null;
  }

  @Override
  public User login(String email, String password) {
    User userFound = null;

    if (email != null && password != null) {
      Subject currentUser = SecurityUtils.getSubject();

      if (!currentUser.isAuthenticated()) {
        UsernamePasswordToken token = new UsernamePasswordToken(email, password);
        // this is all you have to do to support 'remember me' (no config - built in!):
        // token.setRememberMe(rememberMe);

        try {
          LOG.info("Trying to log in the user {} against the database.", email);
          currentUser.login(token);

          // If user is logging-in with their email account.
          if (email.contains("@")) {
            userFound = this.getUserByEmail(email);
          } else {
            // if user is loggin with his username, we must attach the cgiar.org.
            userFound = this.getUserByUsername(email);
          }

        } catch (UnknownAccountException uae) {
          LOG.warn("There is no user with email of " + token.getPrincipal());
        } catch (IncorrectCredentialsException ice) {
          LOG.warn("Password for account " + token.getPrincipal() + " was incorrect!");
        } catch (LockedAccountException lae) {
          LOG.warn("The account for username " + token.getPrincipal() + " is locked.  "
            + "Please contact your administrator to unlock it.");
        }
      } else {
        int userID = (Integer) currentUser.getPrincipals().getPrimaryPrincipal();
        userFound = this.getUser(userID);
        LOG.info("Already logged in");
      }

    }
    return userFound;
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
  public int saveUser(User user, User modifiedBy) {
    Map<String, Object> userData = new HashMap<>();
    if (user.getId() > 0) {
      // If user already exists, the password would be saved in MD5 format.
      userData.put("id", user.getId());
      userData.put("password", user.getPassword());
    } else {
      // If user doesn't exist, the password would have to be converter to MD5 format.
      userData.put("created_by", modifiedBy.getId());
      if (user.getPassword() == null) {
        userData.put("password", " ");
      } else {
        userData.put("password", MD5Convert.stringToMD5(user.getPassword()));
      }
    }
    userData.put("first_name", user.getFirstName());
    userData.put("last_name", user.getLastName());
    userData.put("email", user.getEmail());
    userData.put("is_ccafs_user", user.isCcafsUser() ? 1 : 0);
    userData.put("is_active", user.isActive() ? 1 : 0);

    return userDAO.saveUser(userData);
  }


  @Override
  public List<User> searchUser(String searchValue) {
    List<User> users = new ArrayList<>();
    List<Map<String, String>> usersData = userDAO.searchUser(searchValue);

    for (Map<String, String> userData : usersData) {
      User user = new User();
      user.setId(Integer.parseInt(userData.get("id")));
      user.setActive(userData.get("is_active").equals("1"));
      user.setEmail(userData.get("email"));
      user.setFirstName(userData.get("first_name"));
      user.setLastName(userData.get("last_name"));

      users.add(user);
    }

    return users;
  }
}
