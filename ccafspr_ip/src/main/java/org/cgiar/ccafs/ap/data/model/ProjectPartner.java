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
 * This class represents a Partner that belongs to a project with contact information.
 * 
 * @author Héctor Fabio Tobón R.
 */
public class ProjectPartner {

  private int id;
  private Institution partner;
  private String contactName;
  private String contactEmail;
  private String responsabilities;


  public ProjectPartner() {
    super();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ProjectPartner) {
      ProjectPartner o = (ProjectPartner) obj;
      return o.id == this.id;
    }
    return false;
  }

  public String getComposedName() {
    String composedName = contactName;
    if (contactEmail != null && !contactEmail.isEmpty()) {
      composedName += " <" + contactEmail + "> ";
    }
    return composedName;
  }

  public String getContactEmail() {
    return contactEmail;
  }

  public String getContactName() {
    return contactName;
  }

  public int getId() {
    return id;
  }

  public Institution getPartner() {
    return partner;
  }

  public String getResponsabilities() {
    return responsabilities;
  }

  @Override
  public int hashCode() {
    return id;
  }

  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
  }


  public void setId(int id) {
    this.id = id;
  }

  public void setPartner(Institution partner) {
    this.partner = partner;
  }

  public void setResponsabilities(String responsabilities) {
    this.responsabilities = responsabilities;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
