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
 * This class represents any kind of institution inside the system.
 * An institution can be represented as project partner, activity partner, project leader, etc.
 * 
 * @author Héctor Tobón
 */
public class Institution {
  private int id;
  private String name;
  private String acronym;
  //TODO private Person contactPerson;
  private IPProgram program;
  private InstitutionType type;
  
  public Institution() {
    super();
  }
  
  public Institution(int id, String name, String acronym, IPProgram program, InstitutionType type /*, Person contactPerson*/) {
    super();
    this.id = id;
    this.name = name;
    this.acronym = acronym;
    this.program = program;
    this.type = type;
    //this.contactPerson = contactPerson;
  }

  public int getId() {
    return id;
  }
  
  public void setId(int id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getAcronym() {
    return acronym;
  }
  
  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }
  
  public IPProgram getProgram() {
    return program;
  }
  
  public void setProgram(IPProgram program) {
    this.program = program;
  }
  
  public InstitutionType getType() {
    return type;
  }
  
  public void setType(InstitutionType type) {
    this.type = type;
  } 
  
//  TODO
//  public Person getContactPerson() {
//    return contactPerson;
//  }
//  
//  public void setContactPerson(Person contactPerson) {
//    this.contactPerson = contactPerson;
//  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
  
  
}
