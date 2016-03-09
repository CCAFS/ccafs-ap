/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/
package org.cgiar.ccafs.ap.data.model;

import org.cgiar.ccafs.utils.MD5Convert;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Hernan David Carvajal.
 * @author Hector Fabio Tobón R.
 */
public class User {

  private int id;
  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private String email;
  private boolean isCcafsUser;
  private boolean isActive;
  private List<LiaisonInstitution> liaisonInstitution;
  private Date lastLogin;

  public User() {
    super();
  }

  public User(int id) {
    super();
    this.id = id;
  }

  /**
   * This method returns the user's full name.
   * 
   * @return a String that represents the user's full name.
   *         e.g. Héctor Tobón
   */
  public String getComposedCompleteName() {
    return this.firstName + " " + this.lastName;
  }

  /**
   * This method returns a composed way to show a User.
   * 
   * @return a String that represents a User.
   *         e.g. Tobón, Héctor <h.f.tobon@cgiar.org>
   */
  public String getComposedName() {
    if (this.id == -1) {
      return "";
    }
    return this.lastName + ", " + this.firstName + " <" + this.email + ">";
  }

  /**
   * This method returns a composed way to show a User with its institution and its role.
   * 
   * @return a String that represents a User.
   *         e.g. Tobón, Héctor (CIAT) - FPL
   */
  public String getComposedOwnerName() {
    // return this.lastName + ", " + this.firstName + " (" + this.currentInstitution.getAcronym() + ") - "
    // + this.getRole().getAcronym();
    return this.lastName + ", " + this.firstName;
  }


  public String getEmail() {
    return email;
  }

  public String getFirstName() {
    return firstName;
  }

  public int getId() {
    return id;
  }

  public Date getLastLogin() {
    return lastLogin;
  }

  public String getLastName() {
    return lastName;
  }

  public List<LiaisonInstitution> getLiaisonInstitution() {
    return liaisonInstitution;
  }

  public String getPassword() {
    return password;
  }

  public String getUsername() {
    return username;
  }

  public boolean isActive() {
    return isActive;
  }

  public boolean isCcafsUser() {
    return isCcafsUser;
  }

  public void setActive(boolean isActive) {
    this.isActive = isActive;
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

  public void setLastLogin(Date last_login) {
    this.lastLogin = last_login;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setLiaisonInstitution(List<LiaisonInstitution> liaisonInstitution) {
    this.liaisonInstitution = liaisonInstitution;
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

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
