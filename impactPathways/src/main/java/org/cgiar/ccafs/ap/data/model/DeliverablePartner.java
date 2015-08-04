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
 * This class represents a partner that belongs to a deliverable with contact information.
 * 
 * @author Héctor Fabio Tobón R.
 */
public class DeliverablePartner {

  private int id;
  private Institution institution; // TODO to remove.
  private User user; // TODO to remove.
  private ProjectPartner partner;
  private String type; // Resp, Other.


  public DeliverablePartner() {
    super();
  }

  public DeliverablePartner(int id) {
    super();
    this.id = id;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof DeliverablePartner) {
      DeliverablePartner o = (DeliverablePartner) obj;
      return o.id == this.id;
    }
    return false;
  }

  public int getId() {
    return id;
  }

  public Institution getInstitution() {
    return institution;
  }

  public ProjectPartner getPartner() {
    return partner;
  }

  public String getType() {
    return type;
  }

  public User getUser() {
    return user;
  }

  @Override
  public int hashCode() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setInstitution(Institution institution) {
    this.institution = institution;
  }

  public void setPartner(ProjectPartner partner) {
    this.partner = partner;
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
