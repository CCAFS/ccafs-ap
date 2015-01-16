package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLUserDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLUserDAO.class)
public interface UserDAO {


  /**
   * Get all Users
   * 
   * @return a List of Maps with the users found
   */
  public List<Map<String, String>> getAllUsers();


  /**
   * Get the user's email that relates with the given username.
   * 
   * @param username is the user nickname.
   * @return a String with the user's email, or null if nothing was found.
   */
  public String getEmailByUsername(String username);

  /**
   * Get a user with the given email.
   * 
   * @param email
   * @return a Map with the user information or null if no user found.
   */
  public Map<String, String> getUser(String email);

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

  /**
   * Update the user if belongs to the Active Directory
   * 
   * @param userId the Id of the user to be updated
   * @param userName is the string to be add at the given user
   * @return true if the information was succesfully updated. False Otherwise
   */
  public boolean updateCCAFSUser(int userId, String userName);
}
