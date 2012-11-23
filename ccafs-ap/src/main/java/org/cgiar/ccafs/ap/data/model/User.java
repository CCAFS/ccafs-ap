package org.cgiar.ccafs.ap.data.model;

import org.cgiar.ccafs.ap.util.MD5Convert;

import java.util.Date;


public class User {

  /*
   * TODO - This enum could be used as another class. Please take into consideration in the future of the development
   * process.
   */
  public enum UserRole {
    CP, TL, RPL, Admin
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

  /**
   * @return the password previously codified using MD5 algorithm.
   */
  public String getPassword() {
    return password;
  }


  public UserRole getRole() {
    return role;
  }

  /**
   * Validate if the current user is an Administrator.
   * 
   * @return true if the user is actually an Administrator, or false otherwise.
   */
  public boolean isAdmin() {
    return this.role == UserRole.Admin;
  }

  /**
   * Validate if the current user is a Contact Point.
   * 
   * @return true if the user is actually a Contact Point, or false otherwise.
   */
  public boolean isCP() {
    return this.role == UserRole.CP;
  }

  /**
   * Validate if the current user is a Regional Program Leader.
   * 
   * @return true if the user is actually a Regional Program Leader, or false otherwise.
   */
  public boolean isRPL() {
    return this.role == UserRole.RPL;
  }

  /**
   * Validate if the current user is a Theme Leader.
   * 
   * @return true if the user is actually a Theme Leader, or false otherwise.
   */
  public boolean isTL() {
    return this.role == UserRole.TL;
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
