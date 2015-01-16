package org.cgiar.ccafs.ap.data.model;

import org.cgiar.ccafs.ap.util.MD5Convert;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class User {

  public enum UserRole {
    PI, CP, TL, RPL, Admin
  }

  private int id;
  private String name;
  private String email;
  private String password;
  private UserRole role;
  private Date lastLogin;
  private Leader leader;
  private String username;
  private boolean isCcafsUser;


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


  public int getId() {
    return id;
  }


  public Date getLastLogin() {
    return lastLogin;
  }

  public Leader getLeader() {
    return leader;
  }

  public String getName() {
    return name;
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

  public String getUsername() {
    return username;
  }

  /**
   * Validate if the current user is an Administrator.
   * 
   * @return true if the user is actually an Administrator, or false otherwise.
   */
  public boolean isAdmin() {
    return this.role == UserRole.Admin;
  }

  public boolean isCcafsUser() {
    return isCcafsUser;
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
   * Validate if the current user is a Principal Investigator.
   * 
   * @return true if the user is actually a Principal Investigator, or false otherwise.
   */
  public boolean isPI() {
    return this.role == UserRole.PI;
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

  public void setCcafsUser(boolean isCcafsUser) {
    this.isCcafsUser = isCcafsUser;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLastLogin(Date lastLogin) {
    this.lastLogin = lastLogin;
  }


  public void setLeader(Leader leader) {
    this.leader = leader;
  }

  /**
   * This method will calculate the MD5 of the provided parameter.
   * 
   * @param password normal String.
   */
  public void setMD5Password(String password) {
    if (password != null) {
      this.password = MD5Convert.stringToMD5(password);
    } else {
      this.password = null;
    }
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRole(String roleString) {
    switch (roleString) {
      case "Admin":
        this.role = UserRole.Admin;
        break;
      case "CP":
        this.role = UserRole.CP;
        break;
      case "TL":
        this.role = UserRole.TL;
        break;
      case "RPL":
        this.role = UserRole.RPL;
        break;
      case "PI":
        this.role = UserRole.PI;
        break;
      default:
        break;
    }
  }

  public void setRole(UserRole role) {
    this.role = role;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
