/*
 * ****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform.
 * 
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * 
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 * ***************************************************************
 */
package org.cgiar.ccafs.ap.data.model;

import org.cgiar.ccafs.ap.util.MD5Convert;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class User {

  private int id;
  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;
  private String phone;
  private boolean isCcafsUser;
  private Role role;
  private List<Institution> institutions;
  private Institution currentInstitution;

  private Date lastLogin;

  public String getEmail() {
    return email;
  }

  public String getFirstName() {
    return firstName;
  }

  public int getId() {
    return id;
  }

  public List<Institution> getInstitutions() {
    return institutions;
  }

  public Date getLastLogin() {
    return lastLogin;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPassword() {
    return password;
  }

  public String getPhone() {
    return phone;
  }

  public Role getRole() {
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
    return this.role.getAcronym().equals(Role.UserRole.Admin.name());
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
    return this.role.getAcronym().equals(Role.UserRole.CP.name());
  }

  /**
   * Validate if the current user is a coordinating unit member.
   * 
   * @return true if the user is actually a member of the coordinating unit, or false otherwise.
   */
  public boolean isCU() {
    return this.role.getAcronym().equals(Role.UserRole.CU.name());
  }

  /**
   * Validate if the current user is a Theme Leader.
   * 
   * @return true if the user is actually a Flagship program Leader, or false otherwise.
   */
  public boolean isFPL() {
    return this.role.getAcronym().equals(Role.UserRole.FPL.name());
  }

  /**
   * Validate if the current user is a Principal Investigator.
   * 
   * @return true if the user is actually a Principal Investigator, or false otherwise.
   */
  public boolean isPI() {
    return this.role.getAcronym().equals(Role.UserRole.PI.name());
  }

  /**
   * Validate if the current user is a project leader.
   * 
   * @return true if the user is actually a Project leader, or false otherwise.
   */
  public boolean isPL() {
    return this.role.getAcronym().equals(Role.UserRole.PO.name());
  }

  /**
   * Validate if the current user is a project owner.
   * 
   * @return true if the user is actually a Project owner, or false otherwise.
   */
  public boolean isPO() {
    return this.role.getAcronym().equals(Role.UserRole.PO.name());
  }

  /**
   * Validate if the current user is a Regional Program Leader.
   * 
   * @return true if the user is actually a Regional Program Leader, or false otherwise.
   */
  public boolean isRPL() {
    return this.role.getAcronym().equals(Role.UserRole.RPL.name());
  }

  public void setCcafsUser(boolean isCcafsUser) {
    this.isCcafsUser = isCcafsUser;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setInstitutions(List<Institution> institutions) {
    this.institutions = institutions;
  }

  public void setLastLogin(Date last_login) {
    this.lastLogin = last_login;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setMD5Password(String password) {
    if (password != null) {
      this.password = MD5Convert.stringToMD5(password);
    } else {
      this.password = null;
    }
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  public Institution getCurrentInstitution() {
    return currentInstitution;
  }

  public void setCurrentInstitution(Institution currentInstitution) {
    this.currentInstitution = currentInstitution;
  }
}
