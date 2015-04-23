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

import org.cgiar.ccafs.security.data.manager.UserManager;
import org.cgiar.ccafs.security.data.model.User;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal
 */

public class BaseAuthenticator implements Authenticator {

  public static Logger LOG = LoggerFactory.getLogger(BaseAuthenticator.class);
  private UserManager userManager;

  @Inject
  public BaseAuthenticator(UserManager userManager) {
    this.userManager = userManager;
  }

  @Override
  public User authenticate(String email, String password) {
    User user;

    if (email.contains("@")) {
      user = userManager.getUserByEmail(email);
    } else {
      user = userManager.getUserByUsername(email);
    }

    if (user.isCcafsUser()) {

    } else {

    }

    return null;
  }

  public boolean ccafsAuthentication(User user) {

    return false;
  }
}
