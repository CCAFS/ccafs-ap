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

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This object represents a specific user with a specific role inside an institution.
 * 
 * @author Javier Andrés Gallego 
 * @author Héctor Fabio Tobón R.
 *
 */
public class Employee {

  private int id;
  private User user;
  //private Institution institution;
  //private Role role;

  public Employee() {
	  
  }
  
  public Employee(int id, User user) {
    this.id = id;
    this.user = user;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public User getUser() {
	return user;
  }

  public void setUser(User user) {
	this.user = user;
  }

@Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}