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
import org.cgiar.ccafs.ap.data.model.Role;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.security.authentication.Authenticator;
import org.cgiar.ccafs.utils.MD5Convert;
import org.cgiar.ciat.auth.ADConexion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
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

  @Named("DB")
  Authenticator dbAuthenticator;

  @Named("LDAP")
  Authenticator ldapAuthenticator;

  Injector injector;

  // Managers
  private InstitutionManager institutionManager;

  @Inject
  public UserManagerImp(UserDAO userDAO, InstitutionManager institutionManager,
    @Named("DB") Authenticator dbAuthenticator, @Named("LDAP") Authenticator ldapAuthenticator) {
    this.userDAO = userDAO;
    this.institutionManager = institutionManager;
    this.ldapAuthenticator = ldapAuthenticator;
    this.dbAuthenticator = dbAuthenticator;

    injector = Guice.createInjector();

  }

  /**
   * This method make the login process against the active directory
   * if the user has an institutional account
   * 
   * @param user
   * @return true if it was successfully logged in. False otherwise
   */
  private boolean activeDirectoryLogin(User user) {

    // TODO - Delete this method once it is moved to the security plugin

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
  public List<User> getAllEmployees() {
    List<User> employees = new ArrayList<>();
    List<Map<String, String>> employeesDataList = userDAO.getAllEmployees();
    for (Map<String, String> eData : employeesDataList) {
      User employee = new User();
      employee.setId(Integer.parseInt(eData.get("id")));
      employee.setEmployeeId(Integer.parseInt(eData.get("employee_id")));
      employee.setFirstName(eData.get("first_name"));
      employee.setLastName(eData.get("last_name"));
      employee.setEmail(eData.get("email"));
      // Institution
      if (eData.get("institution_id") != null) {
        employee
          .setCurrentInstitution(institutionManager.getInstitution(Integer.parseInt(eData.get("institution_id"))));
      }
      // Role
      if (eData.get("role_id") != null) {
        Role role = new Role();
        role.setId(Integer.parseInt(eData.get("role_id")));
        role.setName(eData.get("role_name"));
        role.setAcronym(eData.get("role_acronym"));
        employee.setRole(role);
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
      projectContact.setEmployeeId(Integer.parseInt(pData.get("employee_id")));
      projectContact.setFirstName(pData.get("first_name"));
      projectContact.setLastName(pData.get("last_name"));
      projectContact.setEmail(pData.get("email"));
      // Role
      Role role = new Role();
      role.setId(Integer.parseInt(pData.get("role_id")));
      role.setName(pData.get("role_name"));
      role.setAcronym(pData.get("role_acronym"));
      projectContact.setRole(role);
      // Institution
      projectContact.setCurrentInstitution(institutionManager.getInstitution(Integer.parseInt(pData
        .get("institution_id"))));
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
      projectContact.setEmployeeId(Integer.parseInt(pData.get("employee_id")));
      projectContact.setFirstName(pData.get("first_name"));
      projectContact.setLastName(pData.get("last_name"));
      projectContact.setEmail(pData.get("email"));
      // Role
      Role role = new Role();
      role.setId(Integer.parseInt(pData.get("role_id")));
      role.setName(pData.get("role_name"));
      role.setAcronym(pData.get("role_acronym"));
      projectContact.setRole(role);
      // Institution
      projectContact.setCurrentInstitution(institutionManager.getInstitution(Integer.parseInt(pData
        .get("institution_id"))));
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
    int roleId = user.getRole().getId();
    int result = userDAO.getEmployeeID(userId, institutionId, roleId);

    return result;
  }


  @Override
  public User getOwner(int ownerId) {

    Map<String, String> userData = userDAO.getOwner(ownerId);
    // User
    User owner = new User();
    owner.setId(Integer.parseInt(userData.get("id")));
    owner.setEmployeeId(Integer.parseInt(userData.get("employee_id")));
    owner.setFirstName(userData.get("first_name"));
    owner.setLastName(userData.get("last_name"));
    owner.setEmail(userData.get("email"));
    // Role
    Role role = new Role();
    role.setId(Integer.parseInt(userData.get("role_id")));
    role.setName(userData.get("role_name"));
    role.setAcronym(userData.get("role_acronym"));
    owner.setRole(role);
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
      user.setEmployeeId(Integer.parseInt(userData.get("employee_id")));
      user.setFirstName(userData.get("first_name"));
      user.setLastName(userData.get("last_name"));
      user.setEmail(userData.get("email"));
      // Role
      Role role = new Role();
      role.setId(Integer.parseInt(userData.get("role_id")));
      role.setName(userData.get("role_name"));
      role.setAcronym(userData.get("role_acronym"));
      user.setRole(role);
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
        user.setLastLogin(dateFormat.parse(userData.get("last_login")));
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
      user.setPhone(userData.get("phone"));
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

  public User getUserByUsername(String username) {
    String email = userDAO.getEmailByUsername(username);
    if (email != null) {
      return this.getUserByEmail(email);
    }
    return null;
  }

  @Override
  public User login(String email, String password) {

    if (email != null && password != null) {

      User userFound;
      // If user is logging-in with their email account.
      if (email.contains("@")) {
        userFound = this.getUserByEmail(email);
      } else {
        // if user is loggin with his username, we must attach the cgiar.org.
        userFound = this.getUserByUsername(email);
      }

      if (userFound != null) {
        if (userFound.isActive()) {
          if (userFound.isCcafsUser()) {
            if (ldapAuthenticator.authenticate(userFound.getUsername(), password)) {
              // Encrypt the password again
              userFound.setMD5Password(password);
              return userFound;
            }
          } else {
            org.apache.shiro.subject.Subject currentUser = SecurityUtils.getSubject();

            if (!currentUser.isAuthenticated()) {
              UsernamePasswordToken token = new UsernamePasswordToken(email, password);
              // this is all you have to do to support 'remember me' (no config - built in!):
              // token.setRememberMe(rememberMe);

              try {
                LOG.info("Trying to log in the user {} against the database.", email);
                currentUser.login(token);

                // save current username in the session, so we have access to our User model
                currentUser.getSession().setAttribute("username", email);

                return userFound;
              } catch (UnknownAccountException uae) {
                LOG.warn("There is no user with email of " + token.getPrincipal());
              } catch (IncorrectCredentialsException ice) {
                LOG.warn("Password for account " + token.getPrincipal() + " was incorrect!");
              } catch (LockedAccountException lae) {
                LOG.warn("The account for username " + token.getPrincipal() + " is locked.  "
                  + "Please contact your administrator to unlock it.");
              }
            } else {
              LOG.info("Already logged in");
            }

            // TODO - Adjust the authentication to use all the potential of shiro.


            // dbAuthenticator.authenticate(email, password);
            // User tempUser = new User();
            // tempUser.setMD5Password(password);
            // if (userFound.getPassword().equals(tempUser.getPassword())) {
            // return userFound;
            // }
          }
        }
      } else {
        // TODO HT/HC - Do something in case user is not active.
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
