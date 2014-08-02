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
   * Get a list with the Important users according to their role. RPLs, FPLs and CUs.
   *
   * @return a list of Map objects with the important users information or an empty list if no users found.
   */
  public List<Map<String, String>> getAllOwners();

  /**
   * Get a list with All Users information
   *
   * @return a list of Map objects with the users information or an empty list if no users found.
   */
  public List<Map<String, String>> getAllUsers();

  /**
   * This method returns the employee Identifier that is using the given user id taking into account his current
   * institution id and role id.
   *
   * @param userId is the user identifier.
   * @param institutionId is the user's institution identifier.
   * @param roleId is the user's role identifier.
   * @return the id that is used in the database for the table employee, 0 if nothing found or -1 if some error
   *         occur.
   */
  public int getEmployeeID(int userId, int institutionId, int roleId);

  /**
   * This method return the user information from a employee identifier given.
   *
   * @param ownerId is the employee identifier.
   * @return a Map with the information requested.
   */
  public Map<String, String> getOwner(int ownerId);

  /**
   * This method get the User information (Project Owner Contact Person) by a given project ID
   *
   * @param projectID - is the ID of the project
   * @return a Map with the User information associated to a project
   */
  public Map<String, String> getOwnerByProjectId(int projectID);

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
