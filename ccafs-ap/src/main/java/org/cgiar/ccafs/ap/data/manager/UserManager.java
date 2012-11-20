package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.model.User;


public interface UserManager {

  /**
   * Get the user identified by the specified email parameter.
   * 
   * @param email of the user.
   * @return User object representing the user identified by the email provided or Null in the user doesn't exist in the
   *         database.
   */
  public User getUser(String email);

  /**
   * Authenticate a user.
   * 
   * @param email of the user.
   * @param password of the user.
   * @return a User object representing the user identified by the email provided or Null if login failed.
   */
  public User login(String email, String password);
}
