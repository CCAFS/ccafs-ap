package org.cgiar.ccafs.ap.action.home;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APContants;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.google.inject.Inject;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginAction extends BaseAction implements ServletRequestAware {

  private HttpServletRequest request;

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
        session.put(APContants.SESSION_USER, loggedUser);
        LOG.info("User " + user.getEmail() + " logged in successfully.");
        System.out.println("isLogged(): " + this.isLogged());
      } else {
        LOG.info("User " + user.getEmail() + " tried to logged in but failed.");
      }
    }
    return SUCCESS;
  }


  public String logout() {
    session.clear();
    return SUCCESS;
  }


  @Override
  public void setServletRequest(HttpServletRequest request) {
    this.request = request;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public void validate() {

  }


}
