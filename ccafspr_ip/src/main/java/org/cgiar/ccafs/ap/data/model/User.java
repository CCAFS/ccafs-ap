/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform.
 * 
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * 
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R.  If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/
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

  public void setMD5Password(String password) {
    this.password = password;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * This method will calculate the MD5 of the provided parameter.
   * 
   * @param password normal String.
   */
  public void setPassword(String password) {
    if (password != null) {
      this.password = MD5Convert.stringToMD5(password);
    } else {
      this.password = null;
    }
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

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
