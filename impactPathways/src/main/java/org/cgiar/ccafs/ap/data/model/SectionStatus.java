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
 * This class represents the status of a specific project.
 * It will save the list of missing fields that are required to be filled by the system and the idea is that all the
 * information is updated on every save method.
 * 
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */
public class SectionStatus {

  private int id;
  private String section;
  private String cycle;
  private String[] missingFields;
  private int deliverableID;
  private int projectID;


  public SectionStatus() {
  }

  public SectionStatus(String cycle, String section) {
    this.cycle = cycle;
    this.section = section;
  }

  public String getCycle() {
    return cycle;
  }

  public int getDeliverableID() {
    return deliverableID;
  }

  public int getId() {
    return id;
  }

  public String[] getMissingFields() {
    return missingFields;
  }

  /**
   * This method returns all the missing fields separated by a semicolon ";"
   * 
   * @return a String with all the missing fields.
   */
  public String getMissingFieldsWithPrefix() {
    StringBuilder str = new StringBuilder();
    for (String field : this.missingFields) {
      str.append(field);
      str.append(";");
    }
    if (str.length() > 0) {
      str.setLength(str.length() - 1);
    }
    return str.toString();
  }

  public int getProjectID() {
    return projectID;
  }

  public String getSection() {
    return section;
  }


  public void setCycle(String cycle) {
    this.cycle = cycle;
  }

  public void setDeliverableID(int deliverableID) {
    this.deliverableID = deliverableID;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setMissingFields(String missingFields) {
    if (missingFields != null) {
      this.missingFields = missingFields.split(";");
    }
  }

  public void setMissingFields(String[] missingFields) {
    this.missingFields = missingFields;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

  public void setSection(String section) {
    this.section = section;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }


}
