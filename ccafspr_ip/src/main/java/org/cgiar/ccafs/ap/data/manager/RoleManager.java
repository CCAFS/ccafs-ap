package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.RoleManagerImpl;
import org.cgiar.ccafs.ap.data.model.Role;
import org.cgiar.ccafs.ap.data.model.User;

import com.google.inject.ImplementedBy;

@ImplementedBy(RoleManagerImpl.class)
public interface RoleManager {

  /**
   * This method get the role of the user given according to the
   * user identifier and the current institution of the user
   * 
   * @param user
   * @return a Role object with the information
   */
  public Role getRole(User user);
}
