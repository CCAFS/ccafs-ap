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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginAction extends BaseAction {

  // Logging
  private static final Logger LOG = LoggerFactory.getLogger(LoginAction.class);

  private static final long serialVersionUID = -890122014241894430L;

  private User user;

  private UserManager userManager;

  @Inject
  public LoginAction(APConfig config, LogframeManager logframeManager, UserManager userManager) {
    super(config, logframeManager);
    this.userManager = userManager;
  }

  @Override
  public String execute() throws Exception {
    LOG.trace("Running execute() method.");
    return SUCCESS;
  }

  public User getUser() {
    return user;
  }

  public String login() {
    LOG.trace("Running login() method.");
    // validate if user is just visiting the login page at first time.
    if (user != null) {
      User loggedUser = userManager.login(user.getEmail(), user.getPassword());
      if (loggedUser != null) {
        loggedUser.setLastLogin(new Date());
        this.getSession().put(APConstants.SESSION_USER, loggedUser);
        LOG.info("User " + user.getEmail() + " logged in successfully.");
        System.out.println("isLogged(): " + this.isLogged());
      } else {
        LOG.info("User " + user.getEmail() + " tried to logged in but failed.");
        return INPUT;
      }
    }
    return SUCCESS;
  }

  public String logout() {
    this.getSession().clear();
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
        addFieldError("user.email", "User email is required");
        user.setPassword(null);
      } else if (!EmailValidator.isValidEmail(user.getEmail())) {
        addFieldError("user.email", "Email is invalid");
        user.setPassword(null);
      }
    }

  }
}
