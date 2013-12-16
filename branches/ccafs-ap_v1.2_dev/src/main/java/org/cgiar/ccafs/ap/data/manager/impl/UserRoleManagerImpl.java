package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.UserRoleDAO;
import org.cgiar.ccafs.ap.data.manager.UserRoleManager;
import org.cgiar.ccafs.ap.data.model.UserRole;

import java.util.Map;

import com.google.inject.Inject;


public class UserRoleManagerImpl implements UserRoleManager {

  private UserRoleDAO userRoleDAO;

  @Inject
  public UserRoleManagerImpl(UserRoleDAO userRoleDAO) {
    this.userRoleDAO = userRoleDAO;
  }

  @Override
  public UserRole getRole(String name) {
    Map<String, String> userRoleData = userRoleDAO.getUserRole(name);
    UserRole userRole = null;

    if (userRoleData != null) {
      userRole = new UserRole();
      userRole.setId(Integer.parseInt(userRoleData.get("id")));
      userRole.setName(userRoleData.get("name"));
    }

    return userRole;
  }
}
