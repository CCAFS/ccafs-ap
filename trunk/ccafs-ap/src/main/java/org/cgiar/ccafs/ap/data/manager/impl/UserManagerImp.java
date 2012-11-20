package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.util.MD5Convert;


public class UserManagerImp implements UserManager {

  @Override
  public User getUser(String email) {
    // TODO Find the user into the database using the UserDAO interface which is not currently created.

    // Let's simulate two users.

    // Admin
    User htobon = new User();
    htobon.setEmail("htobon@gmail.com");
    htobon.setPassword("12345");
    htobon.setRole(User.UserRole.Admin);

    // Contact Point
    User hcarvajal = new User();
    hcarvajal.setEmail("carvajal.hernandavid@gmail.com");
    hcarvajal.setPassword("maalmu");
    hcarvajal.setRole(User.UserRole.ContactPoint);

    if (email != null) {
      if (email.equals(htobon.getEmail())) {
        return htobon;
      } else if (email.equals(hcarvajal.getEmail())) {
        return hcarvajal;
      }
    }
    return null;
  }

  @Override
  public User login(String email, String password) {
    if (email != null && password != null) {
      User userFound = this.getUser(email);
      if (userFound != null) {
        if (userFound.getPassword().equals(MD5Convert.stringToMD5(password))) {
          return userFound;
        }
      }
    }
    return null;
  }


}
