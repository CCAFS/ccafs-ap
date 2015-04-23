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
package org.cgiar.ccafs.security.data.model;

import java.util.Date;

/**
 * @author Hernán David Carvajal.
 * @author Héctor Fabio Tobón R.
 */
public class User {

  private int id;
  private String email;
  private String firstName;
  private String lastName;
  private String phone;
  private String password;
  private String username;
  private Date lastLogin;
  private boolean isCcafsUser;

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

  public String getPassword() {
    return password;
  }

  public String getPhone() {
    return phone;
  }

  public String getUsername() {
    return username;
  }

  public boolean isCcafsUser() {
    return isCcafsUser;
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

  public void setLastLogin(Date lastLogin) {
    this.lastLogin = lastLogin;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setUsername(String username) {
    this.username = username;
  }

}
