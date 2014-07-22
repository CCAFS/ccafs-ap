package org.cgiar.ccafs.ap.data.manager;

import java.util.List;

import org.cgiar.ccafs.ap.data.manager.impl.UserManagerImp;
import org.cgiar.ccafs.ap.data.model.User;
import com.google.inject.ImplementedBy;

@ImplementedBy(UserManagerImp.class)
public interface UserManager {

  /**
   * this method get all the Users identified as Project Leaders in the system.
   *
   * @return a List of Users that represent Project Leaders. If there are not Project Leaders in the system, the method
   *         will return an empty list.
   */
  public List<User> getAllProjectLeaders();

  /**
   * This method finds the Project Leader user from a specific Project.
   *
   * @param projectId is the project id.
   * @return a User object who represents a Project Leader. Or NULL if no user was found.
   */
  public User getProjectLeader(int projectId);

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

  /**
   * Save in the database the date and time that the user made its last login.
   *
   * @param user - User information
   * @return - True if the information was succesfully saved, false otherwise.
   */
  public boolean saveLastLogin(User user);

  /**
   * Create a new user in the system by saving the
   * user data in the database.
   *
   * @param user - The user information
   * @return true if it was successfully saved. False otherwise.
   */
  public boolean saveUser(User user);

}
