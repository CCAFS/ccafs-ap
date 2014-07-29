package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLUserDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLUserDAO.class)
public interface UserDAO {

  /**
   * Get a list with All Users information
   *
   * @return a list of Map objects with the users information or an empty list if no users found.
   */
  public List<Map<String, String>> getAllUsers();

  /**
   * This method gets the data of a User identified with a given id.
   *
   * @param userId is the id of the User.
   * @return a Map with the user data.
   */
  public Map<String, String> getUser(int userId);

  /**
   * Get a user with the given email.
   *
   * @param username
   * @return a Map with the user information or null if no user found.
   */
  public Map<String, String> getUser(String username);

  /**
   * Save in the database the date and time that the user made its last login.
   *
   * @param userData - User information
   * @return - True if the information was succesfully saved, false otherwise.
   */
  public boolean saveLastLogin(Map<String, String> userData);

  /**
   * Save the user data into the database.
   *
   * @param userData - Information to be saved.
   * @return true if the information was successfully saved. False otherwise.
   */
  public boolean saveUser(Map<String, String> userData);
}
