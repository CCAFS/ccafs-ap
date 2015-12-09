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
 * This class represents a Partner that belongs to a project and includes several contact persons.
 * 
 * @author Héctor Fabio Tobón R.
 */
public class ProjectPartner {

  private int id;

  private String overall;

  private Institution institution;


  private List<PartnerPerson> partnerPersons; // List of people working for this partner.

  private List<ProjectPartner> partnerContributors; // CCAFS PPA institutions this project partner is collaborating.

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
      return this.getId() == o.getId();
    }
    return false;
  }

  public int getId() {
    return id;
  }

  public Institution getInstitution() {
    return institution;
  }

  public String getOverall() {
    return overall;
  }

  /**
   * Get the list of CCAFS PPA Project Partners that this partner is collaborating with.
   * 
   * @return a list of PPA Project Partners
   */
  public List<ProjectPartner> getPartnerContributors() {
    return partnerContributors;
  }

  public List<PartnerPerson> getPartnerPersons() {
    return partnerPersons;
  }

  public String getPersonComposedName(int partnerPersonID) {
    if (partnerPersonID <= 0) {
      return "";
    }

    for (PartnerPerson person : partnerPersons) {
      if (person.getId() == partnerPersonID) {
        StringBuilder str = new StringBuilder();
        str.append(person.getUser().getLastName());
        str.append(", ");
        str.append(person.getUser().getFirstName());
        str.append(" <");
        str.append(person.getUser().getEmail());
        str.append(">, ");
        if (institution.getAcronym() != null) {
          str.append(institution.getAcronym());
          str.append(" - ");
        }
        str.append(institution.getName());
        return str.toString();
      }
    }

    return "";
  }

  @Override
  public int hashCode() {
    return this.getId();
  }

  /**
   * This methods validate if the current project partner has a contact person working as coordinator.
   * 
   * @return true if this project partner is coordinating the project. false otherwise.
   */
  public boolean isCoordinator() {
    for (PartnerPerson person : partnerPersons) {
      if (person.getType().equals(APConstants.PROJECT_PARTNER_PC)) {
        return true;
      }
    }
    return false;
  }

  /**
   * This methods validate if the current project partner has a contact person working as leader.
   * 
   * @return true if this project partner is leading the project. false otherwise.
   */
  public boolean isLeader() {
    for (PartnerPerson person : partnerPersons) {
      if (person.isLeader()) {
        return true;
      }
    }
    return false;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setInstitution(Institution institution) {
    this.institution = institution;
  }

  public void setOverall(String overall) {
    this.overall = overall;
  }

  public void setPartnerContributors(List<ProjectPartner> partnerContributors) {
    this.partnerContributors = partnerContributors;
  }

  public void setPartnerPersons(List<PartnerPerson> partnerPersons) {
    this.partnerPersons = partnerPersons;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
