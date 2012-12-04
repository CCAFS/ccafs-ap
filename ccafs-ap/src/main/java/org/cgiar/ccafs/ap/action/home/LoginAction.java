package org.cgiar.ccafs.ap.action.home;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

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
      Pattern pattern;
      Matcher matcher;

      // Pattern to validate email
      String emailPattern =
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


      pattern = Pattern.compile(emailPattern);
      matcher = pattern.matcher(user.getEmail());

      if (user.getEmail().isEmpty()) {
        addFieldError("user.email", "User email is required");
        user.setPassword(null);
      } else if (!matcher.matches()) {
        addFieldError("user.email", "Email is invalid");
        user.setPassword(null);
      }
    }

  }

}
