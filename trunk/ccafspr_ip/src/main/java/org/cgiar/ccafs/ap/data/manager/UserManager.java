package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.UserManagerImp;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(UserManagerImp.class)
public interface UserManager {

  /**
   * This method gets all the Users from the system.
   *
   * @return a List of Users. If there are not users in the system, the method
   *         will return an empty list.
   */
  public List<User> getAllUsers();

  /**
   * This method finds the Project Leader user from a specific Project.
   *
   * @param projectId is the project id.
   * @return a User object who represents a Project Leader. Or NULL if no user was found.
   */
  public User getProjectLeader(int projectId);

  /**
   * This method find an user identify with a given id.
   * 
   * @param userId is the id of the user.
   * @return a User object.
   */
  public User getUser(int userId);

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
