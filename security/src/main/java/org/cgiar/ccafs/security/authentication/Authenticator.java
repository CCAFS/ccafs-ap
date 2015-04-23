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

package org.cgiar.ccafs.security.authentication;

import org.cgiar.ccafs.security.data.model.User;


/**
 * @author Hernán David Carvajal
 */

public interface Authenticator {

  /**
   * Authenticates a user based on the credentials received as parameter.
   * 
   * @param email - This can be the email or the CCAFS username
   * @param password
   * @return
   */
  public User authenticate(String email, String password);
}
