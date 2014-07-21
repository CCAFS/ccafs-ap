package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.RoleDAO;
import org.cgiar.ccafs.ap.data.manager.RoleManager;
import org.cgiar.ccafs.ap.data.model.Role;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.Map;

import com.google.inject.Inject;


public class RoleManagerImpl implements RoleManager {

  private RoleDAO roleDAO;

  @Inject
  public RoleManagerImpl(RoleDAO roleDAO) {
    this.roleDAO = roleDAO;
  }

  @Override
  public Role getRole(User user) {
    Map<String, String> roleData = roleDAO.getRole(user.getId(), user.getCurrentInstitution().getId());
    if (!roleData.isEmpty()) {
      Role role = new Role();
      role.setId(Integer.parseInt(roleData.get("id")));
      role.setName(roleData.get("acronym"));
      role.setAcronym(roleData.get("acronym"));

      return role;
    }
    return null;
  }

}
