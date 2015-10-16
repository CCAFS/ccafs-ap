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

import org.cgiar.ccafs.ap.config.APConstants;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents the contact name and responsibilities of a partner person within a project.
 * 
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class PartnerPerson {

  private int id;
  private User user;
  private String type;
  private String responsibilities;

  public PartnerPerson() {
  }

  public PartnerPerson(int id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof PartnerPerson) {
      PartnerPerson o = (PartnerPerson) obj;
      return this.getId() == o.getId();
    }
    return false;
  }

  /**
   * This method returns a composed way to show a Project Partner.
   * E.g. Rincon, Silvia <silirincon@madre.lov>
   * 
   * @return a String that represents a Project Partner.
   */
  public String getComposedName() {
    if (this.id == -1) {
      return "";
    }
    StringBuilder str = new StringBuilder();
    str.append(user.getLastName());
    str.append(", ");
    str.append(user.getFirstName());
    str.append(" <");
    str.append(user.getEmail());
    str.append(">");
    return str.toString();

  }

  public int getId() {
    return id;
  }

  public String getResponsibilities() {
    return responsibilities;
  }

  public String getType() {
    return type;
  }

  public User getUser() {
    return user;
  }

  @Override
  public int hashCode() {
    return this.getId();
  }

  public boolean isCoordinator() {
    return type.equals(APConstants.PROJECT_PARTNER_PC);
  }

  public boolean isLeader() {
    return type.equals(APConstants.PROJECT_PARTNER_PL);
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setResponsibilities(String responsibilities) {
    this.responsibilities = responsibilities;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
