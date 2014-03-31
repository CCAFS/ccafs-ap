package org.cgiar.ccafs.ap.security.realm;

import org.cgiar.ccafs.ap.data.manager.UserManager;

import com.google.inject.Inject;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DatabaseRealm extends JdbcRealm {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(DatabaseRealm.class);

  // Manager
  private UserManager userManager;

  // Model
  SimpleAuthenticationInfo authInfo;

  @Inject
  public DatabaseRealm(UserManager userManager) {
    this.userManager = userManager;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    System.out
      .println("------------------------------------------ SHIRO IN ACTION -------------------------------------------");

    UsernamePasswordToken upToken = (UsernamePasswordToken) token;
    String username = upToken.getUsername();
    String password = upToken.getUsername();

    // Null username is invalid
    if (username == null) {
      LOG.warn("Null usernames are not allowed by the realm");
    }

    if (userManager.login(username, password) == null) {
      LOG.warn("User {} tried to login but failed", username);
    }

    authInfo = new SimpleAuthenticationInfo(username, password.toCharArray(), getName());

    return authInfo;
  }

}
