package org.cgiar.ccafs.ap.action.home;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.model.User;

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

  private ActivityManager activityManager;

  @Inject
  public LoginAction(ActivityManager activityController, APConfig config) {
    super(config);
    this.activityManager = activityController;
  }

  @Override
  public String execute() throws Exception {
    LOG.info(activityManager.getActivities().length + " activities");
    LOG.info("Email: " + (user != null ? user.getEmail() : ""));
    LOG.info("Password: " + (user != null ? user.getPassword() : ""));
    LOG.info("LoginAction executed");
    return SUCCESS;
  }


  public User getUser() {
    return user;
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
    LOG.info("Email: " + (user != null ? user.getEmail() : ""));
    LOG.info("Password: " + (user != null ? user.getPassword() : ""));
    LOG.info("validate executed");
    if (user != null && user.getPassword() != null && !user.getPassword().equals("12345")) {
      System.out.println("Error de inicio de sesión - validate()");
      addFieldError("user.email", "Nombre de usuario errado");
      addFieldError("user.password", "Mal password");
      addActionError("Error general (addActionError)");
    }
  }


}
