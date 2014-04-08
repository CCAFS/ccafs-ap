package org.cgiar.ccafs.ap.security.realm;

import org.cgiar.ccafs.ap.data.manager.UserManager;

import org.apache.shiro.authz.AuthorizationException;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import com.google.inject.Inject;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
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


  @Inject
  public DatabaseRealm(UserManager userManager) {
    this.userManager = userManager;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    System.out.println("vamos ----------");
    AuthenticationInfo authInfo;

    UsernamePasswordToken upToken = (UsernamePasswordToken) token;
    String username = upToken.getUsername();
    String password = String.valueOf(upToken.getPassword());

    // Null username is invalid
    if (username == null) {
      throw new AccountException("Null usernames are not allowed by this realm.");
    }

    if (userManager.login(username, password) == null) {
      throw new IncorrectCredentialsException("Null usernames are not allowed by this realm.");
    }

    authInfo = new SimpleAuthenticationInfo(username, password.toCharArray(), getName());

    return authInfo;
  }
  
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(final PrincipalCollection principals) {
    LOG.trace("doGetAuthorizationInfo from DatabaseRealm started");
    
    if(principals == null){
      LOG.error("Principals collection is null");
      throw new AuthorizationException("PrincipalCollection can't be null");
    }
    
    if(principals.isEmpty()){
      LOG.error("Principals collection is empty");
      throw new AuthorizationException("PrincipalCollection can't be empty");
    }
    
    LOG.trace("Principals size = {}", principals.asList().size());
    
    String email = (String) principals.iterator().next();
    
    System.out.println("Authorization called ??????????????????");
    System.out.println(email);
    /*
    Set<String> roles = new HashSet<String>();
    Set<Permission> permissions = new HashSet<Permission>();
    Collection<User> principalsList = principals.byType(User.class);

    //pricipals we're loading roles for !
    if (principalsList.isEmpty()) {
      throw new AuthorizationException("Empty principals list!");
    }
    //Iterate through principals
    for (User userPrincipal : principalsList) {
      try {
        this.userManager.beginTransaction();
        
        User user = this.userManager.loadById(userPrincipal.getId());
        //get User roles
        Set<Role> userRoles = user.getRoles();
        for (Role r : userRoles) {
          roles.add(r.getName()); //add role to roles list
          Set<WildcardPermission> userPermissions = r.getPermissions(); //get Role permissions
          for (WildcardPermission permission : userPermissions) {  //add permissions if not set yet
            if (!permissions.contains(permission)) {
              permissions.add(permission);
            }
          }
        }
        this.userManager.commitTransaction();
      } catch (InvalidDataException idEx) {
        throw new AuthorizationException(idEx);
      } catch (ResourceException rEx) {
        throw new AuthorizationException(rEx);
      } 
    }
    //put everything into authorization info
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
    info.setRoles(roles);
    info.setObjectPermissions(permissions);
    
    return info;
    */
    return super.doGetAuthorizationInfo(principals);
  }

}
