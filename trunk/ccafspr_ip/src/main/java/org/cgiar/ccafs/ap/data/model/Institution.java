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

import org.apache.commons.lang3.StringUtils;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents any kind of institution inside the system.
 * An institution can be represented as project partner, activity partner, project leader, etc.
 *
 * @author Héctor fabio Tobón R.
 */
public class Institution {

  private int id;
  private String name;
  private String acronym;
  private String contactPersonName;
  private String contactPersonEmail;
  private IPProgram program;
  private InstitutionType type;
  private Country country;

  public Institution() {
    super();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Institution) {
      Institution i = (Institution) obj;
      return i.getId() == this.id;
    }
    return false;
  }

  public String getAcronym() {
    return acronym;
  }

  public String getComposedName() {
    if (StringUtils.isEmpty(acronym)) {
      if (country == null) {
        return name;
      } else {
        return name + " - " + country.getName();
      }
    } else {
      if (country == null) {
        return acronym + " - " + name;
      } else {
        return acronym + " - " + name + " - " + country.getName();
      }
    }
  }

  public String getContactPersonEmail() {
    return contactPersonEmail;
  }

  public String getContactPersonName() {
    return contactPersonName;
  }

  public Country getCountry() {
    return country;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public IPProgram getProgram() {
    return program;
  }

  public InstitutionType getType() {
    return type;
  }

  @Override
  public int hashCode() {
    return this.getId();
  }

  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }

  public void setContactPersonEmail(String contactPersonEmail) {
    this.contactPersonEmail = contactPersonEmail;
  }

  public void setContactPersonName(String contactPersonName) {
    this.contactPersonName = contactPersonName;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setProgram(IPProgram program) {
    this.program = program;
  }

  public void setType(InstitutionType type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }


}
