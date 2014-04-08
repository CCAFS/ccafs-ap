package org.cgiar.ccafs.ap.action.home;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.util.EmailValidator;

import java.util.Date;

import com.google.inject.Inject;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginAction extends BaseAction {

  // Logging
  private static final Logger LOG = LoggerFactory.getLogger(LoginAction.class);
  private static final long serialVersionUID = -890122014241894430L;

  private User user;

  private UserManager userManager;

  @Inject
  public LoginAction(APConfig config, LogframeManager logframeManager, UserManager userManager,
    SecurityManager securityManager) {
    super(config, logframeManager, securityManager);
    this.userManager = userManager;

  }

  @Override
  public String execute() throws Exception {
    return SUCCESS;
  }

  public User getUser() {
    return user;
  }

  public String login() {
    if (user != null) {
      UsernamePasswordToken token = new UsernamePasswordToken(user.getEmail(), user.getPassword());
      try {
        getSubject().login(token);
        Session session = getSession();

        User loggedUser = userManager.getUser(user.getEmail());
        loggedUser.setLastLogin(new Date());
        userManager.saveLastLogin(loggedUser);

        session.setAttribute(APConstants.SESSION_USER, loggedUser);
        LOG.info("User " + user.getEmail() + " logged in successfully.");

      } catch (UnknownAccountException uae) {
        LOG.info("User " + user.getEmail() + " is not registered, access denied!", uae);
        return INPUT;
      } catch (IncorrectCredentialsException iae) {
        LOG.info("User " + user.getEmail() + " tried to logged in but failed.", iae);
        return INPUT;
      } catch (LockedAccountException lae) {
        // TODO
        lae.printStackTrace();
        return INPUT;
      } catch (AuthenticationException ae) {
        // TODO
        ae.printStackTrace();
        return INPUT;
      }

      LOG.info("User " + user.getEmail() + " logged in successfully.");

      getSubject().checkPermission("leader:delete:user");
      return SUCCESS;
    } else {
      // Check if the user exists in the session
      return (isLogged()) ? SUCCESS : INPUT;
    }
  }

  public String logout() {
    getSubject().logout();
    return SUCCESS;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public void validate() {
    // If is the first time the user is loading the page
    if (user != null) {
      if (user.getEmail().isEmpty()) {
        addFieldError("user.email", getText("validation.field.required"));
        user.setPassword(null);
      } else if (!EmailValidator.isValidEmail(user.getEmail().trim())) {
        addFieldError("user.email", getText("validation.invalid", new String[] {getText("home.login.email")}));
        user.setPassword(null);
      }
    }

  }
}
