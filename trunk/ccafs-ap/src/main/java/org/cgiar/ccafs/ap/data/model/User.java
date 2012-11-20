package org.cgiar.ccafs.ap.data.model;

import org.cgiar.ccafs.ap.util.MD5Convert;

import java.util.Date;


public class User {

  /*
   * TODO - This enum could be used as another class. Please take into consideration in the future of the development
   * process.
   */
  public enum UserRole {
    ContactPoint, TL, RPL, Admin
  }

  private String email;
  private String password;
  private UserRole role;
  private Date lastLogin;


  @Override
  public boolean equals(Object obj) {
    if (obj instanceof User) {
      User u = (User) obj;
      return u.getEmail().equals(this.getEmail());
    }
    return false;
  }

  public String getEmail() {
    return email;
  }

  public Date getLastLogin() {
    return lastLogin;
  }


  public String getPassword() {
    return password;
  }

  public UserRole getRole() {
    return role;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  public void setLastLogin(Date lastLogin) {
    this.lastLogin = lastLogin;
  }

  /**
   * This method will calculate the MD5 of the provided parameter.
   * 
   * @param password normal String.
   */
  public void setPassword(String password) {
    this.password = MD5Convert.stringToMD5(password);
  }

  public void setRole(UserRole role) {
    this.role = role;
  }


}
