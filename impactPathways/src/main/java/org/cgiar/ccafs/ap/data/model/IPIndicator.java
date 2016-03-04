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

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class IPIndicator {

  private int id;
  private String target;
  private String description;
  private String gender;
  private IPIndicator parent;
  private IPElement outcome;
  private int year;
  private Double archived;
  private String acronym;

  private String archivedText;


  private String narrativeTargets;

  private String narrativeGender;


  private String projectId;

  public IPIndicator() {
  }

  public IPIndicator(int id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof IPIndicator)) {
      return false;
    }

    if (obj == this) {
      return true;
    }

    IPIndicator element = (IPIndicator) obj;
    if (this.id == element.id) {
      return true;
    }

    return false;
  }

  public String getAcronym() {
    return acronym;
  }


  public Double getArchived() {
    return archived;
  }


  public String getArchivedText() {
    return archivedText;
  }


  public String getDescription() {
    return description;
  }


  public String getGender() {
    return gender;
  }


  public int getId() {
    return id;
  }


  public String getNarrativeGender() {
    return narrativeGender;
  }


  public String getNarrativeTargets() {
    return narrativeTargets;
  }


  public IPElement getOutcome() {
    return outcome;
  }

  public IPIndicator getParent() {
    return parent;
  }

  public String getProjectId() {
    return projectId;
  }

  public String getTarget() {
    return target;
  }

  public int getYear() {
    return year;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 31).append(id).toHashCode();
  }

  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }

  public void setArchived(Double archived) {
    this.archived = archived;
  }

  public void setArchivedText(String archivedText) {
    this.archivedText = archivedText;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setNarrativeGender(String narrativeGender) {
    this.narrativeGender = narrativeGender;
  }

  public void setNarrativeTargets(String narrativeTargets) {
    this.narrativeTargets = narrativeTargets;
  }

  public void setOutcome(IPElement outcome) {
    this.outcome = outcome;
  }

  public void setParent(IPIndicator parent) {
    this.parent = parent;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public void setYear(int year) {
    this.year = year;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}