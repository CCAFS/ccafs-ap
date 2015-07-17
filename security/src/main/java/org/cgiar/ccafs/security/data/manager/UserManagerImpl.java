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

import org.cgiar.ccafs.security.data.dao.UserDAO;
import org.cgiar.ccafs.security.data.model.User;

import java.util.Map;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal
 */

public class UserManagerImpl {

  private UserDAO userDao;

  @Inject
  public UserManagerImpl(UserDAO userDao) {
    this.userDao = userDao;
  }

  /**
   * This method gets the information of the user identified by the email
   * received as parameter.
   * 
   * @param email
   * @return
   */
  public User getUserByEmail(String email) {
    User user = new User();
    Map<String, String> userData = userDao.getUserByEmail(email);

    if (userData.isEmpty()) {
      return null;
    }

    user.setEmail(email);
    user.setUsername(userData.get("username"));
    user.setPassword(userData.get("password"));
    user.setId(Integer.parseInt(userData.get("id")));
    user.setActive(userData.get("is_active").equals("1"));
    user.setCcafsUser(userData.get("is_ccafs_user").equals("1"));

    return user;
  }

  public User getUserByUsername(String username) {
    String email = userDao.getEmailByUsername(username);
    if (email != null) {
      return this.getUserByEmail(email);
    }
    return null;
  }
}
