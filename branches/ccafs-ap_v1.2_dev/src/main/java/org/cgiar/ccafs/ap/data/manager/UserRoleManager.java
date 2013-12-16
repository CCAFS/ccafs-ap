package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.UserRoleManagerImpl;
import org.cgiar.ccafs.ap.data.model.UserRole;

import com.google.inject.ImplementedBy;

@ImplementedBy(UserRoleManagerImpl.class)
public interface UserRoleManager {

  /**
   * Get all the information related with the role
   * given if exists
   * 
   * @param name - The role to look for
   * @return a UserRole object with the information or null if the role doesn't exists.
   */
  public UserRole getRole(String name);
}
