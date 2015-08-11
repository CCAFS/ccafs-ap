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

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Partner that belongs to a project with contact information.
 * 
 * @author Héctor Fabio Tobón R.
 */
public class ProjectPartner {

  private int id;
  private Institution institution;
  private User user;
  private String responsabilities;
  private String type;
  private List<Institution> contributeInstitutions; // CCAFS PPA institutions this project partner is collaborating.

  public ProjectPartner() {
    super();
  }

  public ProjectPartner(int id) {
    super();
    this.id = id;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ProjectPartner) {
      ProjectPartner o = (ProjectPartner) obj;
      // return o.getInstitution().getId() == this.institution.getId() && o.getUser().getId() == this.user.getId();
      return this.getId() == o.getId();
    }
    return false;
  }

  /**
   * This method returns a composed way to show a Project Partner.
   * E.g. Rincon, Silvia <silirincon@madre.lov> CLO - Santiago de Cali
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
    str.append(">, ");
    if (institution.getAcronym() != null) {
      str.append(institution.getAcronym());
      str.append(" - ");
    }
    str.append(institution.getName());
    return str.toString();

  }

  /**
   * Get the list of CCAFS PPA institutions that this project partner is collaborating with.
   * 
   * @return a list of PPA Institutions
   */
  public List<Institution> getContributeInstitutions() {
    return contributeInstitutions;
  }

  public int getId() {
    return id;
  }

  public Institution getInstitution() {
    return institution;
  }

  public String getResponsabilities() {
    return responsabilities;
  }

  public String getType() {
    return type;
  }

  public User getUser() {
    return user;
  }

  @Override
  public int hashCode() {
    // int hash = 425;
    // hash = (institution.getId() + (user != null ? user.getId() : 1)) * hash;
    // return hash;
    return this.getId();
  }

  /**
   * Validate if a project partner is a PPA Partner.
   * 
   * @return true if is a PPA Partner, false otherwise.
   */
  public boolean isPPA() {
    return this.type.equals(APConstants.PROJECT_PARTNER_PPA);
  }

  public void setContributeInstitutions(List<Institution> contributeInstitutions) {
    this.contributeInstitutions = contributeInstitutions;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setInstitution(Institution institution) {
    this.institution = institution;
  }

  public void setResponsabilities(String responsabilities) {
    this.responsabilities = responsabilities;
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
