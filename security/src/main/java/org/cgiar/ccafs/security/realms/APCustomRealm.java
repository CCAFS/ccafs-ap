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

package org.cgiar.ccafs.security.realms;

import org.cgiar.ccafs.security.data.manager.UserManagerImpl;
import org.cgiar.ccafs.security.data.model.User;
import org.cgiar.ccafs.security.util.Md5CredentialsMatcher;

import com.google.inject.Inject;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal
 */

public class APCustomRealm extends AuthorizingRealm {

  public static Logger LOG = LoggerFactory.getLogger(APCustomRealm.class);
  private UserManagerImpl userManager;
  private Md5CredentialsMatcher matcher;

  @Inject
  public APCustomRealm(UserManagerImpl userManager, Md5CredentialsMatcher matcher) {
    this.userManager = userManager;
    this.matcher = matcher;

    setName("APCustomRealm");
    setCredentialsMatcher(matcher);
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    // identify account to log to
    UsernamePasswordToken userPassToken = (UsernamePasswordToken) token;
    final String username = userPassToken.getUsername();

    if (username == null) {
      LOG.info("doGetAuthenticationInfo() > The username received is null.");
      return null;
    }

    // Get user info from db
    final User user = userManager.getUserByEmail(username);

    if (user == null) {
      LOG.info("doGetAuthenticationInfo() > No account found for user {}.", username);
      return null;
    }

    SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getEmail(), user.getPassword(), getName());

    return info;
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    return null;
  }
}
