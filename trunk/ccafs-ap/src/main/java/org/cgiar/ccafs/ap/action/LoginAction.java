package org.cgiar.ccafs.ap.action;

import org.cgiar.ccafs.ap.data.manager.ActivityManager;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {

  private static final long serialVersionUID = -890122014241894430L;
  private String email;
  private String password;

  private ActivityManager activityManager;

  @Inject
  public LoginAction(ActivityManager activityController) {
    this.activityManager = activityController;
  }

  @Override
  public String execute() throws Exception {
    System.out.println(activityManager.getActivities().length + " activities");
    System.out.println(getEmail());
    System.out.println();
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
