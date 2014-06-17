/*
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
 */

package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLUserDAO;

import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLUserDAO.class)
public interface UserDAO {

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
}
