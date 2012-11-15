package org.cgiar.ccafs.ap.action.home;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;

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

  private String email;
  private String password;

  private ActivityManager activityManager;

  @Inject
  public LoginAction(ActivityManager activityController, APConfig config) {
    super(config);
    this.activityManager = activityController;
  }

  @Override
  public String execute() throws Exception {
    System.out.println(activityManager.getActivities().length + " activities");
    System.out.println("Email: " + getEmail());
    System.out.println(this.getBaseUrl());
    String contextPath = request.getContextPath();
    System.out.println("Context Path " + contextPath);
    LOG.debug("LoginAction executed");
    return SUCCESS;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public void setServletRequest(HttpServletRequest request) {
    this.request = request;

  }


}
