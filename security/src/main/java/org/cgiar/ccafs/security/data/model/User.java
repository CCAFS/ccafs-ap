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

import java.util.List;


/**
 * @author Hernán David Carvajal
 */

public class User {

  private Integer id;
  private String username;
  private String email;
  private boolean active;
  private boolean ccafsUser;
  private String password;
  private List<UserRole> roles;

  public User(int id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public Integer getId() {
    return id;
  }

  public String getPassword() {
    return password;
  }

  public List<UserRole> getRoles() {
    return roles;
  }

  public String getUsername() {
    return username;
  }

  public boolean isActive() {
    return active;
  }

  public boolean isCcafsUser() {
    return ccafsUser;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public void setCcafsUser(boolean ccafsUser) {
    this.ccafsUser = ccafsUser;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRoles(List<UserRole> roles) {
    this.roles = roles;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
