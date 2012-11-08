package org.cgiar.ccafs.ap.action;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {

  private String email;
  private String password;


  @Override
  public String execute() throws Exception {
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
