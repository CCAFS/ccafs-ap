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

public class UserRole {

  private Integer id;

  private String name;

  private String acronym;

  private List<String> permissions;

  public UserRole() {
    super();
  }

  public UserRole(Integer id) {
    super();
    this.id = id;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    UserRole other = (UserRole) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }

  public String getAcronym() {
    return acronym;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<String> getPermissions() {
    return permissions;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setName(String roleName) {
    this.name = roleName;
  }

  public void setPermissions(List<String> permissions) {
    this.permissions = permissions;
  }
}
