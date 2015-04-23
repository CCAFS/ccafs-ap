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
 *****************************************************************/

package org.cgiar.ccafs.security.data.manager;

import org.cgiar.ccafs.security.data.model.User;


/**
 * @author Hernán David Carvajal
 */

public interface UserManager {

  /**
   * Get the user identified by the specified email parameter.
   * 
   * @param email of the user.
   * @return User object representing the user identified by the email provided or Null if the user doesn't exist in the
   *         database.
   */
  public User getUserByEmail(String email);

  /**
   * Get the user identified by the specified username parameter.
   * 
   * @param username of the user - cgiar account username.
   * @return User object representing the user identifier by the username provided or Null if the user doesn't exist in
   *         the database.
   */
  public User getUserByUsername(String username);
}
