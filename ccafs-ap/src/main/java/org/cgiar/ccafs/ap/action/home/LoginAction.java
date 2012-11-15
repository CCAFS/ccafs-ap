package org.cgiar.ccafs.ap.action.home;

import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {

  private static final long serialVersionUID = -890122014241894430L;
  private String email;
  private String password;

  private ActivityManager activityManager;
  private APConfig config;

  @Inject
  public LoginAction(ActivityManager activityController, APConfig config) {
    this.activityManager = activityController;
    this.config = config;
  }

  @Override
  public String execute() throws Exception {
    System.out.println(activityManager.getActivities().length + " activities");
    System.out.println("Email: " + getEmail());
    System.out.println(config.getBaseUrl());
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


}
