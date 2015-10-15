/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 * ***************************************************************
 */
package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLUserDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Hernán David Carvajal.
 * @author Héctor Fabio Tobón R.
 */
@ImplementedBy(MySQLUserDAO.class)
public interface UserDAO {

  /**
   * Get a list with All the Employees information
   * 
   * @return a list of Map objects with the employees information or an empty list if no users found.
   */
  public List<Map<String, String>> getAllEmployees();

  /**
   * Get a list with the Important users according to their role. RPLs, FPLs and CUs.
   * 
   * @return a list of Map objects with the important users information or an empty list if no users found.
   */
  public List<Map<String, String>> getAllOwners();

  /**
   * This method gets all the users that belongs to a given liaison institution
   * 
   * @param liaisonInstitutionID is an integer that represents a liaison institution identifier within the system.
   * @return a List of Maps with the information from the users table.
   */
  public List<Map<String, String>> getAllOwners(int liaisonInstitutionID);

  /**
   * Get a list with All Users information
   * 
   * @return a list of Map objects with the users information or an empty list if no users found.
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
   * This method gets the data of a User identified with a given id.
   * 
   * @param userId is the id of the User.
   * @return a Map with the user data.
   */
  public Map<String, String> getUser(int userId);

  /**
   * Get a user with the given email.
   * 
   * @param email is the user email
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
   * @return the id of the user that was created, 0 if the user was updated or -1 if some error appeared.
   */
  public int saveUser(Map<String, Object> userData);

  /**
   * This method looks for the active users that contains the
   * searchValue in its name, last name or email
   * 
   * @param searchValue
   * @return a list of maps with the information of the users found.
   */
  public List<Map<String, String>> searchUser(String searchValue);
}
