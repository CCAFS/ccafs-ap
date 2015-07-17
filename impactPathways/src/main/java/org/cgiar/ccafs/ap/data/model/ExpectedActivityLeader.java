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

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * This class represents an Activity Leader, which belongs to a specific Activity.
 *
 * @author Javier Andrés Gallego Barona.
 * @author Héctor Fabio Tobón R.
 */
public class ExpectedActivityLeader {

  private int id;
  private Institution institution;
  private String name;
  private String email;

  public ExpectedActivityLeader() {

  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ExpectedActivityLeader) {
      ExpectedActivityLeader a = (ExpectedActivityLeader) obj;
      return a.getId() == this.id;
    }
    return false;
  }

  public String getEmail() {
    return email;
  }

  public int getId() {
    return id;
  }

  public Institution getInstitution() {
    return institution;
  }

  public String getName() {
    return name;
  }

  @Override
  public int hashCode() {
    return this.id;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setInstitution(Institution institution) {
    this.institution = institution;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
