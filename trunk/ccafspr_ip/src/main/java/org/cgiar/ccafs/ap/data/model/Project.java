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

import java.util.ArrayList;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Project.
 * 
 * @author Javier Andrés Gallego
 * @author Héctor Tobón
 */
public class Project {

  private int id;
  private String title;
  private String summary;
  private String startDate;
  private String endDate;
  private ArrayList<IPProgram> regions; // The list of regions in which this project works with.
  private ArrayList<IPProgram> flagships; // The list of flagships in which this project works with.
  private Institution leader;
  private Institution owner;
  private ArrayList<Institution> partners; // Project partners.

  public Project() {
    super();
  }

  public String getEndDate() {
    return endDate;
  }

  public ArrayList<IPProgram> getFlagships() {
    return flagships;
  }

  public int getId() {
    return id;
  }

  public Institution getLeader() {
    return leader;
  }

  public Institution getOwner() {
    return owner;
  }

  public ArrayList<Institution> getPartners() {
    return partners;
  }

  public ArrayList<IPProgram> getRegions() {
    return regions;
  }

  public String getStartDate() {
    return startDate;
  }

  public String getSummary() {
    return summary;
  }

  public String getTitle() {
    return title;
  }

  public ArrayList<IPProgram> getTypes() {
    return regions;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public void setFlagships(ArrayList<IPProgram> flagships) {
    this.flagships = flagships;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLeader(Institution leader) {
    this.leader = leader;
  }

  public void setOwner(Institution owner) {
    this.owner = owner;
  }

  public void setPartners(ArrayList<Institution> partners) {
    this.partners = partners;
  }

  public void setRegions(ArrayList<IPProgram> regions) {
    this.regions = regions;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setTypes(ArrayList<IPProgram> types) {
    this.regions = types;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}